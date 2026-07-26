package com.sarana_NIS.newTown_backend.service.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.SalaryRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.SalaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.SalarySummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.finance.Salary;
import com.sarana_NIS.newTown_backend.model.entity.user.Staff;
import com.sarana_NIS.newTown_backend.model.enums.SalaryStatus;
import com.sarana_NIS.newTown_backend.repository.SalaryRepository;
import com.sarana_NIS.newTown_backend.repository.StaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryService {

    private static final Logger log = LoggerFactory.getLogger(SalaryService.class);

    private final SalaryRepository salaryRepository;
    private final StaffRepository staffRepository;

    public SalaryService(SalaryRepository salaryRepository, StaffRepository staffRepository) {
        this.salaryRepository = salaryRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public SalaryResponse createSalary(SalaryRequest request) {
        log.info("Creating salary for staff: {}", request.getStaffId());
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        Salary salary = new Salary();
        salary.setStaff(staff);
        salary.setMonth(request.getMonth());
        salary.setBaseSalary(request.getBaseSalary() != null ? request.getBaseSalary() : staff.getBaseSalary());
        salary.setAllowances(request.getAllowances() != null ? request.getAllowances() : staff.getAllowances());
        salary.setDeductions(request.getDeductions() != null ? request.getDeductions() : BigDecimal.ZERO);
        salary.setPaymentDate(request.getPaymentDate());
        salary.setStatus(SalaryStatus.PENDING);
        salary.calculateTotal();

        Salary saved = salaryRepository.save(salary);
        log.info("Salary created for staff: {} for month: {}", staff.getStaffId(), request.getMonth());
        return mapToResponse(saved);
    }

    @Transactional
    public SalaryResponse markAsPaid(Long salaryId) {
        log.info("Marking salary as paid: {}", salaryId);
        Salary salary = salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary not found"));

        salary.setStatus(SalaryStatus.PAID);
        salary.setPaymentDate(LocalDate.now());
        Salary saved = salaryRepository.save(salary);
        log.info("Salary marked as paid: {}", salaryId);
        return mapToResponse(saved);
    }

    @Transactional
    public SalaryResponse markAsPending(Long salaryId) {
        log.info("Marking salary as pending: {}", salaryId);
        Salary salary = salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary not found"));

        salary.setStatus(SalaryStatus.PENDING);
        Salary saved = salaryRepository.save(salary);
        log.info("Salary marked as pending: {}", salaryId);
        return mapToResponse(saved);
    }

    @Transactional
    public SalaryResponse updateSalary(Long salaryId, SalaryRequest request) {
        log.info("Updating salary: {}", salaryId);
        Salary salary = salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary not found"));

        if (request.getBaseSalary() != null) {
            salary.setBaseSalary(request.getBaseSalary());
        }
        if (request.getAllowances() != null) {
            salary.setAllowances(request.getAllowances());
        }
        if (request.getDeductions() != null) {
            salary.setDeductions(request.getDeductions());
        }
        if (request.getPaymentDate() != null) {
            salary.setPaymentDate(request.getPaymentDate());
        }
        salary.calculateTotal();

        Salary saved = salaryRepository.save(salary);
        log.info("Salary updated: {}", salaryId);
        return mapToResponse(saved);
    }

    public List<SalaryResponse> getSalariesByStaff(Long staffId) {
        log.info("Fetching salaries for staff: {}", staffId);
        List<Salary> salaries = salaryRepository.findByStaffId(staffId);
        return salaries.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<SalaryResponse> getSalariesByMonth(String month) {
        log.info("Fetching salaries for month: {}", month);
        List<Salary> salaries = salaryRepository.findByMonth(month);
        return salaries.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<SalaryResponse> getSalariesByStaffAndMonth(Long staffId, String month) {
        log.info("Fetching salaries for staff: {} and month: {}", staffId, month);
        List<Salary> salaries = salaryRepository.findByStaffIdAndMonth(staffId, month);
        return salaries.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<SalaryResponse> getSalariesByStatus(SalaryStatus status) {
        log.info("Fetching salaries by status: {}", status);
        List<Salary> salaries = salaryRepository.findByStatus(status);
        return salaries.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<SalaryResponse> getSalariesByMonthAndStatus(String month, SalaryStatus status) {
        log.info("Fetching salaries for month: {} and status: {}", month, status);
        List<Salary> salaries = salaryRepository.findByMonthAndStatus(month, status);
        return salaries.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public SalaryResponse getSalaryById(Long salaryId) {
        log.info("Fetching salary: {}", salaryId);
        Salary salary = salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary not found"));
        return mapToResponse(salary);
    }

    public SalarySummaryResponse getSalarySummary(String month) {
        log.info("Fetching salary summary for month: {}", month);
        SalarySummaryResponse summary = new SalarySummaryResponse();

        // Get sums - handle null values
        BigDecimal total = salaryRepository.sumTotalByMonth(month);
        BigDecimal paid = salaryRepository.sumPaidByMonth(month);
        BigDecimal pending = salaryRepository.sumPendingByMonth(month);

        summary.setTotalSalaries(total != null ? total : BigDecimal.ZERO);
        summary.setPaid(paid != null ? paid : BigDecimal.ZERO);
        summary.setPending(pending != null ? pending : BigDecimal.ZERO);

        // Get counts - handle null values
        Long totalStaff = salaryRepository.countStaffByMonth(month);
        Long paidStaff = salaryRepository.countPaidByMonth(month);
        Long pendingStaff = salaryRepository.countPendingByMonth(month);

        summary.setTotalStaff(totalStaff != null ? totalStaff.intValue() : 0);
        summary.setPaidStaff(paidStaff != null ? paidStaff.intValue() : 0);
        summary.setPendingStaff(pendingStaff != null ? pendingStaff.intValue() : 0);

        return summary;
    }

    public BigDecimal getTotalSalariesForMonth(String month) {
        log.info("Getting total salaries for month: {}", month);
        BigDecimal total = salaryRepository.sumTotalByMonth(month);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional
    public void deleteSalary(Long salaryId) {
        log.info("Deleting salary: {}", salaryId);
        Salary salary = salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary not found"));
        salaryRepository.delete(salary);
        log.info("Salary deleted: {}", salaryId);
    }

    private SalaryResponse mapToResponse(Salary salary) {
        SalaryResponse response = new SalaryResponse();
        response.setId(salary.getId());

        if (salary.getStaff() != null) {
            response.setStaffId(salary.getStaff().getId());
            response.setStaffName(salary.getStaff().getStaffId());

            if (salary.getStaff().getUser() != null) {
                response.setStaffName(salary.getStaff().getUser().getFullName());
            }

            if (salary.getStaff().getDepartment() != null) {
                response.setDepartment(salary.getStaff().getDepartment().getName());
            }
        }

        response.setMonth(salary.getMonth());
        response.setBaseSalary(salary.getBaseSalary());
        response.setAllowances(salary.getAllowances());
        response.setDeductions(salary.getDeductions());
        response.setTotal(salary.getTotal());
        response.setPaymentDate(salary.getPaymentDate());
        response.setStatus(salary.getStatus());

        return response;
    }
}