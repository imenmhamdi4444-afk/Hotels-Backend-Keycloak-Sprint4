package com.imen.hotels.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imen.hotels.entities.Hotel;
import com.imen.hotels.entities.TypeHotel;
import com.imen.hotels.service.HotelService;

import jakarta.validation.Valid;

@Controller
public class HotelController {

    @Autowired
    HotelService hotelService;
    //route d'acceuil hotels 
    @GetMapping(value = "/")
    public String welcome() {
    // Retourne le fichier templates/index.html
    return "index";
    }

    @RequestMapping("/ListeHotels")
    public String listeHotels(ModelMap modelMap,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="2") int size) {
        Page<Hotel> hotels = hotelService.getAllHotelsParPage(page, size);
        modelMap.addAttribute("hotels", hotels);
        modelMap.addAttribute("pages", new int[hotels.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeHotels";
    }

    @RequestMapping("/showCreate")
    public String showCreate(ModelMap modelMap) {
        List<TypeHotel> types = hotelService.getAllTypeHotels();
        modelMap.addAttribute("hotel", new Hotel());
        modelMap.addAttribute("mode", "new");
        modelMap.addAttribute("types", types);
        modelMap.addAttribute("page", 0);
        modelMap.addAttribute("size", 2);
        return "formHotel";
    }

    @RequestMapping("/modifierHotel")
    public String editerHotel(@RequestParam("id") Long id,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="2") int size,
            ModelMap modelMap) {
        Hotel h = hotelService.getHotel(id);
        List<TypeHotel> types = hotelService.getAllTypeHotels();
        modelMap.addAttribute("hotel", h);
        modelMap.addAttribute("mode", "edit");
        modelMap.addAttribute("types", types);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("size", size);
        return "formHotel";
    }
   

    @RequestMapping("/supprimerHotel")
    public String supprimerHotel(@RequestParam("id") Long id,
            ModelMap modelMap,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="2") int size) {
        hotelService.deleteHotelById(id);
        Page<Hotel> hotels = hotelService.getAllHotelsParPage(page, size);
        modelMap.addAttribute("hotels", hotels);
        modelMap.addAttribute("pages", new int[hotels.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeHotels";
    }

   

    @RequestMapping("/updateHotel")
    public String updateHotel(@ModelAttribute("hotel") Hotel hotel, ModelMap modelMap) {
        hotelService.updateHotel(hotel);
        List<Hotel> hotels = hotelService.getAllHotels();
        modelMap.addAttribute("hotels", hotels);
        return "listeHotels";
    }
    
    @RequestMapping("/saveHotel")
    public String saveHotel(@Valid Hotel hotel, BindingResult bindingResult,
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="2") int size,
            ModelMap modelMap) {
        int currentPage;
        boolean isNew = false;
        if (bindingResult.hasErrors()) {
            List<TypeHotel> types = hotelService.getAllTypeHotels();
            modelMap.addAttribute("types", types);
            modelMap.addAttribute("mode", hotel.getIdHotel() == null ? "new" : "edit");
            modelMap.addAttribute("page", page);
            modelMap.addAttribute("size", size);
            return "formHotel";
        }
        if (hotel.getIdHotel() == null)
            isNew = true;
        hotelService.saveHotel(hotel);
        if (isNew) {
            Page<Hotel> hotels = hotelService.getAllHotelsParPage(page, size);
            currentPage = hotels.getTotalPages() - 1;
        } else {
            currentPage = page;
        }
        return "redirect:/ListeHotels?page=" + currentPage + "&size=" + size;
    }
    
}