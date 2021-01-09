package hospital.backend.hospital.controller;

import hospital.backend.hospital.entity.Bed;
import hospital.backend.hospital.entity.Patient;
import hospital.backend.hospital.entity.Staff;
import hospital.backend.hospital.repository.PatientRepo;
import hospital.backend.hospital.repository.StaffRepo;
import hospital.backend.hospital.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private final StaffRepo staffRepo;
    private final Service service;
    private final PatientRepo patientRepo;
    @Autowired
    public Controller(StaffRepo staffRepo,Service service,PatientRepo patientRepo){
        this.staffRepo = staffRepo;
        this.service = service;
        this.patientRepo = patientRepo;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String,Integer> request){
        int id = request.get("id");
        Staff staff = staffRepo.selectById(id);
        Map<String,Object> response = new HashMap<>();
        Map<String,Integer> staffResponse = new HashMap<>();
        staffResponse.put("id",id);
        staffResponse.put("job",staff.getJob());
        response.put("staff",staffResponse);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/modifyStaff")
    public void modifyStaff(@RequestBody Map<String,Object> request){
        int id = Integer.parseInt(request.get("staffId").toString());
        String name = request.get("name").toString();
        int age = Integer.parseInt(request.get("age").toString());
        int gender = (Integer)request.get("gender");
        staffRepo.updateStaff(id,name,age,gender);
    }
    @PostMapping("/originStaff")
    public ResponseEntity<Map<String,Object>> originStaff(@RequestBody Map<String,Integer> request){
        int id = request.get("staffID");
        Staff staff = staffRepo.selectById(id);
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> staffResponse = new HashMap<>();
        staffResponse.put("name",staff.getName());
        staffResponse.put("gender",staff.getGender());
        staffResponse.put("age",staff.getAge());
        response.put("staffForm",staffResponse);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/addNewPatient")
    public void addNewPatient(@RequestBody Map<String,Object> request){
        String name =  request.get("name").toString();
        int age = Integer.parseInt(request.get("age").toString());
        int gender = Integer.parseInt(request.get("gender").toString());
        String arriveDate = request.get("arriveDate").toString();
        double temperature = Double.parseDouble(request.get("temperature").toString());
        int grade = Integer.parseInt(request.get("grade").toString());
        String testDate = request.get("testDate").toString();
        int testResult = Integer.parseInt(request.get("testResult").toString());
        service.addNewPatient(name,age,gender,arriveDate,grade,temperature,testDate,testResult);
    }
    @PostMapping("/addNewRoomNurse")
    public ResponseEntity<Map<String,Object>> addNewRoomNurse(@RequestBody Map<String,Object> request){
        int nurseLeaderId = Integer.parseInt(request.get("nurseLeaderId").toString());
        String name =  request.get("name").toString();
        int age = Integer.parseInt(request.get("age").toString());
        int gender = Integer.parseInt(request.get("gender").toString());
        int treatArea = staffRepo.selectById(nurseLeaderId).getTreatArea();
        staffRepo.insertRoomNurse(name,age,gender,treatArea);
        Map<String,Integer> staffResponse = new HashMap<>();
        staffResponse.put("id",staffRepo.count());
        Map<String,Object> response = new HashMap<>();
        response.put("roomNurse",staffResponse);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/deleteRoomNurse")
    public ResponseEntity<Map<String,Integer>> deleteRoomNurse(@RequestBody Map<String,Object> request){
        int staffId = Integer.parseInt(request.get("staffId").toString());
        int count = patientRepo.countByNurseID(staffId);
        Map<String,Integer> response = new HashMap<>();
        response.put("status",count);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/deleteSatisfiedPatient")
    public void deleteSatisfiedPatient(@RequestBody Map<String,Object> request){
        int patientId = Integer.parseInt(request.get("patientId").toString());
        patientRepo.updateLifeCondition(patientId, Patient.TREATED);
    }
    @PostMapping("/searchNurseLeader")
    public ResponseEntity<Map<String,Object>> searchNurseLeader(@RequestBody Map<String,Object> request){
        int staffId = Integer.parseInt(request.get("staffId").toString());
        int treatArea = staffRepo.selectById(staffId).getTreatArea();
        Staff staff = staffRepo.selectNurseLeaderByTreatArea(treatArea);
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> staffResponse = new HashMap<>();
        staffResponse.put("id",staff.getId());
        staffResponse.put("name",staff.getName());
        staffResponse.put("gender",staff.getGender());
        staffResponse.put("age",staff.getAge());
        response.put("nurseLeader",staffResponse);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/searchRoomNurse")
    public ResponseEntity<Map<String,Object>> searchRoomNurse(@RequestBody Map<String,Object> request){
        int staffId = Integer.parseInt(request.get("staffId").toString());
        int treatArea = staffRepo.selectById(staffId).getTreatArea();
        Map[] roomNurses = service.searchRoomNurse(treatArea);
        Map<String,Object> response = new HashMap<>();
        response.put("roomNurses",roomNurses);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/searchAreaPatient")
    public ResponseEntity<Map<String,Object>> searchAreaPatient(@RequestBody Map<String,Object> request){
        int staffId = Integer.parseInt(request.get("staffId").toString());
        int treatArea = staffRepo.selectById(staffId).getTreatArea();
        Map[]patient = service.packPatientList(patientRepo.selectByTreatArea(treatArea));
        Map<String,Object> response = new HashMap<>();
        response.put("patient",patient);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/searchAllPatient")
    public ResponseEntity<Map<String,Object>> searchAllPatient(){
        Map[]patient = service.packPatientList(patientRepo.selectAll());
        Map<String,Object> response = new HashMap<>();
        response.put("patient",patient);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/searchNursePatient")
    public ResponseEntity<Map<String,Object>> searchNursePatient(@RequestBody Map<String,Object> request){
        int staffId = Integer.parseInt(request.get("staffId").toString());
        Map[]patient = service.packPatientList(patientRepo.selectByNurseID(staffId));
        Map<String,Object> response = new HashMap<>();
        response.put("patient",patient);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/searchPatientByRoomNurseId")
    public ResponseEntity<Map<String,Object>> searchPatientByRoomNurseId(@RequestBody Map<String,Object> request){
        int staffId = Integer.parseInt(request.get("staffId").toString());
        int roomNurseId = Integer.parseInt(request.get("roomNurseId").toString());
        int treatAreaStaff = staffRepo.selectById(staffId).getTreatArea();
        int treatAreaNurse = staffRepo.selectById(roomNurseId).getTreatArea();
        Map<String,Object> response = new HashMap<>();
        if(treatAreaNurse != treatAreaStaff){
            response.put("status",1);
        }else {
            response.put("status",0);
            Map[]patient = service.packPatientList(patientRepo.selectByNurseID(roomNurseId));
            response.put("patient",patient);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/searchSatisfiedPatient")
    public ResponseEntity<Map<String,Object>>searchSatisfiedPatient(@RequestBody Map<String,Object> request){
        int staffId = Integer.parseInt(request.get("staffId").toString());
        int treatArea = staffRepo.selectById(staffId).getTreatArea();
        List<Patient> patientList = patientRepo.selectByTreatArea(treatArea);
        patientList.removeIf(patient -> !service.canLeaveHospital(patient));
        Map[]patient = service.packPatientList(patientList);
        Map<String,Object> response = new HashMap<>();
        response.put("patient",patient);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/searchSickBed")
    public ResponseEntity<Map<String,Object>> searchSickBed(@RequestBody Map<String,Object> request){
        int staffId = Integer.parseInt(request.get("staffId").toString());
        int treatArea = staffRepo.selectById(staffId).getTreatArea();
        List<Bed> bedList = patientRepo.selectBedByTreatArea(treatArea);
        Map[] beds = new Map[bedList.size()];
        int i = 0;
        for (Bed bed:bedList){
            Map<String,Object> bedResponse = new HashMap<>();
            bedResponse.put("id",bed.id);
            bedResponse.put("roomID",bed.roomID);
            bedResponse.put("state",bed.patientID==0?-1:1);
            beds[i] = bedResponse;
            i++;
        }
        Map<String,Object> response = new HashMap<>();
        response.put("sickBeds",beds);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/modifyLiveState")
    public void modifyLiveState(@RequestBody Map<String,Object> request){
        int patientId = Integer.parseInt(request.get("patientId").toString());
        int newLiveState = Integer.parseInt(request.get("newLiveState").toString());
        service.updateLifeCondition(patientId,newLiveState);
    }
    @PostMapping("/modifyGrade")
    public void modifyGrade(@RequestBody Map<String,Object> request){
        int patientId = Integer.parseInt(request.get("patientId").toString());
        int newGrade = Integer.parseInt(request.get("newGrade").toString());
        service.updateGrade(patientId,newGrade);
    }
    @PostMapping("/submitNewTest")
    public ResponseEntity<Map<String,Integer>> submitNewTest(@RequestBody Map<String,Object> request){
        int patientId = Integer.parseInt(request.get("patientId").toString());
        int grade = Integer.parseInt(request.get("grade").toString());
        int testResult = Integer.parseInt(request.get("testResult").toString());
        String testDate = request.get("testDate").toString();
        Map<String,Integer>response = new HashMap<>();
        if(service.addTest(patientId,testDate,testResult,grade)){
            response.put("status",0);
            service.updateGrade(patientId,grade);
        }else response.put("status",1);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/submitDailyInformation")
    public ResponseEntity<Map<String,Integer>> submitDailyInformation(@RequestBody Map<String,Object> request){
        String currentDate = request.get("currentDate").toString();
        int patientId = Integer.parseInt(request.get("patientId").toString());
        double temperature = Double.parseDouble(request.get("temperature").toString());
        String symptom = request.get("symptom").toString();
        int lifeCondition = Integer.parseInt(request.get("lifeCondition").toString());
        Map<String,Integer>response = new HashMap<>();
        if(service.addRegister(patientId,currentDate,lifeCondition,temperature,symptom)){
            response.put("status",0);
            service.updateLifeCondition(patientId,lifeCondition);
        }else response.put("status",1);
        return ResponseEntity.ok(response);
    }
}
