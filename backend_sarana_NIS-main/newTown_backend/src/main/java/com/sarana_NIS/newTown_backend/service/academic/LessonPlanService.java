package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.LessonPlanRequest;
import com.sarana_NIS.newTown_backend.dto.request.academic.LessonPlanSubmitRequest;
import com.sarana_NIS.newTown_backend.model.entity.academic.ClassSubject;
import com.sarana_NIS.newTown_backend.model.entity.academic.LessonPlan;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.LessonPlanStatus;
import com.sarana_NIS.newTown_backend.repository.ClassSubjectRepository;
import com.sarana_NIS.newTown_backend.repository.LessonPlanRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonPlanService {

    private static final Logger log = LoggerFactory.getLogger(LessonPlanService.class);

    private final LessonPlanRepository lessonPlanRepository;
    private final ClassSubjectRepository classSubjectRepository;
    private final UserRepository userRepository;

    public LessonPlanService(LessonPlanRepository lessonPlanRepository, ClassSubjectRepository classSubjectRepository,
                             UserRepository userRepository) {
        this.lessonPlanRepository = lessonPlanRepository;
        this.classSubjectRepository = classSubjectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LessonPlan createLessonPlan(LessonPlanRequest request, Long createdByUserId) {
        ClassSubject classSubject = classSubjectRepository.findById(request.getClassSubjectId())
                .orElseThrow(() -> new RuntimeException("Class subject not found"));
        User createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LessonPlan lessonPlan = new LessonPlan();
        lessonPlan.setClassSubject(classSubject);
        lessonPlan.setDate(request.getDate());
        lessonPlan.setShift(request.getShift());
        lessonPlan.setTopic(request.getTopic());
        lessonPlan.setObjective(request.getObjective());
        lessonPlan.setActivities(request.getActivities());
        lessonPlan.setHomeworkAssigned(request.getHomeworkAssigned());
        lessonPlan.setStatus(LessonPlanStatus.DRAFT);

        LessonPlan saved = lessonPlanRepository.save(lessonPlan);
        log.info("Lesson plan created: {}", saved.getTopic());
        return saved;
    }

    @Transactional
    public LessonPlan submitLessonPlan(LessonPlanSubmitRequest request) {
        LessonPlan lessonPlan = lessonPlanRepository.findById(request.getLessonPlanId())
                .orElseThrow(() -> new RuntimeException("Lesson plan not found"));

        lessonPlan.setStatus(LessonPlanStatus.SUBMITTED_MGR);
        lessonPlan.setSubmittedAt(LocalDateTime.now());

        User submittedBy = userRepository.findById(request.getLessonPlanId()).orElse(null);
        lessonPlan.setSubmittedBy(submittedBy);

        LessonPlan saved = lessonPlanRepository.save(lessonPlan);
        log.info("Lesson plan submitted: {}", saved.getId());
        return saved;
    }

    @Transactional
    public LessonPlan approveLessonPlan(Long lessonPlanId, Long approverUserId) {
        LessonPlan lessonPlan = lessonPlanRepository.findById(lessonPlanId)
                .orElseThrow(() -> new RuntimeException("Lesson plan not found"));
        User approver = userRepository.findById(approverUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        lessonPlan.setStatus(LessonPlanStatus.APPROVED);
        lessonPlan.setApprovedBy(approver);
        lessonPlan.setApprovedAt(LocalDateTime.now());

        LessonPlan saved = lessonPlanRepository.save(lessonPlan);
        log.info("Lesson plan approved: {}", saved.getId());
        return saved;
    }

    @Transactional
    public LessonPlan requestRevision(LessonPlanSubmitRequest request) {
        LessonPlan lessonPlan = lessonPlanRepository.findById(request.getLessonPlanId())
                .orElseThrow(() -> new RuntimeException("Lesson plan not found"));

        lessonPlan.setStatus(LessonPlanStatus.REVISION);
        lessonPlan.setRevisionComment(request.getRevisionComment());

        LessonPlan saved = lessonPlanRepository.save(lessonPlan);
        log.info("Lesson plan revision requested: {}", saved.getId());
        return saved;
    }

    public LessonPlan getLessonPlanById(Long lessonPlanId) {
        return lessonPlanRepository.findById(lessonPlanId)
                .orElseThrow(() -> new RuntimeException("Lesson plan not found"));
    }

    public List<LessonPlan> getLessonPlansByClassSubject(Long classSubjectId) {
        return lessonPlanRepository.findByClassSubjectId(classSubjectId);
    }

    public List<LessonPlan> getLessonPlansByTeacher(Long teacherId) {
        return lessonPlanRepository.findByTeacherId(teacherId);
    }

    public List<LessonPlan> getLessonPlansByClassSubjectAndDate(Long classSubjectId, LocalDate date) {
        return lessonPlanRepository.findByClassSubjectAndDate(classSubjectId, date);
    }

    public List<LessonPlan> getLessonPlansByStatus(LessonPlanStatus status) {
        return lessonPlanRepository.findByStatus(status);
    }

    public List<LessonPlan> getPendingApproval() {
        return lessonPlanRepository.findPendingApproval();
    }

    @Transactional
    public void deleteLessonPlan(Long lessonPlanId) {
        LessonPlan lessonPlan = lessonPlanRepository.findById(lessonPlanId)
                .orElseThrow(() -> new RuntimeException("Lesson plan not found"));
        lessonPlanRepository.delete(lessonPlan);
        log.info("Lesson plan deleted: {}", lessonPlanId);
    }
}