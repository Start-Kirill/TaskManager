package by.it_academy.report_service.service;

import by.it_academy.report_service.core.dto.ReportCreateDto;
import by.it_academy.report_service.core.dto.ReportUpdateDto;
import by.it_academy.report_service.core.enums.ReportStatus;
import by.it_academy.report_service.dao.api.IReportDao;
import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.api.IMinioService;
import by.it_academy.report_service.service.api.IReportService;
import by.it_academy.report_service.service.exceptions.ReportNotDoneException;
import by.it_academy.report_service.service.exceptions.ReportNotExistsException;
import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReportService implements IReportService {

    private static final String UNIQUE_UUID_CONSTRAINT_NAME = "report_pkey";

    private final IReportDao reportDao;

    private final IMinioService minioService;

    private final UserHolder userHolder;

    private final ConversionService conversionService;

    public ReportService(IReportDao reportDao,
                         UserHolder userHolder,
                         ConversionService conversionService,
                         IMinioService minioService) {
        this.reportDao = reportDao;
        this.userHolder = userHolder;
        this.conversionService = conversionService;
        this.minioService = minioService;
    }


    @Override
    public Report save(ReportCreateDto dto) {
        validate(dto);

        Report report = this.conversionService.convert(dto, Report.class);
        report.setUuid(UUID.randomUUID());
        LocalDateTime now = LocalDateTime.now();
        report.setDtCreate(now);
        report.setDtUpdate(now);

        try {
            return this.reportDao.save(report);
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
            return this.reportDao.save(report);
        } catch (OptimisticLockingFailureException ex) {
            throw new VersionsNotMatchException(List.of(new ErrorResponse(ErrorType.ERROR, "User date updates (versions) don't match. Get up-to-date user")));
        } catch (Exception ex) {
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
        }
    }

    @Override
    public Report get(UUID uuid) {
        if (!this.reportDao.existsById(uuid)) {
            throw new ReportNotExistsException(List.of(new ErrorResponse(ErrorType.ERROR, "Such report does not exist")));
        }
        return this.reportDao.findById(uuid).orElseThrow();
    }

    @Override
    public CustomPage<Report> get(Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Report> reportPage = this.reportDao.findAll(pageRequest);

        CustomPage<Report> convert = this.conversionService.convert(reportPage, CustomPage.class);

        convert.setNumber(page);
        convert.setSize(size);

        return convert;
    }

    @Override
    public String getUrl(UUID report) {
        if (!this.checkAvailability(report)) {
            throw new ReportNotDoneException(List.of(new ErrorResponse(ErrorType.ERROR, "Report is not done yet")));
        }

        return this.minioService.getUrl(report);
    }

    @Override
    public boolean checkAvailability(UUID reportUuid) {
        Report report = this.get(reportUuid);
        return ReportStatus.DONE.equals(report.getStatus());
    }

    @Override
    public List<Report> getByStatus(ReportStatus status) {
        return this.reportDao.findAllByStatus(status);
    }

    //    TODO
    private void validate(ReportCreateDto dto) {

    }

    //    TODO
    private void validate(ReportUpdateDto dto) {

    }

    //    TODO
    private void validate(Integer page, Integer size) {

    }

}
