package com.imen.hotels;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.imen.hotels.entities.Hotel;
import com.imen.hotels.entities.TypeHotel;
import com.imen.hotels.repos.HotelRepository;
import com.imen.hotels.service.HotelService;

@SpringBootTest
class HotelsApplicationTests {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    @Test
    public void testFindByNomHotel() {
        List<Hotel> hotels = hotelRepository.findByNomHotel("Hotel Marhaba");
        for (Hotel h : hotels) {
            System.out.println(h);
        }
    }

    @Test
    public void testFindByNomHotelContains() {
        List<Hotel> hotels = hotelRepository.findByNomHotelContains("Hotel");
        for (Hotel h : hotels) {
            System.out.println(h);
        }
    }

    @Test
    public void testFindByNomPrix() {
        List<Hotel> hotels = hotelRepository.findByNomPrix("Marhaba", 100.0);
        for (Hotel h : hotels) {
            System.out.println(h);
        }
    }

    @Test
    public void testFindByNomPrixParam() {
        List<Hotel> hotels = hotelRepository.findByNomPrixParam("Marhaba", 100.0);
        for (Hotel h : hotels) {
            System.out.println(h);
        }
    }

    @Test
    public void testFindByTypeHotel() {
        TypeHotel type = new TypeHotel();
        type.setIdType(3L);
        List<Hotel> hotels = hotelRepository.findByTypeHotel(type);
        for (Hotel h : hotels) {
            System.out.println(h);
        }
    }

    @Test
    public void testFindByTypeHotelIdType() {
        List<Hotel> hotels = hotelRepository.findByTypeHotelIdType(2L);
        for (Hotel h : hotels) {
            System.out.println(h);
        }
    }

    @Test
    public void testFindByOrderByNomHotelAsc() {
        List<Hotel> hotels = hotelRepository.findByOrderByNomHotelAsc();
        for (Hotel h : hotels) {
            System.out.println(h);
        }
    }

    @Test
    public void testTrierHotelsNomsPrix() {
        List<Hotel> hotels = hotelRepository.trierHotelsNomsPrix();
        for (Hotel h : hotels) {
            System.out.println(h);
        }
    }

    @Test
    public void testGetAllHotelsParPage() {
        Page<Hotel> hotels = hotelService.getAllHotelsParPage(0, 2);
        System.out.println(hotels.getSize());
        System.out.println(hotels.getTotalElements());
        System.out.println(hotels.getTotalPages());
        hotels.getContent().forEach(h -> {
            System.out.println(h.toString());
        });
    }
}