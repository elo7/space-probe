package com.elo7.space_probe.ui.probes;

import com.elo7.space_probe.app.ResourceNotFoundException;
import com.elo7.space_probe.app.planets.FindPlanetService;
import com.elo7.space_probe.app.probes.CreateProbeService;
import com.elo7.space_probe.app.probes.FindAllProbeService;
import com.elo7.space_probe.app.probes.FindProbeService;
import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.domain.Probe;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/probes")
class ProbeController {

    private final CreateProbeService createProbeService;
    private final FindProbeService findProbeService;
    private final FindPlanetService findPlanetService;
    private final FindAllProbeService findAllProbeService;
    private final ProbeCreateDTOToModelConverter probeCreateDTOToModelConverter;
    private final ProbeToDTOConverter probeToDtoConverter;

    ProbeController(CreateProbeService createProbeService, FindProbeService findProbeService, FindPlanetService findPlanetService, FindAllProbeService findAllProbeService, ProbeCreateDTOToModelConverter probeCreateDTOToModelConverter, ProbeToDTOConverter probeToDtoConverter) {
        this.createProbeService = createProbeService;
        this.findProbeService = findProbeService;
        this.findPlanetService = findPlanetService;
        this.findAllProbeService = findAllProbeService;
        this.probeCreateDTOToModelConverter = probeCreateDTOToModelConverter;
        this.probeToDtoConverter = probeToDtoConverter;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<ProbeDTO> findAll() {
        List<Probe> probes = findAllProbeService.execute();
        return probes.stream().map(probeToDtoConverter::convert).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    ProbeDTO findById(@PathVariable("id") Integer id) {
        Probe probe = findProbeService.execute(id).orElseThrow(() -> new ResourceNotFoundException("Probe " + id + " not found"));
        return probeToDtoConverter.convert(probe);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ProbeDTO create(@RequestBody @Valid ProbeCreateDTO dto) {
        Planet planet = findPlanetService.execute(dto.planetId())
                .orElseThrow(() -> new ResourceNotFoundException("Planet " + dto.planetId() + " not found"));

        Probe probe = probeCreateDTOToModelConverter.convert(dto, planet);

        Probe createdProbe = createProbeService.execute(probe);

        return probeToDtoConverter.convert(createdProbe);
    }

}
