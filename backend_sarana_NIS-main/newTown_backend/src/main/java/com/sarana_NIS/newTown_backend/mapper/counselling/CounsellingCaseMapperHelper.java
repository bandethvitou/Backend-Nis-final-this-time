package com.sarana_NIS.newTown_backend.mapper.counselling;

import com.sarana_NIS.newTown_backend.dto.response.counselling.CounsellingDashboardResponse;
import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingCase;
import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingSession;
import com.sarana_NIS.newTown_backend.model.enums.CaseStatus;
import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CounsellingCaseMapperHelper {

    public Integer countSessions(CounsellingCase counsellingCase) {
        if (counsellingCase == null) return 0;
        // This would require a session repository query
        return 0;
    }

    public LocalDateTime getLastSessionDate(CounsellingCase counsellingCase) {
        if (counsellingCase == null) return null;
        return null;
    }

    public boolean isAtRisk(CounsellingCase counsellingCase) {
        if (counsellingCase == null) return false;
        return counsellingCase.getSeverity() == IssueSeverity.HIGH ||
                counsellingCase.getSeverity() == IssueSeverity.CRITICAL;
    }

    public String generateActionPlan(CounsellingCase counsellingCase) {
        if (counsellingCase == null) return null;
        // Generate action plan based on severity
        return "Action Plan for " + counsellingCase.getStudent().getNameEnglish();
    }

    public CounsellingDashboardResponse toDashboardResponse(
            List<CounsellingCase> cases,
            List<CounsellingSession> sessions) {

        CounsellingDashboardResponse dashboard = new CounsellingDashboardResponse();

        if (cases != null && !cases.isEmpty()) {
            dashboard.setActiveCases((int) cases.stream()
                    .filter(c -> c.getStatus() == CaseStatus.ACTIVE ||
                            c.getStatus() == CaseStatus.IN_PROGRESS)
                    .count());

            dashboard.setResolvedCases((int) cases.stream()
                    .filter(c -> c.getStatus() == CaseStatus.RESOLVED ||
                            c.getStatus() == CaseStatus.CLOSED)
                    .count());

            dashboard.setHighSeverity((int) cases.stream()
                    .filter(c -> c.getSeverity() == IssueSeverity.HIGH ||
                            c.getSeverity() == IssueSeverity.CRITICAL)
                    .count());

            dashboard.setMediumSeverity((int) cases.stream()
                    .filter(c -> c.getSeverity() == IssueSeverity.MEDIUM)
                    .count());

            dashboard.setLowSeverity((int) cases.stream()
                    .filter(c -> c.getSeverity() == IssueSeverity.LOW)
                    .count());

            dashboard.setAtRiskStudents((int) cases.stream()
                    .filter(c -> c.getSeverity() == IssueSeverity.HIGH ||
                            c.getSeverity() == IssueSeverity.CRITICAL)
                    .count());

            dashboard.setTotalSessions(sessions != null ? sessions.size() : 0);
        }

        return dashboard;
    }
}