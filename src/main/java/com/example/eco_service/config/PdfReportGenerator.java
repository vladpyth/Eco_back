package com.example.eco_service.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class PdfReportGenerator {
    private static final float MARGIN = 45f;
    private static final float ROW_HEIGHT = 12f;
    private static final float HEADER_GAP = 14f;
    private static final PDRectangle LANDSCAPE_A4 = new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth());

    public byte[] generateDetailedReportByRegion(List<Map<String, Object>> summaryData) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {
            PDType0Font font;
            try (InputStream fontStream = getClass().getResourceAsStream("/fonts/DejaVuSans.ttf")) {
                if (fontStream != null) {
                    font = PDType0Font.load(document, fontStream);
                } else {
                    font = PDType0Font.load(document, getClass().getResourceAsStream("/fonts/DejaVuSans.ttf"));
                }
            }

            PDPage page = new PDPage(LANDSCAPE_A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float yPosition = pageTop(page);
            float[] cols = columnStarts(page);
            float col1 = cols[0];
            float col2 = cols[1];
            float col3 = cols[2];
            float col4 = cols[3];
            float colWidth = columnWidth(page);

            // Заголовок только на первой странице (по центру)
            writeCenteredText(contentStream, page, font, 13, yPosition,
                "Реестр объектов хранения, захоронения и обезвреживания отходов");
            yPosition -= 16;
            writeCenteredText(contentStream, page, font, 12, yPosition, "(хранение, захоронение)");
            yPosition -= 20;
            String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            writeText(contentStream, font, 9, MARGIN, MARGIN - 8, dateStr);
            yPosition -= 20;

            for (Map<String, Object> region : summaryData) {
                String regionName = (String) region.get("regionName");
                List<Map<String, Object>> objects = (List<Map<String, Object>>) region.get("objects");
                String groupPlaceName = extractGroupPlaceName(objects);

                if (yPosition < 120) {
                    contentStream.close();
                    page = new PDPage(LANDSCAPE_A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = pageTop(page);
                    cols = columnStarts(page);
                    col1 = cols[0];
                    col2 = cols[1];
                    col3 = cols[2];
                    col4 = cols[3];
                    colWidth = columnWidth(page);
                }

                // Сначала область (по центру)
                writeCenteredText(contentStream, page, font, 12, yPosition, getValue(regionName));
                yPosition -= HEADER_GAP;
                writeCenteredText(contentStream, page, font, 10, yPosition, getValue(groupPlaceName));
                yPosition -= HEADER_GAP;

                // Затем заголовки столбцов
                int h1 = drawWrappedText(contentStream, font, 9, col1, yPosition, colWidth - 8, "Наименование объекта");
                int h2 = drawWrappedText(contentStream, font, 9, col2, yPosition, colWidth - 8, "Местонахождение объекта");
                int h3 = drawWrappedText(contentStream, font, 9, col3, yPosition, colWidth - 8, "Наименование собственника");
                int h4 = drawWrappedText(contentStream, font, 9, col4, yPosition, colWidth - 8, "Юридический адрес");
                int headerLines = Math.max(Math.max(h1, h2), Math.max(h3, h4));
                yPosition -= (ROW_HEIGHT * headerLines) + 2;

                if (objects != null && !objects.isEmpty()) {
                    for (Map<String, Object> obj : objects) {
                        if (yPosition < 70) {
                            contentStream.close();
                            page = new PDPage(LANDSCAPE_A4);
                            document.addPage(page);
                            contentStream = new PDPageContentStream(document, page);
                            yPosition = pageTop(page);
                            cols = columnStarts(page);
                            col1 = cols[0];
                            col2 = cols[1];
                            col3 = cols[2];
                            col4 = cols[3];
                            colWidth = columnWidth(page);

                            // На новых страницах без общего заголовка; повторяем только секцию
                            writeCenteredText(contentStream, page, font, 12, yPosition, getValue(regionName));
                            yPosition -= HEADER_GAP;
                            writeCenteredText(contentStream, page, font, 10, yPosition, getValue(groupPlaceName));
                            yPosition -= HEADER_GAP;
                            int nh1 = drawWrappedText(contentStream, font, 9, col1, yPosition, colWidth - 8, "Наименование объекта");
                            int nh2 = drawWrappedText(contentStream, font, 9, col2, yPosition, colWidth - 8, "Местонахождение объекта");
                            int nh3 = drawWrappedText(contentStream, font, 9, col3, yPosition, colWidth - 8, "Наименование собственника");
                            int nh4 = drawWrappedText(contentStream, font, 9, col4, yPosition, colWidth - 8, "Юридический адрес");
                            int nextHeaderLines = Math.max(Math.max(nh1, nh2), Math.max(nh3, nh4));
                            yPosition -= (ROW_HEIGHT * nextHeaderLines) + 2;
                        }

                        int l1 = drawWrappedText(contentStream, font, 8, col1, yPosition, colWidth - 8, getValue(obj.get("objectName")));
                        int l2 = drawWrappedText(contentStream, font, 8, col2, yPosition, colWidth - 8, getValue(obj.get("objectLocation")));
                        int l3 = drawWrappedText(contentStream, font, 8, col3, yPosition, colWidth - 8, getValue(obj.get("ownerName")));
                        int l4 = drawWrappedText(contentStream, font, 8, col4, yPosition, colWidth - 8, getValue(obj.get("companyLocated")));
                        int rowLines = Math.max(Math.max(l1, l2), Math.max(l3, l4));
                        yPosition -= (ROW_HEIGHT * rowLines);
                    }
                } else {
                    int noObjLines = drawWrappedText(contentStream, font, 8, col1, yPosition, colWidth - 8, "Нет объектов в данной области");
                    yPosition -= ROW_HEIGHT * noObjLines;
                }

                yPosition -= HEADER_GAP;
            }

            contentStream.close();

            document.save(baos);
            log.info("PDF generated successfully");

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

    private void writeText(PDPageContentStream contentStream, PDType0Font font, int size,
                           float x, float y, String text) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, size);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void writeCenteredText(PDPageContentStream contentStream, PDPage page, PDType0Font font,
                                   int size, float y, String text) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * size;
        float x = (page.getMediaBox().getWidth() - textWidth) / 2f;
        writeText(contentStream, font, size, x, y, text);
    }

    private float pageTop(PDPage page) {
        return page.getMediaBox().getHeight() - MARGIN;
    }

    /** Стартовые X для 4 колонок, равномерно по ширине страницы. */
    private float[] columnStarts(PDPage page) {
        float usableWidth = page.getMediaBox().getWidth() - (MARGIN * 2);
        float colWidth = usableWidth / 4f;
        return new float[]{
            MARGIN,
            MARGIN + colWidth,
            MARGIN + (2 * colWidth),
            MARGIN + (3 * colWidth)
        };
    }

    private float columnWidth(PDPage page) {
        float usableWidth = page.getMediaBox().getWidth() - (MARGIN * 2);
        return usableWidth / 4f;
    }

    private int drawWrappedText(PDPageContentStream contentStream, PDType0Font font, int size,
                                float x, float y, float maxWidth, String text) throws IOException {
        List<String> lines = wrapLines(font, size, text, maxWidth);
        float lineY = y;
        for (String line : lines) {
            writeText(contentStream, font, size, x, lineY, line);
            lineY -= ROW_HEIGHT;
        }
        return lines.size();
    }

    private List<String> wrapLines(PDType0Font font, int size, String text, float maxWidth) throws IOException {
        String normalized = (text == null ? "—" : text).replace("\r", " ").replace("\n", " ").trim();
        if (normalized.isEmpty()) normalized = "—";
        java.util.ArrayList<String> out = new java.util.ArrayList<>();
        String[] words = normalized.split("\\s+");
        StringBuilder line = new StringBuilder();
        for (String w : words) {
            String candidate = line.length() == 0 ? w : line + " " + w;
            float width = font.getStringWidth(candidate) / 1000f * size;
            if (width <= maxWidth || line.length() == 0) {
                line.setLength(0);
                line.append(candidate);
            } else {
                out.add(line.toString());
                line.setLength(0);
                line.append(w);
            }
        }
        if (line.length() > 0) out.add(line.toString());
        if (out.isEmpty()) out.add("—");
        return out;
    }

    private String extractGroupPlaceName(List<Map<String, Object>> objects) {
        if (objects == null || objects.isEmpty()) return "—";
        Set<String> uniq = new LinkedHashSet<>();
        for (Map<String, Object> obj : objects) {
            Object raw = obj.get("groupPlaceName");
            if (raw == null) continue;
            String v = String.valueOf(raw).trim();
            if (!v.isEmpty() && !"null".equalsIgnoreCase(v)) uniq.add(v);
        }
        if (uniq.isEmpty()) return "—";
        return String.join(", ", uniq);
    }
}