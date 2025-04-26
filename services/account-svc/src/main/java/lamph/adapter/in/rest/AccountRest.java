package lamph.adapter.in.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import lamph.adapter.in.rest.dto.ChangeStatusAccountReq;
import lamph.adapter.in.rest.dto.CreateAccountReq;
import lamph.emon.auth.usecase.params.BlockAccountInput;
import lamph.emon.auth.usecase.params.CreateAccountInput;
import lamph.emon.auth.usecase.params.UnBlockAccountInput;
import lamph.services.AccountService;
import lombok.RequiredArgsConstructor;

@Controller("/api/accounts")
@RequiredArgsConstructor
public class AccountRest {

    private final AccountService accountService;

    @Post(consumes = MediaType.APPLICATION_JSON)
    HttpResponse<String> createAccount(@Body CreateAccountReq req) {
        CreateAccountInput input = new CreateAccountInput(req.username(), req.password());
        return HttpResponse.ok(accountService.createAccount(input));
    }

    @Post(value = "{id}/block", consumes = MediaType.APPLICATION_JSON)
    HttpResponse<String> blockAccount(@PathVariable String id, @Body ChangeStatusAccountReq req) {
        BlockAccountInput input = new BlockAccountInput(id, req.reason());
        accountService.blockAccount(input);
        return HttpResponse.ok();
    }

    @Post(value = "{id}/unblock", consumes = MediaType.APPLICATION_JSON)
    HttpResponse<String> unblockAccount(@PathVariable String id, @Body ChangeStatusAccountReq req) {
        UnBlockAccountInput input = new UnBlockAccountInput(id, req.reason());
        accountService.unBlockAccount(input);
        return HttpResponse.ok();
    }
}
