package br.com.fiap.global_solution.dtos;

import br.com.fiap.global_solution.models.Asteroid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AsteroidRequest(

        @NotBlank(message = "nasaId is required")
        String nasaId,

        @NotBlank(message = "name is required")
        String name,

        @NotNull(message = "isPotentiallyDangerous is required")
        Boolean isPotentiallyDangerous
) {
    public Asteroid toEntity() {
        return Asteroid.builder()
                .nasaId(nasaId)
                .name(name)
                .isPotentiallyDangerous(isPotentiallyDangerous)
                .build();
    }
}