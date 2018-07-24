package club.williamleon;

import club.williamleon.config.FileProperties;
import club.williamleon.service.ImageService;
import club.williamleon.service.impl.ImageServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class GalleryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalleryApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ImageService imageService) {
		return (args) -> {
			imageService.init();
		};
	}
}
