import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

public class ReportGenerator {
    private static final String[] PERFORMANCE_HEADERS = {"Job ID", "Job Size (Memory Required)", "Burst Time", "Waiting Time", "Turnaround Time"};
    private static final String[] LOG_HEADERS = {"Job ID", "Start Time", "End Time", "Remaining Burst Time", "State"};
    private static final String PERFORMANCE_FILE_NAME = "PerformanceReport.xlsx";
    private static final String LOG_FILE_NAME = "ExecutionLog.xlsx";

    // Generate a single sheet report for performance
    public void generatePerformanceReport(Queue<Job> jobs, String algorithmName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Performance Report");

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < PERFORMANCE_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(PERFORMANCE_HEADERS[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        // Populate data rows
        int rowNum = 1;
        for (Job job : jobs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(job.getPcb().getId());
            row.createCell(1).setCellValue(job.getPcb().getMemoryRequired());
            row.createCell(2).setCellValue(job.getPcb().getBurstTime());
            row.createCell(3).setCellValue(job.getPcb().getWaitingTime());
            row.createCell(4).setCellValue(job.getPcb().getTurnaroundTime());
        }

        // Autosize columns
        for (int i = 0; i < PERFORMANCE_HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write algorithm name at the top of the report
        Row algorithmRow = sheet.createRow(sheet.getLastRowNum() + 2);
        Cell algorithmCell = algorithmRow.createCell(0);
        algorithmCell.setCellValue("Algorithm Used: " + algorithmName);
        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);
        algorithmCell.setCellStyle(boldStyle);

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(PERFORMANCE_FILE_NAME)) {
            workbook.write(fileOut);
            System.out.println("Performance report generated successfully: " + PERFORMANCE_FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error writing Excel report: " + e.getMessage());
        }

        // Close workbook
        try {
            workbook.close();
        } catch (IOException e) {
            System.err.println("Error closing workbook: " + e.getMessage());
        }
    }

    // Generate a single sheet report for detailed logs
    public void generateExecutionLogReport(List<ExecutionLog.ExecutionLogEntry> logEntries, String algorithmName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Execution Log");

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < LOG_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(LOG_HEADERS[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        // Populate data rows
        int rowNum = 1;
        for (ExecutionLog.ExecutionLogEntry entry : logEntries) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getJobId());
            row.createCell(1).setCellValue(entry.getStartTime());
            row.createCell(2).setCellValue(entry.getEndTime());
            row.createCell(3).setCellValue(entry.getRemainingBurstTime());
            row.createCell(4).setCellValue(String.valueOf(entry.getState()));
        }

        // Autosize columns
        for (int i = 0; i < LOG_HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write algorithm name at the top of the report
        Row algorithmRow = sheet.createRow(sheet.getLastRowNum() + 2);
        Cell algorithmCell = algorithmRow.createCell(0);
        algorithmCell.setCellValue("Algorithm Used: " + algorithmName);
        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);
        algorithmCell.setCellStyle(boldStyle);

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(LOG_FILE_NAME)) {
            workbook.write(fileOut);
            System.out.println("Execution log report generated successfully: " + LOG_FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error writing Excel report: " + e.getMessage());
        }

        // Close workbook
        try {
            workbook.close();
        } catch (IOException e) {
            System.err.println("Error closing workbook: " + e.getMessage());
        }
    }
}
