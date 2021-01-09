package hospital.backend.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Staff {
    public static final int NURSE = 3;
    public static final int NURSE_LEADER = 2;
    private Integer id;
    private String name;
    private int age;
    private int gender;//0:男性 1:女性
    private int job;//0：主治医生，1：急诊护士，2：护士长，3：病房护士
    private int treatArea;//0:隔离区 1:轻症 2：重症 3：危重症 4：急诊护士

    public int getTreatArea() {
        return treatArea;
    }

    public Integer getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public int getJob() {
        return job;
    }

    public String getName() {
        return name;
    }
}
