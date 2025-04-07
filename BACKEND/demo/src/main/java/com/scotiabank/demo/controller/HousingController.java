package com.scotiabank.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scotiabank.demo.model.Housing;
import com.scotiabank.demo.service.HousingService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/housing")
public class HousingController {

    private final HousingService housingService;
    private static final Logger logger = LoggerFactory.getLogger(HousingController.class);

    public HousingController(HousingService housingService) {
        this.housingService = housingService;
    }

    @PostMapping
    public ResponseEntity<?> createHousing(@RequestBody Housing housing) {
        logger.info("Attempting to create housing for user ID: {}", housing.getUserId());
        try {
            Housing savedHousing = housingService.createHousing(housing);
            return ResponseEntity.ok(savedHousing);
        } catch (RuntimeException e) {
            logger.error("Error creating housing: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Housing>> getAllHousing() {
        logger.info("Fetching all housing records");
        return ResponseEntity.ok(housingService.getAllHousing());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Housing> getHousingById(@PathVariable String id) {
        logger.info("Fetching housing with ID: {}", id);
        Optional<Housing> housing = housingService.getHousingById(id);
        return housing.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Housing> getHousingByUserId(@PathVariable String userId) {
        logger.info("Fetching housing for user ID: {}", userId);
        Optional<Housing> housing = housingService.getHousingByUserId(userId);
        return housing.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Housing with userid {} not found", userId);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Housing> updateHousing(@PathVariable String id, @RequestBody Housing updatedHousing) {
        logger.info("Updating housing with ID: {}", id);
        return ResponseEntity.ok(housingService.updateHousing(id, updatedHousing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHousing(@PathVariable String id) {
        logger.info("Deleting housing with ID: {}", id);
        housingService.deleteHousing(id);
        return ResponseEntity.noContent().build();
    }
}
