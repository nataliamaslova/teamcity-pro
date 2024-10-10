package org.example.teamcitypro.api.models;

import org.example.teamcitypro.api.annotations.Optional;
import org.example.teamcitypro.api.annotations.Parameterizable;
import org.example.teamcitypro.api.annotations.Random;

public class BuildType extends BaseModel {
    private String id;
    @Random
    private String name;
    @Parameterizable
    private Project project;
    @Optional
    private Steps step;
}
