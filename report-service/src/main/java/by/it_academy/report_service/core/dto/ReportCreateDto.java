package by.it_academy.report_service.core.dto;

import by.it_academy.report_service.core.enums.ReportType;

import java.util.Map;

public class ReportCreateDto {

    private ReportType type;

    private Map<String, String> params;

    public ReportCreateDto() {
    }

    public ReportCreateDto(ReportType type, Map<String, String> params) {
        this.type = type;
        this.params = params;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
