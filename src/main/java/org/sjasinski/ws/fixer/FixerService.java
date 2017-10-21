package org.sjasinski.ws.fixer;

import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Fixer service.
 * Foreign exchange rates and currency conversion API.
 */
@Service
public class FixerService {

    private final WebTarget webTarget = ClientBuilder.newClient()
            .target("http://api.fixer.io/");

    public FixerRates rates(String date, String base) {
        return webTarget
                .path("/{date}")
                .resolveTemplate("date", date)
                .queryParam("base", base)
                .request()
                .get(FixerRates.class);
    }
}
