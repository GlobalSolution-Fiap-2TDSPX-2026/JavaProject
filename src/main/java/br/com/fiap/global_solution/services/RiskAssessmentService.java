package br.com.fiap.global_solution.services;

import br.com.fiap.global_solution.enums.RiskLevel;
import br.com.fiap.global_solution.models.Asteroid;
import br.com.fiap.global_solution.models.RiskAssessment;
import br.com.fiap.global_solution.models.RiskZone;
import br.com.fiap.global_solution.repositories.RiskAssessmentRepository;
import br.com.fiap.global_solution.repositories.RiskZoneRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RiskAssessmentService {

    private final RiskZoneRepository riskZoneRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    public RiskAssessmentService(RiskAssessmentRepository repository,  RiskZoneRepository riskZoneRepository) {
        this.riskAssessmentRepository = repository;
        this.riskZoneRepository = riskZoneRepository;
    }

    public Optional<RiskAssessment> findById(Long id) {
        return riskAssessmentRepository.findById(id);
    }

    @Cacheable(value = "riskAssessment")
    public Page<RiskAssessment> getRiskAssessments (Pageable pageable) {
        return  riskAssessmentRepository.findAll(pageable);
    }

    @Cacheable(value = "riskLevel")
    public Page<RiskAssessment> getByRiskLevels (RiskLevel riskLevel, Pageable pageable) {
        return  riskAssessmentRepository.findByRiskLevel(riskLevel, pageable);
    }

    @Cacheable(value = "risksLevel")
    public Page<RiskAssessment> getByListOfRiskLevels (List<RiskLevel> riskLevels, Pageable pageable) {
        return  riskAssessmentRepository.findByRiskLevelIn(riskLevels, pageable);
    }

    @Cacheable(value = "date")
    public Page<RiskAssessment> getByAssessed (LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return  riskAssessmentRepository.findByAssessedAtBetween(start, end, pageable);
    }

    @CacheEvict(value = {"riskAssessment", "riskLevel", "risksLevel", "date"}, allEntries = true)
    public RiskAssessment addRiskAssessment(RiskAssessment riskAssessment) {
        return riskAssessmentRepository.save(riskAssessment);
    }

    @CacheEvict(value = {"riskAssessment", "riskLevel", "risksLevel", "date"}, allEntries = true)
    public void deleteRiskAssessment(Long id) {
        var optionalRiskAssessment = riskAssessmentRepository.findById(id);
        if (optionalRiskAssessment.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RiskAssessment not found");}
        riskAssessmentRepository.deleteById(id);
    }

    @CacheEvict(value = {"riskAssessment", "riskLevel", "risksLevel", "date"}, allEntries = true)
    public RiskAssessment updateRiskAssessment(Long id, RiskAssessment newRiskAssessment) {
        var optionalRiskAssessment = riskAssessmentRepository.findById(id);
        if (optionalRiskAssessment.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RiskAssessment not found");}
        newRiskAssessment.setId(id);
        return riskAssessmentRepository.save(newRiskAssessment);
    }

    public void assessmentImpactRisk(Asteroid asteroid, Double distanceKm){

        if (asteroid.getIsPotentiallyDangerous() == true && distanceKm <= 7500000.0) {
            List<RiskZone> zones = riskZoneRepository.findAll();
            for (RiskZone zone : zones) {
                RiskAssessment alert = new RiskAssessment();
                alert.setAsteroid(asteroid);
                alert.setRiskLevel(RiskLevel.HIGH);
                alert.setAssessedAt(LocalDateTime.now());

                riskAssessmentRepository.save(alert);
            }
        }
    }
}
