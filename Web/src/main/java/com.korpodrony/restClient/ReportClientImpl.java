package com.korpodrony.restClient;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ReportClientImpl implements ReportClient {
    private static final String URL = "https://api.exchangeratesapi.io/latest";
    private static final ClientConfig clientConfig = new ClientConfig();
    private static final Client client = ClientBuilder.newClient().register(new JacksonFeature());

    @Override
    public Raport getRates() {
        Response response = client
                .target(URL)
                .queryParam("base", "CHF")
                .queryParam("symbols", "EUR,GBP")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Raport rate = response.readEntity(Raport.class);

        response.close();

        return rate;
    }
}
