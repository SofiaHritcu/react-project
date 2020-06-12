package swimmingContest.persistence.repository;


import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swimmingContest.model.Participant;
import swimmingContest.model.SignUp;
import swimmingContest.model.SwimmingEvent;
import swimmingContest.persistence.SignUpRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SignUpJdbcRepository implements SignUpRepositoryInterface {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    static SignUp getSignUp(ResultSet resultSet) throws SQLException {
        Long idParticipant=resultSet.getLong("IdParticipant");
        Long idSwimmingEvent=resultSet.getLong("IdSwimmingEvent");
        SignUp signUp=new SignUp();
        signUp.setId(new Pair<>(idParticipant,idSwimmingEvent));
        return signUp;
    }

    public SignUpJdbcRepository(Properties props) {
//        Properties props = new Properties();
//        try {
//            props.load(new FileInputStream("C:\\Users\\HP\\Desktop\\ANUL II\\SEM2\\MPP\\MPP_lab2_swimmingContest\\src\\main\\resources\\BD.properties"));
//        }
//        catch (IOException e) {
//            System.out.println("Eroare: "+e); }
//        logger.info("Initializing SignUpJdbcRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count (*) as [SIZE] from SignUps")) {
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

    public void save(SignUp entity) {
        logger.traceEntry("saving signUp {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into SignUps(IdParticipant,IdSwimmingEvent) values (?,?)")){
            preStmt.setLong(1,entity.getId().getKey());
            preStmt.setLong(2,entity.getId().getValue());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    public void delete(Pair<Long,Long> longLongPair) {
        logger.traceEntry("deleting signUp with {}",longLongPair);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from SignUps where IdParticipant=? AND IdSwimmingEvent=?")){
            preStmt.setLong(1,longLongPair.getKey());
            preStmt.setLong(2,longLongPair.getValue());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Pair<Long, Long> longLongPair, SignUp entity) {

    }
//
//    public void update(Pair<Long,Long> longLongPair,SignUp entity) {
//        logger.traceEntry("updating signUp  with {}",longLongPair);
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("update SignUps SET IdParticipant=? ,IdSwimmingEvent=? where Id=?")){
//            preStmt.setLong(1,entity.getId().fst);
//            preStmt.setLong(2,entity.getId().snd);
//            int result=preStmt.executeUpdate();
//        }catch (SQLException ex){
//            logger.error(ex);
//            System.out.println("Error DB "+ex);
//        }
//        logger.traceExit();
//    }

    public SignUp findOne(Pair<Long,Long> longLongPair) {
        logger.traceEntry("finding signUp with {}", longLongPair);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from SignUps where IdParticipant=? AND IdSwimmingEvent=?")) {
            preStmt.setLong(1,longLongPair.getKey());
            preStmt.setLong(2,longLongPair.getValue());
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
//                    Long idParticipant=result.getLong("IdParticipant");
//                    Long idSwimmingEvent=result.getLong("IdSwimmingEvent");
//                    SignUp signUp=new SignUp();
//                    signUp.setId(new Pair<>(idParticipant,idSwimmingEvent));
                    SignUp signUp=getSignUp(result);
                    logger.traceExit(signUp);
                    return signUp;
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

    public Iterable<SignUp> findAll(){
        Connection con=dbUtils.getConnection();
        List<SignUp> signUps=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from SignUps")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
//                    Long idParticipant=result.getLong("IdParticipant");
//                    Long idSwimmingEvent=result.getLong("IdSwimmingEvent");
//                    SignUp signUp=new SignUp();
//                    signUp.setId(new Pair<>(idParticipant,idSwimmingEvent));
                    SignUp signUp=getSignUp(result);
                    signUps.add(signUp);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(signUps);
        return signUps;
    }

    @Override
    public Integer findNumberOfParticipantsBySwimmingEvent(Long idSwimmingEventFilter) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count (*) as [Size] from SignUps inner join Participants on IdParticipant=Id where IdSwimmingEvent=?")) {
            preStmt.setLong(1,idSwimmingEventFilter);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit();
        return 0;
    }

    @Override
    public Iterable<Participant> findParticipantsBySwimmingEvent(Long idSwimmingEventFilter) {
        Connection con=dbUtils.getConnection();
        List<Participant> participants=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select *  from SignUps inner join Participants on IdParticipant=Id where IdSwimmingEvent=?")) {
            preStmt.setLong(1,idSwimmingEventFilter);
            try(ResultSet result=preStmt.executeQuery()) {
                while(result.next()) {
//                    String firstName=result.getString("FirstName");
//                    String lastName=result.getString("LastName");
//                    Integer age=result.getInt("Age");
//                    Participant participant=new Participant(firstName,lastName,age);
//                    participant.setId(result.getLong("Id"));
                    Participant participant=ParticipantJdbcRepository.getParticipant(result);
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

    @Override
    public Iterable<SwimmingEvent> findSwimmingEventsByParticipant(Long idParticipant) {
        Connection con=dbUtils.getConnection();
        List<SwimmingEvent> swimmingEvents=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select *  from SignUps inner join SwimmingEvents on IdSwimmingEvent=Id where IdParticipant=?")) {
            preStmt.setLong(1,idParticipant);
            try(ResultSet result=preStmt.executeQuery()) {
                while(result.next()) {
//                    SwimmingStroke swimmingStroke= SwimmingStroke.valueOf(result.getString("SwimmingStroke"));
//                    Integer distance=result.getInt("Distance");
//                    SwimmingEvent swimmingEvent=new SwimmingEvent(swimmingStroke,distance);
//                    swimmingEvent.setId(result.getLong("Id"));
                    SwimmingEvent swimmingEvent= SwimmingEventJdbcRepository.getSwimmingEvent(result);
                    swimmingEvents.add(swimmingEvent);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(swimmingEvents);
        return swimmingEvents;
    }

    @Override
    public Iterable<SignUp> findAllSignUps() {
        Connection con=dbUtils.getConnection();
        List<SignUp> signUps=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from SignUps")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
//                    String firstName=result.getString("FirstName");
//                    String lastName=result.getString("LastName");
//                    Integer age=result.getInt("Age");
//                    Participant participant=new Participant(firstName,lastName,age);
//                    participant.setId(result.getLong("Id"));
                    SignUp signUp=getSignUp(result);
                    signUps.add(signUp);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(signUps);
        return signUps;
    }
}
