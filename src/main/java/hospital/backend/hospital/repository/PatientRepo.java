package hospital.backend.hospital.repository;

import hospital.backend.hospital.entity.Bed;
import hospital.backend.hospital.entity.Patient;
import hospital.backend.hospital.entity.Register;
import hospital.backend.hospital.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PatientRepo {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PatientRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public Patient selectById(Integer id){
       Patient patient = jdbcTemplate.queryForObject("select * from patient where id = ?",
                new BeanPropertyRowMapper<Patient>(Patient.class),id);
        return patient;
    }
    public List<Patient> selectByTreatArea(Integer treatArea){
        List<Patient> patientList = jdbcTemplate.query("select * from patient where treatArea = ?",
                new BeanPropertyRowMapper<Patient>(Patient.class),treatArea);
        return patientList;
    }
    public List<Patient> selectAll(){
        List<Patient> patientList = jdbcTemplate.query("select * from patient ",
                new BeanPropertyRowMapper<Patient>(Patient.class));
        return patientList;
    }
    public List<Patient> selectByNurseID(int nurseID){
        List<Patient> patientList = jdbcTemplate.query("select * from patient where nurseID = ?",
                new BeanPropertyRowMapper<Patient>(Patient.class),nurseID);
        return patientList;
    }
    public int countByNurseID(int nurseID){
        Integer count = jdbcTemplate.queryForObject("select count(id) from patient where nurseID = ? and lifeCondition=?",
                Integer.class,nurseID,Patient.TREATING);
        return count==null?0:count;
    }
    public void insert(String name,int age,int gender,String arriveDate,int grade){
        jdbcTemplate.update("insert into patient(`name`,age,gender,arriveDate,grade) values(?,?,?,?,?)",
                name,age,gender,arriveDate,grade);
    }
    public int count(){
        Integer count = jdbcTemplate.queryForObject("select count(id) from patient",Integer.class);
        return count==null?0:count;
    }
    public int selectFreeBed(int treatArea){
        List<Bed> beds = jdbcTemplate.query("select id from bed where patientID=0 and treatArea=?",
                new BeanPropertyRowMapper<Bed>(Bed.class),treatArea);
        return  beds.size()==0?0:beds.get(0).id;
    }
    public void updateTransferFailed(int id){
        jdbcTemplate.update("update patient set waitTransfer=? where id=?",Patient.WAITING_TRANSFER,id);
    }
    public void updateTransferSucceed(int id,int treatArea,int nurseID,int bedID){
        jdbcTemplate.update("update patient set treatArea=?,waitTransfer=0,nurseID=? where id=?",
                treatArea,nurseID,id);
        jdbcTemplate.update("update bed set patientID=? where id=?",id,bedID);
    }
    public void  insertRegister(int patientID,String registerDate,int lifeCondition,double temperature,String symptom){
        jdbcTemplate.update("insert into register(patientID,registerDate,lifeCondition,temperature,symptom) " +
                        "values(?,?,?,?,?)",patientID,registerDate,lifeCondition,temperature,symptom);
    }
    public void updateTempCount(int id,boolean isNormal){
        String count = isNormal?"tempCount+1":"0";
        jdbcTemplate.update("update patient set tempCount="+count+" where id=?",id);
    }
    public void insertTest(int patientID,String testDate,int result,int grade){
        jdbcTemplate.update("insert into test(patientID,testDate,testResult,grade) " +
                "values(?,?,?,?)",patientID,testDate,result,grade);
    }
    public void updateTestCount(int id,boolean isNormal){
        String count = isNormal?"testCount+1":"0";
        jdbcTemplate.update("update patient set testCount="+count+" where id=?",id);
    }
    public void updateLifeCondition(int id,int lifeCondition){
        jdbcTemplate.update("update patient set lifeCondition=? where id=?",lifeCondition,id);
    }
    public void updateGrade(int id,int grade){
        jdbcTemplate.update("update patient set grade=? where id=?",grade,id);
    }
    public List<Bed> selectBedByTreatArea(int treatArea){
        List<Bed> bedList = jdbcTemplate.query("select * from bed where treatArea=?",
                new BeanPropertyRowMapper<Bed>(Bed.class),treatArea);
        return bedList;
    }
    public Test selectTestByPatientIDAndTestDate(int patientID,String testDate){
        List<Test> tests = jdbcTemplate.query("select * from test where patientID=? and testDate=?",
                new BeanPropertyRowMapper<Test>(Test.class),patientID,testDate);
        return tests.size() == 0?null:tests.get(0);
    }
    public Register selectRegisterByPatientIDAndRegisterDate(int patientID, String registerDate){
        List<Register> registers = jdbcTemplate.query("select * from register where patientID=? and registerDate=?",
                new BeanPropertyRowMapper<Register>(Register.class),patientID,registerDate);
        return registers.size()==0?null: registers.get(0);
    }
    public List<Patient> selectWaitingTransfer(int treatArea){
        return jdbcTemplate.query("select * from patient where waitTransfer = ? and lifeCondition=? and treatArea!=?",
                new BeanPropertyRowMapper<Patient>(Patient.class), Patient.WAITING_TRANSFER,Patient.TREATING,treatArea);
    }
    public void freeBed(int patientID){
        jdbcTemplate.update("update bed set patientID=0 where patientID=?",patientID);
    }
}
