package dat3.webclientexercise.config;

import dat3.webclientexercise.dtos.GenderResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RemoteApiTester implements CommandLineRunner {

    private Mono<String> callSlowEndpoint() {
        Mono<String> slowResponse = WebClient.create()
                .get()
                .uri("http://localhost:8080/random-string-slow")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> System.out.println("UUUPS : " + e.getMessage()));
        return slowResponse;
    }

    public void callSlowEndpointBlocking() {
        long start = System.currentTimeMillis();
        List<String> ramdomStrings = new ArrayList<>();

        Mono<String> slowResponse = callSlowEndpoint();
        ramdomStrings.add(slowResponse.block()); //Three seconds spent

        slowResponse = callSlowEndpoint();
        ramdomStrings.add(slowResponse.block());//Three seconds spent

        slowResponse = callSlowEndpoint();
        ramdomStrings.add(slowResponse.block());//Three seconds spent
        long end = System.currentTimeMillis();
        ramdomStrings.add(0, "Time spent BLOCKING (ms): " + (end - start));

        System.out.println(ramdomStrings.stream().collect(Collectors.joining(",")));
    }

    public void callSlowEndpointNonBlocking() {
        long start = System.currentTimeMillis();
        Mono<String> sr1 = callSlowEndpoint();
        Mono<String> sr2 = callSlowEndpoint();
        Mono<String> sr3 = callSlowEndpoint();

        var rs = Mono.zip(sr1, sr2, sr3).map(t -> {
            List<String> randomStrings = new ArrayList<>();
            randomStrings.add(t.getT1());
            randomStrings.add(t.getT2());
            randomStrings.add(t.getT3());
            long end = System.currentTimeMillis();
            randomStrings.add(0, "Time spent NON-BLOCKING (ms): " + (end - start));
            return randomStrings;
        });
        List<String> randoms = rs.block(); //We only block when all the three Mono's has fulfilled
        System.out.println(randoms.stream().collect(Collectors.joining(",")));
    }


    Mono<GenderResponse> getGenderForName(String name) {
        WebClient client = WebClient.create();
        Mono<GenderResponse> gender = client.get()
                .uri("https://api.genderize.io/?name=" + name)
                .retrieve()
                .bodyToMono(GenderResponse.class);
        return gender;
    }


    List<String> names = Arrays.asList("lars", "peter", "sanne", "kim", "david", "maja");

    public void getGendersBlocking() {
        long start = System.currentTimeMillis();
        List<GenderResponse> genders = names.stream().map(name -> getGenderForName(name).block()).toList();
        long end = System.currentTimeMillis();
        System.out.println("Time for six external requests, BLOCKING: " + (end - start));
    }

    public void getGendersNonBlocking() {
        long start = System.currentTimeMillis();
        var genders = names.stream().map(name -> getGenderForName(name)).toList();
        Flux<GenderResponse> flux = Flux.merge(Flux.concat(genders));
        List<GenderResponse> res = flux.collectList().block();
        long end = System.currentTimeMillis();
        System.out.println("Time for six external requests, NON-BLOCKING: " + (end - start));
    }


    @Override
    public void run(String... args) throws Exception {
        //System.out.println(callSlowEndpoint().toString());

        //String randomStr = callSlowEndpoint().block();
        //System.out.println(randomStr);

        //callSlowEndpointBlocking();
        //callSlowEndpointNonBlocking();

        //System.out.println(getGenderForName("deniz")); //It only prints the type information of the Mono object.
        //getGenderForName("John").subscribe(result -> System.out.println(result));

        //getGendersBlocking();
        //getGendersNonBlocking();


    }
}

