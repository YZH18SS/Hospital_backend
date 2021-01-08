package hospital.backend.hospital.controller;

import hospital.backend.hospital.entity.Staff;
import hospital.backend.hospital.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {
    private final StaffRepo staffRepo;
    @Autowired
    public Controller(StaffRepo staffRepo){
        this.staffRepo = staffRepo;
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
        int id = (Integer) request.get("staffId");
        String name = request.get("name").toString();
        int age = (Integer)request.get("age");
        int gender = (Integer)request.get("gender");
        staffRepo.updateStaff(id,name,age,gender);
    }
    @PostMapping("/originStaff")
    public ResponseEntity<Map<String,Object>> originStaff(@RequestBody Map<String,Integer> request){
        int id = request.get("id");
        Staff staff = staffRepo.selectById(id);
        Map<String,Object> response = new HashMap<>();
        Map<String,Object> staffResponse = new HashMap<>();
        staffResponse.put("name",staff.getName());
        staffResponse.put("gender",staff.getGender());
        staffResponse.put("age",staff.getAge());
        response.put("staffForm",staffResponse);
        return ResponseEntity.ok(response);
    }
}
