package kigali.clinc.clinicmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinc.clinicmanagement.domain.Office;
import kigali.clinc.clinicmanagement.repository.OfficeRepository;

@Service
public class OfficeService {


    @Autowired
    private OfficeRepository officeRepo;


    
    public String saveOffice(Office office){

        if(officeRepo.findByOfficeNumber(office.getOfficeNumber()) != null){
            return "Office with that number "
                    + office.getOfficeNumber()
                    + " already exists";
        }

        officeRepo.save(office);

        return "Office is saved Successfully";
    }


    
    public List<Office> getAllOffices(){

        return officeRepo.findAll();
    }


    
    public Office getOfficeById(UUID id){

        return officeRepo.findById(id).orElse(null);
    }


    
    public String updateOffice(UUID id, Office office){

        Office existingOffice = officeRepo.findById(id).orElse(null);

        if(existingOffice == null){
            return "Office not found";
        }

        Office officeWithSameNumber =
                officeRepo.findByOfficeNumber(office.getOfficeNumber());

        if(officeWithSameNumber != null &&
                !officeWithSameNumber.getId().equals(id)){

            return "Office with that number "
                    + office.getOfficeNumber()
                    + " already exists";
        }

        existingOffice.setName(office.getName());
        existingOffice.setOfficeNumber(office.getOfficeNumber());

        officeRepo.save(existingOffice);

        return "Office is updated Successfully";
    }


    
    public String deleteOffice(UUID id){

        Office existingOffice = officeRepo.findById(id).orElse(null);

        if(existingOffice == null){
            return "Office not found";
        }

        officeRepo.delete(existingOffice);

        return "Office is deleted Successfully";
    }

    public Object[] getBusiestOffice() {

    List<Object[]> offices =
            officeRepo.findBusiestOffice();

    if (offices.isEmpty()) {
        return null;
    }

    return offices.get(0);
}

}