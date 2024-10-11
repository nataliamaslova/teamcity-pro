package org.example.teamcitypro.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.teamcitypro.api.annotations.Optional;
import org.example.teamcitypro.api.annotations.Parameterizable;
import org.example.teamcitypro.api.annotations.Random;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildType extends BaseModel {
    @Random
    private String id;
    @Random
    private String name;
    @Parameterizable
    private Project project;
    @Optional
    private Steps step;
}
