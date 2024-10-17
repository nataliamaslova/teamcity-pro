package org.example.teamcitypro.api;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.example.teamcitypro.api.models.Project;
import org.example.teamcitypro.api.requests.CheckedRequests;
import org.example.teamcitypro.api.requests.unchecked.UncheckedBase;
import org.example.teamcitypro.api.spec.Specifications;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.example.teamcitypro.api.enums.Endpoint.PROJECTS;
import static org.example.teamcitypro.api.enums.Endpoint.USERS;
import static org.example.teamcitypro.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {
    @Test(description="User should be able to create project", groups = {"Positive", "CRUD"})
    public void projectCreatedForStandardPositiveCaseTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        softy.assertEquals(createdProject.getId(), testData.getProject().getId(), "Project id is not correct");
    }

    @Test(description="User should not be able to create two projects with the same id", groups = {"Negative", "CRUD"})
    public void projectIsNotCreatedForTheSameIdTest() {
        var projectWithSameId = generate(Arrays.asList(testData.getProject()), Project.class, testData.getProject().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.getRequest(PROJECTS).create(testData.getProject());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithSameId)
                .then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("DuplicateExternalIdException: " +
                                "Project ID \"%s\" is already used by another project"
                                        .formatted(testData.getProject().getId())));
    }

    @Test(description="User should not be able to create project with empty name", groups = {"Negative", "CRUD"})
    public void projectIsNotCreatedForEmptyNameTest() {
        var project = testData.getProject();
        project.setName("");

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("BadRequestException: Project name cannot be empty"));
    }

    @Test(description="User should not be able to create project with empty Id", groups = {"Negative", "CRUD"})
    public void projectIsNotCreatedForEmptyIdTest() {
        var project = testData.getProject();
        project.setId("");

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("InvalidIdentifierException: Project ID must not be empty"));
    }

    @Test(description="User can create project with 4000 symbols in name", groups = {"Positive", "CRUD"})
    public void projectCreatedFor4000SymbolsInNameTest() {
        var expectedProjectName = RandomStringUtils.randomAlphabetic(4000);
        var project = testData.getProject();
        project.setName(expectedProjectName);

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(project);

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        softy.assertEquals(createdProject.getName(), expectedProjectName, "Project name is not correct");
    }

    @Test(description="User can create project with Russian and Polish symbols in name", groups = {"Positive", "CRUD"})
    public void projectCreatedForRussianPolishEncodingInNameTest() {
        var expectedProjectName = "новый проект Wrocław/Żielona Góra";
        var project = testData.getProject();
        project.setName(expectedProjectName);

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(project);

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        softy.assertEquals(createdProject.getName(), expectedProjectName, "Project name is not correct");
    }

    @Test(description="User can create project with special symbols in name", groups = {"Positive", "CRUD"})
    public void projectCreatedForSpecialSymbolsInNameTest() {
        var expectedProjectName = "!@#$%^&*()~:,.;'|/?";
        var project = testData.getProject();
        project.setName(expectedProjectName);

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(project);

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        softy.assertEquals(createdProject.getName(), expectedProjectName, "Project name is not correct");
    }
}
