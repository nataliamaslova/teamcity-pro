package org.example.teamcitypro.ui;

import org.example.teamcitypro.api.enums.Endpoint;
import org.example.teamcitypro.ui.pages.LoginPage;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

@Test(groups = "Regression")
public class CreateProjectTest extends BaseUiTest {
    @Test(description = "User should be able to create project", groups = "Positive")
    public void userCreatesProject() {
        // Preparing the environment
        step("Login as user");
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        LoginPage.open().login(testData.getUser());

        // Steps
        // interaction with UI
        step("Open 'Create Project Page' http://localhost:8111/admin/createObjectMenu.html");
        step("Send all project parameters (repository URL)");
        step("Click proceed");
        step("Fix Project Name and Build Type name values");
        step("Click proceed");

        // Verification
        // check API state
        // correctness of sending data from UI to API
        step("Check that all entities (project, build type) was successfully created on API level");

        // check UI state
        // correctness of reading and representing data on UI
        step("Check that project is visible on Projects page (http://localhost:8111/favorite/projects)");
    }

    @Test(description = "User should not be able to create project without name", groups = "Negative")
    public void userCreatesProjectWithoutName() {
        // Preparing the environment
        step("Login as user");
        step("Check number of projects");

        // Steps
        // interaction with UI
        step("Open 'Create Project Page' http://localhost:8111/admin/createObjectMenu.html");
        step("Send all project parameters (repository URL)");
        step("Click proceed");
        step("Set Project Name value is empty");
        step("Click proceed");

        // Verification
        // check API state
        // correctness of sending data from UI to API
        step("Check that number of projects is not changed");


        // check UI state
        // correctness of reading and representing data on UI
        step("Check that error appears 'Project name must not be empty'");
   }
}
