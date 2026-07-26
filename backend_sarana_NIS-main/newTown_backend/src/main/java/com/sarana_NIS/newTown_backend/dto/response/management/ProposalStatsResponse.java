package com.sarana_NIS.newTown_backend.dto.response.management;

import java.util.ArrayList;
import java.util.List;

public class ProposalStatsResponse {
    private Long totalProposals;
    private Long pending;
    private Long underReview;
    private Long approved;
    private Long rejected;
    private Long escalated;
    private Long implemented;
    private Long cancelled;
    private Long escalatedCount;
    private Double approvalRate;
    private List<ProposalTypeStats> byType = new ArrayList<>();

    // Constructors
    public ProposalStatsResponse() {
        this.totalProposals = 0L;
        this.pending = 0L;
        this.underReview = 0L;
        this.approved = 0L;
        this.rejected = 0L;
        this.escalated = 0L;
        this.implemented = 0L;
        this.cancelled = 0L;
        this.escalatedCount = 0L;
        this.approvalRate = 0.0;
    }

    public ProposalStatsResponse(Long totalProposals, Long pending, Long underReview, Long approved,
                                 Long rejected, Long escalated, Long implemented, Long cancelled) {
        this.totalProposals = totalProposals;
        this.pending = pending;
        this.underReview = underReview;
        this.approved = approved;
        this.rejected = rejected;
        this.escalated = escalated;
        this.implemented = implemented;
        this.cancelled = cancelled;
        this.escalatedCount = escalated;
        this.approvalRate = 0.0;
    }

    // Getters and Setters
    public Long getTotalProposals() {
        return totalProposals;
    }

    public void setTotalProposals(Long totalProposals) {
        this.totalProposals = totalProposals;
    }

    public void setTotalProposals(int totalProposals) {
        this.totalProposals = (long) totalProposals;
    }

    public Long getPending() {
        return pending;
    }

    public void setPending(Long pending) {
        this.pending = pending;
    }

    public void setPending(int pending) {
        this.pending = (long) pending;
    }

    public Long getUnderReview() {
        return underReview;
    }

    public void setUnderReview(Long underReview) {
        this.underReview = underReview;
    }

    public void setUnderReview(int underReview) {
        this.underReview = (long) underReview;
    }

    public Long getApproved() {
        return approved;
    }

    public void setApproved(Long approved) {
        this.approved = approved;
    }

    public void setApproved(int approved) {
        this.approved = (long) approved;
    }

    public Long getRejected() {
        return rejected;
    }

    public void setRejected(Long rejected) {
        this.rejected = rejected;
    }

    public void setRejected(int rejected) {
        this.rejected = (long) rejected;
    }

    public Long getEscalated() {
        return escalated;
    }

    public void setEscalated(Long escalated) {
        this.escalated = escalated;
    }

    public void setEscalated(int escalated) {
        this.escalated = (long) escalated;
    }

    public Long getImplemented() {
        return implemented;
    }

    public void setImplemented(Long implemented) {
        this.implemented = implemented;
    }

    public void setImplemented(int implemented) {
        this.implemented = (long) implemented;
    }

    public Long getCancelled() {
        return cancelled;
    }

    public void setCancelled(Long cancelled) {
        this.cancelled = cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = (long) cancelled;
    }

    public Long getEscalatedCount() {
        return escalatedCount;
    }

    public void setEscalatedCount(Long escalatedCount) {
        this.escalatedCount = escalatedCount;
    }

    public void setEscalatedCount(int escalatedCount) {
        this.escalatedCount = (long) escalatedCount;
    }

    public Double getApprovalRate() {
        return approvalRate;
    }

    public void setApprovalRate(Double approvalRate) {
        this.approvalRate = approvalRate;
    }

    public List<ProposalTypeStats> getByType() {
        return byType;
    }

    public void setByType(List<ProposalTypeStats> byType) {
        this.byType = byType;
    }

    // Inner class for type stats
    public static class ProposalTypeStats {
        private String type;
        private int count;
        private double totalBudget;
        private int approved;
        private double approvalRate;

        public ProposalTypeStats() {
            this.count = 0;
            this.totalBudget = 0.0;
            this.approved = 0;
            this.approvalRate = 0.0;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getTotalBudget() {
            return totalBudget;
        }

        public void setTotalBudget(double totalBudget) {
            this.totalBudget = totalBudget;
        }

        public int getApproved() {
            return approved;
        }

        public void setApproved(int approved) {
            this.approved = approved;
        }

        public double getApprovalRate() {
            return approvalRate;
        }

        public void setApprovalRate(double approvalRate) {
            this.approvalRate = approvalRate;
        }
    }
}