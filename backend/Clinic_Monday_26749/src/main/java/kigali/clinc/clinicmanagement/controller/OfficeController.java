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
import org.springframework.web.bind.annotation.CrossOrigin;

import kigali.clinc.clinicmanagement.domain.Office;
import kigali.clinc.clinicmanagement.service.OfficeService;

@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:5174",
    "http://localhost:5175"

})
@RestController
@RequestMapping(value = "/api/office")
public class OfficeController {


    @Autowired
    private OfficeService officeServes;


    
    @PostMapping(
        value = "/save",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveOffice(@RequestBody Office office){

        String returnedMessage = officeServes.saveOffice(office);

        if(returnedMessage.equalsIgnoreCase(
                "Office is saved Successfully")){

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
    public ResponseEntity<?> getAllOffices(){

        List<Office> offices = officeServes.getAllOffices();

        return new ResponseEntity<>(
                offices,
                HttpStatus.OK
        );
    }


    
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getOfficeById(
            @PathVariable UUID id){

        Office office = officeServes.getOfficeById(id);

        if(office != null){

            return new ResponseEntity<>(
                    office,
                    HttpStatus.OK
            );

        }else{

            return new ResponseEntity<>(
                    "Office not found",
                    HttpStatus.NOT_FOUND
            );
        }
    }


    
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateOffice(
            @PathVariable UUID id,
            @RequestBody Office office){

        String returnedMessage =
                officeServes.updateOffice(id, office);

        if(returnedMessage.equalsIgnoreCase(
                "Office is updated Successfully")){

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.OK
            );

        }else if(returnedMessage.equalsIgnoreCase(
                "Office not found")){

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND
            );

        }else{

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT
            );
        }
    }


    
    @DeleteMapping(
        value = "/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteOffice(
            @PathVariable UUID id){

        String returnedMessage =
                officeServes.deleteOffice(id);

        if(returnedMessage.equalsIgnoreCase(
                "Office is deleted Successfully")){

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
    value = "/busiest",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<?> getBusiestOffice() {

    Object[] office =
            officeServes.getBusiestOffice();

    if (office == null) {

        return new ResponseEntity<>(
                "No appointments yet",
                HttpStatus.OK
        );
    }

    return new ResponseEntity<>(
            office,
            HttpStatus.OK
    );
}

}