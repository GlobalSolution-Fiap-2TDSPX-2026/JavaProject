package br.com.fiap.global_solution.dtos;

import br.com.fiap.global_solution.models.RiskZone;

public record RiskZoneResponse(
        Long id,
        String regionName,
        Double latitude,
        Double longitude
){

    public static RiskZoneResponse fromEntity(RiskZone rZ) {
        return new RiskZoneResponse(
                rZ.getId(),
                rZ.getRegionName(),
                rZ.getLatitude(),
                rZ.getLongitude()
        );
    }
}