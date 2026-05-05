package com.imen.hotels.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;

    @NotNull
    @Size(min = 6 , max = 30)
    private String nomHotel;

    @NotNull
    @Size(min = 6 , max = 20)
    private String ville;

    @NotNull
    @Min(value = 50)
    @Max(value = 5000)
    private Double prixNuit;
    
    @Min(value = 1)
    @Max(value = 7)
    private int etoiles;
    
    @ManyToOne
    private TypeHotel typeHotel;

    private String imageName;

    public Hotel() {
        super();
    }

    public Hotel(String nomHotel, String ville, Double prixNuit, int etoiles) {
        super();
        this.nomHotel = nomHotel;
        this.ville = ville;
        this.prixNuit = prixNuit;
        this.etoiles = etoiles;
    }

    public Long getIdHotel() { return idHotel; }
    public void setIdHotel(Long idHotel) { this.idHotel = idHotel; }
    public String getNomHotel() { return nomHotel; }
    public void setNomHotel(String nomHotel) { this.nomHotel = nomHotel; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public Double getPrixNuit() { return prixNuit; }
    public void setPrixNuit(Double prixNuit) { this.prixNuit = prixNuit; }
    public int getEtoiles() { return etoiles; }
    public void setEtoiles(int etoiles) { this.etoiles = etoiles; }
    public TypeHotel getTypeHotel() { return typeHotel; }
    public void setTypeHotel(TypeHotel typeHotel) { this.typeHotel = typeHotel; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    @Override
    public String toString() {
        return "Hotel [idHotel=" + idHotel + ", nomHotel=" + nomHotel + ", ville=" + ville
                + ", prixNuit=" + prixNuit + ", etoiles=" + etoiles + "]";
    }
}