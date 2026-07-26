package com.sarana_NIS.newTown_backend.service.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.PaymentRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.PaymentVerificationRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.PaymentResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.PaymentHistoryResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long paymentId);

    List<PaymentResponse> getPaymentsByStudent(Long studentId);

    PaymentHistoryResponse getPaymentHistory(Long studentId);

    List<PaymentResponse> getPendingVerifications();

    PaymentResponse verifyPayment(PaymentVerificationRequest request);

    PaymentResponse rejectPayment(Long paymentId, String reason);

    void deletePayment(Long paymentId);
}