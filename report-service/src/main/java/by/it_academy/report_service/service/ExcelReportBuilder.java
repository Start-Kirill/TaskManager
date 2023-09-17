package by.it_academy.report_service.service;

import by.it_academy.report_service.core.enums.ReportType;
import by.it_academy.report_service.dao.entity.Report;
import by.it_academy.report_service.service.api.IReportBuilder;
import by.it_academy.report_service.utils.JwtTokenHandler;
import by.it_academy.task_manager_common.dto.AuditDto;
import by.it_academy.task_manager_common.dto.CustomPage;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
public class ExcelReportBuilder implements IReportBuilder {

    private static final Integer DEFAULT_COLUMN_WIDTH = 6000;

    private static final Short DEFAULT_HEADER_HEIGHT_FONT_IN_POINTS = 16;

    private static final String DEFAULT_NAME_FONT = "Arial";

    private static final String AUDIT_FIRST_COLUMN_NAME = "Creation date";

    private static final String AUDIT_SECOND_COLUMN_NAME = "User mail";

    private static final String AUDIT_THIRD_COLUMN_NAME = "User name";

    private static final String AUDIT_FOURTH_COLUMN_NAME = "Description";

    private static final String AUDIT_FIFTH_COLUMN_NAME = "Performed essence";

    private static final String AUDIT_SIXTH_COLUMN_NAME = "Id";


    private final JwtTokenHandler tokenHandler;

    private final AuditClientService auditClientService;

    public ExcelReportBuilder(JwtTokenHandler tokenHandler, AuditClientService auditClientService) {
        this.tokenHandler = tokenHandler;
        this.auditClientService = auditClientService;
    }

    @Override
    public byte[] build(Report report) {
        byte[] reportFile = null;
        if (report.getType().equals(ReportType.JOURNAL_AUDIT)) {
            CustomPage<AuditDto> auditDtoCustomPage = this.auditClientService.get(tokenHandler.generateSystemAccessToken());
            List<AuditDto> content = auditDtoCustomPage.getContent();
            reportFile = buildAuditReport(content);
        }


        return reportFile;
    }

    private byte[] buildAuditReport(List<AuditDto> content) {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();

            initSheet(sheet, content);

            Row header = sheet.createRow(0);

            CellStyle defaultHeaderStyle = createDefaultHeaderStyle(workbook);

            fillAuditHeaders(header, defaultHeaderStyle);

            CellStyle casualCellStyle = createCasualCellStyle(workbook);

            fillAuditContent(sheet, content, casualCellStyle);

            return convert(workbook);
        } catch (IOException e) {
//            TODO
            throw new RuntimeException(e);
        }

    }

    private <T> void initSheet(Sheet sheet, Collection<T> content) {
        initSheet(sheet, content, DEFAULT_COLUMN_WIDTH);
    }

    private <T> void initSheet(Sheet sheet, Collection<T> content, Integer width) {
        initColumn(sheet, content.size(), width);
    }

    private void initColumn(Sheet sheet, int quantity, int width) {
        for (int i = 0; i < quantity; i++) {
            sheet.setColumnWidth(i, width);
        }
    }

    private CellStyle createDefaultHeaderStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont defaultHeaderFont = createDefaultHeaderFont(workbook);
        cellStyle.setFont(defaultHeaderFont);
        return cellStyle;
    }

    private CellStyle createCasualCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private XSSFFont createDefaultHeaderFont(Workbook workbook) {
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName(DEFAULT_NAME_FONT);
        font.setFontHeightInPoints(DEFAULT_HEADER_HEIGHT_FONT_IN_POINTS);
        font.setBold(true);
        return font;
    }

    private void fillAuditHeaders(Row headerRow, CellStyle style) {
        for (int i = 0; i < 6; i++) {
            Cell cell = headerRow.createCell(i);
            switch (i) {
                case 0 -> cell.setCellValue(AUDIT_FIRST_COLUMN_NAME);
                case 1 -> cell.setCellValue(AUDIT_SECOND_COLUMN_NAME);
                case 2 -> cell.setCellValue(AUDIT_THIRD_COLUMN_NAME);
                case 3 -> cell.setCellValue(AUDIT_FOURTH_COLUMN_NAME);
                case 4 -> cell.setCellValue(AUDIT_FIFTH_COLUMN_NAME);
                case 5 -> cell.setCellValue(AUDIT_SIXTH_COLUMN_NAME);
            }
            cell.setCellStyle(style);
        }
    }

    private void fillAuditContent(Sheet sheet, List<AuditDto> content, CellStyle style) {
        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(i + 1);

            Cell cell = row.createCell(0);
            cell.setCellValue(content.get(i).getDtCreate());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(content.get(i).getUser().getMail());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(content.get(i).getUser().getFio());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(content.get(i).getText());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(content.get(i).getType().toString());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(content.get(i).getId());
            cell.setCellStyle(style);
        }
    }

    private byte[] convert(Workbook workbook) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
