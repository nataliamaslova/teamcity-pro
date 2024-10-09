package org.example.teamcitypro.api.requests;

import io.restassured.specification.RequestSpecification;
import org.example.teamcitypro.api.enums.Endpoint;

public class Request {
    /**
     *  Request - class, which described the changed parameters of request:
     *  specification,
     *  endpoint (relative URL, model)
     *
     */
    private final RequestSpecification spec;
    private final Endpoint endpoint;

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
