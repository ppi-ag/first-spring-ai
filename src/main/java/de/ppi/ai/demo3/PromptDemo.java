package de.ppi.ai.demo3;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PromptDemo implements CommandLineRunner {

    private final ChatModel chatModel;

    public PromptDemo(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Spring AI Prompt Objects Demo ===");
        System.out.println("This demo showcases using Message and Prompt objects");
        System.out.println("Type 'exit' to quit\n");
        
        System.out.print("Enter assistant name (e.g., Captain Hook): ");
        String name = scanner.nextLine();
        
        System.out.print("Enter voice/style (e.g., pirate, shakespearean, cowboy): ");
        String voice = scanner.nextLine();
        
        String systemText = """
            You are a helpful AI assistant that helps people find information.
            Your name is {name}
            You should reply to the user's request with your name and also in the style of a {voice}.
            """;
        
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));
        
        System.out.println("\nAssistant configured as: " + name + " speaking like a " + voice);
        System.out.println("----------------------------------------\n");
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            
            if ("exit".equalsIgnoreCase(userInput)) {
                break;
            }
            
            Message userMessage = new UserMessage(userInput);
            
            Prompt prompt = new Prompt(List.of(systemMessage, userMessage ));
            
            System.out.print(name + ": ");
            List<Generation> response = chatModel.call(prompt).getResults();
            
            if (!response.isEmpty()) {
                System.out.println(response.get(0).getOutput().getText());
            }
            System.out.println();
        }
        
        scanner.close();
        System.out.println("\nFarewell from " + name + "!");
    }
    
    public void demonstratePromptTypes() {
        System.out.println("\n=== Demonstrating Different Prompt Types ===\n");
        
        String userText = """
            Tell me about three famous pirates from the Golden Age of Piracy and why they did.
            Write at least a sentence for each pirate.
            """;
        
        Message userMessage = new UserMessage(userText);
        
        String systemText = """
            You are a helpful AI assistant that helps people find information.
            Your name is {name}
            You should reply to the user's request with your name and also in the style of a {voice}.
            """;
        
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage(
            Map.of("name", "Captain Teach", "voice", "old sea captain")
        );
        
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        
        System.out.println("Sending prompt with system message template...");
        List<Generation> response = chatModel.call(prompt).getResults();
        
        if (!response.isEmpty()) {
            System.out.println("\nResponse:");
            System.out.println(response.get(0).getOutput().getText());
        }
    }
}