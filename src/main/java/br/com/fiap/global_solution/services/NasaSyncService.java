package br.com.fiap.global_solution.services;

import br.com.fiap.global_solution.dtos.nasa.NasaAsteroid;
import br.com.fiap.global_solution.dtos.nasa.NasaResponse;
import br.com.fiap.global_solution.models.Asteroid;
import br.com.fiap.global_solution.repositories.AsteroidRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class NasaSyncService {

    private final RestTemplate restTemplate;
    private final AsteroidRepository asteroidRepository;

    @Value("${nasa.api.key}")
    private String apiKey;

    public NasaSyncService(RestTemplate restTemplate, AsteroidRepository asteroidRepository) {
        this.restTemplate = restTemplate;
        this.asteroidRepository = asteroidRepository;
    }

    public void sincronizarAsteroidesDeHoje() {
        String hoje = LocalDate.now().toString();
        String url = "https://api.nasa.gov/neo/rest/v1/feed?start_date=" + hoje + "&end_date=" + hoje + "&api_key=" + apiKey;

        NasaResponse respostaNasa = restTemplate.getForObject(url, NasaResponse.class);

        if (respostaNasa != null && respostaNasa.nearEarthObjects() != null) {
            List<NasaAsteroid> asteroidesDeHoje = respostaNasa.nearEarthObjects().get(hoje);

            if (asteroidesDeHoje != null) {
                for (NasaAsteroid nasaDto : asteroidesDeHoje) {

                    // Transforma o DTO da NASA na tua Entidade
                    Asteroid asteroid = new Asteroid();
                    asteroid.setNasaId(nasaDto.id());
                    asteroid.setName(nasaDto.name());
                    asteroid.setIsPotentiallyDangerous(nasaDto.isPotentiallyHazardousAsteroid());

                    // Guarda no teu banco de dados
                    asteroidRepository.save(asteroid);

                    if (nasaDto.closeApproachData() != null && !nasaDto.closeApproachData().isEmpty()) {

                        String kmString = nasaDto.closeApproachData().get(0).missDistance().toString();
                    }

                }
            }
        }
}
}
