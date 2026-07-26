package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.AcademicLevelRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.AcademicLevelResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.AcademicLevel;
import com.sarana_NIS.newTown_backend.repository.AcademicLevelRepository;
import com.sarana_NIS.newTown_backend.repository.ClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcademicLevelService {

    private static final Logger log = LoggerFactory.getLogger(AcademicLevelService.class);

    private final AcademicLevelRepository academicLevelRepository;
    private final ClassRepository classRepository;

    public AcademicLevelService(AcademicLevelRepository academicLevelRepository, ClassRepository classRepository) {
        this.academicLevelRepository = academicLevelRepository;
        this.classRepository = classRepository;
    }

    @Transactional
    public AcademicLevelResponse createLevel(AcademicLevelRequest request) {
        if (academicLevelRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Level already exists: " + request.getName());
        }
        if (academicLevelRepository.findByCode(request.getCode()).isPresent()) {
            throw new RuntimeException("Level code already exists: " + request.getCode());
        }

        AcademicLevel level = new AcademicLevel();
        level.setName(request.getName());
        level.setCode(request.getCode());
        level.setMinAge(request.getMinAge());
        level.setMaxAge(request.getMaxAge());
        level.setSequenceOrder(request.getSequenceOrder());

        AcademicLevel saved = academicLevelRepository.save(level);
        log.info("Academic level created: {}", saved.getName());
        return mapToResponse(saved);
    }

    @Transactional
    public AcademicLevelResponse updateLevel(Long levelId, AcademicLevelRequest request) {
        AcademicLevel level = academicLevelRepository.findById(levelId)
                .orElseThrow(() -> new RuntimeException("Level not found"));

        if (request.getName() != null) level.setName(request.getName());
        if (request.getCode() != null) level.setCode(request.getCode());
        if (request.getMinAge() != null) level.setMinAge(request.getMinAge());
        if (request.getMaxAge() != null) level.setMaxAge(request.getMaxAge());
        if (request.getSequenceOrder() != null) level.setSequenceOrder(request.getSequenceOrder());

        AcademicLevel updated = academicLevelRepository.save(level);
        log.info("Academic level updated: {}", updated.getName());
        return mapToResponse(updated);
    }

    public AcademicLevelResponse getLevelById(Long levelId) {
        AcademicLevel level = academicLevelRepository.findById(levelId)
                .orElseThrow(() -> new RuntimeException("Level not found"));
        return mapToResponse(level);
    }

    public List<AcademicLevelResponse> getAllLevels() {
        List<AcademicLevel> levels = academicLevelRepository.findAllByOrderBySequenceOrderAsc();
        return levels.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PageResponse<AcademicLevelResponse> getLevels(Pageable pageable) {
        Page<AcademicLevel> page = academicLevelRepository.findAll(pageable);
        List<AcademicLevelResponse> content = page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public List<AcademicLevelResponse> getLevelsForAge(Integer age) {
        List<AcademicLevel> levels = academicLevelRepository.findLevelsForAge(age);
        return levels.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteLevel(Long levelId) {
        AcademicLevel level = academicLevelRepository.findById(levelId)
                .orElseThrow(() -> new RuntimeException("Level not found"));

        Long classCount = classRepository.countByLevelId(levelId);
        if (classCount > 0) {
            throw new RuntimeException("Cannot delete level with existing classes. Remove classes first.");
        }

        academicLevelRepository.delete(level);
        log.info("Academic level deleted: {}", level.getName());
    }

    private AcademicLevelResponse mapToResponse(AcademicLevel level) {
        AcademicLevelResponse response = new AcademicLevelResponse();
        response.setId(level.getId());
        response.setName(level.getName());
        response.setCode(level.getCode());
        response.setMinAge(level.getMinAge());
        response.setMaxAge(level.getMaxAge());
        response.setSequenceOrder(level.getSequenceOrder());

        Long classCount = classRepository.countByLevelId(level.getId());
        response.setClassCount(classCount != null ? classCount.intValue() : 0);

        return response;
    }
}