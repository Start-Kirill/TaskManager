package by.it_academy.report_service.core.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportParamAudit {

    private UUID user;

    private LocalDateTime from;

    private LocalDateTime to;

    public ReportParamAudit() {
    }

    public ReportParamAudit(UUID user, LocalDateTime from, LocalDateTime to) {
        this.user = user;
        this.from = from;
        this.to = to;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }
}
