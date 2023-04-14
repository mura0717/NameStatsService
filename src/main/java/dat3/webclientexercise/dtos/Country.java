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
public class Country {
    String country_id;
    double probability;

    public Country(NameStats nameStats) {
        this.country_id = nameStats.country;
        this.probability = nameStats.getCountryProbability();
    }
}
