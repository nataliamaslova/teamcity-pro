package org.example.teamcitypro.ui;

import com.codeborne.selenide.Condition;
import org.example.teamcitypro.api.enums.Endpoint;
import org.example.teamcitypro.api.generators.TestDataStorage;
import org.example.teamcitypro.api.models.BuildType;
import org.example.teamcitypro.api.models.Project;
import org.example.teamcitypro.api.requests.CheckedRequests;
import org.example.teamcitypro.api.spec.Specifications;
import org.example.teamcitypro.ui.pages.admin.BuildTypePage;
import org.example.teamcitypro.ui.pages.admin.CreateBuildTypePage;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.example.teamcitypro.api.enums.Endpoint.BUILD_TYPES;
import static org.example.teamcitypro.api.enums.Endpoint.PROJECTS;
import static org.example.teamcitypro.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUiTest {

    private static final String REPO_URL = "https://github.com/nataliamaslova/teamcity-pro";

    @Test(description = "User should be able to create a build type", groups = {"Positive"})
    public void userCreatesBuildType() {
        // Setup the environment
        loginAs(testData.getUser());
        var buildType = generate(BuildType.class);

        // API: create a project
        var userAuth = Specifications.authSpec(testData.getUser());
        var project = testData.getProject();
        var requests = new CheckedRequests(userAuth);
        requests.getRequest(PROJECTS).create(project);

        // API: check that project was created
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        // UI: create a build type
        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildType(buildType.getName());

        // API: check that build type was created
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + buildType.getName());
        softy.assertNotNull(createdBuildType);

        // UI: verify the build type is displayed correctly on UI - by name
        // build type page url http://localhost:8111/buildConfiguration/{build_id}?mode=branches
        BuildTypePage.open(createdBuildType.getId())
                .title.shouldHave(Condition.exactText(buildType.getName()));

        // Post-condition: clearing test data after test
        TestDataStorage.getStorage().addCreatedEntity(PROJECTS, createdProject);
        TestDataStorage.getStorage().addCreatedEntity(BUILD_TYPES, createdBuildType);
    }

    @Test(description = "User should NOT be able to create a build type without name", groups = {"Negative"})
    public void userCreatesBuildTypeWithEmptyName() {
        // Setup the environment
        loginAs(testData.getUser());
        var buildType = generate(BuildType.class);

        // API: Create a project
        var userAuth = Specifications.authSpec(testData.getUser());
        var project = testData.getProject();
        var requests = new CheckedRequests(userAuth);
        requests.getRequest(PROJECTS).create(project);

        // API: Check the project was created
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        // UI: Open build type creation page and try to create a build type with empty name
        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .errorMessageEmptyBuildTypeName("");

        // API: check that build type was NOT created
        superUserUncheckRequests.getRequest(BUILD_TYPES).read(buildType.getId())
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);

        // Post-condition: clearing test data after test
        TestDataStorage.getStorage().addCreatedEntity(PROJECTS, createdProject);
    }
}
