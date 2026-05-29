package br.com.fiap.global_solution.dtos.nasa;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record NasaResponse(
        @JsonProperty("element_count") int elementCount,
        @JsonProperty("near_earth_objects") Map<String, List<NasaAsteroid>> nearEarthObjects
) {
}
