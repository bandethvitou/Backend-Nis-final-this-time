package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.request.management.ProposalReviewRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.ProposalStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Proposal;
import com.sarana_NIS.newTown_backend.model.enums.ProposalStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.management.ProposalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/proposals")
@Tag(name = "Proposal Management", description = "Proposal management APIs for managers")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @PostMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("PROPOSAL_CREATE")
    @Operation(summary = "Create a new proposal")
    public ResponseEntity<ApiResponse<Proposal>> createProposal(
            @Valid @RequestBody Proposal proposal) {
        System.out.println("Creating proposal: " + proposal.getTitle());
        Long currentUserId = 1L;
        Proposal created = proposalService.createProposal(proposal, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Proposal created successfully", created));
    }

    @GetMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get all proposals")
    public ResponseEntity<ApiResponse<List<Proposal>>> getAllProposals() {
        System.out.println("Fetching all proposals");
        List<Proposal> proposals = proposalService.getAllProposals();
        return ResponseEntity.ok(ApiResponse.success(proposals));
    }

    @GetMapping("/{proposalId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get proposal by ID")
    public ResponseEntity<ApiResponse<Proposal>> getProposalById(
            @PathVariable Long proposalId) {
        System.out.println("Fetching proposal: " + proposalId);
        Proposal proposal = proposalService.getProposalById(proposalId);
        return ResponseEntity.ok(ApiResponse.success(proposal));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get proposals by status")
    public ResponseEntity<ApiResponse<List<Proposal>>> getProposalsByStatus(
            @PathVariable ProposalStatus status) {
        System.out.println("Fetching proposals by status: " + status);
        List<Proposal> proposals = proposalService.getProposalsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(proposals));
    }

    @GetMapping("/pending")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get pending proposals")
    public ResponseEntity<ApiResponse<List<Proposal>>> getPendingProposals() {
        System.out.println("Fetching pending proposals");
        List<Proposal> proposals = proposalService.getPendingProposals();
        return ResponseEntity.ok(ApiResponse.success(proposals));
    }

    @GetMapping("/escalated")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get escalated proposals")
    public ResponseEntity<ApiResponse<List<Proposal>>> getEscalatedProposals() {
        System.out.println("Fetching escalated proposals");
        List<Proposal> proposals = proposalService.getEscalatedProposals();
        return ResponseEntity.ok(ApiResponse.success(proposals));
    }

    @GetMapping("/user/{userId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get proposals by user")
    public ResponseEntity<ApiResponse<List<Proposal>>> getProposalsBySubmittedBy(
            @PathVariable Long userId) {
        System.out.println("Fetching proposals by user: " + userId);
        List<Proposal> proposals = proposalService.getProposalsBySubmittedBy(userId);
        return ResponseEntity.ok(ApiResponse.success(proposals));
    }

    @GetMapping("/department/{departmentId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get proposals by department")
    public ResponseEntity<ApiResponse<List<Proposal>>> getProposalsByDepartment(
            @PathVariable Long departmentId) {
        System.out.println("Fetching proposals by department: " + departmentId);
        List<Proposal> proposals = proposalService.getProposalsByDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success(proposals));
    }

    @GetMapping("/stats")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get proposal statistics")
    public ResponseEntity<ApiResponse<ProposalStatsResponse>> getProposalStats() {
        System.out.println("Fetching proposal statistics");
        ProposalStatsResponse stats = proposalService.getProposalStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @PostMapping("/review")
    @RequiresRole({"TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("PROPOSAL_REVIEW")
    @Operation(summary = "Review proposal")
    public ResponseEntity<ApiResponse<Proposal>> reviewProposal(
            @Valid @RequestBody ProposalReviewRequest request) {
        System.out.println("Reviewing proposal: " + request.getProposalId());
        Long currentUserId = 1L;
        Proposal proposal = proposalService.reviewProposal(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Proposal reviewed successfully", proposal));
    }

    @PostMapping("/{proposalId}/escalate")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("PROPOSAL_ESCALATE")
    @Operation(summary = "Escalate proposal")
    public ResponseEntity<ApiResponse<Proposal>> escalateProposal(
            @PathVariable Long proposalId) {
        System.out.println("Escalating proposal: " + proposalId);
        Long currentUserId = 1L;
        Proposal proposal = proposalService.escalateProposal(proposalId, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Proposal escalated successfully", proposal));
    }

    @DeleteMapping("/{proposalId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("PROPOSAL_DELETE")
    @Operation(summary = "Delete proposal")
    public ResponseEntity<ApiResponse<Void>> deleteProposal(
            @PathVariable Long proposalId) {
        System.out.println("Deleting proposal: " + proposalId);
        proposalService.deleteProposal(proposalId);
        return ResponseEntity.ok(ApiResponse.success("Proposal deleted successfully", null));
    }
}