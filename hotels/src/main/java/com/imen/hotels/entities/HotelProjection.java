package com.imen.hotels.entities;

import org.springframework.data.rest.core.config.Projection;

// Leçon 32 : Restreindre les données avec les Projections
// Permet d'appeler : /hotels/rest?projection=nomHotel
// pour ne recevoir que le champ nomHotel au lieu de tous les champs
@Projection(name = "nomHotel", types = { Hotel.class })
public interface HotelProjection {
    String getNomHotel();
}
