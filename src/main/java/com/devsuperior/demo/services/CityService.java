package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.services.exceptions.DatabaseException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        List<City> result = cityRepository.findAll(Sort.by("name"));
        return result.stream().map(x -> new CityDTO(x)).toList();
    }

    @Transactional
    public CityDTO insert(CityDTO dto) {
        City entity = new City();
        entity.setName(dto.getName());
        entity = cityRepository.save(entity);
        return new CityDTO(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
