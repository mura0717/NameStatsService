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


    @GetMapping(value= "/non-blocking/name-info")
    public Mono<CombinedResponse> nameCheckNonBlocking(@RequestParam("name") String name) throws InterruptedException {
        return nameStatsService.getNameStatsAsynch(name);
    }

    @PostMapping("/non-blocking/name-info")
    public Mono<CombinedResponse> submitNameNonBlocking(@RequestParam("name") String name) throws InterruptedException {
        return nameStatsService.getNameStatsAsynch(name);
    }

    @GetMapping(value= "/blocking/name-info")
    public CombinedResponse nameCheckBlocking(@RequestParam("name") String name) throws InterruptedException {
        return nameStatsService.getNameStats(name);
    }

    @PostMapping("/blocking/name-info")
    public CombinedResponse submitNameBlocking(@RequestParam("name") String name) throws InterruptedException {
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
