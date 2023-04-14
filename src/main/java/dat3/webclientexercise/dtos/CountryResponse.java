package dat3.webclientexercise.dtos;

import dat3.webclientexercise.entity.NameStats;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountryResponse {
    List<Country> country = new ArrayList<>();
}
