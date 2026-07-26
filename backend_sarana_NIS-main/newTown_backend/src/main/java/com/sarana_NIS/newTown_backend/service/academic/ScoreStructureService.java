package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.ScoreStructureRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.ScoreStructureResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.Class;
import com.sarana_NIS.newTown_backend.model.entity.academic.ScoreStructure;
import com.sarana_NIS.newTown_backend.model.entity.academic.Subject;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import com.sarana_NIS.newTown_backend.repository.ClassRepository;
import com.sarana_NIS.newTown_backend.repository.ScoreStructureRepository;
import com.sarana_NIS.newTown_backend.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreStructureService {

    private static final Logger log = LoggerFactory.getLogger(ScoreStructureService.class);

    private final ScoreStructureRepository scoreStructureRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;

    public ScoreStructureService(ScoreStructureRepository scoreStructureRepository, ClassRepository classRepository,
                                 SubjectRepository subjectRepository) {
        this.scoreStructureRepository = scoreStructureRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public ScoreStructureResponse createScoreStructure(ScoreStructureRequest request) {
        ScoreStructure structure = new ScoreStructure();
        structure.setLevel(request.getLevel());
        structure.setAcademicYear(request.getAcademicYear());
        structure.setTerm(request.getTerm());

        structure.setHomeworkMax(request.getHomeworkMax());
        structure.setHomeworkWeight(request.getHomeworkWeight());
        structure.setGroupWorkMax(request.getGroupWorkMax());
        structure.setGroupWorkWeight(request.getGroupWorkWeight());
        structure.setAttendanceMax(request.getAttendanceMax());
        structure.setAttendanceWeight(request.getAttendanceWeight());
        structure.setActivityMax(request.getActivityMax());
        structure.setActivityWeight(request.getActivityWeight());
        structure.setMultipleChoiceMax(request.getMultipleChoiceMax());
        structure.setMultipleChoiceWeight(request.getMultipleChoiceWeight());
        structure.setTheoryMax(request.getTheoryMax());
        structure.setTheoryWeight(request.getTheoryWeight());
        structure.setPracticeMax(request.getPracticeMax());
        structure.setPracticeWeight(request.getPracticeWeight());

        double totalMax = request.getHomeworkMax() + request.getGroupWorkMax() +
                request.getAttendanceMax() + request.getActivityMax() +
                request.getMultipleChoiceMax() + request.getTheoryMax() +
                request.getPracticeMax();
        structure.setTotalMax(totalMax);

        structure.setGradingScale(request.getGradingScale());
        structure.setNotes(request.getNotes());
        structure.setIsActive(true);
        structure.setEffectiveFrom(LocalDateTime.now());

        if (request.getClassId() != null) {
            Class classEntity = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            structure.setClassEntity(classEntity);
        }

        if (request.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            structure.setSubject(subject);
        }

        ScoreStructure saved = scoreStructureRepository.save(structure);
        log.info("Score structure created for level: {}", saved.getLevel());
        return mapToResponse(saved);
    }

    public ScoreStructureResponse getScoreStructureById(Long structureId) {
        ScoreStructure structure = scoreStructureRepository.findById(structureId)
                .orElseThrow(() -> new RuntimeException("Score structure not found"));
        return mapToResponse(structure);
    }

    public List<ScoreStructureResponse> getByLevelAndAcademicYearAndTerm(Level level, String academicYear, String term) {
        List<ScoreStructure> structures = scoreStructureRepository.findByLevelAndAcademicYearAndTerm(level, academicYear, term);
        return structures.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ScoreStructureResponse getByClassAndAcademicYear(Long classId, String academicYear) {
        ScoreStructure structure = scoreStructureRepository.findByClassAndAcademicYear(classId, academicYear)
                .orElseThrow(() -> new RuntimeException("Score structure not found for class and academic year"));
        return mapToResponse(structure);
    }

    public ScoreStructureResponse getByClassAndSubject(Long classId, Long subjectId) {
        ScoreStructure structure = scoreStructureRepository.findByClassAndSubject(classId, subjectId)
                .orElseThrow(() -> new RuntimeException("Score structure not found for class and subject"));
        return mapToResponse(structure);
    }

    public List<ScoreStructureResponse> getActiveStructures() {
        List<ScoreStructure> structures = scoreStructureRepository.findActiveStructures();
        return structures.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ScoreStructureResponse> getActiveByLevel(Level level) {
        List<ScoreStructure> structures = scoreStructureRepository.findActiveByLevel(level);
        return structures.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public ScoreStructureResponse updateScoreStructure(Long structureId, ScoreStructureRequest request) {
        ScoreStructure structure = scoreStructureRepository.findById(structureId)
                .orElseThrow(() -> new RuntimeException("Score structure not found"));

        if (request.getHomeworkMax() != null) structure.setHomeworkMax(request.getHomeworkMax());
        if (request.getHomeworkWeight() != null) structure.setHomeworkWeight(request.getHomeworkWeight());
        if (request.getGroupWorkMax() != null) structure.setGroupWorkMax(request.getGroupWorkMax());
        if (request.getGroupWorkWeight() != null) structure.setGroupWorkWeight(request.getGroupWorkWeight());
        if (request.getAttendanceMax() != null) structure.setAttendanceMax(request.getAttendanceMax());
        if (request.getAttendanceWeight() != null) structure.setAttendanceWeight(request.getAttendanceWeight());
        if (request.getActivityMax() != null) structure.setActivityMax(request.getActivityMax());
        if (request.getActivityWeight() != null) structure.setActivityWeight(request.getActivityWeight());
        if (request.getMultipleChoiceMax() != null) structure.setMultipleChoiceMax(request.getMultipleChoiceMax());
        if (request.getMultipleChoiceWeight() != null) structure.setMultipleChoiceWeight(request.getMultipleChoiceWeight());
        if (request.getTheoryMax() != null) structure.setTheoryMax(request.getTheoryMax());
        if (request.getTheoryWeight() != null) structure.setTheoryWeight(request.getTheoryWeight());
        if (request.getPracticeMax() != null) structure.setPracticeMax(request.getPracticeMax());
        if (request.getPracticeWeight() != null) structure.setPracticeWeight(request.getPracticeWeight());
        if (request.getGradingScale() != null) structure.setGradingScale(request.getGradingScale());
        if (request.getNotes() != null) structure.setNotes(request.getNotes());

        double totalMax = (structure.getHomeworkMax() != null ? structure.getHomeworkMax() : 0) +
                (structure.getGroupWorkMax() != null ? structure.getGroupWorkMax() : 0) +
                (structure.getAttendanceMax() != null ? structure.getAttendanceMax() : 0) +
                (structure.getActivityMax() != null ? structure.getActivityMax() : 0) +
                (structure.getMultipleChoiceMax() != null ? structure.getMultipleChoiceMax() : 0) +
                (structure.getTheoryMax() != null ? structure.getTheoryMax() : 0) +
                (structure.getPracticeMax() != null ? structure.getPracticeMax() : 0);
        structure.setTotalMax(totalMax);

        ScoreStructure saved = scoreStructureRepository.save(structure);
        log.info("Score structure updated: {}", structureId);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteScoreStructure(Long structureId) {
        ScoreStructure structure = scoreStructureRepository.findById(structureId)
                .orElseThrow(() -> new RuntimeException("Score structure not found"));
        scoreStructureRepository.delete(structure);
        log.info("Score structure deleted: {}", structureId);
    }

    private ScoreStructureResponse mapToResponse(ScoreStructure structure) {
        ScoreStructureResponse response = new ScoreStructureResponse();
        response.setId(structure.getId());
        response.setLevel(structure.getLevel());
        response.setAcademicYear(structure.getAcademicYear());
        response.setTerm(structure.getTerm());

        if (structure.getClassEntity() != null) {
            response.setClassId(structure.getClassEntity().getId());
            response.setClassName(structure.getClassEntity().getName());
        }

        if (structure.getSubject() != null) {
            response.setSubjectId(structure.getSubject().getId());
            response.setSubjectName(structure.getSubject().getName());
        }

        response.setHomeworkMax(structure.getHomeworkMax());
        response.setHomeworkWeight(structure.getHomeworkWeight());
        response.setGroupWorkMax(structure.getGroupWorkMax());
        response.setGroupWorkWeight(structure.getGroupWorkWeight());
        response.setAttendanceMax(structure.getAttendanceMax());
        response.setAttendanceWeight(structure.getAttendanceWeight());
        response.setActivityMax(structure.getActivityMax());
        response.setActivityWeight(structure.getActivityWeight());
        response.setMultipleChoiceMax(structure.getMultipleChoiceMax());
        response.setMultipleChoiceWeight(structure.getMultipleChoiceWeight());
        response.setTheoryMax(structure.getTheoryMax());
        response.setTheoryWeight(structure.getTheoryWeight());
        response.setPracticeMax(structure.getPracticeMax());
        response.setPracticeWeight(structure.getPracticeWeight());
        response.setTotalMax(structure.getTotalMax());
        response.setIsActive(structure.getIsActive());
        response.setGradingScale(structure.getGradingScale());
        response.setNotes(structure.getNotes());
        response.setEffectiveFrom(structure.getEffectiveFrom());
        response.setEffectiveTo(structure.getEffectiveTo());
        response.setCreatedAt(structure.getCreatedAt());
        response.setUpdatedAt(structure.getUpdatedAt());
        return response;
    }
}