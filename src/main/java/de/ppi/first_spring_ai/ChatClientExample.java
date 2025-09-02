package de.ppi.first_spring_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ChatClientExample implements CommandLineRunner {

    @Autowired
    private ChatClient chatClient;

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