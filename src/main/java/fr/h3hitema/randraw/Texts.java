package fr.h3hitema.randraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Texts {

    private static final Random RND = new Random();
    private static List<String> NAMES = Collections.emptyList();
    private static List<String> ADJECTIVES = Collections.emptyList();

    public static void loadTexts() throws IOException {
        try (InputStream namesStream = Texts.class.getResourceAsStream("/names.txt");
             InputStream adjectivesStream = Texts.class.getResourceAsStream("/adjectives.txt")) {
            NAMES = new BufferedReader(new InputStreamReader(namesStream, StandardCharsets.UTF_8)).lines().filter(s -> !s.isEmpty()).collect(Collectors.toList());
            ADJECTIVES = new BufferedReader(new InputStreamReader(adjectivesStream, StandardCharsets.UTF_8)).lines().filter(s -> !s.isEmpty()).collect(Collectors.toList());
            System.out.println("Loaded " + NAMES.size() + " names");
            System.out.println("Loaded " + ADJECTIVES.size() + " adjectives");
        }
    }

    public static String getRandomName() {
        return NAMES.get(RND.nextInt(NAMES.size()));
    }

    public static String getRandomAdjective() {
        return ADJECTIVES.get(RND.nextInt(ADJECTIVES.size()));
    }

    public static List<String> getNames() {
        return new ArrayList<>(NAMES);
    }

    public static List<String> getAdjectives() {
        return new ArrayList<>(ADJECTIVES);
    }

}
