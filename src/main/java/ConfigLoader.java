
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {

    public static void load(String path) throws FileNotFoundException, IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) continue;
                if (line.startsWith("#")) continue;
                if (!line.contains("=")) continue;

                String[] parts = line.split("=", 2);

                String key = parts[0].trim();
                String value = parts[1].trim();

                //value = stripQuotes(value);

                apply(key, value);
            }
        }
    }

    private static String stripQuotes(String value) {
        if ((value.startsWith("\"") && value.endsWith("\"")) ||
            (value.startsWith("'") && value.endsWith("'"))) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
    private static String[] parseList(String value) {

        String[] parts = value.split(",");

        return java.util.Arrays.stream(parts)
                .map(String::trim)
                .map(ConfigLoader::stripQuotes)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }
    
    private static void apply(String key, String value) {

        switch (key) {

            case "DISCORD_TOKEN" -> BotConfig.DISCORD_TOKEN = value;
            case "BOT_USER_ID" -> BotConfig.BOT_USER_ID = value;
            case "DATA_DIR" -> BotConfig.DATA_DIR = value;

            case "YOUTUBE_API_KEY" -> BotConfig.YOUTUBE_API_KEY = value;
            case "SPOTIFY_CLIENT_ID" -> BotConfig.SPOTIFY_CLIENT_ID = value;
            case "SPOTIFY_CLIENT_SECRET" -> BotConfig.SPOTIFY_CLIENT_SECRET = value;

            case "BOT_STATUS" -> BotConfig.BOT_STATUS = value;
            case "BOT_ACTIVITY_TYPE" -> BotConfig.BOT_ACTIVITY_TYPE = value;
            case "BOT_ACTIVITY" -> BotConfig.BOT_ACTIVITY = value;

            case "BOT_ACTIVITY_URL" -> BotConfig.BOT_ACTIVITY_URL = value;
            case "DEBUG" -> BotConfig.DEBUG = value;
            case "TARGETED_GUILDS" -> BotConfig.TARGETED_GUILDS = parseList(value);
        }
    }
}