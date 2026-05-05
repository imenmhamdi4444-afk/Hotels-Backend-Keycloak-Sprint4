package com.imen.hotels.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imen.hotels.entities.Hotel;
import com.imen.hotels.entities.TypeHotel;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Leçon 29-30 : Spring Data REST
// Expose automatiquement les endpoints CRUD via /hotels/rest
@RepositoryRestResource(path = "rest")
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    //  find by name
    List<Hotel> findByNomHotel(String nom);
    List<Hotel> findByNomHotelContains(String nom);

    // find by name and price
 // Version 1 - with ?1 ?2
    @Query("select h from Hotel h where h.nomHotel like %?1 and h.prixNuit > ?2")
    List<Hotel> findByNomPrix(String nom, Double prix);

    // Version 2 - with @Param (optimized)
    @Query("select h from Hotel h where h.nomHotel like %:nom and h.prixNuit > :prix")
    List<Hotel> findByNomPrixParam(@Param("nom") String nom, @Param("prix") Double prix);

    //  find by typeHotel entity
    @Query("select h from Hotel h where h.typeHotel = ?1")
    List<Hotel> findByTypeHotel(TypeHotel typeHotel);

    //  find by typeHotel id
    List<Hotel> findByTypeHotelIdType(Long id);

    //  sort by name
    List<Hotel> findByOrderByNomHotelAsc();

    //  sort by name and price
    @Query("select h from Hotel h order by h.nomHotel ASC, h.prixNuit DESC")
    List<Hotel> trierHotelsNomsPrix();
}