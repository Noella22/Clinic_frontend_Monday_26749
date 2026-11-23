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

import kigali.clinc.clinicmanagement.domain.Patient;
import kigali.clinc.clinicmanagement.service.PatientService;


@RestController
@RequestMapping(value = "/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;


    
    @PostMapping(
        value = "/save",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> savePatient(@RequestBody Patient patient){

        String returnedMessage =
                patientService.savePatient(patient);

        if(returnedMessage.equalsIgnoreCase(
                "Patient is saved Successfully")){

            return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.CREATED
            );

        }else{

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
    public ResponseEntity<?> getAllPatients(){

        List<Patient> patients =
                patientService.getAllPatients();

        return new ResponseEntity<>(
                patients,
                HttpStatus.OK
        );
    }


    
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getPatientById(
            @PathVariable UUID id){

        Patient patient =
                patientService.getPatientById(id);

        if(patient != null){

            return new ResponseEntity<>(
                patient,
                HttpStatus.OK
            );

        }else{

            return new ResponseEntity<>(
                "Patient not found",
                HttpStatus.NOT_FOUND
            );
        }
    }


    
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updatePatient(
            @PathVariable UUID id,
            @RequestBody Patient patient){

        String returnedMessage =
                patientService.updatePatient(id, patient);

        if(returnedMessage.equalsIgnoreCase(
                "Patient is updated Successfully")){

            return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.OK
            );

        }else{

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
    public ResponseEntity<?> deletePatient(
            @PathVariable UUID id){

        String returnedMessage =
                patientService.deletePatient(id);

        if(returnedMessage.equalsIgnoreCase(
                "Patient is deleted Successfully")){

            return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.OK
            );

        }else{

            return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(
    value = "/by-last-name",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<?> getPatientsByLastName(
        @RequestParam String lastName){

    List<Patient> patients =
            patientService.getPatientsByLastName(lastName);

    return new ResponseEntity<>(
            patients,
            HttpStatus.OK
    );
}

@GetMapping(
    value = "/of-doctor/{doctorId}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<?> getPatientsByDoctor(
        @PathVariable UUID doctorId) {

    List<Patient> patients =
            patientService.getPatientsByDoctor(doctorId);

    if (patients == null) {

        return new ResponseEntity<>(
                "The doctor with that id does not exist",
                HttpStatus.NOT_FOUND
        );
    }

    return new ResponseEntity<>(
            patients,
            HttpStatus.OK
    );
}

@GetMapping(
    value = "/frequent",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<?> getFrequentPatients(
        @RequestParam int min) {

    List<Patient> patients =
            patientService.getFrequentPatients(min);

    return new ResponseEntity<>(
            patients,
            HttpStatus.OK
    );
}

}