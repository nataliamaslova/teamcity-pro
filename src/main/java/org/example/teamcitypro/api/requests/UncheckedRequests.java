package org.example.teamcitypro.api.requests;

import io.restassured.specification.RequestSpecification;
import org.example.teamcitypro.api.enums.Endpoint;
import org.example.teamcitypro.api.requests.unchecked.UncheckedBase;

import java.util.EnumMap;

public class UncheckedRequests {
    private final EnumMap<Endpoint, UncheckedBase> requests = new EnumMap<>(Endpoint.class);

    public UncheckedRequests(RequestSpecification spec) {
        for (var endpoint: Endpoint.values()) {
            requests.put(endpoint, new UncheckedBase(spec, endpoint));
        }
    }

    public UncheckedBase getRequest(Endpoint endpoint) {
        return requests.get(endpoint);
    }
}
