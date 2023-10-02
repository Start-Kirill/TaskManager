package by.it_academy.report_service.endpoints.web.controllers;

import by.it_academy.report_service.core.dto.ReportCreateDto;
import by.it_academy.report_service.core.dto.ReportDto;
import by.it_academy.report_service.core.enums.ReportType;
import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.api.IReportService;
import by.it_academy.task_manager_common.dto.CustomPage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class ReportController {

    private static final String CONTENT_FIELD_NAME = "content";

    private final IReportService reportService;

    private final ConversionService conversionService;

    public ReportController(IReportService reportService, ConversionService conversionService) {
        this.reportService = reportService;
        this.conversionService = conversionService;
    }

    @PostMapping("/{type}")
    public ResponseEntity<?> create(@PathVariable ReportType type, @RequestBody Map<String, String> params) {
        ReportCreateDto reportCreateDto = new ReportCreateDto(type, params);
        this.reportService.save(reportCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "20") Integer size) {
        CustomPage<Report> reportCustomPage = this.reportService.get(page, size);
        CustomPage<ReportDto> reportDtoCustomPage = new CustomPage<>();
        BeanUtils.copyProperties(reportCustomPage, reportDtoCustomPage, CONTENT_FIELD_NAME);
        List<ReportDto> reportDtos = reportCustomPage.getContent().stream().map(r -> this.conversionService.convert(r, ReportDto.class)).toList();
        reportDtoCustomPage.setContent(reportDtos);
        return ResponseEntity.status(HttpStatus.OK).body(reportDtoCustomPage);
    }

    @GetMapping("/{uuid}/export")
    public void get(@PathVariable UUID uuid, HttpServletResponse response) throws IOException {
        String url = this.reportService.getUrl(uuid);
        response.sendRedirect(url);
    }

    @RequestMapping(value = "/{uuid}/export", method = RequestMethod.HEAD)
    public ResponseEntity<?> checkAvailability(@PathVariable UUID uuid) {
        boolean availability = this.reportService.checkAvailability(uuid);
        if (availability) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
