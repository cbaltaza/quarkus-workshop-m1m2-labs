package org.acme.people.rest;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.people.stream.PriceConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.GET;

@Path("/hello")
public class GreetingResource {

    public static final Logger log = LoggerFactory.getLogger(GreetingResource.class);
    @Inject
    PriceConsumer consumer;

    /**
     * Endpoint to generate a new quote request id and send it to "quote-requests" AMQP queue using the emitter.
     */
    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest() {
    	log.info("entra a getLastPrice");
    	return consumer.getLastPrice(); 
    }
    

    @GET
    @Path("last")
    @Produces(MediaType.TEXT_PLAIN)
    public String last() {
        return consumer.getLastPrice();
    }
}