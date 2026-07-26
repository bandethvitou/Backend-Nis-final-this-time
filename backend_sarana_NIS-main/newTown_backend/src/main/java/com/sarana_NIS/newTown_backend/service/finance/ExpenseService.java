package com.sarana_NIS.newTown_backend.service.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.ExpenseRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.ExpenseApprovalRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.ExpenseResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.ExpenseSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.finance.Expense;
import com.sarana_NIS.newTown_backend.model.enums.ExpenseCategory;
import com.sarana_NIS.newTown_backend.model.enums.ExpenseStatus;
import com.sarana_NIS.newTown_backend.repository.ExpenseRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private static final Logger log = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest request) {
        log.info("Creating expense: {}", request.getCategory());
        Expense expense = new Expense();
        expense.setCategory(request.getCategory());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setDescription(request.getDescription());
        expense.setReceiptUrl(request.getReceiptUrl());
        expense.setStatus(ExpenseStatus.PENDING);

        if (request.getRequestedBy() != null) {
            User user = userRepository.findById(request.getRequestedBy())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            expense.setRequestedBy(user);
        }

        Expense saved = expenseRepository.save(expense);
        log.info("Expense created: {} - {}", expense.getCategory(), expense.getAmount());
        return mapToResponse(saved);
    }

    @Transactional
    public ExpenseResponse approveExpense(ExpenseApprovalRequest request) {
        log.info("Approving expense: {}", request.getExpenseId());
        Expense expense = expenseRepository.findById(request.getExpenseId())
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (request.getAction().equals("APPROVE")) {
            expense.setStatus(ExpenseStatus.PAID);
        } else {
            expense.setStatus(ExpenseStatus.REJECTED);
            expense.setDescription(expense.getDescription() + " | Rejected: " + request.getReason());
        }

        Expense saved = expenseRepository.save(expense);
        log.info("Expense {}: {}", request.getAction(), expense.getCategory());
        return mapToResponse(saved);
    }

    public ExpenseSummaryResponse getExpenseSummary() {
        log.info("Fetching expense summary");
        ExpenseSummaryResponse summary = new ExpenseSummaryResponse();
        summary.setTotalExpenses(expenseRepository.sumTotalExpenses());
        summary.setPending(expenseRepository.sumPendingExpenses());
        summary.setPaid(expenseRepository.sumPaidExpenses());
        summary.setRejected(expenseRepository.sumRejectedExpenses());
        return summary;
    }

    // ✅ ADD MISSING METHODS

    public List<ExpenseResponse> getPendingExpenses() {
        log.info("Fetching pending expenses");
        List<Expense> expenses = expenseRepository.findPendingApproval();
        return expenses.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ExpenseResponse> getExpensesByStatus(ExpenseStatus status) {
        log.info("Fetching expenses by status: {}", status);
        List<Expense> expenses = expenseRepository.findByStatus(status);
        return expenses.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ExpenseResponse> getExpensesByCategory(ExpenseCategory category) {
        log.info("Fetching expenses by category: {}", category);
        List<Expense> expenses = expenseRepository.findByCategory(category);
        return expenses.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ExpenseResponse getExpenseById(Long expenseId) {
        log.info("Fetching expense: {}", expenseId);
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return mapToResponse(expense);
    }

    public List<ExpenseResponse> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching expenses from {} to {}", startDate, endDate);
        List<Expense> expenses = expenseRepository.findByExpenseDateBetween(startDate, endDate);
        return expenses.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        log.info("Deleting expense: {}", expenseId);
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
        log.info("Expense deleted: {}", expenseId);
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getId());
        response.setCategory(expense.getCategory());
        response.setAmount(expense.getAmount());
        response.setExpenseDate(expense.getExpenseDate());
        response.setDescription(expense.getDescription());
        response.setStatus(expense.getStatus());
        response.setReceiptUrl(expense.getReceiptUrl());
        if (expense.getRequestedBy() != null) {
            response.setRequestedByName(expense.getRequestedBy().getFullName());
            response.setRequestedById(expense.getRequestedBy().getId());
        }
        if (expense.getApprovedBy() != null) {
            response.setApprovedByName(expense.getApprovedBy().getFullName());
            response.setApprovedById(expense.getApprovedBy().getId());
        }
        return response;
    }
}