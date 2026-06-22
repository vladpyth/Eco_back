package com.example.eco_service.routers;


import com.example.eco_service.config.PdfReportGenerator;
import com.example.eco_service.dto.main_dto.WasteReportDto;
import com.example.eco_service.dto.main_dto.WasteRegisterRegionDto;
import com.example.eco_service.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping(value = "/waste/full", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить полный отчет по всем объектам (JSON)")
    public ResponseEntity<List<WasteReportDto>> getFullWasteReport() {
        log.info("REST request to get full waste report");
        List<WasteReportDto> report = reportService.getFullWasteReport();
        return ResponseEntity.ok(report);
    }

    /**
     * Получить детальный реестр по регионам (те же данные, что и в PDF)
     * GET /api/reports/waste/active
     */
    @GetMapping(value = "/waste/active", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить детальный реестр по регионам (JSON, как в PDF)")
    public ResponseEntity<List<WasteRegisterRegionDto>> getActiveWasteReport() {
        log.info("REST request to get detailed waste register as JSON");
        List<WasteRegisterRegionDto> report = reportService.getDetailedSummaryByRegion();
        return ResponseEntity.ok(report);
    }

    /**
     * Получить детальный реестр по регионам (JSON)
     * GET /api/reports/waste/detailed/export/json
     */
    @GetMapping(value = "/waste/detailed/export/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Экспортировать детальный реестр в JSON (как PDF)")
    public ResponseEntity<List<WasteRegisterRegionDto>> exportDetailedReportToJson() {
        log.info("REST request to export detailed waste report to JSON");
        return ResponseEntity.ok(reportService.getDetailedSummaryByRegion());
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
            List<WasteRegisterRegionDto> summary = reportService.getDetailedSummaryByRegion();
            log.info("Retrieved summary data with {} regions", summary.size());

            for (WasteRegisterRegionDto region : summary) {
                log.info("Region: {}, Objects: {}",
                        region.getRegionName(),
                        region.getObjectCount());
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
            List<WasteRegisterRegionDto> summary = reportService.getDetailedSummaryByRegion();
            result.put("regionsCount", summary.size());

            List<Map<String, Object>> debugData = new ArrayList<>();
            for (WasteRegisterRegionDto region : summary) {
                Map<String, Object> regionDebug = new HashMap<>();
                regionDebug.put("regionName", region.getRegionName());
                regionDebug.put("objectCount", region.getObjectCount());
                regionDebug.put("totalWeight", region.getTotalWeight());
                regionDebug.put("totalSquare", region.getTotalSquare());

                if (region.getObjects() != null && !region.getObjects().isEmpty()) {
                    List<Map<String, Object>> objectsDebug = new ArrayList<>();
                    region.getObjects().forEach(obj -> {
                        Map<String, Object> objDebug = new HashMap<>();
                        objDebug.put("objectName", obj.getObjectName());
                        objDebug.put("objectLocation", obj.getObjectLocation());
                        objDebug.put("ownerName", obj.getOwnerName());
                        objDebug.put("companyLocated", obj.getCompanyLocated());
                        objDebug.put("phonesLegal", obj.getPhonesLegal());
                        objDebug.put("phonesOwner", obj.getPhonesOwner());
                        objDebug.put("groupPlaceName", obj.getGroupPlaceName());
                        objDebug.put("status", obj.getStatus());
                        objDebug.put("registrationNumber", obj.getRegistrationNumber());
                        objDebug.put("payerIdentificationNumber", obj.getPayerIdentificationNumber());
                        objDebug.put("startUse", obj.getStartUse());
                        objDebug.put("square", obj.getSquare());
                        objDebug.put("wasteGroups", obj.getWasteGroups());
                        objectsDebug.add(objDebug);
                    });
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
