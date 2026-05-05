package com.imen.hotels.dto;
import com.imen.hotels.entities.TypeHotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder → permet : HotelDTO.builder().nomHotel("...").build()
@Builder
public class HotelDTO {
    private Long idHotel;
    private String nomHotel;
    private String villeHotel;
    // On CHOISIT d'inclure prixNuit.
    // Si on voulait le cacher (données sensibles), on ne l'inclurait pas.
    private Double prixNuit;
    private int etoiles;
    // Au lieu de l'objet TypeHotel entier avec tous ses champs,
    // on met JUSTE le nom du type → "aplatit" la structure JSON
    // Avant DTO : "typeHotel": { "idType": 1, "nomType": "Boutique", "hotels":
    // [...] }
    // Après DTO : "nomType": "Boutique"
    private String nomType;
    private TypeHotel typeHotel;
    private String imageName;
}