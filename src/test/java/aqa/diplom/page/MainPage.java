package aqa.diplom.page;

import aqa.diplom.data.DataGenerator;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class MainPage {

    private final SelenideElement debitBuy = $(byText("Купить"));
    private final SelenideElement creditBuy = $(byText("Купить в кредит"));
    private final SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement cardMonth = $("[placeholder='08']");
    private final SelenideElement cardYear = $("[placeholder='22']");
    private final SelenideElement cardOwner = $x("//*[text()='Владелец']/following-sibling::span/input");
    ;
    private final SelenideElement cardCvv = $("[placeholder='999']");
    private final SelenideElement buyButton = $(byText("Продолжить"));

    private final SelenideElement cardError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    private final SelenideElement monthError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    private final SelenideElement yearError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    private final SelenideElement ownerError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    private final SelenideElement cvvError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");


    public void successOperation() {
        $(byText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void unsuccessOperation() {
        $(byText("Ошибка")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void notificationWrongCard(String text) {
        cardError.shouldBe(visible);
        cardError.shouldHave(exactText(text));
    }

    public void getMonthError(String text) {
        monthError.shouldBe(visible);
        monthError.shouldHave(exactText(text));
    }

    public void getYearError(String text) {
        yearError.shouldBe(visible);
        yearError.shouldHave(exactText(text));
    }

    public void getOwnerError(String text) {
        ownerError.shouldBe(visible);
        ownerError.shouldHave(exactText(text));
    }

    public void getCvvError(String text) {
        cvvError.shouldBe(visible);
        cvvError.shouldHave(exactText(text));
    }

    public void payWithDebitCard() {
        debitBuy.click();
    }

    public void payWithCreditCard() {
        creditBuy.click();
    }

    public void mainPay() {
        buyButton.click();
    }

    public void fullForm(DataGenerator.CardData cardData) {
        cardNumber.setValue(cardData.getCardNumber());
        cardMonth.setValue(cardData.getMonth());
        cardYear.setValue(cardData.getYear());
        cardOwner.setValue(cardData.getOwner());
        cardCvv.setValue(cardData.getCardCVV());
    }
}


