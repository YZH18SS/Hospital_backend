package hospital.backend.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bed {
    public int id;
    public int patientID;
    public int roomID;
    public int treatArea;
}
