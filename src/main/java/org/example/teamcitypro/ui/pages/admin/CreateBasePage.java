package org.example.teamcitypro.ui.pages.admin;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.example.teamcitypro.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public  abstract class CreateBasePage extends BasePage {
    protected static final String CREATE_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=%s";

    protected SelenideElement urlInput = $("#url");
    protected SelenideElement submitButton = $(Selectors.byAttribute("value", "Proceed"));
    protected SelenideElement buildTypeNameInput = $("#buildTypeName");

    protected void baseCreateForm(String url) {
        urlInput.val(url);
        submitButton.click();
    }
}
