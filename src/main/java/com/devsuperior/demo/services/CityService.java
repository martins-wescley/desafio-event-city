package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<CityDTO> findAll() {
        List<City> result = cityRepository.findAll(Sort.by("name"));
        return result.stream().map(x -> new CityDTO(x)).toList();
    }
}
