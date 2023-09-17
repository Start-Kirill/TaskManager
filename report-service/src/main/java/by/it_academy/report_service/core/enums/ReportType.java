package by.it_academy.report_service.core.enums;

import java.time.LocalDateTime;
import java.util.Map;

public enum ReportType {

    JOURNAL_AUDIT("Audit report fro the period from %s to %s");

    private final String descriptionPattern;

    ReportType(String description) {
        this.descriptionPattern = description;
    }

    public String buildDescription(Map<String, String> params) {
        String from = params.get("from");
        String to = params.get("to");
        return this.descriptionPattern.formatted(from, to);
    }

}
