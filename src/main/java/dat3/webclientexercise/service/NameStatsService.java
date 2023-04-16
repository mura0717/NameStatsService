package dat3.webclientexercise.service;

import dat3.webclientexercise.dtos.AgeResponse;
import dat3.webclientexercise.dtos.CombinedResponse;
import dat3.webclientexercise.dtos.CountryResponse;
import dat3.webclientexercise.dtos.GenderResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NameStatsService {

    //private final NameStatsRepository nameStatsRepository;
    private CombinedResponse combinedResponse;

/*    public NameStatsService(NameStatsRepository nameStatsRepository, CombinedResponse combinedResponse) {
        this.nameStatsRepository = nameStatsRepository;
        this.combinedResponse = combinedResponse;
    }*/

    public NameStatsService(CombinedResponse combinedResponse) {
        this.combinedResponse = combinedResponse;
    }

    Mono<GenderResponse> getGenderForName(String name) {
        WebClient client = WebClient.create();
        Mono<GenderResponse> genderResponse = client.get()
                .uri("https://api.genderize.io/?name=" + name)
                .retrieve()
                .bodyToMono(GenderResponse.class);
        return genderResponse;
    }

    Mono<AgeResponse> getAgeForName(String name) {
        WebClient client = WebClient.create();
        Mono<AgeResponse> ageResponse = client.get()
                .uri("https://api.agify.io/?name=" + name)
                .retrieve()
                .bodyToMono(AgeResponse.class);
        return ageResponse;
    }

    Mono<CountryResponse> getCountryForName(String name) {
        WebClient client = WebClient.create();
        Mono<CountryResponse> countryResponse = client.get()
                .uri("https://api.nationalize.io/?name=" + name)
                .retrieve()
                .bodyToMono(CountryResponse.class);
        return countryResponse;
    }

//Blocking
/*    public CombinedResponse getNameStats(String name) {

        String nameUpperCased = name.toUpperCase(); // For Cache

        Mono<GenderResponse> genderResponseMono = getGenderForName(name);
        Mono<AgeResponse> ageResponseMono = getAgeForName(name);
        Mono<CountryResponse> countryResponseMono = getCountryForName(name);

        var rs = Mono.zip(genderResponseMono, ageResponseMono, countryResponseMono).map(t ->
            new CombinedResponse(name, t.getT1(), t.getT2(), t.getT3()));
        CombinedResponse combinedResponse = rs.block();
       // System.out.println("combinedResponse = " + combinedResponse);
        return combinedResponse;
    }*/

    //Non-Blocking
    public Mono<CombinedResponse> getNameStats(String name) {
        Mono<GenderResponse> genderResponseMono = getGenderForName(name);
        Mono<AgeResponse> ageResponseMono = getAgeForName(name);
        Mono<CountryResponse> countryResponseMono = getCountryForName(name);

        return Flux.zip(genderResponseMono, ageResponseMono, countryResponseMono)
                .map(t -> new CombinedResponse(name, t.getT1(), t.getT2(), t.getT3()))
                .single(); // Only one CombinedResponse object is emitted
    }

}
