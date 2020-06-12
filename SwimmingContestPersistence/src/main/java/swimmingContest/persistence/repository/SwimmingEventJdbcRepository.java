package swimmingContest.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swimmingContest.model.SwimmingEvent;
import swimmingContest.model.SwimmingStroke;
import swimmingContest.persistence.SwimmingEventInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SwimmingEventJdbcRepository implements SwimmingEventInterface {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    static SwimmingEvent getSwimmingEvent(ResultSet resultSet) throws SQLException {
        String swimmingStroke= resultSet.getString("SwimmingStroke");
        Integer distance=resultSet.getInt("Distance");
        SwimmingEvent swimmingEvent=new SwimmingEvent(swimmingStroke,distance);
        swimmingEvent.setId(resultSet.getLong("Id"));
        return swimmingEvent;
    }

    public SwimmingEventJdbcRepository(Properties props) {
//        Properties props = new Properties();
//        try {
//            props.load(new FileInputStream("C:\\Users\\HP\\Desktop\\ANUL II\\SEM2\\MPP\\MPP_lab2_swimmingContest\\src\\main\\resources\\BD.properties"));
//        }
//        catch (IOException e) {
//            System.out.println("Eroare: "+e); }
//        logger.info("Initializing SwimmingEventJdbcRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count (*) as [SIZE] from SwimmingEvents")) {
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



    public void save(SwimmingEvent entity) {
        logger.traceEntry("saving swimming event {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into SwimmingEvents(SwimmingStroke,Distance) values (?,?)")){
            preStmt.setString(1,entity.getSwimmingStroke().toString());
            preStmt.setInt(2,entity.getDistance());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    public void delete(Long longId) {
        logger.traceEntry("deleting swimming event with {}",longId);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from SwimmingEvents where Id=?")){
            preStmt.setLong(1,longId);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    public void update(Long longId,SwimmingEvent entity) {
        logger.traceEntry("updating swimming event with {}",longId);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update SwimmingEvents SET SwimmingStroke=? ,Distance=? where Id=?")){
            preStmt.setString(1,entity.getSwimmingStroke().toString());
            preStmt.setInt(2,entity.getDistance());
            preStmt.setLong(3,longId);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    public SwimmingEvent findOne(Long longId) {
        logger.traceEntry("finding swimming event with {}", longId);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from SwimmingEvents where Id=?")) {
            preStmt.setLong(1, longId);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
//                    SwimmingStroke swimmingStroke= SwimmingStroke.valueOf(result.getString("SwimmingStroke"));
//                    Integer distance=result.getInt("Distance");
//                    SwimmingEvent swimmingEvent=new SwimmingEvent(swimmingStroke,distance);
//                    swimmingEvent.setId(result.getLong("Id"));
                    SwimmingEvent swimmingEvent=getSwimmingEvent(result);
                    logger.traceExit(swimmingEvent);
                    return swimmingEvent;
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

    public SwimmingEvent findSwimmingEventByStyleDistance(SwimmingStroke swimmingStroke, Integer distance){
        logger.traceEntry("finding swimming event with {}{}", swimmingStroke,distance);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from SwimmingEvents where SwimmingStroke=? AND Distance=?")) {
            preStmt.setString(1, swimmingStroke.toString());
            preStmt.setInt(2, distance);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
//                    SwimmingStroke swimmingStroke= SwimmingStroke.valueOf(result.getString("SwimmingStroke"));
//                    Integer distance=result.getInt("Distance");
//                    SwimmingEvent swimmingEvent=new SwimmingEvent(swimmingStroke,distance);
//                    swimmingEvent.setId(result.getLong("Id"));
                    SwimmingEvent swimmingEvent=getSwimmingEvent(result);
                    logger.traceExit(swimmingEvent);
                    return swimmingEvent;
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
    public SwimmingEvent findSwimmingEventByStyleDistance(String swimmingStroke, Integer distance) {
        return null;
    }

    @Override
    public SwimmingEvent[] getSwimmingEvents() {
        return new SwimmingEvent[0];
    }


    public Iterable<SwimmingEvent> findAll(){
        Connection con=dbUtils.getConnection();
        List<SwimmingEvent> swimmingEvents=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from SwimmingEvents")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
//                    SwimmingStroke swimmingStroke= SwimmingStroke.valueOf(result.getString("SwimmingStroke"));
//                    Integer distance=result.getInt("Distance");
//                    SwimmingEvent swimmingEvent=new SwimmingEvent(swimmingStroke,distance);
//                    swimmingEvent.setId(result.getLong("Id"));
                    SwimmingEvent swimmingEvent=getSwimmingEvent(result);
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
}
