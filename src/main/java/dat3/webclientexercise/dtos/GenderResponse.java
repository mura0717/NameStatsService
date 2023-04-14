package dat3.webclientexercise.dtos;

import dat3.webclientexercise.entity.NameStats;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GenderResponse {
    String gender;
    double probability;

    public GenderResponse(NameStats nameStats) {
        this.gender = nameStats.getGender();
        this.probability = nameStats.getGenderProbability();
    }
}

