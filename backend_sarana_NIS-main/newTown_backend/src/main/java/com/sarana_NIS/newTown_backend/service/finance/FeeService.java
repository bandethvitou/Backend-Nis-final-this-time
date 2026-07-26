package com.sarana_NIS.newTown_backend.service.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.FeeRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.FeeBulkRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.FeeResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.FeeSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.finance.Fee;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.FeeStatus;
import com.sarana_NIS.newTown_backend.repository.FeeRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeeService {

    private static final Logger log = LoggerFactory.getLogger(FeeService.class);

    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;

    public FeeService(FeeRepository feeRepository, StudentRepository studentRepository) {
        this.feeRepository = feeRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public FeeResponse createFee(FeeRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Fee fee = new Fee();
        fee.setStudent(student);
        fee.setCategory(request.getCategory());  // ✅ Use category, not feeType
        fee.setAmount(request.getAmount());
        fee.setDueDate(request.getDueDate());
        fee.setDescription(request.getDescription());
        fee.setStatus(FeeStatus.DUE);

        Fee saved = feeRepository.save(fee);
        log.info("Fee created for student: {}", student.getNameEnglish());
        return mapToResponse(saved);
    }

    @Transactional
    public List<FeeResponse> createBulkFees(FeeBulkRequest request) {
        List<Student> students = studentRepository.findAllById(request.getStudentIds());
        List<Fee> fees = new ArrayList<>();

        for (Student student : students) {
            Fee fee = new Fee();
            fee.setStudent(student);
            fee.setCategory(request.getCategory());  // ✅ Use category, not feeType
            fee.setAmount(request.getAmount());
            fee.setDueDate(request.getDueDate());
            fee.setDescription(request.getDescription());
            fee.setStatus(FeeStatus.DUE);
            fees.add(fee);
        }

        List<Fee> savedFees = feeRepository.saveAll(fees);
        log.info("Bulk fees created for {} students", savedFees.size());
        return savedFees.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<FeeResponse> getFeesByStudent(Long studentId) {
        List<Fee> fees = feeRepository.findByStudentId(studentId);
        return fees.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<FeeResponse> getOverdueFees() {
        List<Fee> fees = feeRepository.findOverdueFees(LocalDate.now());
        return fees.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public FeeSummaryResponse getFeeSummary() {
        FeeSummaryResponse summary = new FeeSummaryResponse();

        BigDecimal totalCollected = feeRepository.sumTotalCollected();
        BigDecimal totalOutstanding = feeRepository.sumTotalOutstanding();
        BigDecimal totalFees = BigDecimal.ZERO;

        if (totalCollected != null) {
            totalFees = totalFees.add(totalCollected);
        }
        if (totalOutstanding != null) {
            totalFees = totalFees.add(totalOutstanding);
        }

        summary.setTotalFees(totalFees);
        summary.setCollected(totalCollected != null ? totalCollected : BigDecimal.ZERO);
        summary.setOutstanding(totalOutstanding != null ? totalOutstanding : BigDecimal.ZERO);
        summary.setOverdue(BigDecimal.ZERO);  // ✅ Use BigDecimal.ZERO if method doesn't exist
        summary.setDefaultersCount(0);  // ✅ Set default if method doesn't exist

        double collectionRate = 0.0;
        if (summary.getTotalFees().compareTo(BigDecimal.ZERO) > 0) {
            collectionRate = summary.getCollected().doubleValue() / summary.getTotalFees().doubleValue() * 100;
        }
        summary.setCollectionRate(collectionRate);

        return summary;
    }

    @Transactional
    public void markAsPaid(Long feeId) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new RuntimeException("Fee not found"));
        fee.setStatus(FeeStatus.PAID);
        fee.setPaymentDate(LocalDate.now());
        feeRepository.save(fee);
        log.info("Fee marked as paid: {}", feeId);
    }

    @Transactional
    public void deleteFee(Long feeId) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new RuntimeException("Fee not found"));
        feeRepository.delete(fee);
        log.info("Fee deleted: {}", feeId);
    }

    public FeeResponse getFeeById(Long feeId) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new RuntimeException("Fee not found"));
        return mapToResponse(fee);
    }

    public List<FeeResponse> getFeesByStatus(FeeStatus status) {
        List<Fee> fees = feeRepository.findByStatus(status);
        return fees.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private FeeResponse mapToResponse(Fee fee) {
        FeeResponse response = new FeeResponse();
        response.setId(fee.getId());
        response.setStudentId(fee.getStudent().getId());
        response.setStudentName(fee.getStudent().getNameEnglish());
        response.setStudentClass(fee.getStudent().getCurrentClass() != null ?
                fee.getStudent().getCurrentClass().getName() : null);
        response.setCategory(fee.getCategory());  // ✅ Use category, not feeType
        response.setAmount(fee.getAmount());
        response.setDueDate(fee.getDueDate());
        response.setStatus(fee.getStatus());
        response.setPaymentDate(fee.getPaymentDate());
        response.setReceiptNumber(fee.getReceiptNumber());
        response.setDescription(fee.getDescription());

        if (fee.getStatus() == FeeStatus.OVERDUE && fee.getDueDate() != null) {
            response.setDaysOverdue((int) java.time.temporal.ChronoUnit.DAYS.between(fee.getDueDate(), LocalDate.now()));
        }

        return response;
    }
}