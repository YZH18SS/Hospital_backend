package hospital.backend.hospital.repository;

import hospital.backend.hospital.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepo {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public StaffRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public Staff selectById(Integer id){
        Staff staff = jdbcTemplate.queryForObject("select * from staff where id = ?",
                new BeanPropertyRowMapper<Staff>(Staff.class),id);
        return staff;
    }
    public void updateStaff(Integer id,String name,int age,int gender){
        jdbcTemplate.update("update staff set `name`=?,age=?,gender=? where id=?",name,age,gender,id);
    }
}
