package tacos.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"tacos.data", "tacos.messaging"})
public class TacocloudApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacocloudApiApplication.class, args);
	}

}
