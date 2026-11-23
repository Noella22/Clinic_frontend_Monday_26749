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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinc.clinicmanagement.domain.Doctor;
import kigali.clinc.clinicmanagement.service.DoctorService;

@RestController
@RequestMapping(value = "/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


    
    @PostMapping(
        value = "/save/{officeNumber}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveDoctor(
            @RequestBody Doctor doctor,
            @PathVariable int officeNumber) {

        String returnedMessage =
                doctorService.saveDoctor(
                        doctor,
                        officeNumber
                );

        if (returnedMessage.equalsIgnoreCase(
                "Doctor is saved Successfully")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CREATED
            );

        } else {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT
            );
        }
    }


    
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAllDoctors() {

        List<Doctor> doctors =
                doctorService.getAllDoctors();

        return new ResponseEntity<>(
                doctors,
                HttpStatus.OK
        );
    }


    
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getDoctorById(
            @PathVariable UUID id) {

        Doctor doctor =
                doctorService.getDoctorById(id);

        if (doctor != null) {

            return new ResponseEntity<>(
                    doctor,
                    HttpStatus.OK
            );

        } else {

            return new ResponseEntity<>(
                    "Doctor not found",
                    HttpStatus.NOT_FOUND
            );
        }
    }


    
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateDoctor(
            @PathVariable UUID id,
            @RequestBody Doctor doctor) {

        String returnedMessage =
                doctorService.updateDoctor(
                        id,
                        doctor
                );

        if (returnedMessage.equalsIgnoreCase(
                "Doctor is updated Successfully")) {

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
    public ResponseEntity<?> deleteDoctor(
            @PathVariable UUID id) {

        String returnedMessage =
                doctorService.deleteDoctor(id);

        if (returnedMessage.equalsIgnoreCase(
                "Doctor is deleted Successfully")) {

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
        value = "/{doctorId}/office/{officeNumber}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addOfficeToDoctor(
            @PathVariable UUID doctorId,
            @PathVariable int officeNumber) {

        String returnedMessage =
                doctorService.addOfficeToDoctor(
                        doctorId,
                        officeNumber
                );

        if (returnedMessage.equalsIgnoreCase(
                "Office is added to Doctor Successfully")) {

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
    value = "/by-specialization",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public List<Doctor> getDoctorsBySpecialization(
        @RequestParam String name) {

    return doctorService
            .getDoctorsBySpecialization(name);
}

@GetMapping(
    value = "/without-office",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public List<Doctor> getDoctorsWithoutOffice() {

    return doctorService.getDoctorsWithoutOffice();
}

}