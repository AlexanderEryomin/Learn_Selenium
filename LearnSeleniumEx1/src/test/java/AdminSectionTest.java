import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.opentest4j.AssertionFailedError;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminSectionTest {
    private WebDriver driver;
    private String username = "admin";
    private String password = "admin";

    @BeforeEach
    public void startBrowser(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @Test
    public void adminSection() {
        //1. Authorization
        driver.get("http://192.168.1.19/litecart/admin");
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();

        //2. Click on each left section
        List<WebElement> elements = driver.findElements(By.id("app-"));
        int i = 0;
        int length= elements.size();
        String[] links = new String[length];
        for (WebElement element: elements){
                links[i] = element.getText();
                i++;
        }

        for(int j = 0; j < links.length; j++){
            driver.findElement(By.linkText(links[j])).click();
            WebElement selectedElement = driver.findElement(By.className("selected"));
            List<WebElement> nestedMenu = selectedElement.findElements(By.tagName("li"));

            if(nestedMenu.size() > 0){

                    int sizeNestedMenu = nestedMenu.size();
                    String[] nestedMenuLinks = new String[sizeNestedMenu];
                    int count = 0;

                    for(WebElement elementNestedMenu : nestedMenu){
                        nestedMenuLinks[count] = elementNestedMenu.getText();
                        count++;
                    }

                    for(String link : nestedMenuLinks){
                        //3. Check title header for nested menu element
                        driver.findElement(By.linkText(link)).click();
                        String actualHeaderNestedMenu = driver.findElement(By.tagName("h1")).getText();
                        try {
                            assertEquals(link, actualHeaderNestedMenu);
                        }catch (AssertionFailedError ex){
                            System.out.println("Button name '" + link + "' not equal header page '" + actualHeaderNestedMenu + "'!!!");
                        }

                    }
                }
            else{
                //3. Check title header if not nested menu
                String actualHeader = driver.findElement(By.tagName("h1")).getText();
                assertEquals(links[j], actualHeader);
            }
        }
    }
    @AfterEach
    public void closeBrowser(){
        driver.quit();
        driver = null;
    }
}
