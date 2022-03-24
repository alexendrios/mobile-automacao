package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import org.junit.Assert;
import pages.ExemploPage;
import java.net.MalformedURLException;

import static utils.Util.getDriver;


public class ExemploStep {
    ExemploPage test = new ExemploPage(getDriver());

    @Dado("que acess a aplicação e clico em Access-ibility")
    public void queAcessAaplicaçãoEclicoEmAccessibility() throws MalformedURLException {
        test.testando();
    }
    @Então("vejo a sequinte Texto {string}")
    public void vejoASequinteTexto(String string) {
        Assert.assertEquals(test.getStatus(), string);
    }
}
