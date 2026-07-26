package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.HomeworkRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.HomeworkSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.ClassSubject;
import com.sarana_NIS.newTown_backend.model.entity.academic.Homework;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.repository.ClassSubjectRepository;
import com.sarana_NIS.newTown_backend.repository.HomeworkRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class HomeworkService {

    private static final Logger log = LoggerFactory.getLogger(HomeworkService.class);

    private final HomeworkRepository homeworkRepository;
    private final ClassSubjectRepository classSubjectRepository;
    private final UserRepository userRepository;

    public HomeworkService(HomeworkRepository homeworkRepository, ClassSubjectRepository classSubjectRepository, UserRepository userRepository) {
        this.homeworkRepository = homeworkRepository;
        this.classSubjectRepository = classSubjectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Homework createHomework(HomeworkRequest request, Long assignedByUserId) {
        ClassSubject classSubject = classSubjectRepository.findById(request.getClassSubjectId())
                .orElseThrow(() -> new RuntimeException("Class subject not found"));
        User assignedBy = userRepository.findById(assignedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Homework homework = new Homework();
        homework.setTitle(request.getTitle());
        homework.setDescription(request.getDescription());
        homework.setClassSubject(classSubject);
        homework.setAssignedBy(assignedBy);
        homework.setDueDate(request.getDueDate());
        homework.setAttachmentUrl(request.getAttachmentUrl());
        homework.setStatus("PENDING");

        Homework saved = homeworkRepository.save(homework);
        log.info("Homework created: {}", saved.getTitle());
        return saved;
    }

    @Transactional
    public Homework updateHomework(Long homeworkId, HomeworkRequest request) {
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));

        if (request.getTitle() != null) homework.setTitle(request.getTitle());
        if (request.getDescription() != null) homework.setDescription(request.getDescription());
        if (request.getDueDate() != null) homework.setDueDate(request.getDueDate());
        if (request.getAttachmentUrl() != null) homework.setAttachmentUrl(request.getAttachmentUrl());

        Homework updated = homeworkRepository.save(homework);
        log.info("Homework updated: {}", updated.getTitle());
        return updated;
    }

    public Homework getHomeworkById(Long homeworkId) {
        return homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
    }

    public List<Homework> getHomeworkByClassSubject(Long classSubjectId) {
        return homeworkRepository.findByClassSubjectId(classSubjectId);
    }

    public List<Homework> getHomeworkByTeacher(Long teacherId) {
        return homeworkRepository.findByTeacherId(teacherId);
    }

    public List<Homework> getUpcomingHomework(Long classSubjectId) {
        return homeworkRepository.findUpcomingByClassSubject(classSubjectId);
    }

    public List<Homework> getOverdueHomework(Long classSubjectId) {
        return homeworkRepository.findOverdueByClassSubject(classSubjectId);
    }

    public List<Homework> getHomeworkByDateRange(LocalDate startDate, LocalDate endDate) {
        return homeworkRepository.findByDueDateBetween(startDate, endDate);
    }

    public HomeworkSummaryResponse getHomeworkSummary(Long classSubjectId) {
        List<Homework> homeworks = homeworkRepository.findByClassSubjectId(classSubjectId);

        HomeworkSummaryResponse response = new HomeworkSummaryResponse();
        response.setTotalHomework(homeworks.size());

        long pending = homeworks.stream().filter(h -> "PENDING".equals(h.getStatus())).count();
        long submitted = homeworks.stream().filter(h -> "SUBMITTED".equals(h.getStatus())).count();
        long late = homeworks.stream().filter(h -> "LATE".equals(h.getStatus())).count();

        response.setPendingHomework((int) pending);
        response.setSubmittedHomework((int) submitted);
        response.setLateHomework((int) late);
        return response;
    }

    @Transactional
    public void deleteHomework(Long homeworkId) {
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
        homeworkRepository.delete(homework);
        log.info("Homework deleted: {}", homeworkId);
    }
}