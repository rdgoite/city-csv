package de.goeuro.demo.exception;

public class OutputFileExistsException extends RuntimeException {

    public OutputFileExistsException() {
        super("Output file already exists.");
    }

}
