package salarychecker.core;

public enum Errors {

    ERROR_DIALOG("Error!"),
    EMAIL_IN_USE("That email is already in use."),
    NAME_FIELD_EMPTY("Please enter a name."),
    EMAIL_FIELD_EMPTY("Please enter an email."),
    PWD_FIELD_EMPTY("Please enter a password."),
    SOCIAL_NUMBER_EMPTY("Please enter a social number."),
    NAME_IN_USE("That username is already in use."),
    INVALID_NAME("Name should only contain letters, and be atleast two letters.."),
    INVALID_EMAIL("Invalid email, must be of format: name-part@domain, e.g. example@example.com."),
    INVALID_PWD("Invalid password, must be at least 8 characters and contain at least 1 digit and 1 lower and uppercase letter."),
    INVALID_SOCIAL_NUMBER("The entered social number, is not valid."),
    INVALID_EMPLOYEE_NUMBER("Employee number should be exactly 5 numbers."),
    INVALID_TAX_COUNT("Tax count should be a decimal number between 0 and 100."),
    INVALID_EMAIL_AND_OR_PWD("Invalid email address or password."),
    NOT_REGISTERED("This user is not registered.");

    private final String ERROR_MESSAGE;

    Errors(final String ERROR_MESSAGE) {
      this.ERROR_MESSAGE = ERROR_MESSAGE;
    }

    public String getMessage() {
        return ERROR_MESSAGE;
    }
}