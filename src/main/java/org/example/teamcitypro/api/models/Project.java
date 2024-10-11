package org.example.teamcitypro.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.teamcitypro.api.annotations.Random;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends BaseModel {
    private String id;
    @Random
    private String name;
    @Builder.Default
    private String locator = "_Root";

}
