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
import java.io.InputStream;
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

    /**
     * Generate formal PDF letter for a specific lending record
     */
    public byte[] generateLendingLetter(Long lendingId) throws DocumentException {
        Lending lending = lendingRepository.findById(lendingId)
                .orElseThrow(() -> new IllegalArgumentException("Lending record not found"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Letterhead with Logo
        try {
            // Try to load logo from resources
            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/logo.png");
            if (logoStream != null) {
                Image logo = Image.getInstance(logoStream.readAllBytes());
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
                document.add(new Paragraph("\n"));
            } else {
                // Logo placeholder if file not found
                Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
                document.add(logoPlaceholder);
            }
        } catch (Exception e) {
            // Fallback to text placeholder if any error occurs
            Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
            document.add(logoPlaceholder);
        }

        Paragraph letterhead = new Paragraph("iniPerpus Library System", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        letterhead.setAlignment(Element.ALIGN_CENTER);
        document.add(letterhead);

        Paragraph address = new Paragraph("Book Lending Management", 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        address.setAlignment(Element.ALIGN_CENTER);
        document.add(address);

        // Horizontal line
        Paragraph line = new Paragraph("_________________________________________________________________________________________________________");
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);
        document.add(new Paragraph("\n\n"));

        // Document title
        Paragraph title = new Paragraph("BOOK LENDING RECORD", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Date
        Paragraph date = new Paragraph("Date: " + LocalDate.now().format(dateFormatter), 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        date.setAlignment(Element.ALIGN_RIGHT);
        document.add(date);
        document.add(new Paragraph("\n"));

        // Lending details
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        // Borrower information
        Paragraph borrowerSection = new Paragraph();
        borrowerSection.add(new Chunk("Borrower Information:\n", labelFont));
        borrowerSection.add(new Chunk("Name: ", labelFont));
        borrowerSection.add(new Chunk((lending.getBorrower() != null ? lending.getBorrower().getName() : "-") + "\n", valueFont));
        borrowerSection.add(new Chunk("Borrower ID: ", labelFont));
        borrowerSection.add(new Chunk((lending.getBorrower() != null ? lending.getBorrower().getBorrowerId() : "-") + "\n", valueFont));
        borrowerSection.add(new Chunk("Division: ", labelFont));
        borrowerSection.add(new Chunk((lending.getBorrower() != null && lending.getBorrower().getDivision() != null 
                ? lending.getBorrower().getDivision() : "-") + "\n", valueFont));
        document.add(borrowerSection);
        document.add(new Paragraph("\n"));

        // Book information
        Paragraph bookSection = new Paragraph();
        bookSection.add(new Chunk("Book Information:\n", labelFont));
        bookSection.add(new Chunk("Title: ", labelFont));
        bookSection.add(new Chunk((lending.getBook() != null ? lending.getBook().getTitle() : "-") + "\n", valueFont));
        bookSection.add(new Chunk("Author: ", labelFont));
        bookSection.add(new Chunk((lending.getBook() != null ? lending.getBook().getAuthor() : "-") + "\n", valueFont));
        bookSection.add(new Chunk("ISBN: ", labelFont));
        bookSection.add(new Chunk((lending.getBook() != null && lending.getBook().getIsbn() != null 
                ? lending.getBook().getIsbn() : "-") + "\n", valueFont));
        document.add(bookSection);
        document.add(new Paragraph("\n"));

        // Lending details
        Paragraph lendingSection = new Paragraph();
        lendingSection.add(new Chunk("Lending Details:\n", labelFont));
        lendingSection.add(new Chunk("Lend Date: ", labelFont));
        lendingSection.add(new Chunk((lending.getLendDate() != null ? lending.getLendDate().format(dateFormatter) : "-") + "\n", valueFont));
        lendingSection.add(new Chunk("Due Date: ", labelFont));
        lendingSection.add(new Chunk((lending.getDueDate() != null ? lending.getDueDate().format(dateFormatter) : "-") + "\n", valueFont));
        
        // Calculate duration
        if (lending.getLendDate() != null && lending.getDueDate() != null) {
            long duration = java.time.temporal.ChronoUnit.DAYS.between(lending.getLendDate(), lending.getDueDate());
            lendingSection.add(new Chunk("Lending Duration: ", labelFont));
            lendingSection.add(new Chunk(duration + " days\n", valueFont));
        }
        
        // Status
        String status = "Active";
        if (lending.getReturnDate() != null) {
            status = "Returned on " + lending.getReturnDate().format(dateFormatter);
        } else if (lending.getDueDate() != null && lending.getDueDate().isBefore(LocalDate.now())) {
            status = "Overdue";
        }
        lendingSection.add(new Chunk("Status: ", labelFont));
        lendingSection.add(new Chunk(status + "\n", valueFont));
        document.add(lendingSection);
        document.add(new Paragraph("\n\n\n"));

        // Regards section
        Paragraph regards = new Paragraph();
        regards.add(new Chunk("Best Regards,\n\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
        regards.add(new Chunk("Administrator\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
        regards.add(new Chunk("Library Clerk", FontFactory.getFont(FontFactory.HELVETICA, Font.ITALIC, 10)));
        document.add(regards);

        document.close();
        return outputStream.toByteArray();
    }
}
