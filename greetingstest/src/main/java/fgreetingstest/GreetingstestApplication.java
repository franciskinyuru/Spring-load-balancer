package fgreetingstest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@SpringBootApplication
public class GreetingstestApplication {
	public static Logger log= LoggerFactory.getLogger(GreetingstestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GreetingstestApplication.class, args);
	}
    @GetMapping("/greeting")
	public String greet(){
		log.info("access /greeting");
		List<String> greetings = Arrays.asList("Hi there", "Greetings","salutations");
		Random rand = new Random();
		int randomNum = rand.nextInt(greetings.size());
		return greetings.get(randomNum);
	}

	@GetMapping("/")
	public String home(){
		log.info("access /");
		return "Hi!";
	}

}
