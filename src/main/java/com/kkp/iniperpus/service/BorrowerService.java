package com.kkp.iniperpus.service;

import com.kkp.iniperpus.model.Borrower;
import com.kkp.iniperpus.repository.BorrowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public Borrower findById(Long id) { return borrowerRepository.findById(id).orElse(null); }

    public java.util.List<Borrower> findAll() { return borrowerRepository.findAll(); }

    public Borrower save(Borrower s) { return borrowerRepository.save(s); }

    public Borrower enrollPhoto(Long borrowerId, MultipartFile image, Path storageDir) throws IOException {
        Borrower s = borrowerRepository.findById(borrowerId).orElseThrow(() -> new IllegalArgumentException("borrower not found"));
        if (!Files.exists(storageDir)) Files.createDirectories(storageDir);
        String filename = borrowerId + "_photo" + (image.getOriginalFilename() != null ? ("_" + image.getOriginalFilename()) : "") + ".jpg";
        Path target = storageDir.resolve(filename);
        Files.write(target, image.getBytes());
        s.setPhotoFilename(filename);
        return borrowerRepository.save(s);
    }

    public void delete(Long id) { borrowerRepository.deleteById(id); }
}
