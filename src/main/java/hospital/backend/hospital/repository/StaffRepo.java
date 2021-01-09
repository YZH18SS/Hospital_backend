package hospital.backend.hospital.repository;

import hospital.backend.hospital.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Staff> selectByJobAndTreatArea(int job,int treatArea){
        List<Staff> staffList = jdbcTemplate.query("select * from staff where job = ? and treatArea = ?",
                new BeanPropertyRowMapper<Staff>(Staff.class),job,treatArea);
        return staffList;
    }
    public void insertRoomNurse(String name,int age,int gender,int treatArea){
        jdbcTemplate.update("insert  into staff (name,age,gender,job,treatArea) values(?,?,?,?,?)",
                name,age,gender,Staff.NURSE,treatArea);
    }
    public int count(){
        return jdbcTemplate.queryForObject("select count(id) from staff",Integer.class);
    }
    public Staff selectNurseLeaderByTreatArea(int treatArea){
        Staff staff = jdbcTemplate.queryForObject("select * from staff where job = ? and treatArea=?",
                new BeanPropertyRowMapper<Staff>(Staff.class),Staff.NURSE_LEADER,treatArea);
        return staff;
    }
    public void deleteNurseById(int id){
        jdbcTemplate.update("delete from staff where id=?",id);
    }
}
