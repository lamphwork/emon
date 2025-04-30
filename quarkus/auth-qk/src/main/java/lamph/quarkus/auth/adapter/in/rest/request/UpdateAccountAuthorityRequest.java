package lamph.quarkus.auth.adapter.in.rest.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateAccountAuthorityRequest {

    private List<String> roles;
    private List<String> permissions;
}
