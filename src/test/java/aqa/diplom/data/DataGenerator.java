package aqa.diplom.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    static Faker faker = new Faker();

    public static String generateCreditCardNumber() {
        return faker.finance().creditCard(CreditCardType.MASTERCARD);
    }

    public static String actualMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));


    public static String previousMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));

    public static String nextMonth = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));

    public static String zeroMonth = "00";
    public static String emptyValue = "";

    public static String previousYear = LocalDate.now().
            minusYears(1).format(DateTimeFormatter.ofPattern("yy"));

    public static String nextYear = LocalDate.now().
            plusYears(1).format(DateTimeFormatter.ofPattern("yy"));

    public static String getWrongYear = LocalDate.now().
            plusYears(6).format(DateTimeFormatter.ofPattern("yy"));

    public static String getShortCardNumber() {
        return faker.number().digits(15);
    }

    public static String getRandomCardNumber() {
        return faker.number().digits(16);
    }

    public static String getLongCardNumber() {
        return faker.number().digits(17);
    }

    public static String getApprovedCardInfo() {
        return "4444 4444 4444 4441";
    }

    public static String getNotApprovedCardInfo() {
        return "4444 4444 4444 4442";
    }

    public static String generateOwnerName() {

        var faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();

    }

    public static String getOnlyFirstName() {
        var faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public static String getRussianName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getWrongMonth() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
        String month = Integer.toString(faker.number().numberBetween(13, 99));
        return month;
    }

    public static String getYear() {

        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateCvv() {
        return faker.number().digits(3);
    }

    public static String getLongCvv() {
        return faker.number().digits(4);
    }

    public static String getShortNumber() {
        return faker.number().digits(1);

    }

    @Value
    public static class CardData {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cardCVV;
    }


}
