package com.sarana_NIS.newTown_backend.service.management;

import com.sarana_NIS.newTown_backend.dto.request.management.ProposalReviewRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.ProposalStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;
import com.sarana_NIS.newTown_backend.model.entity.management.Proposal;
import com.sarana_NIS.newTown_backend.model.enums.ProposalStatus;
import com.sarana_NIS.newTown_backend.repository.management.ProposalRepository;
import com.sarana_NIS.newTown_backend.service.core.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final UserService userService;

    public ProposalServiceImpl(ProposalRepository proposalRepository, UserService userService) {
        this.proposalRepository = proposalRepository;
        this.userService = userService;
    }

    @Override
    public Proposal createProposal(Proposal proposal, Long userId) {
        User submittedBy = userService.getUserById(userId);
        proposal.setSubmittedBy(submittedBy);
        proposal.setSubmittedDate(LocalDateTime.now());
        proposal.setStatus(ProposalStatus.PENDING);
        proposal.setIsEscalated(false);
        return proposalRepository.save(proposal);
    }

    @Override
    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    @Override
    public Proposal getProposalById(Long proposalId) {
        return proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found with id: " + proposalId));
    }

    @Override
    public List<Proposal> getProposalsByStatus(ProposalStatus status) {
        return proposalRepository.findByStatus(status);
    }

    @Override
    public List<Proposal> getPendingProposals() {
        return proposalRepository.findByStatus(ProposalStatus.PENDING);
    }

    @Override
    public List<Proposal> getEscalatedProposals() {
        return proposalRepository.findEscalatedProposals();
    }

    @Override
    public List<Proposal> getProposalsBySubmittedBy(Long userId) {
        User user = userService.getUserById(userId);
        return proposalRepository.findBySubmittedBy(user);
    }

    @Override
    public List<Proposal> getProposalsByDepartment(Long departmentId) {
        Department department = new Department();
        department.setId(departmentId);
        return proposalRepository.findByDepartment(department);
    }

    @Override
    public ProposalStatsResponse getProposalStats() {
        ProposalStatsResponse stats = new ProposalStatsResponse();
        stats.setTotalProposals(proposalRepository.count());
        stats.setPending(proposalRepository.countByStatus(ProposalStatus.PENDING));
        stats.setApproved(proposalRepository.countByStatus(ProposalStatus.APPROVED));
        stats.setRejected(proposalRepository.countByStatus(ProposalStatus.REJECTED));
        stats.setEscalated(proposalRepository.countByStatus(ProposalStatus.ESCALATED));
        stats.setUnderReview(proposalRepository.countByStatus(ProposalStatus.UNDER_REVIEW));
        return stats;
    }

    @Override
    public Proposal reviewProposal(ProposalReviewRequest request, Long userId) {
        Proposal proposal = getProposalById(request.getProposalId());
        proposal.setStatus(request.getStatus());
        proposal.setReviewerComments(request.getComments());

        User reviewedBy = userService.getUserById(userId);
        proposal.setReviewedBy(reviewedBy);
        proposal.setReviewedDate(LocalDateTime.now());

        return proposalRepository.save(proposal);
    }

    @Override
    public Proposal escalateProposal(Long proposalId, Long userId) {
        Proposal proposal = getProposalById(proposalId);
        proposal.setStatus(ProposalStatus.ESCALATED);
        proposal.setIsEscalated(true);

        User escalatedBy = userService.getUserById(userId);
        proposal.setEscalatedBy(escalatedBy);
        proposal.setEscalatedDate(LocalDateTime.now());

        return proposalRepository.save(proposal);
    }

    @Override
    public void deleteProposal(Long proposalId) {
        Proposal proposal = getProposalById(proposalId);
        proposalRepository.delete(proposal);
    }

    @Override
    public Proposal approveProposal(Long proposalId, Long userId, String comments) {
        Proposal proposal = getProposalById(proposalId);
        proposal.setStatus(ProposalStatus.APPROVED);
        proposal.setReviewerComments(comments);

        User reviewedBy = userService.getUserById(userId);
        proposal.setReviewedBy(reviewedBy);
        proposal.setReviewedDate(LocalDateTime.now());
        proposal.setApprovedDate(LocalDateTime.now());

        return proposalRepository.save(proposal);
    }

    @Override
    public Proposal rejectProposal(Long proposalId, Long userId, String comments) {
        Proposal proposal = getProposalById(proposalId);
        proposal.setStatus(ProposalStatus.REJECTED);
        proposal.setReviewerComments(comments);
        proposal.setRejectionReason(comments);

        User reviewedBy = userService.getUserById(userId);
        proposal.setReviewedBy(reviewedBy);
        proposal.setReviewedDate(LocalDateTime.now());

        return proposalRepository.save(proposal);
    }

    @Override
    public long countProposalsByStatus(ProposalStatus status) {
        Long count = proposalRepository.countByStatus(status);
        return count != null ? count : 0L;
    }
}