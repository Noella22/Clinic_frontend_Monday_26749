package kigali.clinc.clinicmanagement.repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinc.clinicmanagement.domain.Appointment;
import kigali.clinc.clinicmanagement.domain.AppointmentStatus;


@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, UUID> {


    
    List<Appointment> findByDoctorIdAndStatusOrderByAppointmentDateAsc(
            UUID doctorId,
            AppointmentStatus status
    );



    @Query("SELECT a FROM Appointment a " +
           "WHERE a.status = :status " +
           "AND a.appointmentDate < :today")
    List<Appointment> findPastPendingAppointments(
            @Param("status") AppointmentStatus status,
            @Param("today") Date today
    );


   
    @Query("SELECT a.doctor, COUNT(a) " +
           "FROM Appointment a " +
           "GROUP BY a.doctor " +
           "ORDER BY COUNT(a) DESC")
    List<Object[]> countAppointmentsPerDoctor();


    
    @Query("SELECT a FROM Appointment a " +
           "JOIN a.doctor d " +
           "JOIN d.specializations s " +
           "WHERE s.id = :specializationId")
    List<Appointment> findAppointmentsBySpecialization(
            @Param("specializationId") UUID specializationId
    );
    
List<Appointment> findByStatusOrderByAppointmentDateAsc(
        AppointmentStatus status
);

List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAsc(
        Date start,
        Date end
);

boolean existsByDoctorIdAndAppointmentDateAndStatusNot(
        UUID doctorId,
        Date appointmentDate,
        AppointmentStatus status
);

@Query("SELECT a.status, COUNT(a) " +
       "FROM Appointment a " +
       "GROUP BY a.status")
List<Object[]> countAppointmentsByStatus();

@Modifying
@Query("UPDATE Appointment a " +
       "SET a.status = :cancelled " +
       "WHERE a.doctor.id = :doctorId " +
       "AND a.appointmentDate = :date " +
       "AND a.status <> :completed")
int cancelAppointmentsForDay(
        @Param("doctorId") UUID doctorId,
        @Param("date") Date date,
        @Param("cancelled") AppointmentStatus cancelled,
        @Param("completed") AppointmentStatus completed
);

}