package org.example.teamcitypro.api.requests;

import org.example.teamcitypro.api.models.BaseModel;

public interface CrudInterface {
    Object create(BaseModel model);
    Object read(String id);
    Object update(String id, BaseModel model);
    Object delete(String id);
}
