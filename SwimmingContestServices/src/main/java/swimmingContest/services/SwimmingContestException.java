package swimmingContest.services;

public class SwimmingContestException extends Exception{

    public SwimmingContestException() {
    }

    public SwimmingContestException(String message) {
        super(message);
    }

    public SwimmingContestException(String message, Throwable cause) {
        super(message, cause);
    }
}
