package hospital.backend.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Staff {
    private Integer id;
    private String name;
    private int age;
    private int gender;//0:男性 1:女性
    private int job;//0：主治医生，1：急诊护士，2：护士长，3：病房护士
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
