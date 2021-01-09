package hospital.backend.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    public static final int TREATING = 1;
    public static final double NORMAL_TEMPERATURE =  37.3;
    public static final int TREATED = 0;
    public static final int NORMAL_TEMP_COUNT = 3;
    public static final int NORMAL_TEST_COUNT = 2;
    public static final int WAITING_TRANSFER = 1;
    public static final int ISOLATING_AREA = 0;
    private int id;
    private String name;
    private int gender;
    private int age;
    private int treatArea;//0:隔离区 1:轻症 2：重症 3：危重症 4：急诊护士
    private int nurseID;
    private int grade;//1:轻症 2：重症 3：危重症
    private int waitTransfer;//1:代转入
    private int lifeCondition;//0：出院 1：正在治疗 2：死亡
    private String arriveDate;
    private int tempCount;//连续三天正常
    private int testCount;//连续两次阴性

    public int getTempCount() {
        return tempCount;
    }

    public int getTestCount() {
        return testCount;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public int getGrade() {
        return grade;
    }

    public int getLifeCondition() {
        return lifeCondition;
    }

    public int getNurseID() {
        return nurseID;
    }

    public int getTreatArea() {
        return treatArea;
    }

    public int getWaitTransfer() {
        return waitTransfer;
    }

    public String getArriveDate() {
        return arriveDate;
    }
}
