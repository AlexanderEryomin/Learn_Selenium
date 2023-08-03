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
import static junit.framework.Assert.assertTrue;


public class CountrySortTest {
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
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void countrySort(){
        authorize(username, password);

        driver.findElement(By.linkText("Countries")).click();
        WebElement table = driver.findElement(By.className("dataTable"));
        List<WebElement> elements = table.findElements(By.cssSelector("a:not([title=Edit])"));
        List<WebElement> numberOfZones = table.findElements(By.xpath("//td[6]"));

        String[] countryList = new String[238];
        int counter = 0;
        String[] sortList = new String[238];

        for(WebElement element : elements){
           countryList[counter] = element.getAttribute("textContent");
           sortList[counter] = element.getAttribute("textContent");
           counter++;
        }
        Arrays.sort(sortList);
        //a)list of country in alphabetical order
        assertTrue(Arrays.equals(sortList, countryList));


        String[] zones = new String[238];
        int c =0;
        for( WebElement el : numberOfZones ){
            zones[c] = el.getAttribute("textContent");
            c++;
        }
        for(int j = 1; j != zones.length; j++){
            if( !zones[j].equals("0") ){
               driver.findElement(By.linkText(countryList[j])).click();
               WebElement tableZones = driver.findElement(By.id("table-zones"));
               List<WebElement> zoneName = tableZones.findElements(By.xpath("//td[3]/input[@type='hidden']"));

               int k = 0;
               String[] zoneListNotSort = new String[zoneName.size()];
               String[] zoneListSort = new String[zoneName.size()];

               for(WebElement zone: zoneName){
                   zoneListNotSort[k] = zone.getAttribute("value");
                   zoneListSort[k] = zone.getAttribute("value");
                   k++;
               }
               Arrays.sort(zoneListSort);
               //b)list of zone in alphabetical order
               assertTrue(Arrays.equals(zoneListSort, zoneListNotSort));
               driver.findElement(By.linkText("Countries")).click();
            }
        }
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver = null;
    }
}
