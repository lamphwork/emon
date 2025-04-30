package lamph.quarkus.auth.adapter.in.rest;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lamph.quarkus.auth.adapter.in.rest.request.TokenRequest;
import lamph.quarkus.auth.adapter.in.rest.response.TokenResponse;

@Path("/oauth/token")
public class TokenResource {

    @POST
    public TokenResponse issueToken(TokenRequest tokenRequest) {
        return new TokenResponse();
    }
}
