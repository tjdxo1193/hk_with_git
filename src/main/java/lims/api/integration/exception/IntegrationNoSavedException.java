package lims.api.integration.exception;

public class IntegrationNoSavedException extends RuntimeException {

    public IntegrationNoSavedException() {
        super("No saved data.");
    }
}