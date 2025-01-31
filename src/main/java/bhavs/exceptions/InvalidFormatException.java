package bhavs.exceptions;

public class InvalidFormatException extends Exception{

    public InvalidFormatException() {
        super("Wrong format of the items! should be limited" +
                " to atleast 2 commas maximum and 2 parts only");
    }
}
