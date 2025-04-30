package lamph.quarkus.auth.adapter.in.rest.request;

import lombok.Data;

@Data
public class CreateAccountRequest {

    private String username;
    private String password;
}
