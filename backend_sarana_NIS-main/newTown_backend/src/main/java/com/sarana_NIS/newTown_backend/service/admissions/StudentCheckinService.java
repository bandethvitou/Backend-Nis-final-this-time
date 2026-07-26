package com.sarana_NIS.newTown_backend.service.admissions;

import com.sarana_NIS.newTown_backend.dto.request.academic.CheckinRequest;
import com.sarana_NIS.newTown_backend.model.entity.admissions.StudentCheckin;
import com.sarana_NIS.newTown_backend.model.entity.academic.Class;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;
import com.sarana_NIS.newTown_backend.repository.ClassRepository;
import com.sarana_NIS.newTown_backend.repository.StudentCheckinRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentCheckinService {

    private static final Logger log = LoggerFactory.getLogger(StudentCheckinService.class);

    private final StudentCheckinRepository checkinRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public StudentCheckinService(StudentCheckinRepository checkinRepository, StudentRepository studentRepository,
                                 ClassRepository classRepository, UserRepository userRepository) {
        this.checkinRepository = checkinRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public StudentCheckin checkIn(CheckinRequest request, Long markedByUserId) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Class classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));
        User markedBy = userRepository.findById(markedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        checkinRepository.findByStudentAndDate(request.getStudentId(), request.getCheckinDate())
                .ifPresent(existing -> {
                    throw new RuntimeException("Student already checked in today");
                });

        StudentCheckin checkin = new StudentCheckin();
        checkin.setStudent(student);
        checkin.setClassEntity(classEntity);
        checkin.setCheckinDate(request.getCheckinDate());
        checkin.setCheckinTime(request.getCheckinTime() != null ? request.getCheckinTime() : LocalTime.now());
        checkin.setStatus(request.getStatus());
        checkin.setComment(request.getComment());
        checkin.setMarkedBy(markedBy);
        checkin.setIsBulkAction(false);

        if (request.getCheckinTime() != null && request.getCheckinTime().isAfter(LocalTime.of(8, 0))) {
            checkin.setStatus(AttendanceStatus.LATE);
        }

        StudentCheckin saved = checkinRepository.save(checkin);
        log.info("Student checked in: {} at {}", student.getNameEnglish(), saved.getCheckinTime());
        return saved;
    }

    @Transactional
    public StudentCheckin checkOut(Long checkinId) {
        StudentCheckin checkin = checkinRepository.findById(checkinId)
                .orElseThrow(() -> new RuntimeException("Check-in record not found"));

        if (checkin.getCheckoutTime() != null) {
            throw new RuntimeException("Student already checked out");
        }

        checkin.setCheckoutTime(LocalTime.now());

        if (checkin.getCheckoutTime().isBefore(LocalTime.of(15, 0))) {
            checkin.setEarlyDepartureMinutes(
                    (int) java.time.Duration.between(checkin.getCheckoutTime(), LocalTime.of(15, 0)).toMinutes()
            );
        }

        StudentCheckin saved = checkinRepository.save(checkin);
        log.info("Student checked out: {}", checkinId);
        return saved;
    }

    @Transactional
    public List<StudentCheckin> bulkCheckIn(CheckinRequest request, Long markedByUserId) {
        if (request.getBulkCheckins() == null || request.getBulkCheckins().isEmpty()) {
            throw new RuntimeException("No check-in records provided");
        }

        User markedBy = userRepository.findById(markedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Class classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        String bulkActionId = UUID.randomUUID().toString();
        List<StudentCheckin> checkins = new ArrayList<>();

        for (CheckinRequest.BulkCheckin bulk : request.getBulkCheckins()) {
            Student student = studentRepository.findById(bulk.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + bulk.getStudentId()));

            if (checkinRepository.findByStudentAndDate(bulk.getStudentId(), request.getCheckinDate()).isPresent()) {
                continue;
            }

            StudentCheckin checkin = new StudentCheckin();
            checkin.setStudent(student);
            checkin.setClassEntity(classEntity);
            checkin.setCheckinDate(request.getCheckinDate());
            checkin.setCheckinTime(LocalTime.now());
            checkin.setStatus(bulk.getStatus());
            checkin.setComment(bulk.getComment());
            checkin.setMarkedBy(markedBy);
            checkin.setIsBulkAction(true);
            checkin.setBulkActionId(bulkActionId);
            checkins.add(checkin);
        }

        List<StudentCheckin> saved = checkinRepository.saveAll(checkins);
        log.info("Bulk check-in: {} students", saved.size());
        return saved;
    }

    public StudentCheckin getCheckinById(Long checkinId) {
        return checkinRepository.findById(checkinId)
                .orElseThrow(() -> new RuntimeException("Check-in record not found"));
    }

    public StudentCheckin getCheckinByStudentAndDate(Long studentId, LocalDate date) {
        return checkinRepository.findByStudentAndDate(studentId, date)
                .orElseThrow(() -> new RuntimeException("Check-in record not found"));
    }

    public List<StudentCheckin> getCheckinsByClassAndDate(Long classId, LocalDate date) {
        return checkinRepository.findByClassAndDate(classId, date);
    }

    public List<StudentCheckin> getCheckinsByStudentDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        return checkinRepository.findByStudentAndDateRange(studentId, startDate, endDate);
    }

    public List<StudentCheckin> getCheckinsByStatusAndDate(AttendanceStatus status, LocalDate date) {
        return checkinRepository.findByStatusAndDate(status, date);
    }

    public List<StudentCheckin> getLateCheckins(LocalDate date) {
        return checkinRepository.findLateCheckins(LocalTime.of(8, 0), date);
    }

    public List<StudentCheckin> getEarlyCheckins(LocalDate date) {
        return checkinRepository.findEarlyCheckins(LocalTime.of(7, 30), date);
    }

    public long countByClassDateStatus(Long classId, LocalDate date, AttendanceStatus status) {
        return checkinRepository.countByClassDateStatus(classId, date, status);
    }

    public double getAverageLateMinutes(LocalDate date) {
        Double avg = checkinRepository.findAverageLateMinutes(date);
        return avg != null ? avg : 0.0;
    }

    @Transactional
    public void deleteCheckin(Long checkinId) {
        StudentCheckin checkin = checkinRepository.findById(checkinId)
                .orElseThrow(() -> new RuntimeException("Check-in record not found"));
        checkinRepository.delete(checkin);
        log.info("Check-in record deleted: {}", checkinId);
    }
}