package org.example.teamcitypro.api;

import org.apache.http.HttpStatus;
import org.example.teamcitypro.api.models.BuildType;
import org.example.teamcitypro.api.models.Project;
import org.example.teamcitypro.api.models.Roles;
import org.example.teamcitypro.api.models.User;
import org.example.teamcitypro.api.requests.CheckedRequests;
import org.example.teamcitypro.api.requests.unchecked.UncheckedBase;
import org.example.teamcitypro.api.spec.Specifications;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.example.teamcitypro.api.enums.Endpoint.BUILD_TYPES;
import static org.example.teamcitypro.api.enums.Endpoint.PROJECTS;
import static org.example.teamcitypro.api.enums.Endpoint.USERS;
import static org.example.teamcitypro.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("id:" + testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template"
                        .formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        // Create user
        var createdUser = superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Create project
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Grant user PROJECT_ADMIN role in project
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).update("id:" + createdUser.getId(), testData.getUser());

        // Create buildType for project by user (PROJECT_ADMIN)
        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        // Check buildType was created successfully
        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("id:" + testData.getBuildType().getId());
        softy.assertEquals(createdBuildType.getName(), testData.getBuildType().getName(), "Build type name is not correct");
    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        // Data setup
        // create the second data set
        var testData2 = generate();

        // create projects by SuperUser
        superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData2.getProject());

        // set role PROJECT_ADMIN
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        testData2.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData2.getProject().getId()));

        // create users with PROJECT_ADMIN role by SuperUser
        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        superUserCheckRequests.<User>getRequest(USERS).create(testData2.getUser());

        // create buildType for project2
        var buildTypeForProject2 = generate(Arrays.asList(testData2.getProject()), BuildType.class, testData2.getBuildType().getId());

        // Test steps: Create buildType for project2 by user1
        // Check buildType was not created with forbidden code
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeForProject2)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("AccessDeniedException: You do not have enough permissions to edit project with id: %s"
                        .formatted(testData2.getProject().getId())));
    }
}
