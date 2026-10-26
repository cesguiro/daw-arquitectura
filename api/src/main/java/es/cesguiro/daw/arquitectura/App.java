package es.cesguiro.daw.arquitectura;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);
        var env = context.getEnvironment();
        LOG.info("""
                Config de red interna leída del entorno:
                  DB_URL      = {}
                  S3_ENDPOINT = {}
                """,
                env.getProperty("DB_URL", "jdbc:mariadb://localhost:3306/miapp"),
                env.getProperty("S3_ENDPOINT", "http://localhost:9000"));
    }
}
