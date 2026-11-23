package kigali.clinc.clinicmanagement.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kigali.clinc.clinicmanagement.domain.Appointment;
import kigali.clinc.clinicmanagement.domain.AppointmentStatus;
import kigali.clinc.clinicmanagement.domain.Doctor;
import kigali.clinc.clinicmanagement.domain.Patient;
import kigali.clinc.clinicmanagement.repository.AppointmentRepository;
import kigali.clinc.clinicmanagement.repository.DoctorRepository;
import kigali.clinc.clinicmanagement.repository.PatientRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;


    
    public String saveAppointment(
            Appointment appointment,
            UUID patientId,
            UUID doctorId) {

        Patient patient =
                patientRepo.findById(patientId).orElse(null);

        if (patient == null) {
            return "Patient not found";
        }

        Doctor doctor =
                doctorRepo.findById(doctorId).orElse(null);

        if (doctor == null) {
            return "Doctor not found";
        }

            boolean alreadyBooked =
            appointmentRepo
                    .existsByDoctorIdAndAppointmentDateAndStatusNot(
                            doctorId,
                            appointment.getAppointmentDate(),
                            AppointmentStatus.CANCELLED
                    );

    if (alreadyBooked) {
        return "Doctor is already booked on that date";
    }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointmentRepo.save(appointment);

        return "Appointment is saved Successfully";
    }


    
    public List<Appointment> getAllAppointments() {

        return appointmentRepo.findAll();
    }


    
    public Appointment getAppointmentById(UUID id) {

        return appointmentRepo.findById(id).orElse(null);
    }


    
    public String updateAppointment(
            UUID id,
            Appointment appointment) {

        Appointment existingAppointment =
                appointmentRepo.findById(id).orElse(null);

        if (existingAppointment == null) {
            return "Appointment not found";
        }

        existingAppointment.setAppointmentDate(
                appointment.getAppointmentDate());

        existingAppointment.setReason(
                appointment.getReason());

        existingAppointment.setStatus(
                appointment.getStatus());

        appointmentRepo.save(existingAppointment);

        return "Appointment is updated Successfully";
    }


    
    public String deleteAppointment(UUID id) {

        Appointment existingAppointment =
                appointmentRepo.findById(id).orElse(null);

        if (existingAppointment == null) {
            return "Appointment not found";
        }

        appointmentRepo.delete(existingAppointment);

        return "Appointment is deleted Successfully";
    }


    
    public List<Appointment> getPendingAppointmentsByDoctor(
            String firstName,
            String lastName) {

        Doctor doctor =
                doctorRepo.findByFirstNameAndLastName(
                        firstName,
                        lastName
                );

        if (doctor == null) {
            return null;
        }

        return appointmentRepo
                .findByDoctorIdAndStatusOrderByAppointmentDateAsc(
                        doctor.getId(),
                        AppointmentStatus.PENDING
                );
    }


    
    public List<Appointment> getPastPendingAppointments() {

        Date today =
                new Date(System.currentTimeMillis());

        return appointmentRepo.findPastPendingAppointments(
                AppointmentStatus.PENDING,
                today
        );
    }


    
    public List<Map<String, Object>> getAppointmentCountPerDoctor() {

        List<Object[]> results =
                appointmentRepo.countAppointmentsPerDoctor();

        List<Map<String, Object>> response =
                new ArrayList<>();

        for (Object[] row : results) {

            Doctor doctor = (Doctor) row[0];
            Long count = (Long) row[1];

            Map<String, Object> item =
                    new LinkedHashMap<>();

            item.put(
                    "doctorName",
                    doctor.getFirstName() + " " + doctor.getLastName()
            );

            item.put(
                    "appointmentCount",
                    count
            );

            response.add(item);
        }

        return response;
    }


    
    public List<Appointment> getAppointmentsBySpecialization(
            UUID specializationId) {

        return appointmentRepo
                .findAppointmentsBySpecialization(
                        specializationId
                );
    }

    public List<Appointment> getAppointmentsByStatus(
        AppointmentStatus status) {

    return appointmentRepo
            .findByStatusOrderByAppointmentDateAsc(status);
}

public List<Appointment> getAppointmentsBetweenDates(
        Date start,
        Date end) {

    return appointmentRepo
            .findByAppointmentDateBetweenOrderByAppointmentDateAsc(
                    start,
                    end
            );
}

public List<Object[]> getAppointmentCountByStatus() {

    return appointmentRepo.countAppointmentsByStatus();
}

public List<Patient> getFrequentPatients(int min) {

    return patientRepo.findFrequentPatients(min);
}

@Transactional
public int cancelAppointmentsForDay(
        UUID doctorId,
        Date date) {

    return appointmentRepo.cancelAppointmentsForDay(
            doctorId,
            date,
            AppointmentStatus.CANCELLED,
            AppointmentStatus.COMPLETED
    );
}

}