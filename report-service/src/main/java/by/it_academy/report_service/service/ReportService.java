package by.it_academy.report_service.service;

import by.it_academy.report_service.core.dto.ReportCreateDto;
import by.it_academy.report_service.core.dto.ReportUpdateDto;
import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.core.enums.ReportType;
import by.it_academy.report_service.dao.api.IReportDao;
import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.api.IAuditClientService;
import by.it_academy.report_service.service.api.IMinioService;
import by.it_academy.report_service.service.api.IReportService;
import by.it_academy.report_service.service.api.IUserClientService;
import by.it_academy.report_service.service.exceptions.InvalidReportParamsException;
import by.it_academy.report_service.service.exceptions.ReportNotDoneException;
import by.it_academy.report_service.service.exceptions.ReportNotExistsException;
import by.it_academy.report_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.enums.EssenceType;
import by.it_academy.task_manager_common.exceptions.common.VersionsNotMatchException;
import by.it_academy.task_manager_common.exceptions.commonInternal.GeneratedDataNotCorrectException;
import by.it_academy.task_manager_common.exceptions.commonInternal.InternalServerErrorException;
import by.it_academy.task_manager_common.exceptions.commonInternal.UnknownConstraintException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ReportService implements IReportService {

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "report_pkey";

    private static final String USER_FIELD_PARAM_NAME = "user";

    private static final String FROM_FIELD_PARAM_NAME = "from";

    private static final String TO_FIELD_PARAM_NAME = "to";

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd";

    private final IReportDao reportDao;

    private final IMinioService minioService;

    private final UserHolder userHolder;

    private final ConversionService conversionService;

    private final IAuditClientService auditClientService;

    private final JwtTokenHandler tokenHandler;

    private final IUserClientService userClientService;

    public ReportService(IReportDao reportDao,
                         UserHolder userHolder,
                         ConversionService conversionService,
                         IMinioService minioService,
                         IAuditClientService auditClientService,
                         JwtTokenHandler tokenHandler,
                         IUserClientService userClientService) {
        this.reportDao = reportDao;
        this.userHolder = userHolder;
        this.conversionService = conversionService;
        this.minioService = minioService;
        this.auditClientService = auditClientService;
        this.tokenHandler = tokenHandler;
        this.userClientService = userClientService;
    }


    @Transactional
    @Override
    public Report save(ReportCreateDto dto) {
        validate(dto);

        Report report = this.conversionService.convert(dto, Report.class);
        report.setUuid(UUID.randomUUID());
        LocalDateTime now = LocalDateTime.now();
        report.setDtCreate(now);
        report.setDtUpdate(now);

        try {
            Report save = this.reportDao.saveAndFlush(report);
            createAudit(save, "New report was put in queue for forming");
            return save;
        } catch (DataIntegrityViolationException ex) {
            if (ex.contains(ConstraintViolationException.class)) {
                String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
                if (UNIQUE_UUID_CONSTRAINT_NAME.equals(constraintName)) {
                    throw new GeneratedDataNotCorrectException(List.of(new ErrorResponse(ErrorType.ERROR, "Internal failure of server. Duplicate uuid was generated. Repeat request or contact administrator")));
                }
                throw new UnknownConstraintException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
            }
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        } catch (Exception ex) {
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
    }

    @Transactional
    @Override
    public Report update(ReportUpdateDto reportUpdateDto, UUID uuid, LocalDateTime dtUpdate) {
        validate(reportUpdateDto);

        Report report = this.get(uuid);

        LocalDateTime realVersion = report.getDtUpdate().truncatedTo(ChronoUnit.MILLIS);

        if (!realVersion.equals(dtUpdate.truncatedTo(ChronoUnit.MILLIS))) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "Reports date updates (versions) don't match. Get up-to-date report")));
        }

        report.setStatus(reportUpdateDto.getStatus());
        report.setAttempt(reportUpdateDto.getAttempt());

        try {
            Report save = this.reportDao.saveAndFlush(report);
            createAudit(save, "Report parameters were updated");
            return save;
        } catch (OptimisticLockingFailureException ex) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "User date updates (versions) don't match. Get up-to-date user")));
        } catch (Exception ex) {
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Report get(UUID uuid) {
        if (!this.reportDao.existsById(uuid)) {
            throw new ReportNotExistsException(List.of(new ErrorResponse(ErrorType.ERROR, "Such report does not exist")));
        }
        return this.reportDao.findById(uuid).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public CustomPage<Report> get(Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Report> reportPage = this.reportDao.findAll(pageRequest);

        CustomPage<Report> convert = this.conversionService.convert(reportPage, CustomPage.class);

        convert.setNumber(page);
        convert.setSize(size);

        return convert;
    }

    @Transactional(readOnly = true)
    @Override
    public String getUrl(UUID report) {
        if (!this.checkAvailability(report)) {
            throw new ReportNotDoneException(List.of(new ErrorResponse(ErrorType.ERROR, "Report is not done yet")));
        }

        return this.minioService.getUrl(report);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkAvailability(UUID reportUuid) {
        Report report = this.get(reportUuid);
        return ReportStatus.DONE.equals(report.getStatus());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Report> getByStatus(ReportStatus status) {
        return this.reportDao.findAllByStatus(status);
    }


    private void createAudit(Report report, String message) {
        String token;
        if (this.userHolder.isAuthenticated()) {
            token = this.tokenHandler.generateAccessToken(this.userHolder.getUser());
        } else {
            token = this.tokenHandler.generateSystemAccessToken();
        }
        this.auditClientService.save(
                token,
                report.getUuid().toString(),
                message,
                EssenceType.REPORT
        );
    }


    private void validate(ReportCreateDto dto) {

        ReportType type = dto.getType();
        Map<String, String> params = dto.getParams();

        Map<String, String> errors = new HashMap<>();

        if (ReportType.JOURNAL_AUDIT.equals(type)) {
            String userUuidRaw = params.get(USER_FIELD_PARAM_NAME);
            validateUser(userUuidRaw, errors);

            String fromRaw = params.get(FROM_FIELD_PARAM_NAME);
            LocalDate from = validateDate(fromRaw, FROM_FIELD_PARAM_NAME, errors);

            String toRaw = params.get(TO_FIELD_PARAM_NAME);
            LocalDate to = validateDate(toRaw, TO_FIELD_PARAM_NAME, errors);
            if (from != null && to != null) {
                if (to.isBefore(from)) {
                    errors.put(TO_FIELD_PARAM_NAME, "This date must be after 'From' ");
                }
            }
            if (!errors.isEmpty()) {
                throw new InvalidReportParamsException(errors);
            }
        }


    }

    //    TODO
    private void validate(ReportUpdateDto dto) {

    }

    //    TODO
    private void validate(Integer page, Integer size) {

    }

    private void validateUser(String uuid, Map<String, String> errors) {
        UUID userUuid = null;
        if (uuid == null || uuid.isEmpty()) {
            errors.put(USER_FIELD_PARAM_NAME, "User field is required");
        } else {
            try {
                userUuid = UUID.fromString(uuid);
                List<UserDto> userDtos = this.userClientService.get("Bearer " + tokenHandler.generateSystemAccessToken(), Set.of(userUuid));
                if (userDtos.isEmpty()) {
                    errors.put(USER_FIELD_PARAM_NAME, "Such user does not exist");
                }
            } catch (IllegalArgumentException ex) {
                errors.put(USER_FIELD_PARAM_NAME, "User field is invalid");
            }
        }
    }

    private LocalDate validateDate(String source, String fieldName, Map<String, String> errors) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        LocalDate result = null;
        if (source == null || source.isEmpty()) {
            errors.put(fieldName, fieldName + " field is required");
        } else {
            try {
                result = LocalDate.parse(source, formatter);
            } catch (DateTimeParseException ex) {
                errors.put(fieldName, fieldName + " filed is invalid");
            }
        }
        return result;
    }


}
