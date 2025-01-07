package config;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class Config {
    private String url;
    private String databaseUser;
    private String databasePassword;
    private Browsers browserName;
    private ProjectEnvironments envName;
    private String fullName;
    private String email;
    private String currentAddress;
    private String permanentAddress;

    public enum Browsers {
        CHROME, FIREFOX
    }

    public enum ProjectEnvironments {
        TC, RC
    }

    public String getConfigLog() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        sb.append("------------------ CONFIG ------------------").append("\n");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb.append("          ").append(field.getName()).append(" : ").append(field.get(this)).append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("------------------ CONFIG ------------------");

        return sb.toString();
    }
}
