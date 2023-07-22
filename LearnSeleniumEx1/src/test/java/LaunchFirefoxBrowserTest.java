import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.*;
import java.io.File;
public class LaunchFirefoxBrowserTest {
    private WebDriver fireFoxDriver;
    private WebDriver fireFoxNightlyDriver;
    @Test
    public void launchFirefox(){

        FirefoxOptions defaultFirefox = new FirefoxOptions();
        defaultFirefox.setBinary(new FirefoxBinary(
                new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe")
        ));

        fireFoxDriver = new FirefoxDriver(defaultFirefox);
        fireFoxDriver.get("http://192.168.1.19/litecart/admin");
        fireFoxDriver.findElement(By.name("username")).sendKeys("admin");
        fireFoxDriver.findElement(By.name("password")).sendKeys("admin");
        fireFoxDriver.findElement(By.name("login")).click();

        fireFoxDriver.quit();
        fireFoxDriver = null;

    }
    @Test
    public void launchFirefoxNightly(){

        FirefoxOptions firefoxNightlyOptions = new FirefoxOptions();
        firefoxNightlyOptions.setBinary(new FirefoxBinary(
                new File("C:\\Program Files\\Firefox Nightly\\firefox.exe")
        ));

        fireFoxNightlyDriver = new FirefoxDriver(firefoxNightlyOptions);
        fireFoxNightlyDriver.get("http://192.168.1.19/litecart/admin");
        fireFoxNightlyDriver.findElement(By.name("username")).sendKeys("admin");
        fireFoxNightlyDriver.findElement(By.name("password")).sendKeys("admin");
        fireFoxNightlyDriver.findElement(By.name("login")).click();

        fireFoxNightlyDriver.quit();
        fireFoxNightlyDriver = null;
    }
}
