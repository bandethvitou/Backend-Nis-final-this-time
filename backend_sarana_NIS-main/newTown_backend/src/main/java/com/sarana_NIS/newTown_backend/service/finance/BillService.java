package com.sarana_NIS.newTown_backend.service.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.BillRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.BillApprovalRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.BillResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.BillSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.finance.Bill;
import com.sarana_NIS.newTown_backend.model.enums.BillStatus;
import com.sarana_NIS.newTown_backend.repository.BillRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    private static final Logger log = LoggerFactory.getLogger(BillService.class);

    private final BillRepository billRepository;
    private final UserRepository userRepository;

    public BillService(BillRepository billRepository, UserRepository userRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BillResponse createBill(BillRequest request) {
        log.info("Creating bill: {}", request.getBillType());
        Bill bill = new Bill();
        bill.setBillType(request.getBillType());
        bill.setAmount(request.getAmount());
        bill.setDueDate(request.getDueDate());
        bill.setAttachmentUrl(request.getAttachmentUrl());
        bill.setStatus(BillStatus.PENDING);

        if (request.getRequestedBy() != null) {
            User user = userRepository.findById(request.getRequestedBy())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            bill.setRequestedBy(user);
        }

        Bill saved = billRepository.save(bill);
        log.info("Bill created: {} - ${}", bill.getBillType(), bill.getAmount());
        return mapToResponse(saved);
    }

    @Transactional
    public BillResponse approveBill(BillApprovalRequest request) {
        log.info("Processing bill: {}", request.getBillId());
        Bill bill = billRepository.findById(request.getBillId())
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (request.getAction().equalsIgnoreCase("APPROVE")) {
            bill.setStatus(BillStatus.APPROVED);
            bill.setApprovedAt(LocalDateTime.now());
            log.info("Bill approved: {}", bill.getBillType());
        } else if (request.getAction().equalsIgnoreCase("REJECT")) {
            bill.setStatus(BillStatus.REJECTED);
            bill.setRejectedReason(request.getReason());
            log.info("Bill rejected: {}", bill.getBillType());
        } else {
            throw new IllegalArgumentException("Invalid action. Must be APPROVE or REJECT");
        }

        Bill saved = billRepository.save(bill);
        return mapToResponse(saved);
    }

    public List<BillResponse> getPendingBills() {
        log.info("Fetching pending bills");
        List<Bill> bills = billRepository.findByStatus(BillStatus.PENDING);
        return bills.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<BillResponse> getBillsByStatus(BillStatus status) {
        log.info("Fetching bills by status: {}", status);
        List<Bill> bills = billRepository.findByStatus(status);
        return bills.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public BillSummaryResponse getBillSummary() {
        log.info("Fetching bill summary");
        BillSummaryResponse summary = new BillSummaryResponse();

        // Using repository methods - handle null values
        Double total = billRepository.sumTotalBills();
        Double pending = billRepository.sumPendingBills();
        Double approved = billRepository.sumApprovedBills();
        Double rejected = billRepository.sumRejectedBills();

        summary.setTotalBills(total != null ? total : 0.0);
        summary.setPending(pending != null ? pending : 0.0);
        summary.setApproved(approved != null ? approved : 0.0);
        summary.setRejected(rejected != null ? rejected : 0.0);

        // Add counts
        summary.setTotalCount(billRepository.count());
        summary.setPendingCount(billRepository.countByStatus(BillStatus.PENDING));
        summary.setApprovedCount(billRepository.countByStatus(BillStatus.APPROVED));
        summary.setRejectedCount(billRepository.countByStatus(BillStatus.REJECTED));

        return summary;
    }

    public BillResponse getBillById(Long billId) {
        log.info("Fetching bill: {}", billId);
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        return mapToResponse(bill);
    }

    public List<BillResponse> getBillsByDateRange(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching bills from {} to {}", startDate, endDate);
        List<Bill> bills = billRepository.findByDueDateBetween(startDate, endDate);
        return bills.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<BillResponse> getBillsByUser(Long userId) {
        log.info("Fetching bills for user: {}", userId);
        List<Bill> bills = billRepository.findByRequestedById(userId);
        return bills.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteBill(Long billId) {
        log.info("Deleting bill: {}", billId);
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        billRepository.delete(bill);
        log.info("Bill deleted: {}", billId);
    }

    private BillResponse mapToResponse(Bill bill) {
        BillResponse response = new BillResponse();
        response.setId(bill.getId());
        response.setBillType(bill.getBillType());
        response.setAmount(bill.getAmount());
        response.setDueDate(bill.getDueDate());
        response.setStatus(bill.getStatus());
        response.setAttachmentUrl(bill.getAttachmentUrl());
        response.setApprovedAt(bill.getApprovedAt());
        response.setRejectedReason(bill.getRejectedReason());

        if (bill.getRequestedBy() != null) {
            response.setRequestedById(bill.getRequestedBy().getId());
            response.setRequestedByName(bill.getRequestedBy().getFullName());
            response.setRequestedBy(bill.getRequestedBy().getUsername());
        }

        if (bill.getApprovedBy() != null) {
            response.setApprovedById(bill.getApprovedBy().getId());
            response.setApprovedByName(bill.getApprovedBy().getFullName());
            response.setApprovedBy(bill.getApprovedBy().getUsername());
        }

        if (bill.getStatus() == BillStatus.PENDING && bill.getDueDate() != null) {
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(bill.getDueDate(), LocalDate.now());
            response.setDaysOverdue(daysOverdue > 0 ? (int) daysOverdue : 0);
        }

        return response;
    }
}