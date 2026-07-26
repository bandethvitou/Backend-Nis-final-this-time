package com.sarana_NIS.newTown_backend.service.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.TourRequest;
import com.sarana_NIS.newTown_backend.dto.response.admissions.TourResponse;
import com.sarana_NIS.newTown_backend.mapper.admissions.TourMapper;
import com.sarana_NIS.newTown_backend.model.entity.admissions.Tour;
import com.sarana_NIS.newTown_backend.model.enums.TourStatus;
import com.sarana_NIS.newTown_backend.repository.TourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    private static final Logger log = LoggerFactory.getLogger(TourService.class);

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    public TourService(TourRepository tourRepository, TourMapper tourMapper) {
        this.tourRepository = tourRepository;
        this.tourMapper = tourMapper;
    }

    @Transactional
    public TourResponse createTour(TourRequest request) {
        Tour tour = tourMapper.toEntity(request);
        tour.setStatus(TourStatus.SCHEDULED);
        Tour saved = tourRepository.save(tour);
        log.info("Tour scheduled for: {}", tour.getParentName());
        return tourMapper.toResponse(saved);
    }

    @Transactional
    public TourResponse updateTourStatus(Long tourId, TourStatus status) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        tour.setStatus(status);
        Tour saved = tourRepository.save(tour);
        log.info("Tour status updated: {} -> {}", tourId, status);
        return tourMapper.toResponse(saved);
    }

    @Transactional
    public TourResponse completeTour(Long tourId) {
        return updateTourStatus(tourId, TourStatus.COMPLETED);
    }

    @Transactional
    public TourResponse cancelTour(Long tourId) {
        return updateTourStatus(tourId, TourStatus.CANCELLED);
    }

    public TourResponse getTourById(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        return tourMapper.toResponse(tour);
    }

    public List<TourResponse> getToursByStatus(TourStatus status) {
        List<Tour> tours = tourRepository.findByStatus(status);
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    public List<TourResponse> getToursByDate(LocalDate date) {
        List<Tour> tours = tourRepository.findByTourDate(date);
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    public List<TourResponse> getToursByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Tour> tours = tourRepository.findByTourDateBetween(startDate, endDate);
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    public List<TourResponse> getScheduledToursForDate(LocalDate date) {
        List<Tour> tours = tourRepository.findScheduledToursForDate(date);
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    public List<TourResponse> getUpcomingTours() {
        List<Tour> tours = tourRepository.findUpcomingTours(LocalDate.now());
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    public List<TourResponse> searchTours(String name) {
        List<Tour> tours = tourRepository.searchTours(name);
        return tours.stream().map(tourMapper::toResponse).collect(Collectors.toList());
    }

    public long countScheduledTours() {
        return tourRepository.countScheduledTours();
    }

    public long countCompletedTours() {
        return tourRepository.countCompletedTours();
    }

    @Transactional
    public void deleteTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        tourRepository.delete(tour);
        log.info("Tour deleted: {}", tourId);
    }
}