import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class FactorialTest {
    private WebDriver driver;
    private final String page_Url="http://qainterview.pythonanywhere.com/";

    @BeforeAll
    public static void beforeAll(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

    }

    @BeforeEach
    public  void beforeEach(){
        setupDriverSession();
        driver.get(page_Url);
        driver.manage().window().maximize();
    }

    private void setupDriverSession(){
        driver=new ChromeDriver();
        ChromeOptions options= new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterEach
    public void afterEach(){
        if(driver!=null) driver.quit();
    }

    @ParameterizedTest
    @CsvSource({"2","10","0"})
    @DisplayName("Ввод положительных чисел")
    public void PozitiveTests(int a) throws InterruptedException {

        Actions actions=new Actions(driver);

        for(        int i=a; i<=a+5; i++)
        {
            WebElement inputNumber=driver.findElement(By.id("number"));
            inputNumber.clear();
        actions.click(inputNumber).sendKeys(Integer.toString(i)).build().perform();

        WebElement buttonCalculate=driver.findElement(By.id("getFactorial"));
        buttonCalculate.click();
        Thread.sleep(5000);
        String message=driver.findElement(By.id("resultDiv")).getText();

        String expMessage = "The factorial of "+i+" is: "+factorial(i);
        System.out.println("Expected: "+ expMessage);
        System.out.println("Actual: "+ message);
        Assertions.assertEquals(expMessage,message);
        }
    }

    @ParameterizedTest
    @CsvSource({"-2","-5","-10","-1"})
    @DisplayName("Ввод отрицательных чисел")
    public void NegativeNumber(int a) throws InterruptedException {

        Actions actions=new Actions(driver);

        WebElement inputNumber=driver.findElement(By.id("number"));
        inputNumber.clear();
        actions.click(inputNumber).sendKeys(Integer.toString(a)).build().perform();

        WebElement buttonCalculate=driver.findElement(By.id("getFactorial"));
        buttonCalculate.click();
        Thread.sleep(5000);
        String message=driver.findElement(By.id("resultDiv")).getText();
        String expMessage = "Please enter a positive integer";
        System.out.println("Expected: "+ expMessage);
        System.out.println("Actual: "+ message);
        Assertions.assertEquals(expMessage,message);
    }

    @ParameterizedTest
    @CsvSource({"a","!","+_","s#"})
    @DisplayName("Ввод букв и символов")
    public void inputLetterAndSymbols(String a) throws InterruptedException {

        Actions actions=new Actions(driver);

        WebElement inputNumber=driver.findElement(By.id("number"));
        inputNumber.clear();
        actions.click(inputNumber).sendKeys(a).build().perform();

        WebElement buttonCalculate=driver.findElement(By.id("getFactorial"));
        buttonCalculate.click();
        Thread.sleep(5000);
        String message=driver.findElement(By.id("resultDiv")).getText();
        String expMessage = "Please enter an integer";
        System.out.println("Expected: "+ expMessage);
        System.out.println("Actual: "+ message);
        Assertions.assertEquals(expMessage,message);
    }
    public long factorial (int a)
    {
        long f=1l;
        for (int i=1; i<=a; i++)
            f=f*i;
        return f;
    }
}
