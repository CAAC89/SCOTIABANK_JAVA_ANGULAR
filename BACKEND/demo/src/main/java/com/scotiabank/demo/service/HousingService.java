package com.scotiabank.demo.service;

import org.springframework.stereotype.Service;

import com.scotiabank.demo.model.Housing;
import com.scotiabank.demo.repository.HousingRepository;
import com.scotiabank.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HousingService {
    private final HousingRepository housingRepository;
    private final UserRepository userRepository; // Agregado para validar usuario

    public HousingService(HousingRepository housingRepository, UserRepository userRepository) {
        this.housingRepository = housingRepository;
        this.userRepository = userRepository;
    }

    public Housing createHousing(Housing housing) {
        // Verificar si el usuario asociado existe
        userRepository.findById(housing.getUserId()).orElseThrow(
            () -> new RuntimeException("Usuario no encontrado, no se puede registrar la vivienda")
        );

        return housingRepository.save(housing);
    }

    public List<Housing> getAllHousing() {
        return housingRepository.findAll();
    }

    public Optional<Housing> getHousingById(String id) {
        return housingRepository.findById(id);
    }

    public Optional<Housing> getHousingByUserId(String userId) {
        return housingRepository.findByUserId(userId);
    }

    public Housing updateHousing(String id, Housing updatedHousing) {
        return housingRepository.findById(id).map(existingHousing -> {
            existingHousing.setDepartamento(updatedHousing.getDepartamento());
            existingHousing.setMunicipio(updatedHousing.getMunicipio());
            existingHousing.setDireccion(updatedHousing.getDireccion());
            existingHousing.setIngresosMensuales(updatedHousing.getIngresosMensuales());
            return housingRepository.save(existingHousing);
        }).orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));
    }

    public void deleteHousing(String id) {
        housingRepository.deleteById(id);
    }
}
