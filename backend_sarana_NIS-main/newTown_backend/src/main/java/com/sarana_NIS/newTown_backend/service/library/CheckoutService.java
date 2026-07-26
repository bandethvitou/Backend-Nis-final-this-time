package com.sarana_NIS.newTown_backend.service.library;

import com.sarana_NIS.newTown_backend.dto.request.library.CheckoutRequest;
import com.sarana_NIS.newTown_backend.dto.request.library.ReturnBookRequest;
import com.sarana_NIS.newTown_backend.dto.response.library.CheckoutResponse;
import com.sarana_NIS.newTown_backend.dto.response.library.OverdueBookResponse;
import com.sarana_NIS.newTown_backend.model.entity.library.Book;
import com.sarana_NIS.newTown_backend.model.entity.library.Checkout;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.BookStatus;
import com.sarana_NIS.newTown_backend.model.enums.CheckoutStatus;
import com.sarana_NIS.newTown_backend.repository.BookRepository;
import com.sarana_NIS.newTown_backend.repository.CheckoutRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private static final Logger log = LoggerFactory.getLogger(CheckoutService.class);

    private final CheckoutRepository checkoutRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public CheckoutService(CheckoutRepository checkoutRepository, BookRepository bookRepository,
                           StudentRepository studentRepository) {
        this.checkoutRepository = checkoutRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public CheckoutResponse checkoutBook(CheckoutRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // ✅ Fix: Use availableCopies instead of available
        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies of this book");
        }

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Checkout> active = checkoutRepository.findActiveByStudentId(request.getStudentId());
        if (active.stream().anyMatch(c -> c.getBook().getId().equals(request.getBookId()))) {
            throw new RuntimeException("Student already has this book checked out");
        }

        if (active.size() >= 5) {
            throw new RuntimeException("Student has reached maximum book limit (5)");
        }

        Checkout checkout = new Checkout();
        checkout.setBook(book);
        checkout.setStudent(student);
        checkout.setCheckoutDate(LocalDate.now());
        checkout.setDueDate(request.getDueDate());
        checkout.setStatus(CheckoutStatus.CHECKED_OUT);

        // ✅ Fix: Use availableCopies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Checkout saved = checkoutRepository.save(checkout);
        log.info("Book checked out: {} to student: {}", book.getTitle(), student.getNameEnglish());
        return mapToResponse(saved);
    }

    @Transactional
    public CheckoutResponse returnBook(ReturnBookRequest request) {
        Checkout checkout = checkoutRepository.findById(request.getCheckoutId())
                .orElseThrow(() -> new RuntimeException("Checkout record not found"));

        checkout.setReturnDate(request.getReturnDate() != null ?
                request.getReturnDate() : LocalDate.now());
        checkout.setStatus(CheckoutStatus.RETURNED);

        if (checkout.getDueDate().isBefore(checkout.getReturnDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(checkout.getDueDate(), checkout.getReturnDate());
            BigDecimal fine = BigDecimal.valueOf(daysOverdue * 0.50);
            checkout.setFineAmount(fine);
            checkout.setFinePaid(request.getFinePaid() != null && request.getFinePaid());
        }

        Book book = checkout.getBook();
        // ✅ Fix: Use availableCopies
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        Checkout saved = checkoutRepository.save(checkout);
        log.info("Book returned: {}", book.getTitle());
        return mapToResponse(saved);
    }

    public List<CheckoutResponse> getActiveCheckoutsByStudent(Long studentId) {
        List<Checkout> checkouts = checkoutRepository.findActiveByStudentId(studentId);
        return checkouts.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<OverdueBookResponse> getOverdueBooks() {
        List<Checkout> overdue = checkoutRepository.findOverdueCheckouts(LocalDate.now());
        return overdue.stream().map(this::mapToOverdueResponse).collect(Collectors.toList());
    }

    public CheckoutResponse getCheckoutById(Long checkoutId) {
        Checkout checkout = checkoutRepository.findById(checkoutId)
                .orElseThrow(() -> new RuntimeException("Checkout not found"));
        return mapToResponse(checkout);
    }

    public List<CheckoutResponse> getCheckoutHistoryByStudent(Long studentId) {
        List<Checkout> checkouts = checkoutRepository.findByStudentId(studentId);
        return checkouts.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void markOverdueFines() {
        List<Checkout> overdue = checkoutRepository.findOverdueCheckouts(LocalDate.now());
        for (Checkout checkout : overdue) {
            if (checkout.getFineAmount() == null) {
                long daysOverdue = ChronoUnit.DAYS.between(checkout.getDueDate(), LocalDate.now());
                BigDecimal fine = BigDecimal.valueOf(daysOverdue * 0.50);
                checkout.setFineAmount(fine);
                checkout.setStatus(CheckoutStatus.OVERDUE);
                checkoutRepository.save(checkout);
            }
        }
        log.info("Marked {} overdue checkouts with fines", overdue.size());
    }

    private CheckoutResponse mapToResponse(Checkout checkout) {
        CheckoutResponse response = new CheckoutResponse();
        response.setId(checkout.getId());
        response.setBookId(checkout.getBook().getId());
        response.setBookTitle(checkout.getBook().getTitle());
        response.setBookIsbn(checkout.getBook().getIsbn());
        response.setStudentId(checkout.getStudent().getId());
        response.setStudentName(checkout.getStudent().getNameEnglish());
        response.setStudentClass(checkout.getStudent().getCurrentClass() != null ?
                checkout.getStudent().getCurrentClass().getName() : null);
        response.setCheckoutDate(checkout.getCheckoutDate());
        response.setDueDate(checkout.getDueDate());
        response.setReturnDate(checkout.getReturnDate());
        response.setStatus(checkout.getStatus());
        response.setFineAmount(checkout.getFineAmount());
        response.setFinePaid(checkout.getFinePaid());

        if (checkout.getStatus() == CheckoutStatus.CHECKED_OUT) {
            response.setStatusColor("BLUE");
        } else if (checkout.getStatus() == CheckoutStatus.OVERDUE) {
            response.setStatusColor("RED");
        } else if (checkout.getStatus() == CheckoutStatus.RETURNED) {
            response.setStatusColor("GREEN");
        } else {
            response.setStatusColor("GREY");
        }

        if (checkout.getStatus() == CheckoutStatus.CHECKED_OUT || checkout.getStatus() == CheckoutStatus.OVERDUE) {
            LocalDate now = LocalDate.now();
            if (checkout.getDueDate().isAfter(now)) {
                response.setDaysRemaining((int) ChronoUnit.DAYS.between(now, checkout.getDueDate()));
                response.setDaysOverdue(0);
            } else {
                response.setDaysRemaining(0);
                response.setDaysOverdue((int) ChronoUnit.DAYS.between(checkout.getDueDate(), now));
            }
        }
        return response;
    }

    private OverdueBookResponse mapToOverdueResponse(Checkout checkout) {
        OverdueBookResponse response = new OverdueBookResponse();
        response.setCheckoutId(checkout.getId());
        response.setBookTitle(checkout.getBook().getTitle());
        response.setBookIsbn(checkout.getBook().getIsbn());
        response.setStudentName(checkout.getStudent().getNameEnglish());
        response.setStudentId(checkout.getStudent().getStudentId());
        response.setClassName(checkout.getStudent().getCurrentClass() != null ?
                checkout.getStudent().getCurrentClass().getName() : null);
        response.setCheckoutDate(checkout.getCheckoutDate());
        response.setDueDate(checkout.getDueDate());
        response.setDaysOverdue((int) ChronoUnit.DAYS.between(checkout.getDueDate(), LocalDate.now()));
        response.setFineAmount(checkout.getFineAmount());
        response.setFinePaid(checkout.getFinePaid() != null && checkout.getFinePaid());
        response.setStatus("OVERDUE");
        response.setReminderCount(0);
        return response;
    }
}