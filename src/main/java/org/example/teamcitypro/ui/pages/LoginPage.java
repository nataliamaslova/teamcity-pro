package org.example.teamcitypro.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.example.teamcitypro.api.models.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    public static final String LOGIN_URL = "/login.html";

    private SelenideElement inputUsername = $("#username");
    private SelenideElement inputPassword = $("#password");
    private SelenideElement inputSubmitLogin = $(".loginButton");

    @Step("Open login page")
    public static LoginPage open() {
        return Selenide.open(LOGIN_URL, LoginPage.class);
    }

    @Step("Login as {user.username}")
    public ProjectsPage login(User user) {
        // Method val = (clear, sendKeys)
        inputUsername.val(user.getUsername());
        inputPassword.val(user.getPassword());
        inputSubmitLogin.click();

        return Selenide.page(ProjectsPage.class);
    }

}
