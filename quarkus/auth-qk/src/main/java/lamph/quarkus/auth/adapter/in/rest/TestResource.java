package lamph.quarkus.auth.adapter.in.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/rest/test")
public class TestResource {

    @GET
    public String testOK() {
        return "Test OK";
    }
}
