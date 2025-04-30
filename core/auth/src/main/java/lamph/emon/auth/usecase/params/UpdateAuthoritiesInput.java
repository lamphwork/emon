package lamph.emon.auth.usecase.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthoritiesInput {

    private String accountId;
    private List<String> roles;
    private List<String> permissions;
}
