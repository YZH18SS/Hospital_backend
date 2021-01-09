package hospital.backend.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    public static final int NORMAL_RESULT = 1;
    public int patientID;
    public String testDate;
    public int testResult;//0：阳性 1:阴性
    public int grade;
}
