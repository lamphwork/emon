package lamph.quarkus.auth.adapter.in.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lamph.emon.auth.usecase.params.CreateAccountInput;
import lamph.emon.auth.usecase.params.UpdateAuthoritiesInput;
import lamph.quarkus.auth.adapter.in.rest.request.CreateAccountRequest;
import lamph.quarkus.auth.adapter.in.rest.request.UpdateAccountAuthorityRequest;
import lamph.quarkus.auth.services.AccountService;

import java.util.LinkedList;

@Path("/api/v1/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    AccountService accountService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createAccount(CreateAccountRequest request) {
        CreateAccountInput input = new CreateAccountInput(
                request.getUsername(), request.getPassword(),
                new LinkedList<>(), new LinkedList<>()
        );
        return accountService.createAccount(input);
    }

    @PUT
    @Path("/{id}/authorities")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateAuthority(@PathParam("id") String id, UpdateAccountAuthorityRequest request) {
        UpdateAuthoritiesInput input = new UpdateAuthoritiesInput(
                id, request.getRoles(), request.getPermissions()
        );
        return accountService.updateAuthorities(input);
    }
}
