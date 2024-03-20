package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Bank App api",
                description = "Backend Rest APIs For TJA Bank",
                version = "v1.0"
        )
)
public class Demo1Application {


    public static void main(String[] args) {

        SpringApplication.run(Demo1Application.class, args);
    }

}
