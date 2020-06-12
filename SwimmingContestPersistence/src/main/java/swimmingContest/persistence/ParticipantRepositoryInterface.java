package swimmingContest.persistence;


import swimmingContest.model.Participant;

public interface ParticipantRepositoryInterface extends CrudRepository<Long, Participant> {
    public Participant findParticipantByNameAge(String firstName,String lastName,Integer age);
}
