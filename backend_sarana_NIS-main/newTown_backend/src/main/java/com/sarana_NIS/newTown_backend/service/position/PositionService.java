package com.sarana_NIS.newTown_backend.service.position;

import com.sarana_NIS.newTown_backend.dto.request.position.PositionRequest;
import com.sarana_NIS.newTown_backend.dto.response.position.PositionResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.position.Position;
import com.sarana_NIS.newTown_backend.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {

    private static final Logger log = LoggerFactory.getLogger(PositionService.class);

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Transactional
    public PositionResponse createPosition(PositionRequest request) {
        if (positionRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Position already exists: " + request.getName());
        }

        Position position = new Position();
        position.setName(request.getName());
        position.setDescription(request.getDescription());
        position.setCategory(request.getCategory());
        position.setIsTemporaryAllowed(request.getIsTemporaryAllowed() != null ? request.getIsTemporaryAllowed() : true);

        Position savedPosition = positionRepository.save(position);
        log.info("Position created: {}", savedPosition.getName());
        return mapToResponse(savedPosition);
    }

    @Transactional
    public PositionResponse updatePosition(Long positionId, PositionRequest request) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found"));

        if (request.getName() != null) position.setName(request.getName());
        if (request.getDescription() != null) position.setDescription(request.getDescription());
        if (request.getCategory() != null) position.setCategory(request.getCategory());
        if (request.getIsTemporaryAllowed() != null) position.setIsTemporaryAllowed(request.getIsTemporaryAllowed());

        Position updatedPosition = positionRepository.save(position);
        log.info("Position updated: {}", updatedPosition.getName());
        return mapToResponse(updatedPosition);
    }

    public PositionResponse getPositionById(Long positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found"));
        return mapToResponse(position);
    }

    public PositionResponse getPositionByName(String name) {
        Position position = positionRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Position not found: " + name));
        return mapToResponse(position);
    }

    public PageResponse<PositionResponse> getAllPositions(Pageable pageable) {
        Page<Position> page = positionRepository.findAll(pageable);
        PageResponse<PositionResponse> response = new PageResponse<>();
        response.setContent(page.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()));
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        response.setFirst(page.isFirst());
        return response;
    }

    public List<PositionResponse> getPositionsByCategory(String category) {
        List<Position> positions = positionRepository.findByCategory(category);
        return positions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<PositionResponse> getTemporaryAllowedPositions() {
        List<Position> positions = positionRepository.findTemporaryAllowedPositions();
        return positions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deletePosition(Long positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found"));
        positionRepository.delete(position);
        log.info("Position deleted: {}", position.getName());
    }

    private PositionResponse mapToResponse(Position position) {
        PositionResponse response = new PositionResponse();
        response.setId(position.getId());
        response.setName(position.getName());
        response.setDescription(position.getDescription());
        response.setCategory(position.getCategory());
        response.setIsTemporaryAllowed(position.getIsTemporaryAllowed());
        response.setIsActive(true);
        response.setCreatedAt(position.getCreatedAt());
        response.setCreatedBy(position.getCreatedBy() != null ? position.getCreatedBy().toString() : null);
        return response;
    }
}