package com.imen.hotels.restcontrollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.imen.hotels.dto.HotelDTO;
import com.imen.hotels.entities.Hotel;
import com.imen.hotels.repos.HotelRepository;
import com.imen.hotels.service.FileStorageService;
import com.imen.hotels.service.HotelService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class HotelRESTController {

    @Autowired
    HotelService hotelService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    HotelRepository hotelRepository;

    // GET all hotels: /hotels/api/hotels
    @RequestMapping(path = "/hotels", method = RequestMethod.GET)
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotelsDto();
    }

    // GET one hotel by ID: /hotels/api/hotels/{id}
    @RequestMapping(value = "/hotels/{id}", method = RequestMethod.GET)
    public HotelDTO getHotelById(@PathVariable("id") Long id) {
        return hotelService.getHotelDto(id);
    }

    // POST create hotel: /hotels/api/hotels
    @RequestMapping(path = "/hotels", method = RequestMethod.POST)
    public HotelDTO createHotel(@RequestBody HotelDTO hotelDTO) {
        return hotelService.saveHotel(hotelDTO);

    }

    // PUT update hotel: /hotels/api/hotels
    @RequestMapping(path = "/hotels", method = RequestMethod.PUT)
    public HotelDTO updateHotel(@RequestBody HotelDTO hotelDTO) {
        return hotelService.updateHotelDto(hotelDTO);
    }

    // DELETE hotel: /hotels/api/hotels/{id}
    @RequestMapping(value = "/hotels/{id}", method = RequestMethod.DELETE)
    public void deleteHotel(@PathVariable("id") Long id) {
        hotelService.deleteHotelById(id);
    }

    @RequestMapping(value = "/hotels/type/{idType}", method = RequestMethod.GET)
    public List<Hotel> getHotelsByTypeId(@PathVariable("idType") Long idType) {
        return hotelService.findByTypeHotelIdType(idType);
    }

    @RequestMapping(value = "/hotels/nom/{nom}", method = RequestMethod.GET)
    public List<Hotel> getHotelsByName(@PathVariable("nom") String nom) {
        return hotelService.findByNomHotelContains(nom);
    }

    // POST upload image for a hotel: /hotels/api/uploadImage/{id}
    @PostMapping("/uploadImage/{id}")
    public Hotel uploadImage(@PathVariable("id") Long id,
                             @RequestParam("image") MultipartFile image) throws Exception {
        Hotel hotel = hotelRepository.findById(id).orElseThrow();
        String fileName = fileStorageService.saveFile(image);
        hotel.setImageName(fileName);
        return hotelRepository.save(hotel);
    }

    // GET image by filename: /hotels/api/getImage/{imageName}
    @GetMapping("/getImage/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName) throws Exception {
        byte[] imageData = fileStorageService.loadFile(imageName);
        // Detect image type from extension
        String extension = imageName.substring(imageName.lastIndexOf(".") + 1).toLowerCase();
        MediaType mediaType = extension.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
        return ResponseEntity.ok().contentType(mediaType).body(imageData);
    }

    @GetMapping("/auth")
    public Authentication getAuth(Authentication auth) {
        return auth;
    }
}
