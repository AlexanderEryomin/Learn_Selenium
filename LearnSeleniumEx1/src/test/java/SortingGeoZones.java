import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class SortingGeoZones {
    private WebDriver driver;
    private String username = "admin";
    private String password = "admin";
    @BeforeEach
    public void start(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public void authorize(String login, String pass){
        driver.get("http://192.168.1.19/litecart/admin");
        driver.findElement(By.name("username")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(pass);
        driver.findElement(By.name("login")).click();
    }
    @Test
    public void sortingGeoZones(){
        authorize( username, password );
        driver.findElement(By.linkText("Geo Zones")).click();
        WebElement tableGeoZones = driver.findElement(By.className("dataTable"));
        List<WebElement> listGeoZones = tableGeoZones.findElements(By.cssSelector("a:not([title=Edit])"));
        String[] nameGeoZones = new String[listGeoZones.size()];
        int i = 0;
        for( WebElement geoZone : listGeoZones ){
            nameGeoZones[i] = geoZone.getAttribute("text");
            i++;
        }
        for( int j = 0; j < nameGeoZones.length; j++ ){
            driver.findElement(By.linkText(nameGeoZones[j])).click();

            WebElement tableSelectedZone = driver.findElement(By.className("dataTable"));
            List<WebElement> listSelectedZones = tableSelectedZone.findElements(By.xpath("//td[3]/select/option[@selected='selected']"));
            String[] nameSelectedZone = new String[listSelectedZones.size()];
            String[] sortNameSelectedZone = new String[listSelectedZones.size()];
            int c = 0;
            for ( WebElement selectedZone : listSelectedZones ){
                nameSelectedZone[c] = selectedZone.getAttribute("textContent");
                sortNameSelectedZone[c] = selectedZone.getAttribute("textContent");
                c++;
            }
            Arrays.sort(sortNameSelectedZone);
            assertTrue(Arrays.equals(nameSelectedZone, sortNameSelectedZone));
            driver.findElement(By.linkText("Geo Zones")).click();
        }
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver = null;
    }

}
