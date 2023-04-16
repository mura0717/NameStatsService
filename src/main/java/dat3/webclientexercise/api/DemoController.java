package dat3.webclientexercise.api;

import dat3.webclientexercise.dtos.CombinedResponse;
import dat3.webclientexercise.service.NameStatsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    private final int SLEEP_TIME = 1000*3;
    private final NameStatsService nameStatsService;

    public DemoController(NameStatsService nameStatsService) {
        this.nameStatsService = nameStatsService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping(value= "/name-info")
    public Mono<CombinedResponse> nameCheck (@RequestParam("name") String name) throws InterruptedException {
        return nameStatsService.getNameStats(name);
    }

    @PostMapping("/name-info")
    public Mono<CombinedResponse> submitName(@RequestParam("fname") String name) throws InterruptedException {
        return nameStatsService.getNameStats(name);
    }

    @GetMapping(value = "/random-string-slow")
    public String slowEndpoint() throws InterruptedException {
        Thread.sleep(SLEEP_TIME);
        return RandomStringUtils.randomAlphanumeric(10);
    }

/*    @GetMapping(value= "/name-info")
    public CombinedResponse nameCheck (@RequestParam("name") String name) throws InterruptedException {
        return nameStatsService.getNameStats(name);
    }*/

}
