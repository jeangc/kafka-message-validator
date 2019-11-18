package validators;

public interface Validator {
    void validate(String message) throws InvalidException;
}
