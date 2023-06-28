package com.example.apoorpoor_backend.dto.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
@Component
@Slf4j
public class BadWordFiltering implements BadWords {
    private final Set<String> set = new HashSet<>(List.of(badWords));


    public ChatDto change(ChatDto chatDto) {
        String text = chatDto.getMessage();
        StringBuilder singBuilder = new StringBuilder("[");
        for (String sing : sings) singBuilder.append(Pattern.quote(sing));
        singBuilder.append("]*");
        String patternText = singBuilder.toString();

        for (String word : set) {
            if (word.length() == 1) text = text.replace(word, substituteValue);
            String[] chars = word.split("");
            text = Pattern.compile(String.join(patternText, chars))
                    .matcher(text)
                    .replaceAll(v -> substituteValue.repeat(v.group().length()));
        }
        chatDto.setMessage(text);

        return chatDto;
    }

    public boolean checkBadId(String input) {
        StringBuilder singBuilder = new StringBuilder("[");
        for (String sing : sings) singBuilder.append(Pattern.quote(sing));
        singBuilder.append("]*");
        String patternText = singBuilder.toString();

        for (String word : set) {
            String[] chars = word.split("");
            if (Pattern.compile(String.join(patternText, chars))
                    .matcher(input)
                    .find()) return true;
        }
        return false;
    }
}