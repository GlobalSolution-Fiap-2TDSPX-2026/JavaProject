package br.com.fiap.global_solution.dtos;

import br.com.fiap.global_solution.models.CloseApproach;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CloseApproachRequest(

        @NotNull(message = "asteroidId is required")
        Long asteroidId,

        @NotNull(message = "approachDate is required")
        LocalDate approachDate,

        @NotNull(message = "missDistanceKm is required")
        Double missDistanceKm,

        @NotNull(message = "relativeVelocityKmH is required")
        Double relativeVelocityKmH,

        @NotBlank(message = "orbitingBody is required")
        String orbitingBody
) {
    public CloseApproach toEntity() {
        return CloseApproach.builder()
                .missDistanceKm(missDistanceKm)
                .approachDate(approachDate)
                .relativeVelocityKmH(relativeVelocityKmH)
                .orbitingBody(orbitingBody)
                .build();
    }
}