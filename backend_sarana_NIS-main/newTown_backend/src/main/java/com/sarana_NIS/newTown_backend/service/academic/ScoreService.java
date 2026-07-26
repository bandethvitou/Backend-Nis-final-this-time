package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.ScoreRequest;
import com.sarana_NIS.newTown_backend.dto.request.academic.ScoreSubmitRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.ScoreSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.ClassSubject;
import com.sarana_NIS.newTown_backend.model.entity.academic.Score;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.repository.ClassSubjectRepository;
import com.sarana_NIS.newTown_backend.repository.ScoreRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreService {

    private static final Logger log = LoggerFactory.getLogger(ScoreService.class);

    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;
    private final ClassSubjectRepository classSubjectRepository;
    private final UserRepository userRepository;

    public ScoreService(ScoreRepository scoreRepository, StudentRepository studentRepository,
                        ClassSubjectRepository classSubjectRepository, UserRepository userRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
        this.classSubjectRepository = classSubjectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Score enterScore(ScoreRequest request, Long submittedByUserId) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        ClassSubject classSubject = classSubjectRepository.findById(request.getClassSubjectId())
                .orElseThrow(() -> new RuntimeException("Class subject not found"));
        User submittedBy = userRepository.findById(submittedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        scoreRepository.findByStudentClassSubjectTerm(request.getStudentId(), request.getClassSubjectId(), request.getTerm())
                .ifPresent(existing -> {
                    throw new RuntimeException("Scores already exist for this student in this class/term");
                });

        Score score = new Score();
        score.setStudent(student);
        score.setClassSubject(classSubject);
        score.setTerm(request.getTerm());
        score.setHomeworkScore(request.getHomeworkScore());
        score.setGroupWorkScore(request.getGroupWorkScore());
        score.setAttendanceScore(request.getAttendanceScore());
        score.setActivityScore(request.getActivityScore());
        score.setMultipleChoiceScore(request.getMultipleChoiceScore());
        score.setTheoryScore(request.getTheoryScore());
        score.setPracticeScore(request.getPracticeScore());
        score.setComment(request.getComment());
        score.setSubmittedBy(submittedBy);
        score.setSubmittedAt(LocalDateTime.now());

        Score saved = scoreRepository.save(score);
        log.info("Scores entered for student: {}", student.getNameEnglish());
        return saved;
    }

    @Transactional
    public List<Score> submitBulkScores(ScoreSubmitRequest request, Long submittedByUserId) {
        if (request.getScores() == null || request.getScores().isEmpty()) {
            throw new RuntimeException("No scores provided");
        }

        ClassSubject classSubject = classSubjectRepository.findById(request.getClassSubjectId())
                .orElseThrow(() -> new RuntimeException("Class subject not found"));
        User submittedBy = userRepository.findById(submittedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Score> scores = new ArrayList<>();
        for (ScoreSubmitRequest.ScoreEntry entry : request.getScores()) {
            Student student = studentRepository.findById(entry.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + entry.getStudentId()));

            Score score = new Score();
            score.setStudent(student);
            score.setClassSubject(classSubject);
            score.setTerm(request.getTerm());
            score.setHomeworkScore(entry.getHomeworkScore());
            score.setGroupWorkScore(entry.getGroupWorkScore());
            score.setAttendanceScore(entry.getAttendanceScore());
            score.setActivityScore(entry.getActivityScore());
            score.setMultipleChoiceScore(entry.getMultipleChoiceScore());
            score.setTheoryScore(entry.getTheoryScore());
            score.setPracticeScore(entry.getPracticeScore());
            score.setComment(request.getComment());
            score.setSubmittedBy(submittedBy);
            score.setSubmittedAt(LocalDateTime.now());
            scores.add(score);
        }

        List<Score> saved = scoreRepository.saveAll(scores);
        log.info("Bulk scores submitted: {} students", saved.size());
        return saved;
    }

    @Transactional
    public Score updateScore(Long scoreId, ScoreRequest request) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score not found"));

        if (request.getHomeworkScore() != null) score.setHomeworkScore(request.getHomeworkScore());
        if (request.getGroupWorkScore() != null) score.setGroupWorkScore(request.getGroupWorkScore());
        if (request.getAttendanceScore() != null) score.setAttendanceScore(request.getAttendanceScore());
        if (request.getActivityScore() != null) score.setActivityScore(request.getActivityScore());
        if (request.getMultipleChoiceScore() != null) score.setMultipleChoiceScore(request.getMultipleChoiceScore());
        if (request.getTheoryScore() != null) score.setTheoryScore(request.getTheoryScore());
        if (request.getPracticeScore() != null) score.setPracticeScore(request.getPracticeScore());
        if (request.getComment() != null) score.setComment(request.getComment());

        Score updated = scoreRepository.save(score);
        log.info("Scores updated: {}", scoreId);
        return updated;
    }

    @Transactional
    public Score approveScore(Long scoreId, Long approverUserId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score not found"));
        User approver = userRepository.findById(approverUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        score.setApprovedBy(approver);
        score.setApprovedAt(LocalDateTime.now());

        Score saved = scoreRepository.save(score);
        log.info("Scores approved: {}", scoreId);
        return saved;
    }

    public Score getScoreById(Long scoreId) {
        return scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score not found"));
    }

    public List<Score> getScoresByStudent(Long studentId, String term) {
        return scoreRepository.findByStudentAndTerm(studentId, term);
    }

    public List<Score> getScoresByClassSubject(Long classSubjectId, String term) {
        return scoreRepository.findByClassSubjectAndTerm(classSubjectId, term);
    }

    public Score getScoreByStudentClassSubjectTerm(Long studentId, Long classSubjectId, String term) {
        return scoreRepository.findByStudentClassSubjectTerm(studentId, classSubjectId, term)
                .orElseThrow(() -> new RuntimeException("Score not found"));
    }

    public List<Score> getScoresPendingApproval() {
        return scoreRepository.findPendingApproval();
    }

    public ScoreSummaryResponse getScoreSummary(Long classSubjectId, String term) {
        List<Score> scores = scoreRepository.findByClassSubjectAndTerm(classSubjectId, term);

        ScoreSummaryResponse response = new ScoreSummaryResponse();
        response.setTotalStudents(scores.size());

        long submitted = scores.stream().filter(s -> s.getSubmittedAt() != null).count();
        response.setSubmittedStudents((int) submitted);

        if (!scores.isEmpty()) {
            double avg = scores.stream().filter(s -> s.getTotalScore() != null).mapToDouble(Score::getTotalScore).average().orElse(0);
            response.setClassAverage(avg);

            double max = scores.stream().filter(s -> s.getTotalScore() != null).mapToDouble(Score::getTotalScore).max().orElse(0);
            response.setHighestScore(max);

            double min = scores.stream().filter(s -> s.getTotalScore() != null).mapToDouble(Score::getTotalScore).min().orElse(0);
            response.setLowestScore(min);

            long pass = scores.stream().filter(s -> s.getTotalScore() != null && s.getTotalScore() >= 50).count();
            response.setPassCount((int) pass);
            response.setFailCount(scores.size() - (int) pass);
            response.setPassRate((double) pass / scores.size() * 100);
        }

        if (submitted == scores.size() && !scores.isEmpty()) {
            response.setStatus("COMPLETED");
        } else if (submitted > 0) {
            response.setStatus("PARTIAL");
        } else {
            response.setStatus("NOT_STARTED");
        }
        return response;
    }

    @Transactional
    public void deleteScore(Long scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score not found"));
        scoreRepository.delete(score);
        log.info("Score deleted: {}", scoreId);
    }
}