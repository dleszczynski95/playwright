package config;

import lombok.Data;

import java.util.Map;

@Data
public class Environments {
    private Map<String, Config> config;
    private String activeEnvironment;

    public Map<String, Config> getConfig() {
        return config;
    }

    public String getActiveEnvironment() {
        return activeEnvironment;
    }
}