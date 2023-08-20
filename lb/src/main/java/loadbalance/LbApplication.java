package loadbalance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class LbApplication {
	private final WebClient.Builder lbWebClientBuilder;
	private final ReactorLoadBalancerExchangeFilterFunction lbFuction;

	public LbApplication(WebClient.Builder lbWebClientBuilder, ReactorLoadBalancerExchangeFilterFunction lbFuction) {
		this.lbWebClientBuilder = lbWebClientBuilder;
		this.lbFuction = lbFuction;
	}

	public static void main(String[] args) {
		SpringApplication.run(LbApplication.class, args);
	}
	@RequestMapping("/hi")
	public Mono<String> hi(@RequestParam(value = "name", defaultValue = "Francis") String name){
		return lbWebClientBuilder.build().get().uri("http://greetings/greeting")
				.retrieve().bodyToMono(String.class)
				.map(greeting ->String.format("%s, %s!",greeting, name));
	}

	@RequestMapping("/hello")
	public Mono<String> hello(@RequestParam(value = "name", defaultValue = "Warui") String name){
		return WebClient.builder()
				.filter(lbFuction)
				.build().get().uri("http://greetings/greeting")
				.retrieve().bodyToMono(String.class).map(greeting-> String.format("%s, %s!", greeting, name));
	}
}
