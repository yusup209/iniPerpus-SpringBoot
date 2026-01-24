package com.kkp.iniperpus.service;

import com.kkp.iniperpus.model.Book;
import com.kkp.iniperpus.model.Borrower;
import com.kkp.iniperpus.model.Lending;
import com.kkp.iniperpus.model.PresenceRecord;
import com.kkp.iniperpus.repository.BookRepository;
import com.kkp.iniperpus.repository.BorrowerRepository;
import com.kkp.iniperpus.repository.LendingRepository;
import com.kkp.iniperpus.repository.PresenceRecordRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;
    private final LendingRepository lendingRepository;
    private final PresenceRecordRepository presenceRecordRepository;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReportService(BorrowerRepository borrowerRepository, BookRepository bookRepository,
                        LendingRepository lendingRepository, PresenceRecordRepository presenceRecordRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
        this.lendingRepository = lendingRepository;
        this.presenceRecordRepository = presenceRecordRepository;
    }

    /**
     * Generate PDF report for all borrowers
     */
    public byte[] generateBorrowerReport() throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Add title
        Paragraph title = new Paragraph("Borrower Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph dateInfo = new Paragraph("Generated: " + LocalDateTime.now().format(dateTimeFormatter),
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        dateInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(dateInfo);

        document.add(new Paragraph("\n"));

        // Create table
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        // Header cells
        String[] headers = { "ID", "Borrower ID", "Name", "Division", "Photo" };
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setBackgroundColor(new java.awt.Color(211, 211, 211));
            cell.setPadding(8);
            table.addCell(cell);
        }

        // Data rows
        List<Borrower> borrowers = borrowerRepository.findAll();
        for (Borrower borrower : borrowers) {
            table.addCell(borrower.getId().toString());
            table.addCell(borrower.getBorrowerId() != null ? borrower.getBorrowerId() : "-");
            table.addCell(borrower.getName() != null ? borrower.getName() : "-");
            table.addCell(borrower.getDivision() != null ? borrower.getDivision() : "-");
            table.addCell(borrower.getPhotoFilename() != null ? "Yes" : "No");
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

    /**
     * Generate PDF report for all books
     */
    public byte[] generateBookReport() throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Add title
        Paragraph title = new Paragraph("Book Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph dateInfo = new Paragraph("Generated: " + LocalDateTime.now().format(dateTimeFormatter),
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        dateInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(dateInfo);

        document.add(new Paragraph("\n"));

        // Create table
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);

        // Header cells
        String[] headers = { "ID", "Title", "Author", "ISBN", "Publisher", "Year", "Total", "Available" };
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setBackgroundColor(new java.awt.Color(211, 211, 211));
            cell.setPadding(8);
            table.addCell(cell);
        }

        // Data rows
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            table.addCell(book.getId().toString());
            table.addCell(book.getTitle() != null ? book.getTitle() : "-");
            table.addCell(book.getAuthor() != null ? book.getAuthor() : "-");
            table.addCell(book.getIsbn() != null ? book.getIsbn() : "-");
            table.addCell(book.getPublisher() != null ? book.getPublisher() : "-");
            table.addCell(book.getYearPublished() != null ? book.getYearPublished().toString() : "-");
            table.addCell(book.getCopiesTotal() != null ? book.getCopiesTotal().toString() : "0");
            table.addCell(book.getCopiesAvailable() != null ? book.getCopiesAvailable().toString() : "0");
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

    /**
     * Generate PDF report for all lending records
     */
    public byte[] generateLendingReport(LocalDate startDate, LocalDate endDate, String status) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Add title
        Paragraph title = new Paragraph("Lending Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph dateInfo = new Paragraph("Generated: " + LocalDateTime.now().format(dateTimeFormatter),
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        dateInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(dateInfo);

        document.add(new Paragraph("\n"));

        // Create table
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);

        // Header cells
        String[] headers = { "ID", "Borrower", "Book", "Lend Date", "Due Date", "Return Date", "Status", "Days" };
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setBackgroundColor(new java.awt.Color(211, 211, 211));
            cell.setPadding(8);
            table.addCell(cell);
        }

        // Data rows
        List<Lending> lendings = lendingRepository.findAll();
        if (startDate != null) {
            lendings = lendings.stream()
                    .filter(l -> l.getLendDate() != null && !l.getLendDate().isBefore(startDate))
                    .toList();
        }
        if (endDate != null) {
            lendings = lendings.stream()
                    .filter(l -> l.getLendDate() != null && !l.getLendDate().isAfter(endDate))
                    .toList();
        }
        for (Lending lending : lendings) {
            table.addCell(lending.getId().toString());
            table.addCell(lending.getBorrower() != null && lending.getBorrower().getName() != null
                    ? lending.getBorrower().getName() : "-");
            table.addCell(lending.getBook() != null && lending.getBook().getTitle() != null
                    ? lending.getBook().getTitle() : "-");
            table.addCell(lending.getLendDate() != null ? lending.getLendDate().format(dateFormatter) : "-");
            table.addCell(lending.getDueDate() != null ? lending.getDueDate().format(dateFormatter) : "-");
            table.addCell(lending.getReturnDate() != null ? lending.getReturnDate().format(dateFormatter) : "Not Returned");

            // Status
            String rowStatus = "Returned";
            if (lending.getReturnDate() == null) {
                rowStatus = lending.getDueDate() != null && lending.getDueDate().isBefore(LocalDate.now()) ? "Overdue" : "Active";
            }
            table.addCell(rowStatus); 

            // Apply status filter after computing
            if (status != null) {
                String desired = status.trim().toLowerCase();
                String current = rowStatus.toLowerCase();
                // Skip rows that don't match desired status
                if (!desired.isEmpty() && !current.equals(desired)) {
                    continue;
                }
            }

            // Days borrowed
            LocalDate calcEndDate = lending.getReturnDate() != null ? lending.getReturnDate() : LocalDate.now();
            long days = java.time.temporal.ChronoUnit.DAYS.between(lending.getLendDate(), calcEndDate);
            table.addCell(String.valueOf(days));
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

    /**
     * Backward-compatible method without filters
     */
    public byte[] generateLendingReport() throws DocumentException {
        return generateLendingReport(null, null, null);
    }

    /**
     * Generate PDF report for presence records
     */
    public byte[] generatePresenceReport(LocalDate startDate, LocalDate endDate) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Add title
        Paragraph title = new Paragraph("Presence Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph dateInfo = new Paragraph("Generated: " + LocalDateTime.now().format(dateTimeFormatter),
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        dateInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(dateInfo);

        document.add(new Paragraph("\n"));

        // Create table
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        // Header cells
        String[] headers = { "ID", "Borrower", "Timestamp", "Matched", "Status" };
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setBackgroundColor(new java.awt.Color(211, 211, 211));
            cell.setPadding(8);
            table.addCell(cell);
        }

        // Data rows
        List<PresenceRecord> records = presenceRecordRepository.findAll();
        if (startDate != null) {
            records = records.stream()
                    .filter(r -> r.getTimestamp() != null && !r.getTimestamp().toLocalDate().isBefore(startDate))
                    .toList();
        }
        if (endDate != null) {
            records = records.stream()
                    .filter(r -> r.getTimestamp() != null && !r.getTimestamp().toLocalDate().isAfter(endDate))
                    .toList();
        }
        for (PresenceRecord record : records) {
            table.addCell(record.getId().toString());
            table.addCell(record.getBorrower() != null && record.getBorrower().getName() != null
                    ? record.getBorrower().getName() : "-");
            table.addCell(record.getTimestamp() != null ? record.getTimestamp().format(dateTimeFormatter) : "-");
            table.addCell(record.getMatched() != null ? record.getMatched().toString() : "-");
            table.addCell(record.getMatched() != null && record.getMatched() ? "Success" : "Failed");
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

    /** Backward-compatible method without filters */
    public byte[] generatePresenceReport() throws DocumentException {
        return generatePresenceReport(null, null);
    }
}
