package com.ToDoBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//Для запуска(Пометка для будущего, в ресурсах настройка для запуска без БД!!!!!!!!)
@SpringBootApplication
public class ToDoBotApplication {
        public static void main(String[] args) {
            SpringApplication.run(ToDoBotApplication.class, args);
        }
}

