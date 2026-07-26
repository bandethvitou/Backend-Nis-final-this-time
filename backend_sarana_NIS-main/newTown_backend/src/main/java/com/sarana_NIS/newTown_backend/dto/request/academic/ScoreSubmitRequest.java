package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class ScoreSubmitRequest {

    @Size(max = 20, message = "Term must be less than 20 characters")
    private String term;

    @NotNull(message = "Class Subject ID is required")
    private Long classSubjectId;

    @NotEmpty(message = "At least one score is required")
    private List<ScoreEntry> scores = new ArrayList<>();

    @Size(max = 500, message = "Comment must be less than 500 characters")
    private String comment;

    // Constructors
    public ScoreSubmitRequest() {}

    public ScoreSubmitRequest(Long classSubjectId, String term) {
        this.classSubjectId = classSubjectId;
        this.term = term;
    }

    // Getters and Setters
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public Long getClassSubjectId() { return classSubjectId; }
    public void setClassSubjectId(Long classSubjectId) { this.classSubjectId = classSubjectId; }

    public List<ScoreEntry> getScores() { return scores; }
    public void setScores(List<ScoreEntry> scores) { this.scores = scores; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    // Inner class
    public static class ScoreEntry {
        @NotNull(message = "Student ID is required")
        private Long studentId;

        @Min(value = 0, message = "Homework score must be between 0 and 10")
        @Max(value = 10, message = "Homework score must be between 0 and 10")
        private Double homeworkScore;

        @Min(value = 0, message = "Group work score must be between 0 and 10")
        @Max(value = 10, message = "Group work score must be between 0 and 10")
        private Double groupWorkScore;

        @Min(value = 0, message = "Attendance score must be between 0 and 10")
        @Max(value = 10, message = "Attendance score must be between 0 and 10")
        private Double attendanceScore;

        @Min(value = 0, message = "Activity score must be between 0 and 10")
        @Max(value = 10, message = "Activity score must be between 0 and 10")
        private Double activityScore;

        @Min(value = 0, message = "Multiple choice score must be between 0 and 20")
        @Max(value = 20, message = "Multiple choice score must be between 0 and 20")
        private Double multipleChoiceScore;

        @Min(value = 0, message = "Theory score must be between 0 and 20")
        @Max(value = 20, message = "Theory score must be between 0 and 20")
        private Double theoryScore;

        @Min(value = 0, message = "Practice score must be between 0 and 20")
        @Max(value = 20, message = "Practice score must be between 0 and 20")
        private Double practiceScore;

        // Constructors
        public ScoreEntry() {}

        public ScoreEntry(Long studentId) {
            this.studentId = studentId;
        }

        // Getters and Setters
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public Double getHomeworkScore() { return homeworkScore; }
        public void setHomeworkScore(Double homeworkScore) { this.homeworkScore = homeworkScore; }

        public Double getGroupWorkScore() { return groupWorkScore; }
        public void setGroupWorkScore(Double groupWorkScore) { this.groupWorkScore = groupWorkScore; }

        public Double getAttendanceScore() { return attendanceScore; }
        public void setAttendanceScore(Double attendanceScore) { this.attendanceScore = attendanceScore; }

        public Double getActivityScore() { return activityScore; }
        public void setActivityScore(Double activityScore) { this.activityScore = activityScore; }

        public Double getMultipleChoiceScore() { return multipleChoiceScore; }
        public void setMultipleChoiceScore(Double multipleChoiceScore) { this.multipleChoiceScore = multipleChoiceScore; }

        public Double getTheoryScore() { return theoryScore; }
        public void setTheoryScore(Double theoryScore) { this.theoryScore = theoryScore; }

        public Double getPracticeScore() { return practiceScore; }
        public void setPracticeScore(Double practiceScore) { this.practiceScore = practiceScore; }
    }
}