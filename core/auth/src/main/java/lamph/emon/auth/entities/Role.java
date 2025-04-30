package lamph.emon.auth.entities;

import java.util.List;

public record Role(String value, String description, List<Permission> permissions) implements Authority {

    @Override
    public String getValue() {
        return "ROLE_" + value;
    }
}
