package guru.bonacci.moreai;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JokeRunner {

	@Bean
  CommandLineRunner run(JokeService jokeService) {
      return args -> {
            String response = jokeService.tellJoke();

            System.out.println("🤖 Good Joke:");
            System.out.println(response);
            System.out.println("---");
        };
    }
}