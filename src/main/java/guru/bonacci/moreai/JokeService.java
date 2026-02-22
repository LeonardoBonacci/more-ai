package guru.bonacci.moreai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class JokeService {

    private final ChatClient chatClient;

    public JokeService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String tellJoke() {
        String prompt = """
                Tell me a good joke.
                Make it short.
                """;

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
