package org.example.teamcitypro.api.requests.checked;

import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.example.teamcitypro.api.enums.Endpoint;
import org.example.teamcitypro.api.models.BaseModel;
import org.example.teamcitypro.api.requests.CrudInterface;
import org.example.teamcitypro.api.requests.Request;
import org.example.teamcitypro.api.requests.unchecked.UncheckedBase;

@SuppressWarnings("unchecked")
public final class CheckedBase<T extends BaseModel> extends Request implements CrudInterface {
    private final UncheckedBase uncheckedBase;
    public CheckedBase(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
        this.uncheckedBase = new UncheckedBase(spec, endpoint);
    }

    @Override
    public T create(BaseModel model) {
        return (T) uncheckedBase
                .create(model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T read(String id) {
        return (T) uncheckedBase
                .read(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T update(String id, BaseModel model) {
        return (T) uncheckedBase
                .update(id, model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public Object delete(String id) {
        return uncheckedBase
                .delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
