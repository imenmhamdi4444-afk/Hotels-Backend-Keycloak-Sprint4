package com.imen.hotels.repos;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.imen.hotels.entities.Hotel;

// Leçon 31 : Retourner l'ID avec Spring Data REST
// Par défaut Spring Data REST cache l'ID dans les réponses JSON.
// Cette configuration force l'exposition de l'ID de l'entité Hotel.
@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Force l'inclusion de l'idHotel dans les réponses JSON
        config.exposeIdsFor(Hotel.class);
    }
}
