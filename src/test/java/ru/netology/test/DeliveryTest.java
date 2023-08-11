package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class DeliveryTest {

    @BeforeEach
    void shouldSetUp() {

        open("http://localhost:9999/");

    }

    @Test
    void shouldTestGoodPath() {
        int addFirstDate = 4;
        String firstDate = DataGenerator.generateDate(addFirstDate);
        int addSecondDate = 10;
        String secondDate = DataGenerator.generateDate(addSecondDate);


        $("[data-test-id='city'] input").setValue(DataGenerator.generateCityInList());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='success-notification']").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstDate));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondDate);
        $(withText("Запланировать")).click();
        $("[data-test-id=replan-notification]")
                .shouldBe((visible))
                .shouldHave(text("Необходимо подтверждение"));
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + secondDate));
    }

    @Test
    void shouldTestIfCityEmpty() {
        String date = DataGenerator.generateDate(3);
        $("[data-test-id='city'] input").setValue(" ");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]")
                .shouldBe(visible);
    }


    @Test
    void shouldTestIfCityNotInList() {
        String date = DataGenerator.generateDate(3);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCityNotInList());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[@class='input__sub'][contains(text(), 'Доставка в выбранный город недоступна')]").should(appear);

    }

    @Test
    void shouldTestIfNameIsEmpty() {
        String date = DataGenerator.generateDate(3);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCityInList());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);

    }

    @Test
    void shouldTestIfLatinName() {
        String date = DataGenerator.generateDate(3);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCityInList());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Dasha");
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear);

    }

    @Test
    void shouldTestIfNameWithSymbol() {
        String date = DataGenerator.generateDate(3);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCityInList());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Марго123");
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear);

    }

    @Test
    void shouldTestIfNoDate() {
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCityInList());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[@class='input__sub'][contains(text(), 'Неверно введена дата')]").should(appear);

    }

    @Test
    void shouldTestIfDateIsNotPassed() {
        String date = DataGenerator.generateDate(1);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCityInList());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[@class='input__sub'][contains(text(), 'Заказ на выбранную дату невозможен')]").should(appear);
    }

    @Test
    void shouldTestPhoneIsEmpty() {
        String date = DataGenerator.generateDate(3);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCityInList());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(" ");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);

    }


}
