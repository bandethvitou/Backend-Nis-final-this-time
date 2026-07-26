package com.sarana_NIS.newTown_backend.service.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.PaymentRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.PaymentVerificationRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.PaymentResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.PaymentHistoryResponse;
import com.sarana_NIS.newTown_backend.model.entity.finance.Payment;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;  // ✅ FIXED: user.Student, not core.Student
import com.sarana_NIS.newTown_backend.model.enums.PaymentStatus;
import com.sarana_NIS.newTown_backend.repository.PaymentRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              StudentRepository studentRepository,
                              UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        log.info("Creating payment for student: {}", request.getStudentId());

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setDescription(request.getDescription());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        log.info("Payment created with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        log.info("Fetching payment: {}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByStudent(Long studentId) {
        log.info("Fetching payments for student: {}", studentId);
        List<Payment> payments = paymentRepository.findByStudentId(studentId);
        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentHistoryResponse getPaymentHistory(Long studentId) {
        log.info("Fetching payment history for student: {}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Payment> payments = paymentRepository.findByStudentIdOrderByPaymentDateDesc(studentId);

        PaymentHistoryResponse response = new PaymentHistoryResponse();
        response.setStudentId(studentId);
        response.setStudentName(student.getUser() != null ? student.getUser().getFullName() : "Unknown");

        List<PaymentHistoryResponse.PaymentHistoryItem> historyItems = payments.stream()
                .map(this::mapToHistoryItem)
                .collect(Collectors.toList());
        response.setPayments(historyItems);

        double totalPaid = payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID)
                .mapToDouble(Payment::getAmount)
                .sum();
        response.setTotalPaid(totalPaid);

        double totalPending = payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.PENDING)
                .mapToDouble(Payment::getAmount)
                .sum();
        response.setTotalPending(totalPending);

        double totalOverdue = payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.OVERDUE)
                .mapToDouble(Payment::getAmount)
                .sum();
        response.setTotalOverdue(totalOverdue);

        return response;
    }

    @Override
    public List<PaymentResponse> getPendingVerifications() {
        log.info("Fetching pending verifications");
        List<Payment> payments = paymentRepository.findByStatus(PaymentStatus.PENDING);
        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse verifyPayment(PaymentVerificationRequest request) {
        log.info("Verifying payment: {}", request.getPaymentId());

        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException("Payment is not in pending status");
        }

        payment.setStatus(PaymentStatus.PAID);
        payment.setVerifiedBy(request.getVerifiedBy());
        payment.setVerifiedDate(LocalDateTime.now());
        payment.setNotes(request.getNotes());
        payment.setUpdatedAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        log.info("Payment verified: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public PaymentResponse rejectPayment(Long paymentId, String reason) {
        log.info("Rejecting payment: {} with reason: {}", paymentId, reason);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException("Payment is not in pending status");
        }

        payment.setStatus(PaymentStatus.REJECTED);
        payment.setRejectionReason(reason);
        payment.setUpdatedAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        log.info("Payment rejected: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public void deletePayment(Long paymentId) {
        log.info("Deleting payment: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Cannot delete a paid payment");
        }

        paymentRepository.delete(payment);
        log.info("Payment deleted: {}", paymentId);
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    private PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setStudentId(payment.getStudent().getId());

        if (payment.getStudent().getUser() != null) {
            response.setStudentName(payment.getStudent().getUser().getFullName());
            response.setStudentEmail(payment.getStudent().getUser().getEmail());
        }

        response.setAmount(payment.getAmount());
        response.setPaymentDate(payment.getPaymentDate());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setDescription(payment.getDescription());
        response.setReferenceNumber(payment.getReferenceNumber());
        response.setStatus(payment.getStatus());
        response.setVerifiedBy(payment.getVerifiedBy());
        response.setVerifiedDate(payment.getVerifiedDate());
        response.setNotes(payment.getNotes());
        response.setRejectionReason(payment.getRejectionReason());
        response.setCreatedAt(payment.getCreatedAt());
        response.setUpdatedAt(payment.getUpdatedAt());

        return response;
    }

    private PaymentHistoryResponse.PaymentHistoryItem mapToHistoryItem(Payment payment) {
        PaymentHistoryResponse.PaymentHistoryItem item = new PaymentHistoryResponse.PaymentHistoryItem();
        item.setId(payment.getId());
        item.setAmount(payment.getAmount());
        item.setPaymentDate(payment.getPaymentDate());
        item.setPaymentMethod(payment.getPaymentMethod());
        item.setDescription(payment.getDescription());
        item.setStatus(payment.getStatus());
        item.setReferenceNumber(payment.getReferenceNumber());
        return item;
    }
}