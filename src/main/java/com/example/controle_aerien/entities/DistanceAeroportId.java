package com.example.controle_aerien.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class
git DistanceAeroportId implements Serializable {

    private static final long serialVersionUID=1L;

    @ManyToOne
    @JoinColumn(name = "aeroport_1_id")
    private Aeroport aeroport1;

    @ManyToOne
    @JoinColumn(name = "aeroport_2_id")
    private Aeroport aeroport2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistanceAeroportId that = (DistanceAeroportId) o;
        return Objects.equals(aeroport1, that.aeroport1) &&
                Objects.equals(aeroport2, that.aeroport2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aeroport1, aeroport2);
    }

}
