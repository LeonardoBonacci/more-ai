package guru.bonacci.moreai;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.ollama.OllamaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class OllamaIntegrationTest {

    static final String MODEL = "tinyllama";

    @MockitoBean
    CommandLineRunner run;

    @Autowired
    JokeService jokeService;

    @Container
    @ServiceConnection
    static OllamaContainer ollama =
        new OllamaContainer("ollama/ollama:latest");

    @DynamicPropertySource
    static void overrideModel(DynamicPropertyRegistry registry) {
        registry.add("spring.ai.ollama.chat.options.model", () -> MODEL);
    }

    @BeforeAll
    static void pullModel() throws IOException, InterruptedException {
        ollama.execInContainer("ollama", "pull", MODEL);
    }

    @Test
    void contextLoads() {
        // If the context starts, Spring AI is using OllamaConnectionDetails from the container
    }

    @Test
    void shouldReturnAJoke() {
        String joke = jokeService.tellJoke();

        assertThat(joke)
            .isNotBlank()
            .hasSizeGreaterThan(10);

        System.out.println("🤖 Joke from Ollama container: " + joke);
    }
}