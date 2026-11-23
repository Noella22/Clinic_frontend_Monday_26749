package kigali.clinc.clinicmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinc.clinicmanagement.domain.Doctor;
import kigali.clinc.clinicmanagement.domain.Specialization;
import kigali.clinc.clinicmanagement.repository.DoctorRepository;
import kigali.clinc.clinicmanagement.repository.SpecializationRepository;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepo;

    @Autowired
    private DoctorRepository doctorRepo;


    
    public String saveSpecialization(Specialization specialization) {

        specializationRepo.save(specialization);

        return "Specialization is saved Successfully";
    }


    
    public List<Specialization> getAllSpecializations() {

        return specializationRepo.findAll();
    }


    
    public Specialization getSpecializationById(UUID id) {

        return specializationRepo.findById(id).orElse(null);
    }


    
    public String updateSpecialization(
            UUID id,
            Specialization specialization) {

        Specialization existingSpecialization =
                specializationRepo.findById(id).orElse(null);

        if (existingSpecialization == null) {

            return "Specialization not found";
        }

        existingSpecialization.setName(
                specialization.getName());

        specializationRepo.save(existingSpecialization);

        return "Specialization is updated Successfully";
    }


    
    public String deleteSpecialization(UUID id) {

        Specialization existingSpecialization =
                specializationRepo.findById(id).orElse(null);

        if (existingSpecialization == null) {

            return "Specialization not found";
        }

        specializationRepo.delete(existingSpecialization);

        return "Specialization is deleted Successfully";
    }


    
    public String addDoctorToSpecialization(
            UUID specializationId,
            UUID doctorId) {

        Specialization specialization =
                specializationRepo.findById(specializationId).orElse(null);

        if (specialization == null) {

            return "Specialization not found";
        }

        Doctor doctor =
                doctorRepo.findById(doctorId).orElse(null);

        if (doctor == null) {

            return "Doctor not found";
        }

        specialization.getDoctors().add(doctor);

        specializationRepo.save(specialization);

        return "Doctor is added to Specialization Successfully";
    }

     public List<Specialization> getUnusedSpecializations() {

        return specializationRepo.findUnusedSpecializations();
    }

}