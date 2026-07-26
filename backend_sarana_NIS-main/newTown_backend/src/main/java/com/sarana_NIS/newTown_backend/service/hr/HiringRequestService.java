package com.sarana_NIS.newTown_backend.service.hr;

import com.sarana_NIS.newTown_backend.dto.request.hr.HiringRequestDTO;
import com.sarana_NIS.newTown_backend.dto.request.hr.HiringApprovalRequest;
import com.sarana_NIS.newTown_backend.dto.response.hr.HiringResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.hr.HiringRequest;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;
import com.sarana_NIS.newTown_backend.model.enums.RequestStatus;
import com.sarana_NIS.newTown_backend.repository.DepartmentRepository;
import com.sarana_NIS.newTown_backend.repository.HiringRequestRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HiringRequestService {

    private static final Logger log = LoggerFactory.getLogger(HiringRequestService.class);

    private final HiringRequestRepository hiringRequestRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public HiringRequestService(HiringRequestRepository hiringRequestRepository, DepartmentRepository departmentRepository,
                                UserRepository userRepository) {
        this.hiringRequestRepository = hiringRequestRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public HiringResponse createHiringRequest(HiringRequestDTO request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        HiringRequest hiringRequest = new HiringRequest();
        hiringRequest.setPosition(request.getPosition());
        hiringRequest.setDepartment(department);
        hiringRequest.setNumberOfPositions(request.getNumberOfPositions());
        hiringRequest.setJustification(request.getJustification());
        hiringRequest.setPriority(request.getPriority());
        hiringRequest.setStatus(RequestStatus.PENDING);

        if (request.getRequestedBy() != null) {
            User user = userRepository.findById(request.getRequestedBy())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            hiringRequest.setRequestedBy(user);
        }

        HiringRequest saved = hiringRequestRepository.save(hiringRequest);
        log.info("Hiring request created: {}", saved.getPosition());
        return mapToResponse(saved);
    }

    @Transactional
    public HiringResponse approveHiringRequest(HiringApprovalRequest request) {
        HiringRequest hiringRequest = hiringRequestRepository.findById(request.getHiringId())
                .orElseThrow(() -> new RuntimeException("Hiring request not found"));

        if (request.getAction().equals("APPROVE")) {
            hiringRequest.setStatus(RequestStatus.APPROVED);
            if (request.getApprovedPositions() != null) {
                hiringRequest.setNumberOfPositions(request.getApprovedPositions());
            }
        } else {
            hiringRequest.setStatus(RequestStatus.REJECTED);
        }

        HiringRequest saved = hiringRequestRepository.save(hiringRequest);
        log.info("Hiring request {}: {}", request.getAction(), hiringRequest.getId());
        return mapToResponse(saved);
    }

    public List<HiringResponse> getPendingHiringRequests() {
        List<HiringRequest> requests = hiringRequestRepository.findPendingApproval();
        return requests.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<HiringResponse> getHiringRequestsByDepartment(Long departmentId) {
        List<HiringRequest> requests = hiringRequestRepository.findByDepartmentId(departmentId);
        return requests.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public HiringResponse getHiringRequestById(Long hiringId) {
        HiringRequest hiringRequest = hiringRequestRepository.findById(hiringId)
                .orElseThrow(() -> new RuntimeException("Hiring request not found"));
        return mapToResponse(hiringRequest);
    }

    @Transactional
    public void deleteHiringRequest(Long hiringId) {
        HiringRequest hiringRequest = hiringRequestRepository.findById(hiringId)
                .orElseThrow(() -> new RuntimeException("Hiring request not found"));
        hiringRequestRepository.delete(hiringRequest);
        log.info("Hiring request deleted: {}", hiringId);
    }

    private HiringResponse mapToResponse(HiringRequest hiringRequest) {
        HiringResponse response = new HiringResponse();
        response.setId(hiringRequest.getId());
        response.setPosition(hiringRequest.getPosition());
        if (hiringRequest.getDepartment() != null) {
            response.setDepartmentName(hiringRequest.getDepartment().getName());
        }
        response.setNumberOfPositions(hiringRequest.getNumberOfPositions());
        response.setJustification(hiringRequest.getJustification());
        response.setPriority(hiringRequest.getPriority());
        response.setStatus(hiringRequest.getStatus());

        if (hiringRequest.getRequestedBy() != null) {
            response.setRequestedBy(hiringRequest.getRequestedBy().getUsername());
            response.setRequestedByName(hiringRequest.getRequestedBy().getFullName());
        }

        if (hiringRequest.getApprovedBy() != null) {
            response.setApprovedBy(hiringRequest.getApprovedBy().getUsername());
            response.setApprovedByName(hiringRequest.getApprovedBy().getFullName());
        }

        return response;
    }
}