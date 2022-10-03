package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

class DeliveryTest {
    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateValidUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));

        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $x("//span[contains(text(), 'Запланировать')]").click();
        $x("//span[contains(text(), 'Перепланировать')]").click();
        $("[data-test-id='success-notification']").shouldHave(Condition.exactText("Успешно! Встреча успешно запланирована на " + secondMeetingDate));
    }
}