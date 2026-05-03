package com.imen.hotels.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.imen.hotels.entities.Hotel;
import com.imen.hotels.entities.TypeHotel;
import com.imen.hotels.repos.HotelRepository;
import com.imen.hotels.repos.TypeHotelRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import com.imen.hotels.dto.HotelDTO;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	ModelMapper modelMapper;
	@Override
	public HotelDTO convertEntityToDto(Hotel hotel) {
	// ModelMapper copie automatiquement les champs de même nom
	// hotel.idHotel → dto.idHotel
	// hotel.nomHotel → dto.nomHotel
	// hotel.ville → dto.ville etc.
	HotelDTO dto = modelMapper.map(hotel, HotelDTO.class);
	// Mapping manuel pour nomType (au cas où LOOSE ne suffit pas)
	if (hotel.getTypeHotel() != null) {
	dto.setNomType(hotel.getTypeHotel().getNomType());
	}
	// Mapping manuel pour villeHotel car les noms diffèrent (ville vs villeHotel)
	dto.setVilleHotel(hotel.getVille());
	return dto;
	}
	@Override
	public Hotel convertDtoToEntity(HotelDTO hotelDTO) {
	// Convertit le DTO en entité Hotel
	// nomType est ignoré : Hotel n'a pas de champ nomType
	Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);
	// Mapping manuel inverse pour ville
	hotel.setVille(hotelDTO.getVilleHotel());
	return hotel;
	}
	@Override
	public HotelDTO saveProduit(HotelDTO hotelDTO) {
	// 1. Convertit DTO → Hotel
	// 2. Sauvegarde en base (JPA génère l'ID)
	// 3. Convertit l'entité sauvegardée → DTO et retourne
	return convertEntityToDto(
	hotelRepository.save(convertDtoToEntity(hotelDTO))
	);
	}
	@Override
	public HotelDTO updateHotelDto(HotelDTO hotelDTO) {
	// save() de JPA fait UPDATE si l'ID est présent, INSERT sinon
	return convertEntityToDto(
	hotelRepository.save(convertDtoToEntity(hotelDTO))
	);
	}
	@Override
	public HotelDTO getHotelDto(Long id) {
	return convertEntityToDto(hotelRepository.findById(id).get());
	}
	@Override
	public List<HotelDTO> getAllHotelsDto() {
	// .stream() → transforme la liste en flux
	// .map(this::convertEntityToDto) → applique la conversion sur chaque élément
	// .collect(Collectors.toList()) → rassemble en liste
	return hotelRepository.findAll()
	.stream()
	.map(this::convertEntityToDto)
	.collect(Collectors.toList());
	}
	
	
	
	
	@Autowired
	TypeHotelRepository typeHotelRepository;

	@Override
	public List<TypeHotel> getAllTypeHotels() {
	    return typeHotelRepository.findAll();
	}

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Hotel hotel) {
        hotelRepository.delete(hotel);
    }

    @Override
    public void deleteHotelById(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public Hotel getHotel(Long id) {
        return hotelRepository.findById(id).get();
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Page<Hotel> getAllHotelsParPage(int page, int size) {
        return hotelRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Hotel> findByNomHotel(String nom) {
        return hotelRepository.findByNomHotel(nom);
    }

    @Override
    public List<Hotel> findByNomHotelContains(String nom) {
        return hotelRepository.findByNomHotelContains(nom);
    }

    @Override
    public List<Hotel> findByNomPrix(String nom, Double prix) {
        return hotelRepository.findByNomPrix(nom, prix);
    }

    @Override
    public List<Hotel> findByNomPrixParam(String nom, Double prix) {
        return hotelRepository.findByNomPrixParam(nom, prix);
    }

    @Override
    public List<Hotel> findByTypeHotel(TypeHotel typeHotel) {
        return hotelRepository.findByTypeHotel(typeHotel);
    }

    @Override
    public List<Hotel> findByTypeHotelIdType(Long id) {
        return hotelRepository.findByTypeHotelIdType(id);
    }

    @Override
    public List<Hotel> findByOrderByNomHotelAsc() {
        return hotelRepository.findByOrderByNomHotelAsc();
    }

    @Override
    public List<Hotel> trierHotelsNomsPrix() {
        return hotelRepository.trierHotelsNomsPrix();
    }
}