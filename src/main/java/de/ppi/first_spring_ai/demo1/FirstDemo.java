package de.ppi.first_spring_ai.demo1;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FirstDemo {

    @Autowired
    private ChatClient chatClient4oMini;

    @Bean
	public CommandLineRunner runner(ChatClient.Builder builder) {
		return args -> {
			String response = chatClient4oMini.prompt("Tell me a joke").call().content();							
			System.out.println(response);
		};
	}
}
