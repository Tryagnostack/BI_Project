/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package bi_project;

import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;




public class App {

    static String url = "";
    static String ssFolder = "";

    static void Sec_1(ChromeDriver driver){
        boolean status = false;

        // StopWatch watch = new StopWatch();

        WebDriverWait wait = new WebDriverWait(driver, 5);

        try{
            Instant s = Instant.now();
            
            driver.get(url);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("css-1msksyp")));
            
            Instant e = Instant.now();
            long time = Duration.between(s, e).toMillis();
            
            CustomLoggers.sendLog("Section 1", "Time Taken: ", time/1000.0 + " seconds");
            status = true;

        } catch (Exception e) {
            System.out.println("Exception Occured:\n"+e);
            status = false;
        }
        
        CustomLoggers.sendLog("Section 1", "Time taken to load", status);
        
    }

    static void Sec_2(ChromeDriver driver){
        boolean status = false;

        try {
            File dir = new File(ssFolder);
            // System.out.println("saaaa");
            // System.out.println(dir.exists());
            // System.out.println(new File("").getAbsolutePath());
            if(!dir.exists())
                dir.mkdirs(); // System.out.println(dir.mkdirs()?"ha":"da");
        
            AShot ash = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000));
            Screenshot screenshot = ash.takeScreenshot(driver);

            String timestamp = String.valueOf(LocalDateTime.now()).replaceAll(":", "+");
            File desfile =  new File (ssFolder + "\\" + timestamp + ".png");

            ImageIO.write(screenshot.getImage(), "png", desfile);

            System.out.println("Screenshot taken: " + timestamp + ".png");
            status = true;
        } catch (Exception e) {
            System.out.println("Exception Occured:\n"+e);
            status = false;
        }
        
        CustomLoggers.sendLog("Section 2", "Screenshot", status);

    }

    static void Sec_3(ChromeDriver driver, String input){
        boolean status = false;
        // System.out.println(input);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try{
            driver.get(url);
            
            driver.findElement(By.tagName("input")).sendKeys(input);

            // Thread.sleep(2000);

            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//p[text()=\""+input+"\"]")));
            WebElement price = driver.findElement(By.xpath("//p[text()=\""+input+"\"]/following-sibling::p"));
            String itemPrice = price.getText();

            WebElement img = price.findElement(By.xpath("./parent::div/parent::div/img"));
            String imgUrl = img.getAttribute("src");

            System.out.println("Item price: " + itemPrice);
            System.out.println("Image Url: " + imgUrl);
            status = true;
        } catch (Exception e) {
            System.out.println("Exception Occured:\n"+e);
            status = false;
        }
        
        CustomLoggers.sendLog("Section 3", "Price and image", status);
    }



    public static void main(String[] args) {

        
        try {
            
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(".\\config.json")).getAsJsonObject();
            url = jsonObject.get("QKART_URL").getAsString();
            ssFolder = jsonObject.get("ScreenshotSaveLocation").getAsString();
            System.out.println(url);
            System.out.println(ssFolder);
            
        } catch (Exception e) {
            System.out.println("YEOWCH");
        }

        Methods methods = new Methods();
        ChromeDriver driver = methods.creatDriver();
        String input = String.join(" ",args);

        //Sec 1: Find QKart loading Time
        CustomLoggers.sendLog("Section 1","Check loading time","Start");
        Sec_1(driver);
        CustomLoggers.sendLog("Section 1","Check loading time","End");
        System.out.println();
        
        //Sec 2: Take fullpage Screenshot
        CustomLoggers.sendLog("Section 2","Take full page Screenshot","Start");
        Sec_2(driver);
        CustomLoggers.sendLog("Section 2","Take full page Screenshot","End");
        System.out.println();

        //Sec 3: Find product, get price and image url
        CustomLoggers.sendLog("Section 3","Get price and image Urls","Start");
        Sec_3(driver,input);
        CustomLoggers.sendLog("Section 3","Get price and image Urls","End");

        driver.quit();

    }
}
