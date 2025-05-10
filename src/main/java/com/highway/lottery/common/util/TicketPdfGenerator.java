package com.highway.lottery.common.util;

public class TicketPdfGenerator {
 /*
    public static byte[] generateTicketPdf(String ticketCode, String agentCode) throws IOException, WriterException {
        String qrContent = "TICKET:" + ticketCode + "|AGENT:" + agentCode;
        BufferedImage qrImage = generateQrCode(qrContent, 200, 200);

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(pdfOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Ticket Confirmation"));
        document.add(new Paragraph("Ticket Code: " + ticketCode));
        document.add(new Paragraph("Agent Code: " + agentCode));

        ByteArrayOutputStream qrStream = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(qrImage, "PNG", qrStream);

        Image qr = new Image(ImageDataFactory.create(qrStream.toByteArray()));
        document.add(qr);

        document.close();
        return pdfOutputStream.toByteArray();
    }

    private static BufferedImage generateQrCode(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

  */
}
