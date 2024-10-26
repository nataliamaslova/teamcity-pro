package org.example.teamcitypro.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class ProjectElement extends BasePageElement {
    private SelenideElement name;
    private SelenideElement link;
    private SelenideElement button;

    public ProjectElement(SelenideElement element) {
        super(element);
        this.name = find("span[class*='MiddleEllipsis']");
        this.link = find("a");
        this.button = find("button");
    }

    // ElementsCollection: Selenide Element 1, Selenide Element 2 и тд
    // collection.stream() -> Конвейер: Selenide Element 1, Selenide Element 2 и тд
    // creator(Selenide Element 1) -> T -> add to list
    // creator(Selenide Element 2) -> T -> add to list
}
