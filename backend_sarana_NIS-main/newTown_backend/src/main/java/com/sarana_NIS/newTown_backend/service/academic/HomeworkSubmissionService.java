package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.HomeworkSubmissionRequest;
import com.sarana_NIS.newTown_backend.model.entity.academic.Homework;
import com.sarana_NIS.newTown_backend.model.entity.academic.HomeworkSubmission;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.HomeworkStatus;
import com.sarana_NIS.newTown_backend.repository.HomeworkRepository;
import com.sarana_NIS.newTown_backend.repository.HomeworkSubmissionRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeworkSubmissionService {

    private static final Logger log = LoggerFactory.getLogger(HomeworkSubmissionService.class);

    private final HomeworkSubmissionRepository submissionRepository;
    private final HomeworkRepository homeworkRepository;
    private final StudentRepository studentRepository;

    public HomeworkSubmissionService(HomeworkSubmissionRepository submissionRepository, HomeworkRepository homeworkRepository,
                                     StudentRepository studentRepository) {
        this.submissionRepository = submissionRepository;
        this.homeworkRepository = homeworkRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public HomeworkSubmission submitHomework(HomeworkSubmissionRequest request) {
        Homework homework = homeworkRepository.findById(request.getHomeworkId())
                .orElseThrow(() -> new RuntimeException("Homework not found"));
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        submissionRepository.findByHomeworkAndStudent(request.getHomeworkId(), request.getStudentId())
                .ifPresent(existing -> {
                    throw new RuntimeException("Homework already submitted by this student");
                });

        HomeworkSubmission submission = new HomeworkSubmission();
        submission.setHomework(homework);
        submission.setStudent(student);
        submission.setSubmissionUrl(request.getSubmissionUrl());
        submission.setSubmissionDate(LocalDateTime.now());

        if (homework.getDueDate() != null && LocalDateTime.now().toLocalDate().isAfter(homework.getDueDate())) {
            submission.setStatus(HomeworkStatus.LATE);
        } else {
            submission.setStatus(HomeworkStatus.SUBMITTED);
        }

        HomeworkSubmission saved = submissionRepository.save(submission);
        log.info("Homework submitted by student: {}", student.getNameEnglish());
        return saved;
    }

    @Transactional
    public HomeworkSubmission gradeSubmission(Long submissionId, Double score, String feedback) {
        HomeworkSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        submission.setScore(score);
        submission.setFeedback(feedback);
        submission.setReviewedByTeacher(true);

        HomeworkSubmission saved = submissionRepository.save(submission);
        log.info("Submission graded: {}", submissionId);
        return saved;
    }

    @Transactional
    public HomeworkSubmission markParentReviewed(Long submissionId) {
        HomeworkSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setReviewedByParent(true);
        return submissionRepository.save(submission);
    }

    public HomeworkSubmission getSubmissionById(Long submissionId) {
        return submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + submissionId));
    }

    public List<HomeworkSubmission> getSubmissionsByHomework(Long homeworkId) {
        return submissionRepository.findByHomeworkId(homeworkId);
    }

    public List<HomeworkSubmission> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId);
    }

    public HomeworkSubmission getSubmissionByHomeworkAndStudent(Long homeworkId, Long studentId) {
        return submissionRepository.findByHomeworkAndStudent(homeworkId, studentId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    public List<HomeworkSubmission> getSubmissionsByStatus(HomeworkStatus status) {
        return submissionRepository.findByStatus(status);
    }

    public List<HomeworkSubmission> getPendingTeacherReview() {
        return submissionRepository.findPendingTeacherReview();
    }

    public List<HomeworkSubmission> getPendingParentReview() {
        return submissionRepository.findPendingParentReview();
    }

    public Double getAverageScoreByHomework(Long homeworkId) {
        return submissionRepository.findAverageScoreByHomework(homeworkId);
    }

    public long getSubmissionCountByHomework(Long homeworkId) {
        return submissionRepository.countSubmittedByHomework(homeworkId);
    }

    @Transactional
    public void deleteSubmission(Long submissionId) {
        HomeworkSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submissionRepository.delete(submission);
        log.info("Submission deleted: {}", submissionId);
    }
}