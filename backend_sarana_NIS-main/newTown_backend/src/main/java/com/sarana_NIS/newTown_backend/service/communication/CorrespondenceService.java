package com.sarana_NIS.newTown_backend.service.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.CorrespondenceActionRequest;
import com.sarana_NIS.newTown_backend.model.entity.communication.Correspondence;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceStatus;
import com.sarana_NIS.newTown_backend.repository.CorrespondenceRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CorrespondenceService {

    private static final Logger log = LoggerFactory.getLogger(CorrespondenceService.class);

    private final CorrespondenceRepository correspondenceRepository;
    private final UserRepository userRepository;

    public CorrespondenceService(CorrespondenceRepository correspondenceRepository, UserRepository userRepository) {
        this.correspondenceRepository = correspondenceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Correspondence createCorrespondence(Correspondence correspondence, Long createdByUserId) {
        User createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Fix: Use setCreatedByUser() instead of setCreatedBy()
        // setCreatedBy() is from BaseEntity and expects Long, not User
        correspondence.setCreatedByUser(createdBy);
        correspondence.setStatus(CorrespondenceStatus.PENDING);
        correspondence.setDate(LocalDate.now());

        Correspondence saved = correspondenceRepository.save(correspondence);
        log.info("Correspondence created: {}", saved.getSubject());
        return saved;
    }

    @Transactional
    public Correspondence replyCorrespondence(CorrespondenceActionRequest request) {
        Correspondence correspondence = correspondenceRepository.findById(request.getCorrespondenceId())
                .orElseThrow(() -> new RuntimeException("Correspondence not found"));

        if (request.getReplyContent() != null) {
            String replyContent = correspondence.getContent() + "\n\n--- Reply ---\n" + request.getReplyContent();
            correspondence.setContent(replyContent);
        }
        correspondence.setStatus(CorrespondenceStatus.REPLIED);

        Correspondence saved = correspondenceRepository.save(correspondence);
        log.info("Correspondence replied: {}", request.getCorrespondenceId());
        return saved;
    }

    @Transactional
    public Correspondence closeCorrespondence(CorrespondenceActionRequest request) {
        Correspondence correspondence = correspondenceRepository.findById(request.getCorrespondenceId())
                .orElseThrow(() -> new RuntimeException("Correspondence not found"));
        correspondence.setStatus(CorrespondenceStatus.CLOSED);
        Correspondence saved = correspondenceRepository.save(correspondence);
        log.info("Correspondence closed: {}", request.getCorrespondenceId());
        return saved;
    }

    @Transactional
    public Correspondence forwardCorrespondence(CorrespondenceActionRequest request) {
        Correspondence correspondence = correspondenceRepository.findById(request.getCorrespondenceId())
                .orElseThrow(() -> new RuntimeException("Correspondence not found"));

        if (request.getForwardedTo() != null) {
            correspondence.setFromTo("Forwarded to: " + request.getForwardedTo());
        }

        Correspondence saved = correspondenceRepository.save(correspondence);
        log.info("Correspondence forwarded: {}", request.getCorrespondenceId());
        return saved;
    }

    public Correspondence getCorrespondenceById(Long correspondenceId) {
        return correspondenceRepository.findById(correspondenceId)
                .orElseThrow(() -> new RuntimeException("Correspondence not found"));
    }

    public List<Correspondence> getCorrespondenceByType(String type) {
        return correspondenceRepository.findByType(
                com.sarana_NIS.newTown_backend.model.enums.CorrespondenceType.valueOf(type)
        );
    }

    public List<Correspondence> getCorrespondenceByStatus(String status) {
        return correspondenceRepository.findByStatus(
                com.sarana_NIS.newTown_backend.model.enums.CorrespondenceStatus.valueOf(status)
        );
    }

    public List<Correspondence> getCorrespondenceByDate(LocalDate date) {
        return correspondenceRepository.findByDate(date);
    }

    public List<Correspondence> getCorrespondenceByDateRange(LocalDate startDate, LocalDate endDate) {
        return correspondenceRepository.findByDateBetween(startDate, endDate);
    }

    public List<Correspondence> getPendingCorrespondence() {
        return correspondenceRepository.findPendingCorrespondence();
    }

    public List<Correspondence> getHighPriorityPending() {
        return correspondenceRepository.findHighPriorityPending();
    }

    public List<Correspondence> searchCorrespondence(String keyword) {
        return correspondenceRepository.searchCorrespondence(keyword);
    }

    public long countPendingCorrespondence() {
        return correspondenceRepository.countPendingCorrespondence();
    }

    @Transactional
    public void deleteCorrespondence(Long correspondenceId) {
        Correspondence correspondence = correspondenceRepository.findById(correspondenceId)
                .orElseThrow(() -> new RuntimeException("Correspondence not found"));
        correspondenceRepository.delete(correspondence);
        log.info("Correspondence deleted: {}", correspondenceId);
    }
}