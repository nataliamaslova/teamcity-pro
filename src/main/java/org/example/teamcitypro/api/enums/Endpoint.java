package org.example.teamcitypro.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.teamcitypro.api.models.BaseModel;
import org.example.teamcitypro.api.models.BuildType;
import org.example.teamcitypro.api.models.Project;
import org.example.teamcitypro.api.models.User;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECT("/app/rest/projects",Project.class),
    USERS("/app/rest/users", User.class);

    private final String url;
    private final Class <? extends BaseModel> modelClass;
}
