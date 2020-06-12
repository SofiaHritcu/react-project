package swimmingContest.model;

public class SwimmingEventDTO extends SwimmingEvent {
    private Integer numberOfParticipants;

    public SwimmingEventDTO(String swimmingStroke, Integer distance, Integer numberOfParticipants) {
        super(swimmingStroke, distance);
        this.numberOfParticipants = numberOfParticipants;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    @Override
    public String toString() {
        return super.toString()+" "+this.getNumberOfParticipants();
    }
}
