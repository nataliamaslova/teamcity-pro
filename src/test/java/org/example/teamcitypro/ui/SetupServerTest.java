package org.example.teamcitypro.ui;

import org.example.teamcitypro.ui.setup.FirstStartPage;
import org.testng.annotations.Test;

public class SetupServerTest extends BaseUiTest {
    @Test(groups = {"Setup"})
    public void setupTeamCityServerTest(){
        FirstStartPage.open().setupFirstStart();
    }
}
