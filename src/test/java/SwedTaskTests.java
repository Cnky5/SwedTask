import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SwedTaskTests {

    @BeforeAll
    public static void setUp(){
        Configuration.baseUrl = "https://www.swedbank.lt";
        open("/business/finance/trade/factoring");

        //I wasn't really sure about the best practices regarding cookies so in this case I just accept all of them at the start.
        $(By.className("ui-cookie-consent__accept-all-button")).click(); //accept cookies
    }


    //Refreshing the page for each test to get rid of the validation messages for following tests.
    @BeforeEach
    public void OpenPage(){
        open("/business/finance/trade/factoring");
    }

    @Test
    public void TestSanityCalculateExpenses()
    {
        //Assuming, of course, that the calculator has already been tested and returns correct values.
        //I think a test with hardcoded values, such as this one, would prove to be helpful for a regression test
        //in the future where the calculation in the background has been re-written.

        $(By.name("calc_d5")).setValue("123");
        $(By.name("calc_d6")).selectOptionByValue("90");
        $(By.name("calc_d7")).setValue("20");
        $(By.name("calc_d8")).selectOptionByValue("120");
        $(By.name("calc_d9")).setValue("456");

        Calculate();

        $(By.id("result")).shouldHave(text("568.26"));
        $(By.id("result_perc")).shouldHave(text("462.00"));
    }

    @Test
    public void TestInvoiceAmountZeroErrorMessage()
    {
        $(By.name("calc_d5")).setValue("0");
        Calculate();

        //The reason I only check if the validation-message element is enabled, and not the different error texts is the presence of
        //different languages (LT, EN, RU). In this case the tests work with all available languages.
        $(By.className("validation-message")).isEnabled();
    }

    @Test
    public void TestInvoiceAmountNegativeErrorMessage()
    {
        $(By.name("calc_d5")).setValue("-1");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }

    @Test
    public void TestInvoiceAmountStringErrorMessage()
    {
        $(By.name("calc_d5")).setValue("asd");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }

    @Test
    public void TestInvoiceAmountNullErrorMessage()
    {
        $(By.name("calc_d5")).setValue("");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }


    @Test
    public void TestInterestRateTooHighErrorMessage()
    {
        $(By.name("calc_d7")).setValue("21");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }

    @Test
    public void TestInterestRateNegativeErrorMessage()
    {
        $(By.name("calc_d7")).setValue("-1");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }

    @Test
    public void TestInterestRateStringErrorMessage()
    {
        $(By.name("calc_d7")).setValue("asd");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }

    @Test
    public void TestInterestRateNullErrorMessage()
    {
        $(By.name("calc_d7")).setValue("");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }


    @Test
    public void TestCommissionFeeNullErrorMessage()
    {
        $(By.name("calc_d9")).setValue("");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }

    @Test
    public void TestCommissionFeeStringErrorMessage()
    {
        $(By.name("calc_d9")).setValue("asd");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }

    @Test
    public void TestCommissionFeeNegativeErrorMessage()
    {
        $(By.name("calc_d9")).setValue("-1");
        Calculate();
        $(By.className("validation-message")).isEnabled();
    }

    public void Calculate()
    {
        $(By.id("calculate-factoring")).click();
    }

}
