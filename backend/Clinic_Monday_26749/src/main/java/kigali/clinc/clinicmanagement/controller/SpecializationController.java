package kigali.clinc.clinicmanagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinc.clinicmanagement.domain.Specialization;
import kigali.clinc.clinicmanagement.service.SpecializationService;

@RestController
@RequestMapping(value = "/api/specialization")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;


    
    @PostMapping(
        value = "/save",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveSpecialization(
            @RequestBody Specialization specialization) {

        String returnedMessage =
                specializationService.saveSpecialization(specialization);

        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.CREATED
        );
    }


    
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAllSpecializations() {

        List<Specialization> specializations =
                specializationService.getAllSpecializations();

        return new ResponseEntity<>(
                specializations,
                HttpStatus.OK
        );
    }


    
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getSpecializationById(
            @PathVariable UUID id) {

        Specialization specialization =
                specializationService.getSpecializationById(id);

        if (specialization != null) {

            return new ResponseEntity<>(
                    specialization,
                    HttpStatus.OK
            );

        } else {

            return new ResponseEntity<>(
                    "Specialization not found",
                    HttpStatus.NOT_FOUND
            );
        }
    }


    
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateSpecialization(
            @PathVariable UUID id,
            @RequestBody Specialization specialization) {

        String returnedMessage =
                specializationService.updateSpecialization(
                        id,
                        specialization
                );

        if (returnedMessage.equalsIgnoreCase(
                "Specialization is updated Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK
            );

        } else {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @DeleteMapping(
        value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteSpecialization(
            @PathVariable UUID id) {

        String returnedMessage =
                specializationService.deleteSpecialization(id);

        if (returnedMessage.equalsIgnoreCase(
                "Specialization is deleted Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK
            );

        } else {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND
            );
        }
    }


    
    @PutMapping(
        value = "/{specializationId}/doctor/{doctorId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addDoctorToSpecialization(
            @PathVariable UUID specializationId,
            @PathVariable UUID doctorId) {

        String returnedMessage =
                specializationService.addDoctorToSpecialization(
                        specializationId,
                        doctorId
                );

        if (returnedMessage.equalsIgnoreCase(
                "Doctor is added to Specialization Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK
            );

        } else {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND
            );
        }
    }

     @GetMapping(
        value = "/unused",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUnusedSpecializations() {

        List<Specialization> specializations =
                specializationService.getUnusedSpecializations();

        return new ResponseEntity<>(
                specializations,
                HttpStatus.OK
        );
    }
}