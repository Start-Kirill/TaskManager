package by.it_academy.report_service.core.dto;

import by.it_academy.report_service.core.enums.ReportStatus;

public class ReportUpdateDto {

    private ReportStatus status;

    private Integer attempt;

    public ReportUpdateDto() {
    }

    public ReportUpdateDto(ReportStatus status, Integer attempt) {
        this.status = status;
        this.attempt = attempt;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }
}
