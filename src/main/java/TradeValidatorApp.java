import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.sjasinski.ws.common.CORSFilter;
import org.sjasinski.ws.fixer.FixerService;

import java.io.IOException;
import java.net.URI;

/**
 * Simple trade validation application.
 *
 * Implemented API allows to:
 * 1. Perform bulk validation of array of trades via /validateTrades POST request.
 * 2. Validate single trades via /validateTrade.
 *
 * Online API documentation is available at https://app.swaggerhub.com/apis/sjasinski/TradeValidator/1.0.0
 *
 */
public class TradeValidatorApp {
    private static final URI BASE_URI = URI.create("http://localhost:8080/");

    public static void main(String[] args) throws IOException {
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, create());
        System.in.read();
        server.shutdownNow();
    }

    public static ResourceConfig create() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("org.sjasinski.ws.api");
        resourceConfig.register(new CORSFilter());
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(FixerService.class).to(FixerService.class);
            }
        });
        return resourceConfig;
    }

}