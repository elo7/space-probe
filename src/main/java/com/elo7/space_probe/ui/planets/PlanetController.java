package com.elo7.space_probe.ui.planets;

import com.elo7.space_probe.app.ResourceNotFoundException;
import com.elo7.space_probe.app.planets.CreatePlanetService;
import com.elo7.space_probe.app.planets.FindAllPlanetService;
import com.elo7.space_probe.app.planets.FindPlanetService;
import com.elo7.space_probe.domain.Planet;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/planets")
class PlanetController {

    private final CreatePlanetService createPlanetService;
    private final FindPlanetService findPlanetService;
    private final FindAllPlanetService findAllPlanetService;
    private final PlanetCreateDTOToModelConverter planetCreateDTOToModelConverter;
    private final PlanetToDTOConverter planetToDtoConverter;

    PlanetController(CreatePlanetService createPlanetService, FindPlanetService findPlanetService, FindAllPlanetService findAllPlanetService, PlanetCreateDTOToModelConverter planetCreateDTOToModelConverter, PlanetToDTOConverter planetToDtoConverter) {
        this.createPlanetService = createPlanetService;
        this.findPlanetService = findPlanetService;
        this.findAllPlanetService = findAllPlanetService;
        this.planetCreateDTOToModelConverter = planetCreateDTOToModelConverter;
        this.planetToDtoConverter = planetToDtoConverter;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<PlanetDTO> findAll() {
        List<Planet> planets = findAllPlanetService.execute();
        return planets.stream().map(planetToDtoConverter::convert).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    PlanetDTO findById(@PathVariable("id") Integer id) {
        Planet planet = findPlanetService.execute(id).orElseThrow(() -> new ResourceNotFoundException("Planet " + id + " not found"));
        return planetToDtoConverter.convert(planet);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    PlanetDTO create(@RequestBody @Valid PlanetCreateDTO planetCreateDTO) {
        Planet planet = planetCreateDTOToModelConverter.convert(planetCreateDTO);
        Planet createdPlanet = createPlanetService.execute(planet);
        return planetToDtoConverter.convert(createdPlanet);
    }
}
