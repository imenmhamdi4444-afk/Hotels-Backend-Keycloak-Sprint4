package com.imen.hotels.restcontrollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.imen.hotels.entities.TypeHotel;
import com.imen.hotels.service.HotelService;

@RestController
@RequestMapping("/api/type-hotel")
@CrossOrigin
public class TypeHotelRESTController {

    @Autowired
    HotelService hotelService;

    // GET all types: /hotels/api/type-hotel
    @RequestMapping(method = RequestMethod.GET)
    public List<TypeHotel> getAllTypeHotels() {
        return hotelService.getAllTypeHotels();
    }

    // GET one type by ID: /hotels/api/type-hotel/{id}
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TypeHotel getTypeHotelById(@PathVariable("id") Long id) {
        return hotelService.getAllTypeHotels().stream()
                .filter(t -> t.getIdType().equals(id))
                .findFirst().orElse(null);
    }
}
