package de.ppi.first_spring_ai.config;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AzureOpenAiConfig {

    @Bean
    @Primary
    public ChatClient chatClient4o(ChatClient.Builder builder, AzureOpenAiChatModel chatModel) {
        AzureOpenAiChatOptions options = AzureOpenAiChatOptions.builder()
                .deploymentName("gpt-4o")
                .temperature(0.7)
                .build();

        return builder
                .defaultOptions(options)
                .build();
    }

    @Bean
    public ChatClient chatClient4oMini(ChatClient.Builder builder, AzureOpenAiChatModel chatModel) {
        AzureOpenAiChatOptions options = AzureOpenAiChatOptions.builder()
                .deploymentName("gpt-4o-mini")
                .temperature(0.7)
                .build();

        return builder
                .defaultOptions(options)
                .build();
    }
}