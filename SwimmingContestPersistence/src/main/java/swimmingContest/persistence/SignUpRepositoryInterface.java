package swimmingContest.persistence;

import javafx.util.Pair;
import swimmingContest.model.Participant;
import swimmingContest.model.SignUp;
import swimmingContest.model.SwimmingEvent;

public interface SignUpRepositoryInterface extends CrudRepository<Pair<Long,Long>, SignUp> {
    /***
     *the method gives the number of participants that are already signed up for a specific swimming event
     * @param idSwimmingEvent - the id of the swimming event
     * @return - the number of participants
     */
   Integer findNumberOfParticipantsBySwimmingEvent(Long idSwimmingEvent);

    /***
     * the method find all of the participants for a specific swimming event
     * @param idSwimmingEvent - the id of the swimming event
     * @return - all the participants who are signed up
     */
   Iterable<Participant> findParticipantsBySwimmingEvent(Long idSwimmingEvent);

    /***
     * the method filters the signUps by the id of the participant
     * @param idParticipant - the given idof the participant
     * @return - the swimming event that a specific participant signed up for
     */
   Iterable<SwimmingEvent> findSwimmingEventsByParticipant(Long idParticipant);

   Iterable<SignUp> findAllSignUps();
}
