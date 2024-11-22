package org.example.teamcitypro.api;

import org.example.teamcitypro.BaseTest;
import org.example.teamcitypro.api.models.AuthModules;
import org.example.teamcitypro.api.models.ServerAuthSettings;
import org.example.teamcitypro.api.requests.ServerAuthRequest;
import org.example.teamcitypro.api.spec.Specifications;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static org.example.teamcitypro.api.generators.TestDataGenerator.generate;

public class BaseApiTest extends BaseTest {
    private final ServerAuthRequest serverAuthRequest = new ServerAuthRequest(Specifications.superUserSpec());
    private AuthModules authModules;
    private boolean perProjectPermission;

    @BeforeSuite(alwaysRun = true)
    public void setUpServerAuthSettings() {
        // Get current settings perProjectPermission
        perProjectPermission = serverAuthRequest.read().getPerProjectPermissions();

        authModules = generate(AuthModules.class);

        // Update value perProjectPermissions with true
        serverAuthRequest.update(ServerAuthSettings.builder()
                        .perProjectPermissions(true)
                        .modules((authModules))
                .build());
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUpServerAuthSettings() {
        // Update perProjectPermission with initial value
        serverAuthRequest.update(ServerAuthSettings.builder()
                .perProjectPermissions(perProjectPermission)
                .modules((authModules))
                .build());
    }
}
