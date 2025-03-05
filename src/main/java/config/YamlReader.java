package config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class YamlReader {
    private final Environments environment;

    public YamlReader(String yamlFilePath) {
        Yaml yaml = new Yaml();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(yamlFilePath)) {
            if (inputStream == null) throw new IllegalArgumentException("File not found: " + yamlFilePath);
            environment = yaml.loadAs(inputStream, Environments.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file: " + yamlFilePath, e);
        }
    }

    public Config getActiveConfig() {
        return environment.getConfig().get(environment.getActiveEnvironment());
    }
}