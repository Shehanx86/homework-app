package com.homework.app;

import com.homework.app.model.User;
import com.homework.app.respository.HomeworkRepository;
import com.homework.app.respository.UserRepository;
import com.homework.app.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.homework.app.model.Roles.*;

@SpringBootApplication
@ComponentScan(basePackages = {"com.homework.app"})

@EnableMongoRepositories(basePackageClasses = { HomeworkRepository.class , UserRepository.class})
public class AppApplication {

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	User user1 = new User(
			"1",
			"teacher_1",
			"teacher1",
			"123",
			TEACHER
	);

	User user2 = new User(
			"2",
			"student_1",
			"student1",
			"123",
			STUDENT
	);

	@Bean
	CommandLineRunner run(UserServiceImpl userService){
		return args -> {
			userService.addUser(user1,"teacher");
			userService.addUser(user2,"student");
		};
	}

}
