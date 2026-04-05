package com.example.eco_service.routers;


import com.example.eco_service.config.PdfReportGenerator;
import com.example.eco_service.dto.main_dto.WasteReportDto;
import com.example.eco_service.services.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final PdfReportGenerator pdfReportGenerator;

    /**
     * Получить полный отчет по всем объектам
     * GET /api/reports/waste/full
     */
    @GetMapping("/waste/full")
    public ResponseEntity<List<WasteReportDto>> getFullWasteReport() {
        log.info("REST request to get full waste report");
        List<WasteReportDto> report = reportService.getFullWasteReport();
        return ResponseEntity.ok(report);
    }

    /**
     * Получить отчет только по активным объектам
     * GET /api/reports/waste/active
     */
    @GetMapping("/waste/active")
    public ResponseEntity<List<WasteReportDto>> getActiveWasteReport() {
        log.info("REST request to get active waste report");
        List<WasteReportDto> report = reportService.getActiveWasteReport();
        return ResponseEntity.ok(report);
    }

    /**
     * Получить отчет по региону
     * GET /api/reports/waste/region?name=Киевская область
     */
    @GetMapping("/waste/region")
    public ResponseEntity<List<WasteReportDto>> getReportByRegion(
            @RequestParam String name) {
        log.info("REST request to get waste report by region: {}", name);
        List<WasteReportDto> report = reportService.getReportByRegion(name);
        return ResponseEntity.ok(report);
    }

    /**
     * Получить отчет по группе отходов
     * GET /api/reports/waste/name-group?name=Органические
     */
    @GetMapping("/waste/name-group")
    public ResponseEntity<List<WasteReportDto>> getReportByNameGroup(
            @RequestParam String name) {
        log.info("REST request to get waste report by name group: {}", name);
        List<WasteReportDto> report = reportService.getReportByNameGroup(name);
        return ResponseEntity.ok(report);
    }

    /**
     * Экспортировать отчет в CSV
     * GET /api/reports/waste/export/csv
     */
    @GetMapping(value = "/waste/export/csv", produces = "text/csv")
    public ResponseEntity<String> exportReportToCsv() {
        log.info("REST request to export waste report to CSV");

        List<WasteReportDto> report = reportService.getFullWasteReport();
        StringBuilder csv = new StringBuilder();

        // Добавляем заголовки
        csv.append("Регион;Группа отходов;Название объекта;Местоположение;Владелец;Эксплуатирующая компания;Статус;Регистрационный номер\n");

        // Добавляем данные
        for (WasteReportDto dto : report) {
            csv.append(escapeCsv(dto.getRegionName())).append(";")
                    .append(escapeCsv(dto.getNameGroup())).append(";")
                    .append(escapeCsv(dto.getObjectName())).append(";")
                    .append(escapeCsv(dto.getObjectLocation())).append(";")
                    .append(escapeCsv(dto.getOwnerName())).append(";")
                    .append(escapeCsv(dto.getLocatedCompany())).append(";")
                    .append(dto.getStatus() != null && dto.getStatus() ? "Активен" : "Неактивен").append(";")
                    .append(escapeCsv(dto.getRegistrationNumber())).append("\n");
        }

        String fileName = "waste_report_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csv.toString());
    }

    /**
     * Экранирование CSV полей
     */
    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(";") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }



    /**
     * Экспортировать детальный отчет по регионам в PDF (реестр)
     * GET /api/reports/waste/detailed/export/pdf
     */
    @GetMapping(value = "/waste/detailed/export/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportDetailedReportToPdf() {
        log.info("REST request to export detailed waste report to PDF");

        try {
            List<Map<String, Object>> summary = reportService.getDetailedSummaryByRegion();
            log.info("Retrieved summary data with {} regions", summary.size());

            for (Map<String, Object> region : summary) {
                log.info("Region: {}, Objects: {}",
                        region.get("regionName"),
                        region.get("objectCount"));
            }

            byte[] pdfBytes = pdfReportGenerator.generateDetailedReportByRegion(summary);

            String fileName = "waste_register_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.setContentType(MediaType.APPLICATION_PDF);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            log.error("Error generating detailed PDF report", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/waste/debug-summary")
    public ResponseEntity<Map<String, Object>> debugSummary() {
        Map<String, Object> result = new HashMap<>();

        try {
            List<Map<String, Object>> summary = reportService.getDetailedSummaryByRegion();
            result.put("regionsCount", summary.size());

            List<Map<String, Object>> debugData = new ArrayList<>();
            for (Map<String, Object> region : summary) {
                Map<String, Object> regionDebug = new HashMap<>();
                regionDebug.put("regionName", region.get("regionName"));
                regionDebug.put("objectCount", region.get("objectCount"));
                regionDebug.put("totalWeight", region.get("totalWeight"));
                regionDebug.put("totalSquare", region.get("totalSquare"));

                List<Map<String, Object>> objects = (List<Map<String, Object>>) region.get("objects");
                if (objects != null && !objects.isEmpty()) {
                    List<Map<String, Object>> objectsDebug = new ArrayList<>();
                    for (Map<String, Object> obj : objects) {
                        Map<String, Object> objDebug = new HashMap<>();
                        objDebug.put("objectName", obj.get("objectName"));
                        objDebug.put("objectLocation", obj.get("objectLocation"));
                        objDebug.put("ownerName", obj.get("ownerName"));
                        objDebug.put("companyLocated", obj.get("companyLocated"));
                        objDebug.put("status", obj.get("status"));
                        objDebug.put("wasteGroups", obj.get("wasteGroups"));
                        objectsDebug.add(objDebug);
                    }
                    regionDebug.put("objects", objectsDebug);
                }
                debugData.add(regionDebug);
            }
            result.put("data", debugData);

        } catch (Exception e) {
            result.put("error", e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.ok(result);
    }


}
