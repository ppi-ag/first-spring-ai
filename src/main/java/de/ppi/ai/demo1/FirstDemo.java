package de.ppi.ai.demo1;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FirstDemo {

    private final ChatClient chatClient;

    public FirstDemo(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @Bean
	public CommandLineRunner runner() {
		return args -> {
			String response = chatClient.prompt("Tell me a joke").call().content();							
			System.out.println(response);
		};
	}
}
