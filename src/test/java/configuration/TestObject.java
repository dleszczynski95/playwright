package configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import static configuration.Listener.*;

@Data
@AllArgsConstructor
public class TestObject {
    private String testName;
    private TestResult testResult;

    @AllArgsConstructor
    @Getter
    public enum TestResult {
        PASSED(PASS_SIGN, "\u001B[32m"), FAILED(FAIL_SIGN, "\u001B[31m"),
        SKIPPED(SKIP_SIGN, "\u001B[33m"), NOT_RUN(" ", "");

        private final String sign;
        private final String color;
    }

    @Override
    public String toString() {
        return "          " + testResult.color + "[" + testResult.sign + "] " + testName + RESET;
    }
}