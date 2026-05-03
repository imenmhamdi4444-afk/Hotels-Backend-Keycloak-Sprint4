package com.imen.hotels.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.imen.hotels.entities.Hotel;
import com.imen.hotels.entities.TypeHotel;

import com.imen.hotels.dto.HotelDTO;

public interface HotelService {
    Hotel saveHotel(Hotel hotel);
    Hotel updateHotel(Hotel hotel);
    void deleteHotel(Hotel hotel);
    void deleteHotelById(Long id);
    Hotel getHotel(Long id);
    List<Hotel> getAllHotels();
    Page<Hotel> getAllHotelsParPage(int page, int size);
    List<Hotel> findByNomHotel(String nom);
    List<Hotel> findByNomHotelContains(String nom);
    List<Hotel> findByNomPrix(String nom, Double prix);
    List<Hotel> findByNomPrixParam(String nom, Double prix);
    List<Hotel> findByTypeHotel(TypeHotel typeHotel);
    List<Hotel> findByTypeHotelIdType(Long id);
    List<Hotel> findByOrderByNomHotelAsc();
    List<Hotel> trierHotelsNomsPrix();
    List<TypeHotel> getAllTypeHotels();
    
 // Convertit une entité Hotel vers un DTO (Hotel → HotelDTO)
    HotelDTO convertEntityToDto(Hotel hotel);
    // Convertit un DTO vers une entité Hotel (HotelDTO → Hotel)
    Hotel convertDtoToEntity(HotelDTO hotelDTO);
    // Sauvegarde en recevant un DTO et en retournant un DTO
    HotelDTO saveProduit(HotelDTO hotelDTO);
    // Mise à jour en recevant un DTO et en retournant un DTO
    HotelDTO updateHotelDto(HotelDTO hotelDTO);
    // Retourne un seul hôtel sous forme de DTO
    HotelDTO getHotelDto(Long id);
    // Retourne tous les hôtels sous forme de liste de DTOs
    List<HotelDTO> getAllHotelsDto();
}