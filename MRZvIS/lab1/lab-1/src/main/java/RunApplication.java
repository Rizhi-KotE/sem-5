import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RunApplication {
    @Bean
    public Window window() {
        return new Window();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RunApplication.class, args);

        Window window = context.getBean(Window.class);
        window.setVisible(true);
    }
}
