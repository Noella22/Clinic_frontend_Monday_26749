package kigali.clinc.clinicmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinc.clinicmanagement.domain.Doctor;
import kigali.clinc.clinicmanagement.domain.Office;
import kigali.clinc.clinicmanagement.repository.DoctorRepository;
import kigali.clinc.clinicmanagement.repository.OfficeRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private OfficeRepository officeRepo;


    
    public String saveDoctor(
            Doctor doctor,
            int officeNumber) {

        Office office =
                officeRepo.findByOfficeNumber(officeNumber);

        if (office == null) {
            return "Office not found";
        }

        doctor.setOffice(office);

        doctorRepo.save(doctor);

        return "Doctor is saved Successfully";
    }


    
    public List<Doctor> getAllDoctors() {

        return doctorRepo.findAll();
    }


    
    public Doctor getDoctorById(UUID id) {

        return doctorRepo.findById(id).orElse(null);
    }


    
    public String updateDoctor(
            UUID id,
            Doctor doctor) {

        Doctor existingDoctor =
                doctorRepo.findById(id).orElse(null);

        if (existingDoctor == null) {
            return "Doctor not found";
        }

        existingDoctor.setFirstName(
                doctor.getFirstName());

        existingDoctor.setLastName(
                doctor.getLastName());

        existingDoctor.setDateOfBirth(
                doctor.getDateOfBirth());

        doctorRepo.save(existingDoctor);

        return "Doctor is updated Successfully";
    }


    
    public String deleteDoctor(UUID id) {

        Doctor existingDoctor =
                doctorRepo.findById(id).orElse(null);

        if (existingDoctor == null) {
            return "Doctor not found";
        }

        doctorRepo.delete(existingDoctor);

        return "Doctor is deleted Successfully";
    }


    
    public String addOfficeToDoctor(
            UUID doctorId,
            int officeNumber) {

        Doctor doctor =
                doctorRepo.findById(doctorId).orElse(null);

        if (doctor == null) {
            return "Doctor not found";
        }

        Office office =
                officeRepo.findByOfficeNumber(officeNumber);

        if (office == null) {
            return "Office not found";
        }

        doctor.setOffice(office);

        doctorRepo.save(doctor);

        return "Office is added to Doctor Successfully";
    }

    public List<Doctor> getDoctorsBySpecialization(String name) {

    return doctorRepo.findDoctorsBySpecialization(name);
}

public List<Doctor> getDoctorsWithoutOffice() {

    return doctorRepo.findDoctorsWithoutOffice();
}

}