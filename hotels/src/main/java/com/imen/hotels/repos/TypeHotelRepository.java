package com.imen.hotels.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.imen.hotels.entities.TypeHotel;

@RepositoryRestResource(path = "type-hotel")
@CrossOrigin("*")
public interface TypeHotelRepository extends JpaRepository<TypeHotel, Long> {
}