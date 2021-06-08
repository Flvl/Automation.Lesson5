import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class CRMTest {

    private final String crm_login="Applanatest1";
    private final String crm_Password="Student2020!";
    private final String page_Url="https://crm.geekbrains.space/user/login";
    private WebDriver driver;


    @BeforeAll
    public static void beforeAll(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
    }

    @BeforeEach
    public  void beforeEach(){


        setupDriverSession();
        login();


    }

     @AfterEach
    public void afterEach(){
        if(driver!=null) driver.quit();
    }

    @Test
    @DisplayName("Создание проекта")
    public void createProject(){
        Actions actions=new Actions(driver);

        WebElement menuProject=driver.findElement(By.xpath("//span[contains(.,'Проекты')]"));
        actions.moveToElement(menuProject);

        WebElement menuMyProject=driver.findElement(By.xpath("//span[contains(.,'Мои проекты')]"));
        actions.moveToElement(menuMyProject).click().build().perform();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement buttonCreateProject=driver.findElement(By.linkText("Создать проект"));
        buttonCreateProject.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        Date date = new Date();
        String NameProject="myProject"+date.getTime();
        WebElement inputNameProject=driver.findElement(By.name("crm_project[name]"));
        inputNameProject.click();
        inputNameProject.sendKeys(NameProject);

        WebElement nameOrganizationDropDown=driver.findElement(By.xpath("//span[contains(.,'Укажите организацию')]"));
        nameOrganizationDropDown.click();


        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement inputNameOrganization=driver.findElement(By.xpath("//*[@id=\"select2-drop\"]/ul[2]/li[1]/div"));
        inputNameOrganization.click();

        Select businessUnitDropDown= new Select(driver.findElement(By.name("crm_project[businessUnit]")));
        businessUnitDropDown.selectByVisibleText("Research & Development");

        Select curatorDropDown= new Select(driver.findElement(By.name("crm_project[curator]")));
        curatorDropDown.selectByValue("115");

        Select managerDropDown= new Select(driver.findElement(By.name("crm_project[manager]")));
        managerDropDown.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");

        Select rpDropDown= new Select(driver.findElement(By.name("crm_project[rp]")));
        rpDropDown.selectByVisibleText("Applanatest2 Applanatest2 Applanatest2");


        Select administratorDropDown= new Select(driver.findElement(By.name("crm_project[administrator]")));
        administratorDropDown.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");


        WebElement buttonSaveProject=driver.findElement(By.xpath("//button[contains(.,'Сохранить и закрыть')]"));
        buttonSaveProject.click();

        String message = new WebDriverWait(driver,10). until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='message']"))).getText();
        Assertions.assertTrue(message.contains("Проект сохранен"));
    }

    @Test
    @DisplayName("Создание контакта")
    public void createContact(){
        Actions actions=new Actions(driver);
        WebElement menuProject=driver.findElement(By.xpath("//span[contains(.,'Контрагенты')]"));
        actions.moveToElement(menuProject);

        WebElement menuMyProject=driver.findElement(By.xpath("//span[contains(.,'Контактные лица')]"));
        actions.moveToElement(menuMyProject).click().build().perform();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement buttonCreateProject=driver.findElement(By.linkText("Создать контактное лицо"));
        buttonCreateProject.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement inputLastName=driver.findElement(By.name("crm_contact[lastName]"));
        inputLastName.click();
        inputLastName.sendKeys("Иванов");

        WebElement inputFirstName=driver.findElement(By.name("crm_contact[firstName]"));
        inputFirstName.click();
        inputFirstName.sendKeys("Иван");

        WebElement nameOrganizationDropDown=driver.findElement(By.xpath("//span[contains(.,'Укажите организацию')]"));
        nameOrganizationDropDown.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement inputNameOrganization=driver.findElement(By.xpath("//*[@id=\"select2-drop\"]/ul[2]/li[2]/div"));
        inputNameOrganization.click();

        WebElement inputJobTitle=driver.findElement(By.name("crm_contact[jobTitle]"));
        inputJobTitle.click();
        inputJobTitle.sendKeys("Инженер");


        WebElement buttonSaveProject=driver.findElement(By.xpath("//button[contains(.,'Сохранить и закрыть')]"));
        buttonSaveProject.click();

        String message = new WebDriverWait(driver,10). until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='message']"))).getText();
        //Assertions.assertTrue(message.contains("Контактное лицо сохранено"));
        Assertions.assertEquals("Контактное лицо сохранено",message);
    }
    private void login(){
        driver.get(page_Url);
        driver.manage().window().maximize();

        WebElement loginTextInput= driver.findElement(By.name("_username"));
        loginTextInput.sendKeys(crm_login);

        WebElement passwordTextInput= driver.findElement(By.name("_password"));
        passwordTextInput.sendKeys(crm_Password);

        WebElement buttonCreate = driver.findElement(By.name("_submit"));
        buttonCreate.click();
    }

    private void setupDriverSession(){
        driver=new ChromeDriver();
        ChromeOptions options= new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
