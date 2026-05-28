package br.com.fiap.global_solution.services;

import br.com.fiap.global_solution.models.Asteroid;
import br.com.fiap.global_solution.models.CloseApproach;
import br.com.fiap.global_solution.repositories.CloseApproachRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CloseApproachService {

    private final CloseApproachRepository closeApproachRepository;

    public CloseApproachService(CloseApproachRepository closeApproachRepository) {
        this.closeApproachRepository = closeApproachRepository;
    }

    public Optional<CloseApproach> findById(Long id) {
        return closeApproachRepository.findById(id);
    }

    @Cacheable(value = "closeApproaches")
    public Page<CloseApproach> getCloseApproachs(Pageable pageable) {
        return closeApproachRepository.findAll(pageable);
    }

    @Cacheable(value = "asteroid")
    public Page<CloseApproach> getCloseApproachesByAsteroid(Asteroid asteroid, Pageable pageable) {
        return closeApproachRepository.findByAsteroid(asteroid, pageable);
    }

    @Cacheable(value = "date")
    public Page<CloseApproach> getCloseApproachesByDate(LocalDate start, LocalDate end, Pageable pageable) {
        return closeApproachRepository.findByApproachDateBetween(start, end, pageable);
    }

    @Cacheable(value = "distance")
    public Page<CloseApproach>  getCloseApproachesByDistanceKmLessThan(Double distance, Pageable pageable) {
        return closeApproachRepository.findByMissDistanceKmLessThan(distance, pageable);
    }

    @CacheEvict(value = {"closeApproaches", "asteroid", "date", "distance"}, allEntries = true)
    public CloseApproach addCloseApproach(CloseApproach closeApproach) {
        return closeApproachRepository.save(closeApproach);
    }

    @CacheEvict(value = {"closeApproaches", "asteroid", "date", "distance"}, allEntries = true)
    public CloseApproach updateCloseApproach(Long id, CloseApproach newCloseApproach) {
        var optionalCloseApproach = closeApproachRepository.findById(id);
        if (optionalCloseApproach.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CloseApproach not found");
        newCloseApproach.setId(id);
        return closeApproachRepository.save(newCloseApproach);
    }

    @CacheEvict(value = {"closeApproaches", "asteroid", "date", "distance"}, allEntries = true)
    public void deleteCloseApproach(Long id) {
        var optionalCloseApproach = closeApproachRepository.findById(id);
        if (optionalCloseApproach.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CloseApproach not found");
        closeApproachRepository.deleteById(id);
    }

}
