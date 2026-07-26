package com.sarana_NIS.newTown_backend.service.management;

import com.sarana_NIS.newTown_backend.dto.request.management.ProposalReviewRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.ProposalStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Proposal;
import com.sarana_NIS.newTown_backend.model.enums.ProposalStatus;

import java.util.List;

public interface ProposalService {
    Proposal createProposal(Proposal proposal, Long userId);
    List<Proposal> getAllProposals();
    Proposal getProposalById(Long proposalId);
    List<Proposal> getProposalsByStatus(ProposalStatus status);
    List<Proposal> getPendingProposals();
    List<Proposal> getEscalatedProposals();
    List<Proposal> getProposalsBySubmittedBy(Long userId);
    List<Proposal> getProposalsByDepartment(Long departmentId);
    ProposalStatsResponse getProposalStats();
    Proposal reviewProposal(ProposalReviewRequest request, Long userId);
    Proposal escalateProposal(Long proposalId, Long userId);
    void deleteProposal(Long proposalId);
    Proposal approveProposal(Long proposalId, Long userId, String comments);
    Proposal rejectProposal(Long proposalId, Long userId, String comments);
    long countProposalsByStatus(ProposalStatus status);
}