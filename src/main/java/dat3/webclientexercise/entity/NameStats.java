package dat3.webclientexercise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class NameStats {

    @Id
    public String name;
    public String gender;
    public int genderProbability;
    public int age;
    public int ageCount;
    public String country;
    public int countryProbability;

    public NameStats(String name, String gender, int genderProbability, int age, int ageCount, String country, int countryProbability) {
        this.name = name;
        this.gender = gender;
        this.genderProbability = genderProbability;
        this.age = age;
        this.ageCount = ageCount;
        this.country = country;
        this.countryProbability = countryProbability;
    }


}
