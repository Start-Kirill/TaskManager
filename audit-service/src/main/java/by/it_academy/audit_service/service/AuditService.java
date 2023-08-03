package by.it_academy.audit_service.service;

import by.it_academy.audit_service.dao.api.IAuditDao;
import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.service.api.IAuditService;
import by.it_academy.audit_service.service.exceptions.AuditNotExistsException;
import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.exceptions.CommonInternalErrorException;
import by.it_academy.task_manager_common.exceptions.structured.NotCorrectPageDataException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuditService implements IAuditService {

    private static final String PAGE_FIELD_NAME = "page";

    private static final String SIZE_FIELD_NAME = "size";


    private IAuditDao auditDao;

    private ConversionService conversionService;

    public AuditService(IAuditDao auditDao, ConversionService conversionService) {
        this.auditDao = auditDao;
        this.conversionService = conversionService;
    }

    @Override
    public CustomPage<Audit> getPage(Integer page, Integer size) {

        validate(page, size);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Audit> auditPage = this.auditDao.findAll(pageRequest);
        CustomPage<Audit> auditCustomPage = (CustomPage<Audit>) this.conversionService.convert(auditPage, CustomPage.class);

        auditCustomPage.setNumber(page);
        auditCustomPage.setSize(size);

        return auditCustomPage;
    }

    @Override
    public Audit get(UUID uuid) {
        try {
            return this.auditDao.findById(uuid).orElseThrow();
        } catch (NoSuchElementException ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "Such audit doesn't exist"));
            throw new AuditNotExistsException(errors);
        }
    }


    @Override
    public void create(AuditCreateDto dto) {
        try {
            Audit audit = this.conversionService.convert(dto, Audit.class);
            this.auditDao.save(audit);
        } catch (Exception ex) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator"));
            throw new CommonInternalErrorException(errors);
        }

    }

    private void validate(Integer page, Integer size) {
        Map<String, String> errors = new HashMap<>();

        if (page == null) {
            errors.put(PAGE_FIELD_NAME, "Page is missing");
        } else if (page < 0) {
            errors.put(PAGE_FIELD_NAME, "Page must not to be negative value");
        }

        if (size == null) {
            errors.put(SIZE_FIELD_NAME, "Size is missing");
        } else if (size < 1) {
            errors.put(SIZE_FIELD_NAME, "Size must not to be less than 1");
        }

        if (!errors.isEmpty()) {
            throw new NotCorrectPageDataException(errors);
        }

    }
}
