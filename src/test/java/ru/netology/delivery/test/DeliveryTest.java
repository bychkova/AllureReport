package ru.netology.delivery.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.delivery.data.DataGenerator;


import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.Keys.BACK_SPACE;

class DeliveryTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void close() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSuccessfullyPlanAndReplanMeeting() {
        DataGenerator.UserInfo user = DataGenerator.Registration.generateValidUser("ru");
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='city'] input")).sendKeys(user.getCity());
        driver.findElement(By.cssSelector("[data-test-id='date'] input")).sendKeys(Keys.CONTROL + "A", BACK_SPACE);
        driver.findElement(By.cssSelector("[data-test-id='date'] input")).sendKeys(user.getDate());
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(user.getName());
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys(user.getPhone());
        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/fieldset/div[6]/div[2]/div/button")).click();

        driver.findElement(By.cssSelector("[data-test-id='date'] input")).sendKeys(Keys.CONTROL + "A", BACK_SPACE);
        String secondDate = DataGenerator.generateDate(5);
        driver.findElement(By.cssSelector("[data-test-id='date'] input")).sendKeys(secondDate);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/fieldset/div[6]/div[2]/div/button")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='success-notification'] .notification__content")).getText();
        String expected = "Встреча успешно запланирована на " + secondDate;

        assertEquals(expected, actual);
    }
}