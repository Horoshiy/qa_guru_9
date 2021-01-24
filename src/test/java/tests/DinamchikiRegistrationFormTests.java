package tests;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class DinamchikiRegistrationFormTests extends TestBase {
    @Test
    @DisplayName("successful registration form")
    void registrationFormFillAllInputs() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        String name = faker.name().firstName();
        String phone = fakeValuesService.regexify("[0-9]{10}");

        step("Open registration from", () -> {
            open("http://www.dinamchiki.ru");
            $(".hero__title").shouldHave(text("лицензированная негосударственная футбольная школа"));
        });

        step("Fill registration form", () -> {
           $(".cmn-btn").click();
           $(".subscribe-form #subscribe_name").val(name);
           $(".subscribe-form #subscribe_phone").val(phone);
           $(".subscribe-form button").click();
        });

        step("Verify successful from submit", () -> {
            $(".subscribe-area").shouldHave(text("Заявка отправлена. Мы Вам обязательно перезвоним!"));
        });
    }

}
