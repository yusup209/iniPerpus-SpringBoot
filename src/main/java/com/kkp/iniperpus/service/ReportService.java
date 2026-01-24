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

        // Letterhead with Logo
        try {
            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/logo.png");
            if (logoStream != null) {
                Image logo = Image.getInstance(logoStream.readAllBytes());
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
                document.add(new Paragraph("\n"));
            } else {
                Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
                document.add(logoPlaceholder);
            }
        } catch (Exception e) {
            Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
            document.add(logoPlaceholder);
        }

        Paragraph letterhead = new Paragraph("iniPerpus Library System", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        letterhead.setAlignment(Element.ALIGN_CENTER);
        document.add(letterhead);

        Paragraph subtitle = new Paragraph("Borrower Management", 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        Paragraph line = new Paragraph("_________________________________________________________________________________________________________");
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);
        document.add(new Paragraph("\n"));

        // Add title
        Paragraph title = new Paragraph("Borrower Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
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

        // Letterhead with Logo
        try {
            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/logo.png");
            if (logoStream != null) {
                Image logo = Image.getInstance(logoStream.readAllBytes());
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
                document.add(new Paragraph("\n"));
            } else {
                Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
                document.add(logoPlaceholder);
            }
        } catch (Exception e) {
            Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
            document.add(logoPlaceholder);
        }

        Paragraph letterhead = new Paragraph("iniPerpus Library System", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        letterhead.setAlignment(Element.ALIGN_CENTER);
        document.add(letterhead);

        Paragraph subtitle = new Paragraph("Book Inventory Management", 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        Paragraph line = new Paragraph("_________________________________________________________________________________________________________");
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);
        document.add(new Paragraph("\n"));

        // Add title
        Paragraph title = new Paragraph("Book Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
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

        // Letterhead with Logo
        try {
            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/logo.png");
            if (logoStream != null) {
                Image logo = Image.getInstance(logoStream.readAllBytes());
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
                document.add(new Paragraph("\n"));
            } else {
                Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
                document.add(logoPlaceholder);
            }
        } catch (Exception e) {
            Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
            document.add(logoPlaceholder);
        }

        Paragraph letterhead = new Paragraph("iniPerpus Library System", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        letterhead.setAlignment(Element.ALIGN_CENTER);
        document.add(letterhead);

        Paragraph subtitle = new Paragraph("Book Lending Management", 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        Paragraph line = new Paragraph("_________________________________________________________________________________________________________");
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);
        document.add(new Paragraph("\n"));

        // Add title
        Paragraph title = new Paragraph("Lending Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
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

        // Letterhead with Logo
        try {
            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/logo.png");
            if (logoStream != null) {
                Image logo = Image.getInstance(logoStream.readAllBytes());
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
                document.add(new Paragraph("\n"));
            } else {
                Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
                document.add(logoPlaceholder);
            }
        } catch (Exception e) {
            Paragraph logoPlaceholder = new Paragraph("[LIBRARY LOGO]", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            logoPlaceholder.setAlignment(Element.ALIGN_CENTER);
            document.add(logoPlaceholder);
        }

        Paragraph letterhead = new Paragraph("iniPerpus Library System", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        letterhead.setAlignment(Element.ALIGN_CENTER);
        document.add(letterhead);

        Paragraph subtitle = new Paragraph("Presence Record Management", 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        Paragraph line = new Paragraph("_________________________________________________________________________________________________________");
        line.setAlignment(Element.ALIGN_CENTER);
        document.add(line);
        document.add(new Paragraph("\n"));

        // Add title
        Paragraph title = new Paragraph("Presence Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
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

        // Header with Logo and Company Info side by side
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1, 3});
        
        // Logo cell
        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        try {
            InputStream logoStream = getClass().getClassLoader().getResourceAsStream("static/ina_armedforces_logo.png");
            if (logoStream != null) {
                Image logo = Image.getInstance(logoStream.readAllBytes());
                logo.scaleToFit(60, 60);
                logoCell.addElement(logo);
            } else {
                logoCell.addElement(new Paragraph("[LOGO]", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            }
        } catch (Exception e) {
            logoCell.addElement(new Paragraph("[LOGO]", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        }
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTable.addCell(logoCell);
        
        // Company info cell
        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        Paragraph companyName = new Paragraph("iniPerpus Library System", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        companyName.setAlignment(Element.ALIGN_CENTER);
        infoCell.addElement(companyName);
        
        Paragraph companyAddress = new Paragraph("Book Lending Management System\nLibrary Administration Office", 
                FontFactory.getFont(FontFactory.HELVETICA, 9));
        companyAddress.setAlignment(Element.ALIGN_CENTER);
        infoCell.addElement(companyAddress);
        infoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTable.addCell(infoCell);
        
        document.add(headerTable);
        
        // Horizontal line separator (Indonesian letter format)
        PdfPTable lineTable = new PdfPTable(1);
        lineTable.setWidthPercentage(100);
        PdfPCell lineCell = new PdfPCell();
        lineCell.setBorder(Rectangle.NO_BORDER);
        lineCell.setBorderWidthBottom(2);
        lineCell.setPaddingTop(5);
        lineCell.setPaddingBottom(5);
        lineTable.addCell(lineCell);
        document.add(lineTable);
        
        document.add(new Paragraph("\n"));

        // Document title
        Paragraph title = new Paragraph("Book Lending Record", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Date aligned to the right
        // Paragraph date = new Paragraph("Date: " + LocalDate.now().format(dateFormatter), 
        //         FontFactory.getFont(FontFactory.HELVETICA, 10));
        // date.setAlignment(Element.ALIGN_RIGHT);
        // document.add(date);
        // document.add(new Paragraph("\n"));

        // Lending details
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        // Borrower information
        PdfPTable borrowerTable = new PdfPTable(2);
        borrowerTable.setWidthPercentage(100);
        borrowerTable.setWidths(new float[]{1, 3});
        
        PdfPCell borrowerHeaderCell = new PdfPCell(new Phrase("Borrower Information:", labelFont));
        borrowerHeaderCell.setColspan(2);
        borrowerHeaderCell.setBorder(Rectangle.NO_BORDER);
        borrowerHeaderCell.setPaddingBottom(5);
        borrowerTable.addCell(borrowerHeaderCell);
        
        PdfPCell nameLabel = new PdfPCell(new Phrase("Name", labelFont));
        nameLabel.setBorder(Rectangle.NO_BORDER);
        nameLabel.setPaddingLeft(10);
        borrowerTable.addCell(nameLabel);
        PdfPCell nameValue = new PdfPCell(new Phrase(": " + (lending.getBorrower() != null ? lending.getBorrower().getName() : "-"), valueFont));
        nameValue.setBorder(Rectangle.NO_BORDER);
        borrowerTable.addCell(nameValue);
        
        PdfPCell idLabel = new PdfPCell(new Phrase("Borrower ID", labelFont));
        idLabel.setBorder(Rectangle.NO_BORDER);
        idLabel.setPaddingLeft(10);
        borrowerTable.addCell(idLabel);
        PdfPCell idValue = new PdfPCell(new Phrase(": " + (lending.getBorrower() != null ? lending.getBorrower().getBorrowerId() : "-"), valueFont));
        idValue.setBorder(Rectangle.NO_BORDER);
        borrowerTable.addCell(idValue);
        
        PdfPCell divisionLabel = new PdfPCell(new Phrase("Division", labelFont));
        divisionLabel.setBorder(Rectangle.NO_BORDER);
        divisionLabel.setPaddingLeft(10);
        borrowerTable.addCell(divisionLabel);
        PdfPCell divisionValue = new PdfPCell(new Phrase(": " + (lending.getBorrower() != null && lending.getBorrower().getDivision() != null 
                ? lending.getBorrower().getDivision() : "-"), valueFont));
        divisionValue.setBorder(Rectangle.NO_BORDER);
        borrowerTable.addCell(divisionValue);
        
        document.add(borrowerTable);
        document.add(new Paragraph("\n"));

        // Book information
        PdfPTable bookTable = new PdfPTable(2);
        bookTable.setWidthPercentage(100);
        bookTable.setWidths(new float[]{1, 3});
        
        PdfPCell bookHeaderCell = new PdfPCell(new Phrase("Book Information:", labelFont));
        bookHeaderCell.setColspan(2);
        bookHeaderCell.setBorder(Rectangle.NO_BORDER);
        bookHeaderCell.setPaddingBottom(5);
        bookTable.addCell(bookHeaderCell);
        
        PdfPCell titleLabel = new PdfPCell(new Phrase("Title", labelFont));
        titleLabel.setBorder(Rectangle.NO_BORDER);
        titleLabel.setPaddingLeft(10);
        bookTable.addCell(titleLabel);
        PdfPCell titleValue = new PdfPCell(new Phrase(": " + (lending.getBook() != null ? lending.getBook().getTitle() : "-"), valueFont));
        titleValue.setBorder(Rectangle.NO_BORDER);
        bookTable.addCell(titleValue);
        
        PdfPCell authorLabel = new PdfPCell(new Phrase("Author", labelFont));
        authorLabel.setBorder(Rectangle.NO_BORDER);
        authorLabel.setPaddingLeft(10);
        bookTable.addCell(authorLabel);
        PdfPCell authorValue = new PdfPCell(new Phrase(": " + (lending.getBook() != null ? lending.getBook().getAuthor() : "-"), valueFont));
        authorValue.setBorder(Rectangle.NO_BORDER);
        bookTable.addCell(authorValue);
        
        PdfPCell isbnLabel = new PdfPCell(new Phrase("ISBN", labelFont));
        isbnLabel.setBorder(Rectangle.NO_BORDER);
        isbnLabel.setPaddingLeft(10);
        bookTable.addCell(isbnLabel);
        PdfPCell isbnValue = new PdfPCell(new Phrase(": " + (lending.getBook() != null && lending.getBook().getIsbn() != null 
                ? lending.getBook().getIsbn() : "-"), valueFont));
        isbnValue.setBorder(Rectangle.NO_BORDER);
        bookTable.addCell(isbnValue);
        
        document.add(bookTable);
        document.add(new Paragraph("\n"));

        // Lending details
        PdfPTable lendingTable = new PdfPTable(2);
        lendingTable.setWidthPercentage(100);
        lendingTable.setWidths(new float[]{1, 3});
        
        PdfPCell lendingHeaderCell = new PdfPCell(new Phrase("Lending Details:", labelFont));
        lendingHeaderCell.setColspan(2);
        lendingHeaderCell.setBorder(Rectangle.NO_BORDER);
        lendingHeaderCell.setPaddingBottom(5);
        lendingTable.addCell(lendingHeaderCell);
        
        PdfPCell lendDateLabel = new PdfPCell(new Phrase("Lend Date", labelFont));
        lendDateLabel.setBorder(Rectangle.NO_BORDER);
        lendDateLabel.setPaddingLeft(10);
        lendingTable.addCell(lendDateLabel);
        PdfPCell lendDateValue = new PdfPCell(new Phrase(": " + (lending.getLendDate() != null ? lending.getLendDate().format(dateFormatter) : "-"), valueFont));
        lendDateValue.setBorder(Rectangle.NO_BORDER);
        lendingTable.addCell(lendDateValue);
        
        PdfPCell dueDateLabel = new PdfPCell(new Phrase("Due Date", labelFont));
        dueDateLabel.setBorder(Rectangle.NO_BORDER);
        dueDateLabel.setPaddingLeft(10);
        lendingTable.addCell(dueDateLabel);
        PdfPCell dueDateValue = new PdfPCell(new Phrase(": " + (lending.getDueDate() != null ? lending.getDueDate().format(dateFormatter) : "-"), valueFont));
        dueDateValue.setBorder(Rectangle.NO_BORDER);
        lendingTable.addCell(dueDateValue);
        
        // Calculate duration
        if (lending.getLendDate() != null && lending.getDueDate() != null) {
            long duration = java.time.temporal.ChronoUnit.DAYS.between(lending.getLendDate(), lending.getDueDate());
            PdfPCell durationLabel = new PdfPCell(new Phrase("Lending Duration", labelFont));
            durationLabel.setBorder(Rectangle.NO_BORDER);
            durationLabel.setPaddingLeft(10);
            lendingTable.addCell(durationLabel);
            PdfPCell durationValue = new PdfPCell(new Phrase(": " + duration + " days", valueFont));
            durationValue.setBorder(Rectangle.NO_BORDER);
            lendingTable.addCell(durationValue);
        }
        
        // Status
        String status = "Active";
        if (lending.getReturnDate() != null) {
            status = "Returned on " + lending.getReturnDate().format(dateFormatter);
        } else if (lending.getDueDate() != null && lending.getDueDate().isBefore(LocalDate.now())) {
            status = "Overdue";
        }
        PdfPCell statusLabel = new PdfPCell(new Phrase("Status", labelFont));
        statusLabel.setBorder(Rectangle.NO_BORDER);
        statusLabel.setPaddingLeft(10);
        lendingTable.addCell(statusLabel);
        PdfPCell statusValue = new PdfPCell(new Phrase(": " + status, valueFont));
        statusValue.setBorder(Rectangle.NO_BORDER);
        lendingTable.addCell(statusValue);
        
        document.add(lendingTable);
        document.add(new Paragraph("\n\n"));

        // Footer with location, date and signature
        String[] months = {"January", "February", "March", "April", "May", "June", 
                          "July", "August", "September", "October", "November", "December"};
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        LocalDate now = LocalDate.now();
        String dayName = days[now.getDayOfWeek().getValue() % 7];
        String monthName = months[now.getMonthValue() - 1];
        
        Paragraph dateTime = new Paragraph(
                String.format("Jakarta, %s, %d %s %d", dayName, now.getDayOfMonth(), monthName, now.getYear()),
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        dateTime.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateTime);
        
        Paragraph onBehalf = new Paragraph("On behalf of Kapusinfolahta TNI", 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        onBehalf.setAlignment(Element.ALIGN_RIGHT);
        document.add(onBehalf);
        
        Paragraph clerkPosition = new Paragraph("The Library", 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        clerkPosition.setAlignment(Element.ALIGN_RIGHT);
        document.add(clerkPosition);
        
        document.add(new Paragraph("\n\n\n"));

        // Signature section
        Paragraph clerkName = new Paragraph("Khane, S.E., M.M.", 
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11));
        clerkName.setAlignment(Element.ALIGN_RIGHT);
        document.add(clerkName);
        
        Paragraph academicDegree = new Paragraph("Mayor Caj NRP 630117", 
                FontFactory.getFont(FontFactory.HELVETICA, 10));
        academicDegree.setAlignment(Element.ALIGN_RIGHT);
        document.add(academicDegree);

        document.close();
        return outputStream.toByteArray();
    }
}
