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
    public ResponseEntity<Map<String,Integer>> login(@RequestBody Map<String,Integer> request){
        int id = request.get("id");
        Staff staff = staffRepo.selectById(id);
        Map<String,Integer> response = new HashMap<>();
        response.put("id",id);
        response.put("job",staff.getJob());
        return ResponseEntity.ok(response);
    }
}
