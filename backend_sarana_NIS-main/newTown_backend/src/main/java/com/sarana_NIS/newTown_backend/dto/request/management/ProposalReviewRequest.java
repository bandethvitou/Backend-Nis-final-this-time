package com.sarana_NIS.newTown_backend.dto.request.management;

import com.sarana_NIS.newTown_backend.model.enums.ProposalStatus;
import jakarta.validation.constraints.NotNull;

public class ProposalReviewRequest {

    @NotNull(message = "Proposal ID is required")
    private Long proposalId;

    @NotNull(message = "Status is required")
    private ProposalStatus status;

    private String comments;

    // Constructors
    public ProposalReviewRequest() {
    }

    public ProposalReviewRequest(Long proposalId, ProposalStatus status, String comments) {
        this.proposalId = proposalId;
        this.status = status;
        this.comments = comments;
    }

    // Getters and Setters
    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}