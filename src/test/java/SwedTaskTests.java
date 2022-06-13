import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.junit5.TextReportExtension;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@ExtendWith({TextReportExtension.class, SoftAssertsExtension.class})
public class SwedTaskTests {


    @BeforeAll
    public static void setUp(){
        Configuration.baseUrl = "https://www.swedbank.lt";
        open("/business/finance/trade/factoring");
        $(By.className("ui-cookie-consent__accept-all-button")).click(); //accept cookies
    }

    @BeforeEach
    public void OpenPage(){
        open("/business/finance/trade/factoring");
    }

    @Test
    public void calculate(){
        $(By.name("calc_d5")).setValue("15000");
    }

    @Test
    public void TestSanityCalculateCorrectExpenseWithMinAmounts()
    {
        $(By.name("calc_d5")).setValue("1");
        $(By.name("calc_d7")).setValue("0");
        $(By.name("calc_d9")).setValue("0");
        //$(By.className("validation-message")).shouldBe(visible)

    }

    @Test
    public void TestInvoiceAmountZeroErrorMessage()
    {
        $(By.name("calc_d5")).setValue("0");
        Calculate();
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

    /*public void AssertCalculationResult()
    {
        $(By.id("calculate-factoring")).click();
        //$(By.id("result")).shouldHave(text("78.75"));
    }
*/

}
