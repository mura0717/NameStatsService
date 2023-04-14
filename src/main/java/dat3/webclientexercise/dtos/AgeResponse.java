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
public class AgeResponse {

    int age;
    int count;

    public AgeResponse(NameStats nameStats) {
        this.age = nameStats.getAge();
        this.count = nameStats.getAgeCount();
    }
}
