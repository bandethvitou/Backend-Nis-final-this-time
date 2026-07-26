package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.AttendanceRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.AttendanceSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.Attendance;
import com.sarana_NIS.newTown_backend.model.entity.academic.ClassSubject;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;
import com.sarana_NIS.newTown_backend.repository.AttendanceRepository;
import com.sarana_NIS.newTown_backend.repository.ClassSubjectRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {

    private static final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final ClassSubjectRepository classSubjectRepository;
    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository,
                             ClassSubjectRepository classSubjectRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.classSubjectRepository = classSubjectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Attendance markAttendance(AttendanceRequest request, Long markedByUserId) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        ClassSubject classSubject = classSubjectRepository.findById(request.getClassSubjectId())
                .orElseThrow(() -> new RuntimeException("Class subject not found"));
        User markedBy = userRepository.findById(markedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        attendanceRepository.findByStudentClassSubjectDate(request.getStudentId(), request.getClassSubjectId(), request.getDate())
                .ifPresent(existing -> {
                    throw new RuntimeException("Attendance already marked for this student on this date");
                });

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setClassSubject(classSubject);
        attendance.setDate(request.getDate());
        attendance.setStatus(request.getStatus());
        attendance.setTimeIn(request.getTimeIn());
        attendance.setTimeOut(request.getTimeOut());
        attendance.setComment(request.getComment());
        attendance.setMarkedBy(markedBy);

        Attendance saved = attendanceRepository.save(attendance);
        log.info("Attendance marked for student: {} on {}", student.getNameEnglish(), request.getDate());
        return saved;
    }

    @Transactional
    public List<Attendance> markBulkAttendance(AttendanceRequest request, Long markedByUserId) {
        if (request.getAttendances() == null || request.getAttendances().isEmpty()) {
            throw new RuntimeException("No attendance records provided");
        }

        ClassSubject classSubject = classSubjectRepository.findById(request.getClassSubjectId())
                .orElseThrow(() -> new RuntimeException("Class subject not found"));
        User markedBy = userRepository.findById(markedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Attendance> attendances = new ArrayList<>();
        for (AttendanceRequest.BulkAttendance bulk : request.getAttendances()) {
            Student student = studentRepository.findById(bulk.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + bulk.getStudentId()));

            attendanceRepository.findByStudentClassSubjectDate(bulk.getStudentId(), request.getClassSubjectId(), request.getDate())
                    .ifPresent(existing -> { });

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setClassSubject(classSubject);
            attendance.setDate(request.getDate());
            attendance.setStatus(bulk.getStatus());
            attendance.setComment(bulk.getComment());
            attendance.setMarkedBy(markedBy);
            attendances.add(attendance);
        }

        List<Attendance> saved = attendanceRepository.saveAll(attendances);
        log.info("Bulk attendance marked: {} students", saved.size());
        return saved;
    }

    @Transactional
    public Attendance updateAttendance(Long attendanceId, AttendanceStatus status, String comment) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendance.setStatus(status);
        if (comment != null) {
            attendance.setComment(comment);
        }
        Attendance updated = attendanceRepository.save(attendance);
        log.info("Attendance updated: {}", attendanceId);
        return updated;
    }

    public Attendance getAttendanceById(Long attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
    }

    public List<Attendance> getAttendanceByStudent(Long studentId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByStudentAndDateRange(studentId, startDate, endDate);
    }

    public List<Attendance> getAttendanceByClassSubject(Long classSubjectId, LocalDate date) {
        return attendanceRepository.findByClassSubjectAndDate(classSubjectId, date);
    }

    public List<Attendance> getAttendanceByClassSubjectDateRange(Long classSubjectId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByClassSubjectAndDateRange(classSubjectId, startDate, endDate);
    }

    public AttendanceSummaryResponse getAttendanceSummary(Long classSubjectId, LocalDate date) {
        List<Attendance> attendances = attendanceRepository.findByClassSubjectAndDate(classSubjectId, date);

        long present = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        long absent = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
        long late = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
        long excused = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.EXCUSED).count();

        AttendanceSummaryResponse response = new AttendanceSummaryResponse();
        response.setTotalStudents(attendances.size());
        response.setPresentStudents((int) present);
        response.setAbsentStudents((int) absent);
        response.setLateStudents((int) late);
        response.setExcusedStudents((int) excused);

        if (attendances.size() > 0) {
            response.setOverallAttendanceRate((double) (present + late) / attendances.size() * 100);
        }
        return response;
    }

    public AttendanceSummaryResponse.StudentAttendance getStudentAttendanceStats(Long studentId) {
        List<Attendance> attendances = attendanceRepository.findByStudentAndDateRange(studentId, LocalDate.now().minusMonths(1), LocalDate.now());

        long present = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        long absent = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
        long late = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
        long excused = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.EXCUSED).count();

        AttendanceSummaryResponse.StudentAttendance stats = new AttendanceSummaryResponse.StudentAttendance();
        stats.setStudentId(studentId);
        stats.setPresentDays((int) present);
        stats.setAbsentDays((int) absent);
        stats.setLateDays((int) late);
        stats.setExcusedDays((int) excused);

        if (attendances.size() > 0) {
            stats.setAttendanceRate((double) (present + late) / attendances.size() * 100);
        }

        if (stats.getAttendanceRate() >= 90) {
            stats.setStatus("GOOD");
        } else if (stats.getAttendanceRate() >= 75) {
            stats.setStatus("WARNING");
        } else {
            stats.setStatus("CRITICAL");
        }
        return stats;
    }

    @Transactional
    public void deleteAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendanceRepository.delete(attendance);
        log.info("Attendance deleted: {}", attendanceId);
    }

    public boolean hasAttendance(Long studentId, Long classSubjectId, LocalDate date) {
        return attendanceRepository.findByStudentClassSubjectDate(studentId, classSubjectId, date).isPresent();
    }
}