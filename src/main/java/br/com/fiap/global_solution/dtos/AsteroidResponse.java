package br.com.fiap.global_solution.dtos;

import br.com.fiap.global_solution.models.Asteroid;

public record AsteroidResponse(
        Long id,
        String nasaId,
        String name,
        Boolean isPotentiallyDangerous
) {
    public static AsteroidResponse fromEntity(Asteroid a) {
        return new AsteroidResponse(
                a.getId(),
                a.getNasaId(),
                a.getName(),
                a.getIsPotentiallyDangerous()
        );

    }
}