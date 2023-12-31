package com.example.controle_aerien.DTO;

import com.example.controle_aerien.entities.Vol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VolDTO {

    private Long id;
    private Long aeroportDepartId;
    private Long aeroportArriveeId;
    private VolDTO parentVol;
    private AvionDTO avion;


    public VolDTO(Vol vol) {
        this.id = vol.getId();
        this.aeroportDepartId = vol.getAeroportDepart().getId();
        this.aeroportArriveeId = vol.getAeroportArrivee().getId();
        this.avion = new AvionDTO(vol.getAvion());

        // Convert the parentVol to VolDTO recursively
        if (vol.getParentVol() != null) {
            this.parentVol = new VolDTO(vol.getParentVol());
        }
    }
}
