package steps;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static utils.Util.*;

public class Hooks {
    private ArrayList<String> infomation = null;

    @Before
    public void setUp() throws MalformedURLException {
        abrirApp();
        gravar();
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        infomation= new ArrayList<String>();
        infomation.add("Api Demos");
        infomation.add(" XXX ");
        infomation.add("Homol");
        capturarScreenshot(scenario);
        pararGravacao(scenario);
        addEnvironmentAllure(infomation);
        killDriver();

    }

}
