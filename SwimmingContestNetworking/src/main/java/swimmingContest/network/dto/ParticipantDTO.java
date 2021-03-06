package swimmingContest.network.dto;

import swimmingContest.model.Participant;
import swimmingContest.model.SwimmingEvent;

import java.io.Serializable;
import java.util.List;

public class ParticipantDTO extends Participant implements Serializable {
    List<SwimmingEvent> signedUpFor;

    public ParticipantDTO(String firstName, String lastName, Integer age, List<SwimmingEvent> signedUpFor) {
        super(firstName, lastName, age);
        this.signedUpFor = signedUpFor;
    }

    public List<SwimmingEvent> getSignedUpFor() {
        return signedUpFor;
    }

    public void setSignedUpFor(List<SwimmingEvent> signedUpFor) {
        this.signedUpFor = signedUpFor;
    }

    @Override
    public String toString() {
        return "ParticipantDTO{" +
                "signedUpFor=" + signedUpFor +
                '}';
    }
}
