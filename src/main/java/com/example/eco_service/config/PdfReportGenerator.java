package com.example.eco_service.config;

import com.example.eco_service.dto.main_dto.WasteReportDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PdfReportGenerator {
    public byte[] generateDetailedReportByRegion(List<Map<String, Object>> summaryData) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {

            // Загружаем шрифт
            PDType0Font font;
            try (InputStream fontStream = getClass().getResourceAsStream("/fonts/DejaVuSans.ttf")) {
                if (fontStream != null) {
                    font = PDType0Font.load(document, fontStream);
                    log.info("Font loaded successfully");
                } else {
                    log.warn("Custom font not found, using default");
                    font = PDType0Font.load(document, getClass().getResourceAsStream("/fonts/DejaVuSans.ttf"));
                }
            }

            // Создаем первую страницу
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 50;
            float yPosition = PDRectangle.A4.getHeight() - margin;

            // Заголовок
            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("РЕЕСТР ОБЪЕКТОВ РАЗМЕЩЕНИЯ ОТХОДОВ");
            contentStream.endText();
            yPosition -= 20;

            // Подзаголовок
            contentStream.beginText();
            contentStream.setFont(font, 11);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("(хранение, захоронение)");
            contentStream.endText();
            yPosition -= 15;

            // Дата
            String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            contentStream.beginText();
            contentStream.setFont(font, 9);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Дата формирования: " + dateStr);
            contentStream.endText();
            yPosition -= 20;

            int totalRecords = 0;

            // Выводим данные
            for (Map<String, Object> region : summaryData) {
                String regionName = (String) region.get("regionName");
                Long objectCount = (Long) region.get("objectCount");
                Double totalWeight = (Double) region.get("totalWeight");
                Double totalSquare = (Double) region.get("totalSquare");
                List<Map<String, Object>> objects = (List<Map<String, Object>>) region.get("objects");

                if (objects != null) {
                    totalRecords += objects.size();
                }

                // Проверяем место на странице
                if (yPosition < 100) {
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = PDRectangle.A4.getHeight() - margin;
                }

                // Название региона
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(regionName != null ? regionName : "—");
                contentStream.endText();
                yPosition -= 15;

                // Статистика
                contentStream.beginText();
                contentStream.setFont(font, 9);
                contentStream.newLineAtOffset(margin + 10, yPosition);
                contentStream.showText("Количество объектов: " + objectCount);
                contentStream.endText();
                yPosition -= 12;

                contentStream.beginText();
                contentStream.setFont(font, 9);
                contentStream.newLineAtOffset(margin + 10, yPosition);
                contentStream.showText(String.format("Общая масса отходов: %,.2f тонн/год", totalWeight));
                contentStream.endText();
                yPosition -= 12;

                contentStream.beginText();
                contentStream.setFont(font, 9);
                contentStream.newLineAtOffset(margin + 10, yPosition);
                contentStream.showText(String.format("Общая площадь: %,.2f м²", totalSquare));
                contentStream.endText();
                yPosition -= 15;

                // Объекты
                if (objects != null && !objects.isEmpty()) {
                    contentStream.beginText();
                    contentStream.setFont(font, 10);
                    contentStream.newLineAtOffset(margin + 5, yPosition);
                    contentStream.showText("Объекты:");
                    contentStream.endText();
                    yPosition -= 12;

                    for (Map<String, Object> obj : objects) {
                        if (yPosition < 80) {
                            contentStream.close();
                            page = new PDPage(PDRectangle.A4);
                            document.addPage(page);
                            contentStream = new PDPageContentStream(document, page);
                            yPosition = PDRectangle.A4.getHeight() - margin;
                        }

                        contentStream.beginText();
                        contentStream.setFont(font, 9);
                        contentStream.newLineAtOffset(margin + 15, yPosition);
                        contentStream.showText("• " + getValue(obj.get("objectName")));
                        contentStream.endText();
                        yPosition -= 10;

                        contentStream.beginText();
                        contentStream.setFont(font, 9);
                        contentStream.newLineAtOffset(margin + 20, yPosition);
                        contentStream.showText("Местоположение: " + getValue(obj.get("objectLocation")));
                        contentStream.endText();
                        yPosition -= 10;

                        contentStream.beginText();
                        contentStream.setFont(font, 9);
                        contentStream.newLineAtOffset(margin + 20, yPosition);
                        contentStream.showText("Владелец: " + getValue(obj.get("ownerName")));
                        contentStream.endText();
                        yPosition -= 10;

                        contentStream.beginText();
                        contentStream.setFont(font, 9);
                        contentStream.newLineAtOffset(margin + 20, yPosition);
                        contentStream.showText("Эксплуатирующая организация: " + getValue(obj.get("companyLocated")));
                        contentStream.endText();
                        yPosition -= 10;

                        contentStream.beginText();
                        contentStream.setFont(font, 9);
                        contentStream.newLineAtOffset(margin + 20, yPosition);
                        contentStream.showText("Статус: " + getValue(obj.get("status")));
                        contentStream.endText();
                        yPosition -= 10;

                        contentStream.beginText();
                        contentStream.setFont(font, 9);
                        contentStream.newLineAtOffset(margin + 20, yPosition);
                        contentStream.showText("Группы отходов: " + getValue(obj.get("wasteGroups")));
                        contentStream.endText();
                        yPosition -= 12;
                    }
                } else {
                    contentStream.beginText();
                    contentStream.setFont(font, 9);
                    contentStream.newLineAtOffset(margin + 15, yPosition);
                    contentStream.showText("Нет объектов в данном регионе");
                    contentStream.endText();
                    yPosition -= 10;
                }

                yPosition -= 10;
            }

            // Итог
            yPosition -= 15;
            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Всего записей: " + totalRecords);
            contentStream.endText();

            contentStream.close();

            document.save(baos);
            log.info("PDF generated successfully with {} total records", totalRecords);

        } catch (Exception e) {
            log.error("Error generating PDF report", e);
            throw new IOException("Failed to generate PDF report: " + e.getMessage(), e);
        }

        return baos.toByteArray();
    }

    private String getValue(Object value) {
        if (value == null) return "—";
        String str = String.valueOf(value);
        return str.isEmpty() || str.equals("null") ? "—" : str;
    }
}