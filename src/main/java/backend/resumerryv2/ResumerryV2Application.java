package backend.resumerryv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ResumerryV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ResumerryV2Application.class, args);
	}

}
