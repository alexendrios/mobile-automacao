package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "json:target/cucumber-report/report.json",
                "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"
        },
        features = "classpath:features",
        monochrome = true,
        dryRun = false,
        strict = false,
        glue = {"steps"},
        tags = "@exemplo",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunnerTest {
}
