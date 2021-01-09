package hospital.backend.hospital.service;

import hospital.backend.hospital.entity.Patient;
import hospital.backend.hospital.entity.Staff;
import hospital.backend.hospital.entity.Test;
import hospital.backend.hospital.repository.PatientRepo;
import hospital.backend.hospital.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service {
    private static final int[] CARED_PATIENT_COUNT = {3,2,1};
    private final PatientRepo patientRepo;
    private final StaffRepo staffRepo;
    @Autowired
    public Service(PatientRepo patientRepo,StaffRepo staffRepo){
        this.patientRepo = patientRepo;
        this.staffRepo = staffRepo;
    }
    public void addNewPatient(String name,int age,int gender,String arriveDate,int grade,
                              double temperature,String testDate,int testResult){
        patientRepo.insert(name,age,gender,arriveDate,grade);
        int id = patientRepo.count();
        transfer(id,grade);
        addRegister(id,arriveDate, Patient.TREATING,temperature,null);
        addTest(id,testDate,testResult,grade);
    }
    public boolean addRegister(int patientID,String registerDate,int lifeCondition,double temperature,
                             String symptom){
        if(patientRepo.selectRegisterByPatientIDAndRegisterDate(patientID,registerDate)!=null)
            return false;
        patientRepo.insertRegister(patientID,registerDate,lifeCondition,temperature,symptom);
        boolean isNormal = temperature < Patient.NORMAL_TEMPERATURE;
        patientRepo.updateTempCount(patientID,isNormal);
        return true;
    }
    public boolean addTest(int patientID,String testDate,int result,int grade){
        if(patientRepo.selectTestByPatientIDAndTestDate(patientID,testDate) != null)
            return false;
        patientRepo.insertTest(patientID,testDate,result,grade);
        boolean isNormal = result== Test.NORMAL_RESULT;
        patientRepo.updateTestCount(patientID,isNormal);
        return true;
    }

    private void transfer(int patientID,int grade){
        //find a free bed
        int bedID = patientRepo.selectFreeBed(grade);//0 is not found
        //find a free nurse
        int nurseID = findFreeNurse(grade);//0 is not found
        //if found,update treatArea=grade waitTransfer=0 nurseID=nurseID update bed;else waitTransfer=1
        if(bedID == 0 || nurseID == 0)
            patientRepo.updateTransferFailed(patientID);
        else {
            patientRepo.updateTransferSucceed(patientID,grade,nurseID,bedID);
        }
    }
    private int findFreeNurse(int treatArea){
        List<Staff> nurseList = staffRepo.selectByJobAndTreatArea(Staff.NURSE,treatArea);
        for (Staff nurse :nurseList){
            int cared = patientRepo.countByNurseID(nurse.getId());
            if(cared < CARED_PATIENT_COUNT[treatArea-1])
                return nurse.getId();
        }
        return 0;
    }
    public Map[] searchRoomNurse(int treatArea){
        List<Staff> nurseList = staffRepo.selectByJobAndTreatArea(Staff.NURSE,treatArea);
        Map[] response = new Map[nurseList.size()];
        int i = 0;
        for (Staff staff :nurseList){
            Map<String,Object> staffResponse = new HashMap<>();
            staffResponse.put("id",staff.getId());
            staffResponse.put("name",staff.getName());
            staffResponse.put("gender",staff.getGender());
            staffResponse.put("age",staff.getAge());
            response[i] = staffResponse;
            i++;
        }
        return response;
    }
    public Map[] packPatientList(List<Patient> patientList){
        Map[]patients = new Map[patientList.size()];
        int i = 0;
        for(Patient patient:patientList){
            int leaveHospital = (canLeaveHospital(patient))? 1:0;
            Map<String,Object> patientResponse = new HashMap<>();
            patientResponse.put("id",patient.getId());
            patientResponse.put("name",patient.getName());
            patientResponse.put("gender",patient.getGender());
            patientResponse.put("age",patient.getAge());
            patientResponse.put("treatArea",patient.getTreatArea());
            patientResponse.put("nurseID",patient.getNurseID());
            patientResponse.put("grade",patient.getGrade());
            patientResponse.put("waitTransfer",patient.getWaitTransfer());
            patientResponse.put("leaveHospital",leaveHospital);
            patientResponse.put("lifeCondition",patient.getLifeCondition());
            patients[i] = patientResponse;
            i++;
        }
        return patients;
    }
    public boolean canLeaveHospital(Patient patient){
        return patient.getTempCount() >= Patient.NORMAL_TEMP_COUNT &&
                patient.getTestCount() >= Patient.NORMAL_TEST_COUNT;
    }
    public void updateGrade(int patientId,int grade){
        Patient patient = patientRepo.selectById(patientId);
        int oldGrade = patient.getGrade();
        if(oldGrade != grade){
            patientRepo.updateGrade(patientId,grade);
            transfer(patientId,grade);
        }
    }
    public void updateLifeCondition(int patientID,int lifeCondition){
        Patient patient = patientRepo.selectById(patientID);
        int old = patient.getLifeCondition();
        if(old == lifeCondition || old != Patient.TREATING)
            return;
        patientRepo.updateLifeCondition(patientID,lifeCondition);
        patientRepo.freeBed(patientID);
        //不清理病房护士是因为这时候病人的生命状态已经变为出院或死亡，不算在空闲护士计数里
        int treatArea = patient.getTreatArea();
        List<Patient> patientList = patientRepo.selectByTreatArea(Patient.ISOLATING_AREA);
        patientList.removeIf(patient1 -> patient1.getLifeCondition()!=Patient.TREATING);
        if(patientList.size() == 0)
            patientList = patientRepo.selectWaitingTransfer(treatArea);
        if(patientList.size() == 0)
            return;
        Patient waitingPatient = patientList.get(0);
        transfer(waitingPatient.getId(),treatArea);
    }
}
