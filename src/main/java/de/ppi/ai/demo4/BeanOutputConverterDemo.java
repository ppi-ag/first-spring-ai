package de.ppi.ai.demo4;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.boot.CommandLineRunner;

// @Component
public class BeanOutputConverterDemo implements CommandLineRunner {

    private final ChatModel chatModel;

    public BeanOutputConverterDemo(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    record ActorsFilms(String actor, List<String> movies) {
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Spring AI BeanOutputConverter Demo ===");
        System.out.println("This demo shows structured output generation using BeanOutputConverter");
        System.out.println("Choose a demo:");
        System.out.println("1. High-level ChatClient API");
        System.out.println("2. Low-level ChatModel API");
        System.out.println("3. Exit");
        System.out.println();

        while (true) {
            System.out.print("Enter choice (1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> demonstrateHighLevelApi();
                case "2" -> demonstrateLowLevelApi();
                case "3" -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private void demonstrateHighLevelApi() {
        System.out.println("\n--- High-level ChatClient API Demo ---");
        System.out.println("Using the fluent ChatClient API with .entity() method");
        System.out.println("Generating filmography for Tom Hanks...\n");

        ActorsFilms actorsFilms = ChatClient.create(chatModel).prompt()
                .user(u -> u.text("Generate the filmography of 5 movies for {actor}.")
                        .param("actor", "Tom Hanks"))
                .call()
                .entity(ActorsFilms.class);

        System.out.println("Actor: " + actorsFilms.actor());
        System.out.println("Movies:");
        actorsFilms.movies().forEach(movie -> System.out.println("  - " + movie));
    }

    private void demonstrateLowLevelApi() {
        System.out.println("\n--- Low-level ChatModel API Demo ---");
        System.out.println("Using BeanOutputConverter directly with ChatModel");
        System.out.println("Generating filmography for Tom Hanks...\n");

        BeanOutputConverter<ActorsFilms> beanOutputConverter = 
            new BeanOutputConverter<>(ActorsFilms.class);

        String format = beanOutputConverter.getFormat();
        String actor = "Tom Hanks";

        String template = """
                Generate the filmography of 5 movies for {actor}.
                {format}
                """;

        Generation generation = chatModel.call(
            PromptTemplate.builder()
                .template(template)
                .variables(Map.of("actor", actor, "format", format))
                .build()
                .create()
        ).getResult();

        ActorsFilms actorsFilms = beanOutputConverter.convert(generation.getOutput().getText());

        System.out.println("Actor: " + actorsFilms.actor());
        System.out.println("Movies:");
        actorsFilms.movies().forEach(movie -> System.out.println("  - " + movie));
    }
}