package dat3.webclientexercise.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Component
public class CombinedResponse {
        public String name;
        public String gender;
        public double genderProbability;
        public int age;
        public int ageCount;
        public String country;
        public double countryProbability;

    public CombinedResponse(String name, GenderResponse t1, AgeResponse t2, CountryResponse t3) {
        this.name = name;
        this.gender = t1.getGender();
        this.genderProbability = t1.getProbability();
        this.age = t2.getAge();
        this.ageCount = t2.getCount();
        this.country = t3.getCountry().get(0).getCountry_id();
        this.countryProbability = t3.getCountry().get(0).getProbability();
    }
}
