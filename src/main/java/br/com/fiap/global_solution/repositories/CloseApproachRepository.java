package br.com.fiap.global_solution.repositories;

import br.com.fiap.global_solution.models.Asteroid;
import br.com.fiap.global_solution.models.CloseApproach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface CloseApproachRepository extends JpaRepository<CloseApproach, Long> {

    Page<CloseApproach> findByAsteroid( Asteroid asteroid, Pageable pageable);

    Page<CloseApproach> findByApproachDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    Page<CloseApproach> findByMissDistanceKmLessThan(Double distance, Pageable pageable);


}
