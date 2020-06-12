package swimmingContest.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swimmingContest.model.Participant;
import swimmingContest.persistence.ParticipantRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantJdbcRepository implements ParticipantRepositoryInterface {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    static Participant getParticipant(ResultSet resultSet) throws SQLException {
        String firstName=resultSet.getString("FirstName");
        String lastName=resultSet.getString("LastName");
        Integer age=resultSet.getInt("Age");
        Participant participant=new Participant(firstName,lastName,age);
        participant.setId(resultSet.getLong("Id"));
        return participant;
    }

    public ParticipantJdbcRepository(Properties props) {
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count (*) as [SIZE] from Participants")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Participant entity) {

        logger.traceEntry("saving person {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Participants(FirstName,LastName,Age) values (?,?,?)")){
            preStmt.setString(1,entity.getFirstName());
            preStmt.setString(2,entity.getLastName());
            preStmt.setInt(3,entity.getAge());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Long longId) {
        logger.traceEntry("deleting person with {}",longId);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Participants where Id=?")){
            preStmt.setLong(1,longId);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }


    @Override
    public void update(Long longId,Participant entity) {
        logger.traceEntry("updating person with {}",longId);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Participants SET FirstName=? ,LastName=?,Age=? where Id=?")){
            preStmt.setString(1,entity.getFirstName());
            preStmt.setInt(3,entity.getAge());
            preStmt.setString(2,entity.getLastName());
            preStmt.setLong(4,longId);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Participant findOne(Long longId) {
        logger.traceEntry("finding person with {}", longId);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Participants where Id=?")) {
            preStmt.setLong(1, longId);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
//                    String firstName=result.getString("FirstName");
//                    String lastName=result.getString("LastName");
//                    Integer age=result.getInt("Age");
//                    Participant participant=new Participant(firstName,lastName,age);
//                    participant.setId(result.getLong("Id"));
                    Participant participant=getParticipant(result);
                    logger.traceExit(participant);
                    return participant;
                }
            }
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Participant findParticipantByNameAge(String firstName, String lastName, Integer age) {
        logger.traceEntry("finding person with {}{}{}", firstName,lastName,age);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Participants where FirstName=? AND LastName=? AND Age=?")) {
            preStmt.setString(1, firstName);
            preStmt.setString(2, lastName);
            preStmt.setInt(3, age);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
//                    String firstName=result.getString("FirstName");
//                    String lastName=result.getString("LastName");
//                    Integer age=result.getInt("Age");
//                    Participant participant=new Participant(firstName,lastName,age);
//                    participant.setId(result.getLong("Id"));
                    Participant participant=getParticipant(result);
                    logger.traceExit(participant);
                    return participant;
                }
            }
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<Participant> findAll(){
        Connection con=dbUtils.getConnection();
        List<Participant> participants=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participants")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
//                    String firstName=result.getString("FirstName");
//                    String lastName=result.getString("LastName");
//                    Integer age=result.getInt("Age");
//                    Participant participant=new Participant(firstName,lastName,age);
//                    participant.setId(result.getLong("Id"));
                    Participant participant=getParticipant(result);
                    participants.add(participant);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(participants);
        return participants;
    }
}
