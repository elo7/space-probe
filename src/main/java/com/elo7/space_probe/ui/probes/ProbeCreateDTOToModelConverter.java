package com.elo7.space_probe.ui.probes;

import com.elo7.space_probe.domain.Planet;
import com.elo7.space_probe.domain.Position;
import com.elo7.space_probe.domain.Probe;
import org.springframework.stereotype.Component;

@Component
class ProbeCreateDTOToModelConverter {

    Probe convert(ProbeCreateDTO probeCreateDTO, Planet planet) {
        return new Probe(probeCreateDTO.name(), new Position(probeCreateDTO.x(), probeCreateDTO.y()), planet);
    }

}
