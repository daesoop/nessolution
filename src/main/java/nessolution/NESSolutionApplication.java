package nessolution;

import nessolution.common.file.service.FileProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import nessolution.common.file.service.FileService;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class NESSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(NESSolutionApplication.class, args);
    }

   @Bean
   CommandLineRunner init(FileService fileService) {
        return (args) -> {
//            fileService.deleteAll();
            fileService.init();
        };
    }
}