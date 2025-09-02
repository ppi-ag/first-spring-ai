package de.ppi.ai.demo3;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;

// @Component
public class PromptDemo implements CommandLineRunner {

    private final ChatModel chatModel;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

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

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
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

            Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

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

}