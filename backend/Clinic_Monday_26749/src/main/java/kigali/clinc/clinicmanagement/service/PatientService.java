package kigali.clinc.clinicmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinc.clinicmanagement.domain.Patient;
import kigali.clinc.clinicmanagement.repository.DoctorRepository;
import kigali.clinc.clinicmanagement.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
private DoctorRepository doctorRepo;


    
    public String savePatient(Patient patient){

        patientRepo.save(patient);

        return "Patient is saved Successfully";
    }


    
    public List<Patient> getAllPatients(){

        return patientRepo.findAll();
    }


    
    public Patient getPatientById(UUID id){

        return patientRepo.findById(id).orElse(null);
    }


    
    public String updatePatient(UUID id, Patient patient){

        Patient existingPatient =
                patientRepo.findById(id).orElse(null);

        if(existingPatient == null){
            return "Patient not found";
        }

        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setDateOfBirth(patient.getDateOfBirth());

        patientRepo.save(existingPatient);

        return "Patient is updated Successfully";
    }


    
    public String deletePatient(UUID id){

        Patient existingPatient =
                patientRepo.findById(id).orElse(null);

        if(existingPatient == null){
            return "Patient not found";
        }

        patientRepo.delete(existingPatient);

        return "Patient is deleted Successfully";
    }

    public List<Patient> getPatientsByLastName(String lastName){

    return patientRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
}

public List<Patient> getPatientsByDoctor(UUID doctorId) {

    if (doctorRepo.findById(doctorId).orElse(null) == null) {
        return null;
    }

    return patientRepo.findPatientsByDoctor(doctorId);
}

public List<Patient> getFrequentPatients(int min) {

    return patientRepo.findFrequentPatients(min);
}

}