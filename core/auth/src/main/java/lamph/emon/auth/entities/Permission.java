package lamph.emon.auth.entities;

public record Permission(String name, String description) implements Authority {

    @Override
    public String getValue() {
        return name.toLowerCase();
    }
}
