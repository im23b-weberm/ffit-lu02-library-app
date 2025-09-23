package ch.bzz;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Config {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("config.properties konnte nicht geladen werden!", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
