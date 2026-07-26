package com.sarana_NIS.newTown_backend.service.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.VisitorCheckoutRequest;
import com.sarana_NIS.newTown_backend.dto.request.admissions.VisitorRequest;
import com.sarana_NIS.newTown_backend.dto.response.admissions.VisitorPassResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.VisitorResponse;
import com.sarana_NIS.newTown_backend.mapper.admissions.VisitorMapper;
import com.sarana_NIS.newTown_backend.model.entity.admissions.Visitor;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.VisitorStatus;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import com.sarana_NIS.newTown_backend.repository.VisitorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorService {

    private static final Logger log = LoggerFactory.getLogger(VisitorService.class);

    private final VisitorRepository visitorRepository;
    private final UserRepository userRepository;
    private final VisitorMapper visitorMapper;

    public VisitorService(VisitorRepository visitorRepository, UserRepository userRepository, VisitorMapper visitorMapper) {
        this.visitorRepository = visitorRepository;
        this.userRepository = userRepository;
        this.visitorMapper = visitorMapper;
    }

    @Transactional
    public VisitorResponse registerVisitor(VisitorRequest request) {
        Visitor visitor = visitorMapper.toEntity(request);

        if (request.getHostId() != null) {
            User host = userRepository.findById(request.getHostId())
                    .orElseThrow(() -> new RuntimeException("Host not found"));
            visitor.setHost(host);
        }

        String passNumber = generatePassNumber();
        visitor.setPassNumber(passNumber);
        visitor.setCheckIn(LocalDateTime.now());
        visitor.setStatus(VisitorStatus.ACTIVE);

        Visitor saved = visitorRepository.save(visitor);
        log.info("Visitor registered: {} (Pass: {})", visitor.getName(), passNumber);
        return visitorMapper.toResponse(saved);
    }

    @Transactional
    public VisitorResponse checkoutVisitor(VisitorCheckoutRequest request) {
        Visitor visitor = visitorRepository.findById(request.getVisitorId())
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        if (visitor.getStatus() == VisitorStatus.CHECKED_OUT) {
            throw new RuntimeException("Visitor already checked out");
        }

        visitor.setStatus(VisitorStatus.CHECKED_OUT);
        visitor.setCheckOut(request.getCheckoutTime() != null ? request.getCheckoutTime() : LocalDateTime.now());

        Visitor saved = visitorRepository.save(visitor);
        log.info("Visitor checked out: {}", visitor.getName());
        return visitorMapper.toResponse(saved);
    }

    public VisitorResponse getVisitorById(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        return visitorMapper.toResponse(visitor);
    }

    public VisitorPassResponse getVisitorByPassNumber(String passNumber) {
        Visitor visitor = visitorRepository.findByPassNumber(passNumber)
                .orElseThrow(() -> new RuntimeException("Visitor not found with pass: " + passNumber));
        return visitorMapper.toPassResponse(visitor);
    }

    public List<VisitorResponse> getActiveVisitors() {
        List<Visitor> visitors = visitorRepository.findActiveVisitors();
        return visitors.stream().map(visitorMapper::toResponse).collect(Collectors.toList());
    }

    public List<VisitorResponse> getVisitorsByHost(Long hostId) {
        List<Visitor> visitors = visitorRepository.findByHostId(hostId);
        return visitors.stream().map(visitorMapper::toResponse).collect(Collectors.toList());
    }

    public List<VisitorResponse> getVisitorsByDateRange(LocalDateTime start, LocalDateTime end) {
        List<Visitor> visitors = visitorRepository.findByCheckInBetween(start, end);
        return visitors.stream().map(visitorMapper::toResponse).collect(Collectors.toList());
    }

    public List<VisitorResponse> searchVisitors(String name) {
        List<Visitor> visitors = visitorRepository.searchVisitors(name);
        return visitors.stream().map(visitorMapper::toResponse).collect(Collectors.toList());
    }

    public List<VisitorResponse> findVisitorsWithNoCheckout() {
        List<Visitor> visitors = visitorRepository.findVisitorsWithNoCheckout(LocalDateTime.now().minusHours(4));
        return visitors.stream().map(visitorMapper::toResponse).collect(Collectors.toList());
    }

    public long countActiveVisitors() {
        return visitorRepository.countActiveVisitors();
    }

    public long countVisitorsToday() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return visitorRepository.countVisitorsBetween(start, end);
    }

    @Transactional
    public void deleteVisitor(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        visitorRepository.delete(visitor);
        log.info("Visitor deleted: {}", visitorId);
    }

    private String generatePassNumber() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = visitorRepository.count() + 1;
        return "VIS-" + datePart + "-" + String.format("%04d", count);
    }
}