package de.ppi.ai.demo2;

import java.util.Scanner;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;

// @Component
public class ChatClientExample implements CommandLineRunner {

    private final ChatClient chatClient;

    public ChatClientExample(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Simple Chat Client - Type 'exit' to quit");
        System.out.println("----------------------------------------");
        
        while (true) {
            System.out.print("\nYou: ");
            String input = scanner.nextLine();
            
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            
            System.out.print("AI: ");
            String response = chatClient.prompt(input)
                    .call()
                    .content();
            System.out.println(response);
        }
        
        scanner.close();
        System.out.println("\nGoodbye!");
    }
}
