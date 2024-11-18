package org.example.sportsorder.repositories;

import org.example.sportsorder.models.City;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

}
