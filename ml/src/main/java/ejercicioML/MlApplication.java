package ejercicioML;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ejercicioML.Domain.Categoria;
import ejercicioML.Domain.Datos;

@SpringBootApplication
public class MlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			
			List<String> users = new ArrayList<String>();

			users.add("179571326");

			boolean append = true;
			FileHandler handler = new FileHandler("default.log", append);
			handler.setFormatter(new SimpleFormatter());
			Logger logger = Logger.getLogger("ejercicioML");
			logger.addHandler(handler);
			logger.setUseParentHandlers(false);
			
			for (int i = 0; i < users.size(); i++) {
				
				System.out.println("Realizando consultas...");
				Datos datos = restTemplate.getForObject(
						"https://api.mercadolibre.com/sites/MLA/search?seller_id=" + users.get(i), Datos.class);
				logger.info("USER_ID: " + users.get(i));
				
				for (int j = 0; j < datos.getResults().length; j++) {
					
					Categoria categoria = restTemplate.getForObject(
							"https://api.mercadolibre.com/categories/" + datos.getResults()[j].getCategory_id(),
							Categoria.class);
					logger.info("ID: " + datos.getResults()[j].getId() + " || " + "TITLE: "
							+ datos.getResults()[j].getTitle() + " || " + "CATEGORY_ID: "
							+ datos.getResults()[j].getCategory_id() + " || " + "CATEGORY_NAME: "
							+ categoria.getName());
				}
				
				System.out.println("Consulta finalizada para usuario " + users.get(i) + "!");
			}
		};
	}
}
