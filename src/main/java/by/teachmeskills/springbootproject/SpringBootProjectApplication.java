package by.teachmeskills.springbootproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        WebSocketServletAutoConfiguration.class,
        TaskSchedulingAutoConfiguration.class})
public class SpringBootProjectApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootProjectApplication.class);
        Environment environment = springApplication.run(args).getEnvironment();
        log.info("""
                        \n----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\thttp://localhost:{}{}
                        ----------------------------------------------------------""",
                environment.getProperty("spring.application.name"), environment.getProperty("server.port"), "/login");
    }
}