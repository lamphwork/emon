package lamph.emon.auth.entities;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleAuthority implements Authority {

    private final String name;

    @Override
    public String getValue() {
        return name;
    }
}
