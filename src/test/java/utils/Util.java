package utils;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.cucumber.core.api.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Util {

    private static File app = new File("src/test/resources/apps/ApiDemos-debug.apk");
    private static AndroidDriver driver = null;

    public static AndroidDriver getDriver(){

        if (driver == null) {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", "Android");
            desiredCapabilities.setCapability("deviceName", "emulator-5554");
            desiredCapabilities.setCapability("automationName", "uiautomator2");
            desiredCapabilities.setCapability("app", app.getAbsolutePath());

            // abrir conexao com dispostivo (sessao)
            try {
                driver = new AndroidDriver (new URL("http://0.0.0.0:4723/wd/hub"), desiredCapabilities);
            } catch (MalformedURLException e) {

                e.printStackTrace();
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void killDriver() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void abrirApp() throws MalformedURLException {
        getDriver();
    }

    public static void capturarScreenshot() throws MalformedURLException {
        File imagem = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            Allure.addAttachment("Screenshot", Files.newInputStream(Paths.get(imagem.getPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void capturarScreenshot(Scenario scenario) throws MalformedURLException {
        final byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");

        File imagem = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(imagem,
                    (new File("./target/screnshot", scenario.getName() + " - " + scenario.getStatus() + ".jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addEnvironmentAllure(ArrayList<String> infomationAplication) {
        try {
            Capabilities cap =  getDriver().getCapabilities();
            String sistemaMobile = (String) cap.getCapability("platformName");

            File env = new File("./target/allure-reports/environment.properties");
            BufferedWriter as = new BufferedWriter(new FileWriter(env));
            as.write("MOBILE SYSTEM: "+sistemaMobile.toUpperCase()+" - Version: "+ cap.getCapability("platformVersion"));
            as.newLine();
            as.write("APLICATION: "+infomationAplication.get(0).toUpperCase() + " - VERSION: " +infomationAplication.get(1));
            as.newLine();
            as.write("ENVIRONMENT: " + infomationAplication.get(2).toUpperCase());
            as.newLine();
            as.write("OPERATIONAL SYSTEM:  " + System.getProperty("os.name").toUpperCase());
            as.newLine();
            as.write("USER RESPONSIBLE FOR THE EXECUTION: " + System.getProperty("user.name").toUpperCase());
            as.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void gravar() throws MalformedURLException {
        driver.startRecordingScreen(new AndroidStartScreenRecordingOptions());

    }

    @Attachment(value = "Evidence Video", type = "video/mp4")
    public static void pararGravacao(Scenario scenario) throws Exception {

        File arquivo = new File("target/recordings");

        String base64String = driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        String
                destinationPath = arquivo + scenario.getName()+" - " + scenario.getStatus()+".mp4";
        Path path = Paths.get(destinationPath);
        Files.write(path, data);

        try {
            byte[] byteArr = IOUtils.toByteArray(new FileInputStream(String.valueOf(path)));
            Allure.addAttachment(scenario.getName()+" - "+scenario.getStatus(), "video/mp4", new ByteArrayInputStream(byteArr), "mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
