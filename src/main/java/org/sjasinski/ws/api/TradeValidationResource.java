package org.sjasinski.ws.api;

import org.sjasinski.ws.fixer.FixerService;
import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.validation.TradeValidator;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationResult;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class TradeValidationResource {
    @Inject
    private FixerService fixerService;

    @POST
    @Path("/validateTrade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(Trade trade) {
        ValidationContext ctx = new TradeValidator(fixerService).validate(trade);
        return Response.status(Response.Status.OK).entity(ctx).build();
    }

    @POST
    @Path("/validateTrades")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(List<Trade> trades) {
        List<ValidationContext> contexts = new TradeValidator(fixerService).validate(trades);
        return Response.status(Response.Status.OK).entity(contexts).build();
    }
}
