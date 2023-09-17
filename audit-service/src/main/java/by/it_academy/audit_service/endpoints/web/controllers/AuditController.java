package by.it_academy.audit_service.endpoints.web.controllers;

import by.it_academy.task_manager_common.dto.AuditDto;
import by.it_academy.audit_service.dao.entity.Audit;
import by.it_academy.audit_service.service.api.IAuditService;
import by.it_academy.task_manager_common.dto.AuditCreateDto;
import by.it_academy.task_manager_common.dto.CustomPage;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private static final String CONTENT_FIELD_NAME = "content";

    private IAuditService auditService;

    private ConversionService conversionService;

    public AuditController(IAuditService auditService, ConversionService conversionService) {
        this.auditService = auditService;
        this.conversionService = conversionService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYSTEM')")
    @GetMapping
    public ResponseEntity<?> get(@RequestParam(defaultValue = "0", required = false) Integer page,
                                 @RequestParam(defaultValue = "20", required = false) Integer size) {
        CustomPage<Audit> auditCustomPage = this.auditService.getPage(page, size);
        CustomPage<AuditDto> auditDtoCustomPage = new CustomPage<>();
        BeanUtils.copyProperties(auditCustomPage, auditDtoCustomPage, CONTENT_FIELD_NAME);
        List<AuditDto> auditDtos = auditCustomPage.getContent().stream().map(a -> this.conversionService.convert(a, AuditDto.class)).toList();
        auditDtoCustomPage.setContent(auditDtos);
        return ResponseEntity.status(HttpStatus.OK).body(auditDtoCustomPage);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYSTEM')")
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getByUuid(@PathVariable UUID uuid) {
        Audit audit = this.auditService.get(uuid);
        AuditDto auditDto = this.conversionService.convert(audit, AuditDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(auditDto);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody AuditCreateDto dto) {
        this.auditService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
