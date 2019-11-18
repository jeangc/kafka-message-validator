package validators;

public interface Validator {
    public void validate(String message) throws InvalidException;
}
