package com.kkp.iniperpus.service;

import com.kkp.iniperpus.model.PresenceRecord;
import com.kkp.iniperpus.model.Borrower;
import com.kkp.iniperpus.repository.PresenceRecordRepository;
import com.kkp.iniperpus.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PresenceService {

    private final StudentRepository borrowerRepository;
    private final PresenceRecordRepository presenceRecordRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${face.service.url}")
    private String faceServiceUrl;

    public PresenceService(StudentRepository borrowerRepository, PresenceRecordRepository presenceRecordRepository) {
        this.borrowerRepository = borrowerRepository;
        this.presenceRecordRepository = presenceRecordRepository;
    }

    public Map<String, Object> enroll(String borrowerId, MultipartFile image) throws Exception {
        String url = faceServiceUrl + "/enroll";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("student_id", borrowerId);
        ByteArrayResource contents = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() { return image.getOriginalFilename() == null ? "image.jpg" : image.getOriginalFilename(); }
        };
        body.add("image", contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> resp = restTemplate.postForEntity(url, requestEntity, Map.class);
        return resp.getBody();
    }

    public Map<String, Object> match(MultipartFile image) throws Exception {
        String url = faceServiceUrl + "/match";
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource contents = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() { return image.getOriginalFilename() == null ? "image.jpg" : image.getOriginalFilename(); }
        };
        body.add("image", contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> resp = restTemplate.postForEntity(url, requestEntity, Map.class);
        Map<String, Object> result = resp.getBody();

        // persist presence record if matched to a borrower
        boolean matched = result != null && Boolean.TRUE.equals(result.get("matched"));
        String sid = result != null ? (String) result.get("student_id") : null;
        Borrower s = null;
        if (sid != null) s = borrowerRepository.findByStudentId(sid);

        PresenceRecord pr = new PresenceRecord();
        pr.setTimestamp(LocalDateTime.now());
        pr.setMatched(matched);
        if (s != null) pr.setStudent(s);
        presenceRecordRepository.save(pr);

        return result;
    }

    public void deleteFaceData(String borrowerId) {
        try {
            String url = faceServiceUrl + "/student/" + borrowerId;
            restTemplate.delete(url);
        } catch (Exception e) {
            // Log error but don't fail the borrower deletion
            System.err.println("Failed to delete face data for borrower " + borrowerId + ": " + e.getMessage());
        }
    }
}
