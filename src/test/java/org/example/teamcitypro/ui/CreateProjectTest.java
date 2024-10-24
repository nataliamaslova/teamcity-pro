package org.example.teamcitypro.ui;

import org.example.teamcitypro.ui.pages.admin.CreateProjectPage;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

@Test(groups = "Regression")
public class CreateProjectTest extends BaseUiTest {
    public static final String REPO_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @Test(description = "User should be able to create project", groups = "Positive")
    public void userCreatesProject() {
        // Preparing the environment
        loginAs(testData.getUser());

        // Steps
        // interaction with UI
        CreateProjectPage.open("_Root")
                .createForm(REPO_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());


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
