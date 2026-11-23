package kigali.clinc.clinicmanagement.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinc.clinicmanagement.domain.Appointment;
import kigali.clinc.clinicmanagement.domain.AppointmentStatus;
import kigali.clinc.clinicmanagement.service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    
    @PostMapping(
        value = "/save/{patientId}/{doctorId}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveAppointment(
            @RequestBody Appointment appointment,
            @PathVariable UUID patientId,
            @PathVariable UUID doctorId) {

        String returnedMessage =
                appointmentService.saveAppointment(
                        appointment,
                        patientId,
                        doctorId
                );


        
        if (returnedMessage.equalsIgnoreCase(
                "Doctor is already booked on that date")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.CONFLICT
            );
        }


        
        if (returnedMessage.equalsIgnoreCase(
                "Patient not found")
                || returnedMessage.equalsIgnoreCase(
                "Doctor not found")) {

            return new ResponseEntity<>(
                    returnedMessage,
                    HttpStatus.NOT_FOUND
            );
        }


        
        return new ResponseEntity<>(
                returnedMessage,
                HttpStatus.CREATED
        );
    }


    
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Appointment> getAllAppointments() {

        return appointmentService.getAllAppointments();
    }


    
    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Appointment getAppointmentById(
            @PathVariable UUID id) {

        return appointmentService.getAppointmentById(id);
    }


    
    @PutMapping(
        value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public String updateAppointment(
            @PathVariable UUID id,
            @RequestBody Appointment appointment) {

        return appointmentService.updateAppointment(
                id,
                appointment
        );
    }



    @DeleteMapping("/delete/{id}")
    public String deleteAppointment(
            @PathVariable UUID id) {

        return appointmentService.deleteAppointment(id);
    }


    
    @GetMapping(
        value = "/doctor/{firstName}/{lastName}/pending",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Appointment> getPendingAppointmentsByDoctor(
            @PathVariable String firstName,
            @PathVariable String lastName) {

        return appointmentService
                .getPendingAppointmentsByDoctor(
                        firstName,
                        lastName
                );
    }


    
    @GetMapping(
        value = "/pending/past",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Appointment> getPastPendingAppointments() {

        return appointmentService
                .getPastPendingAppointments();
    }


    
    @GetMapping(
        value = "/count-by-doctor",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Map<String, Object>>
            getAppointmentCountPerDoctor() {

        return appointmentService
                .getAppointmentCountPerDoctor();
    }


    
    @GetMapping(
        value = "/specialization/{specializationId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Appointment> getAppointmentsBySpecialization(
            @PathVariable UUID specializationId) {

        return appointmentService
                .getAppointmentsBySpecialization(
                        specializationId
                );
    }



    @GetMapping(
        value = "/by-status",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Appointment> getAppointmentsByStatus(
            @RequestParam AppointmentStatus status) {

        return appointmentService
                .getAppointmentsByStatus(status);
    }


    
    @GetMapping(
        value = "/between",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Appointment> getAppointmentsBetweenDates(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startLocalDate =
                LocalDate.parse(start);

        LocalDate endLocalDate =
                LocalDate.parse(end);

        Date startDate =
                Date.valueOf(startLocalDate);

        Date endDate =
                Date.valueOf(endLocalDate);

        return appointmentService
                .getAppointmentsBetweenDates(
                        startDate,
                        endDate
                );
    }

    @GetMapping(
    value = "/stats/by-status",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public List<Object[]> getAppointmentCountByStatus() {

    return appointmentService
            .getAppointmentCountByStatus();
}

@PatchMapping("/cancel-day")
public ResponseEntity<?> cancelAppointmentsForDay(
        @RequestParam UUID doctorId,
        @RequestParam String date) {

    Date appointmentDate = Date.valueOf(date);

    int numberCancelled =
            appointmentService.cancelAppointmentsForDay(
                    doctorId,
                    appointmentDate
            );

    return new ResponseEntity<>(
            numberCancelled + " appointments cancelled",
            HttpStatus.OK
    );
}

}