package com.highway.lottery.common.util;

import com.highway.lottery.modules.ticket.dto.TicketNumberDto;
import com.highway.lottery.modules.ticket.dto.TicketResponse;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.context.annotation.Configuration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Configuration
public class TicketPdfGenerator {

    public byte[] generateTicketPdf(TicketResponse ticket) {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            // Title
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            document.add(new Paragraph("Lottery Ticket", titleFont));
            document.add(new Paragraph(" ")); // empty line

            // Ticket Info
            document.add(new Paragraph("Ticket Code: " + ticket.getTicketCode()));
            document.add(new Paragraph("Agent Code: " + ticket.getAgentCode()));
            document.add(new Paragraph("Created At: " + ticket.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            document.add(new Paragraph(" "));

            // Table for Ticket Numbers
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 2});
            table.addCell("Number");
            table.addCell("Amount (MMK)");

            for (TicketNumberDto num : ticket.getTicketNumbers()) {
                table.addCell(num.getNumber());
                table.addCell(String.valueOf(num.getAmount()));
            }

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Amount: " + ticket.getTotalAmount() + " MMK"));

            // 🔐 Embed QR Code
            String qrContent = "Ticket_Code"+ ticket.getTicketCode();
            BufferedImage bufferedQR =QrCodeGeneratorService.generateQRCodeImage(qrContent,200,200);
            ByteArrayOutputStream qrBaos = new ByteArrayOutputStream();
            ImageIO.write(bufferedQR, "png", qrBaos);
            Image qrImage = Image.getInstance(qrBaos.toByteArray());
            qrImage.scaleToFit(120, 120);
            document.add(new Paragraph("Scan QR for verification:"));
            document.add(qrImage);

            document.close();
            return outputStream.toByteArray();
        }catch (Exception e){
            throw new RuntimeException("Error generating PDF", e);

        }
    }
}
