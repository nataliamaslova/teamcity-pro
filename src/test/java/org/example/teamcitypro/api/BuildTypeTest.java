package org.example.teamcitypro.api;

import org.example.teamcitypro.api.enums.Endpoint;
import org.example.teamcitypro.api.models.User;
import org.example.teamcitypro.api.requests.checked.CheckedBase;
import org.example.teamcitypro.api.spec.Specifications;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description="User should be able to create build type", groups = {"Positive", "CRUD"})
    public static void userCreatesBuildTypeTest() {
        step("Create user", () -> {
            var user = User.builder()
                    .username("name1")
                    .password("password1")
                    .build();

            var requester = new CheckedBase<User>(Specifications.superUserAuth(), Endpoint.USERS);

            requester.create(user);
        });

        step("Create project by user");
        step("Create buildType for project by user");
        step("Check buildType was created successfully with correct data");
    }

    @Test(description="User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public static void userCreatesTwoBuildTypesWithTheSameIdTest() {
        step("Create user");
        step("Create project by user");
        step("Create buildType1 for project by user");
        step("Create buildType2 with the same id as buildType1 for project by user");
        step("Check buildType2 was not created with bad request code");
    }

    @Test(description="Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public static void projectAdminCreatesBuildTypeTest() {
        step("Create user");
        step("Create project");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create buildType for project by user (PROJECT_ADMIN)");
        step("Check buildType was created successfully ");
    }

    @Test(description="Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public static void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        step("Create user1");
        step("Create project1");
        step("Grant user1 PROJECT_ADMIN role in project1");

        step("Create user2");
        step("Create project2");
        step("Grant user2 PROJECT_ADMIN role in project2");

        step("Create buildType for project1 by user2");
        step("Check buildType was not created with forbidden code");
    }
}
