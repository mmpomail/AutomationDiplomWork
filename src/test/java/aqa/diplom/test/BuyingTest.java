package aqa.diplom.test;

import aqa.diplom.data.DataGenerator;
import aqa.diplom.data.SQLHelper;
import aqa.diplom.page.MainPage;
import aqa.diplom.data.SQLHelper;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static aqa.diplom.data.DataGenerator.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyingTest {

    MainPage mainPage;

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //@AfterEach
    //void eraseDownAll() {
    //   SQLHelper.cleanDB();
    // }


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void openPage() {
        open("http://localhost:8080");
    }


    @Test
    @DisplayName("Сценарий 1. Покупка тура по дебетовой карте, одобренной для проведения операций в приложении")
    void buyingTourByApprovedDebitCard() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.successOperation();
        assertEquals("APPROVED", SQLHelper.getDebitCardStatus());

    }

    @Test
    @DisplayName("Сценарий 2. Покупка тура в кредит, с использованием карты, одобренной для проведения операций в приложении")
    void buyingTourByApprovedCreditCard() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.successOperation();
        assertEquals("APPROVED", SQLHelper.getCreditCardStatus());

    }

    @Test
    @DisplayName("Сценарий 3. Покупка тура по дебетовой карте, отклонённой для проведения операций в приложении")
    void buyingTourByNotApprovedDebitCard() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getNotApprovedCardInfo(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.unsuccessOperation();
        assertEquals("DECLINED", SQLHelper.getDebitCardStatus());

    }

    @Test
    @DisplayName("Сценарий 4. Покупка тура в кредит, с использованием карты, отклонённой для проведения операций в приложении")
    void buyingTourByNotApprovedCreditCard() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getNotApprovedCardInfo(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.unsuccessOperation();
        assertEquals("DECLINED", SQLHelper.getCreditCardStatus());

    }

    @Test
    @DisplayName("Сценарий 5. Покупка тура по дебетовой карте с отправкой пустой формы")
    void buyingTourByAprrovedDebitCardWithEmptyForm() {

        var mainPage = new MainPage();
        mainPage.payWithDebitCard();
        mainPage.mainPay();
        mainPage.notificationWrongCard("Поле обязательно для заполнения");
        mainPage.getMonthError("Поле обязательно для заполнения");
        mainPage.getYearError("Поле обязательно для заполнения");
        mainPage.getOwnerError("Поле обязательно для заполнения");
        mainPage.getCvvError("Поле обязательно для заполнения");


    }

    @Test
    @DisplayName("Сценарий 6. Покупка тура в кредит с отправкой пустой формы")
    void buyingTourByAprrovedCreditCardWithEmptyForm() {

        var mainPage = new MainPage();
        mainPage.payWithCreditCard();
        mainPage.mainPay();
        mainPage.notificationWrongCard("Поле обязательно для заполнения");
        mainPage.getMonthError("Поле обязательно для заполнения");
        mainPage.getYearError("Поле обязательно для заполнения");
        mainPage.getOwnerError("Поле обязательно для заполнения");
        mainPage.getCvvError("Поле обязательно для заполнения");

    }

    @Test
    @DisplayName("Сценарий 7. Покупка тура по дебетовой карте с номером карты меньше валидной длины")
    void buyingTourByDebitCardWithLowerLength() {
        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getShortCardNumber(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.notificationWrongCard("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 8. Покупка тура в кредит, с номер карты меньше валидной длины")
    void buyingTourByCreditCardWithLowerLength() {
        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getShortCardNumber(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.notificationWrongCard("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 9. Покупка тура по дебетовой карте с номером карты больше валидной длины")
    void buyingTourByDebitCardWithBiggerLength() {
        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getLongCardNumber(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.unsuccessOperation();
    }

    @Test
    @DisplayName("Сценарий 10. Покупка тура в кредит с номером карты больше валидной длины")
    void buyingTourByCreditCardWithBiggerLength() {
        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getLongCardNumber(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.unsuccessOperation();
    }

    @Test
    @DisplayName("Сценарий 11. Покупка тура по дебетовой карте с использованием несуществующей карты")
    void buyingTourByRandomDebitCard() {
        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getRandomCardNumber(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.unsuccessOperation();
    }

    @Test
    @DisplayName("Сценарий 12. Покупка тура в кредит с использованием несуществующей карты")
    void buyingTourByRandomCreditCard() {
        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getRandomCardNumber(), nextMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.unsuccessOperation();
    }


    @Test
    @DisplayName("Сценарий 13. Покупка тура по дебетовой карте с указанием месяца истечения срока карты в невалидном формате")
    void buyingTourByDebitCardWithInvalidMonthValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), getShortNumber(), nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getMonthError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 14. Покупка тура в кредит с указанием месяца истечения срока карты в невалидном формате")
    void buyingTourByCreditCardWithInvalidMonthValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), getShortNumber(), nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getMonthError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 15. Покупка тура по дебетовой карте с указанием нулевого значения в поле месяца истечения срока карты")
    void buyingTourByDebitCardWithZeroMonthValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), zeroMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Сценарий 16. Покупка тура в кредит с указанием нулевого значения в поле месяца истечения срока карты")
    void buyingTourByCreditCardWithZeroMonthValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), zeroMonth, nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Сценарий 17. Покупка тура по дебетовой карте с указанием несуществующего месяца в сроке истечения карты")
    void buyingTourByDebitCardWithWrongMonthValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), getWrongMonth(), nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Сценарий 18. Покупка тура в кредит с указанием несуществующего месяца в сроке истечения карты")
    void buyingTourByCreditCardWithWrongMonthValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), getWrongMonth(), nextYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Сценарий 19. Покупка тура по дебетовой карте с указанием месяца, в котором срок действия карты уже был просрочен")
    void buyingTourByDebitCardWithExpiredMonthValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), previousMonth, getYear(), generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Сценарий 20. Покупка тура в кредит с указанием месяца, в котором срок действия карты уже был просрочен")
    void buyingTourByCreditCardWithExpiredMonthValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), previousMonth, getYear(), generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Сценарий 21. Покупка тура по дебетовой карте с указанием года истечения срока карты в невалидном формате")
    void buyingTourByDebitCardWithInvalidYearValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, getShortNumber(), generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getYearError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 22. Покупка тура в кредит с указанием года истечения срока карты в невалидном формате")
    void buyingTourByCreditCardWithInvalidYearValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, getShortNumber(), generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getYearError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 23. Покупка тура по дебетовой карте с указанием года, в котором срок действия карты уже был просрочен")
    void buyingTourByDebitCardWithExpiredYearValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, previousYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Сценарий 24. Покупка тура в кредит с указанием года, в котором срок действия карты уже был просрочен")
    void buyingTourByCreditCardWithExpiredYearValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, previousYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Сценарий 25. Покупка тура по дебетовой карте с неверно указанным сроком действия карты")
    void buyingTourByDebitCardWithWrongYearValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, getWrongYear, generateOwnerName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getYearError("Неверно указан срок действия карты");
    }


    @Test
    @DisplayName("Сценарий 26. Покупка тура в кредит с неверно указанным сроком действия карты:")
    void buyingTourByCreditCardWithWrongYearValue() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, getWrongYear, generateOwnerName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getYearError("Неверно указан срок действия карты");
    }


    @Test
    @DisplayName("Сценарий 27. Покупка тура по дебетовой карте с указанием имени и фамилии владельца карты кириллицей")
    void buyingTourByDebitCardWithCyrillicOwnerName() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, getRussianName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getOwnerError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 28. Покупка тура в кредит с указанием имени и фамилии владельца карты кириллицей")
    void buyingTourByCreditCardWithCyrillicOwnerName() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, getRussianName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getOwnerError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 29. Покупка тура по дебетовой карте с указанием только имени владельца на латинице")
    void buyingTourByDebitCardWithLatinNameOnly() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, getOnlyFirstName(), generateCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getOwnerError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 30. Покупка тура в кредит с указанием только имени владельца на латинице")
    void buyingTourByCreditCardWithLatinNameOnly() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, getOnlyFirstName(), generateCvv());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getOwnerError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 31. Покупка тура по дебетовой карте с указанием CVV меньшей длины, чем у валидного значения")
    void buyingTourByDebitCardWithInvalidCvv() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, generateOwnerName(), getShortNumber());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getCvvError("Неверный формат");

    }

    @Test
    @DisplayName("Сценарий 32. Покупка тура в кредит с указанием CVV меньшей длины, чем у валидного значения")
    void buyingTourByCreditCardWithInvalidCvv() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, generateOwnerName(), getShortNumber());
        mainPage.payWithCreditCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.getCvvError("Неверный формат");

    }

    @Test
    @DisplayName("Сценарий 33. Покупка тура по дебетовой карте с указанием CVV большей длины, чем валидное значение")
    void buyingTourByDebitCardWithWrongCvv() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, generateOwnerName(), getLongCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.successOperation();
        assertEquals("APPROVED", SQLHelper.getDebitCardStatus());

    }

    @Test
    @DisplayName("Сценарий 34. Покупка тура в кредит с указанием CVV большей длины, чем валидное значение")
    void buyingTourByCreditCardWithWrongCvv() {

        var mainPage = new MainPage();
        DataGenerator.CardData card = new DataGenerator.CardData(getApprovedCardInfo(), nextMonth, nextYear, generateOwnerName(), getLongCvv());
        mainPage.payWithDebitCard();
        mainPage.fullForm(card);
        mainPage.mainPay();
        mainPage.successOperation();
        assertEquals("APPROVED", SQLHelper.getCreditCardStatus());

    }

}
