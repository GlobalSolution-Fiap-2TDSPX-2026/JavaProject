package br.com.fiap.global_solution.dtos;

import br.com.fiap.global_solution.models.RiskZone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RiskZoneRequest(

        @NotBlank(message = "name is required")
        String regionName,

        @NotNull(message = "latitude is required")
        Double latitude,

        @NotNull(message = "longitude is required")
        Double longitude
) {
    public RiskZone toEntity() {
        return RiskZone.builder()
                .regionName(regionName)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}