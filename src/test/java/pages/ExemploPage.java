package pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class ExemploPage {

    private String status ="";

    public ExemploPage(AndroidDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(accessibility = "Access'ibility")
    private MobileElement acesso;

    @AndroidFindBy(accessibility = "Accessibility Node Provider")
    private MobileElement txtAccessibilityNodeProvider;

    public void testando(){
        acesso.click();
        this.status = txtAccessibilityNodeProvider.getText();
    }
    public String getStatus(){
        return this.status;
    }


}
