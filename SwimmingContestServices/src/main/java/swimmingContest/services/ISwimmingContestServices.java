package swimmingContest.services;

import swimmingContest.model.*;

public interface ISwimmingContestServices {

    void login(User user, ISwimmingContestObserver client) throws SwimmingContestException;
    void logout(User user, ISwimmingContestObserver client) throws SwimmingContestException;

    void addSignUp(SignUpDTO signUpDTO) throws SwimmingContestException;
    Iterable<Participant> getParticipantsSwimmingEvent(SwimmingEvent swimmingEvent) throws SwimmingContestException;
    Iterable<SwimmingEvent> getSwimmingEventsParticipant(Participant participant) throws SwimmingContestException;

    //services from ParticipantService
    Iterable<Participant> findAllParticipants() throws SwimmingContestException;
    Participant findParticipant(String firstName,String lastName,Integer age) throws SwimmingContestException;
    void addParticipant(ParticipantDTO participant) throws SwimmingContestException;

    //services from SignUpService
    Integer filterNumberOfParticipantsBySwimmingEvent(SwimmingEvent swimmingEvent) throws SwimmingContestException;
    Iterable<SignUp> findAllSignUps() throws SwimmingContestException;

    //services from SwimmingEventService
    Iterable<SwimmingEvent> findAllSwimmingEvents() throws SwimmingContestException;
    SwimmingEvent findSwimmingEventByStrokeDistance(String swimmingStroke, Integer distance) throws SwimmingContestException;

    //services from UserService
    User findOneUserNamePassword(User user) throws SwimmingContestException;
    User findOneUserName(User user) throws SwimmingContestException;
}
