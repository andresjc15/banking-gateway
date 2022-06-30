package pe.com.nttdata.gateway.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BankingGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingGatewayApplication.class, args);
	}

}
