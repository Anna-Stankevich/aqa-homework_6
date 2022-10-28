package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;

public class TransferBetweenOwnCardsTest {
    LoginPage loginPage;

    @BeforeEach
    void setupTest() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCard() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }

    @Test
    void shouldTransferMoneyBetweenOwnCard1() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var transferAmount = DataHelper.getTransferAmount();
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int minIndex = dashboardPage.minBalanceIndex();
        int balance = dashboardPage.getCardBalance(minIndex);
        var replenishmentPage = dashboardPage.transferTo(minIndex);
        dashboardPage = replenishmentPage.transferAmount(transferAmount);
        dashboardPage.validBalance(minIndex, balance + Integer.parseInt(transferAmount.getAmount()));
    }
}
