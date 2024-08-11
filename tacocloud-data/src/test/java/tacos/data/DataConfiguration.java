package tacos.data;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
// Без следующей аннотации не работает тестирование.
// Походу Spring не шарит, где искать сущьности
@EntityScan("tacos")
public class DataConfiguration {
}
