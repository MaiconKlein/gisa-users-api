package br.com.boasaude.gisa.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableKafka
@EnableTransactionManagement
@OpenAPIDefinition(info = @Info(title = "Usuários API", version = "1.0", description = "Cadastro de Usuários"))
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
