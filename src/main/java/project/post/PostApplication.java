package project.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostApplication {

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.setData("test");
		String result = hello.getData();
		System.out.println("result = " + result);

		SpringApplication.run(PostApplication.class, args);
	}

}
