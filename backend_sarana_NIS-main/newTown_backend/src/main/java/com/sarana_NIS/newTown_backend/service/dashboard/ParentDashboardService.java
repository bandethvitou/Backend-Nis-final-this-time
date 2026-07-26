package com.sarana_NIS.newTown_backend.service.dashboard;

import com.sarana_NIS.newTown_backend.repository.FeeRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class ParentDashboardService {

    private static final Logger log = LoggerFactory.getLogger(ParentDashboardService.class);

    private final StudentRepository studentRepository;
    private final FeeRepository feeRepository;

    public ParentDashboardService(StudentRepository studentRepository, FeeRepository feeRepository) {
        this.studentRepository = studentRepository;
        this.feeRepository = feeRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDashboardStats() {
        log.info("Fetching parent dashboard statistics");

        Map<String, Object> stats = new HashMap<>();
        stats.put("childrenCount", 0);
        stats.put("totalPaid", 0.0);
        stats.put("totalOutstanding", 0.0);

        return stats;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getChildren() {
        log.info("Fetching parent's children");

        Map<String, Object> children = new HashMap<>();
        children.put("children", new java.util.ArrayList<>());

        return children;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getFees() {
        log.info("Fetching parent fee status");

        Map<String, Object> fees = new HashMap<>();
        fees.put("totalPaid", 0.0);
        fees.put("totalOutstanding", 0.0);
        fees.put("dueFees", new java.util.ArrayList<>());

        return fees;
    }
}