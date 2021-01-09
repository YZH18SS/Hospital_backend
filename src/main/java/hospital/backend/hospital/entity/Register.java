package hospital.backend.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {
    public int patientID;
    public String registerDate;
    public int lifeCondition;
    public double temperature;
    public String symptom;
}
