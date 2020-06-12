package swimmingContest.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swimmingContest.model.User;
import swimmingContest.persistence.UserRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserJdbcRepository implements UserRepositoryInterface {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    private User getUser(ResultSet resultSet) throws SQLException {
        String firstName=resultSet.getString("FirstName");
        String lastName=resultSet.getString("LastName");
        String password=resultSet.getString("Password");
        User user=new User(firstName,lastName,password);
        user.setId(resultSet.getLong("Id"));
        return user;
    }

    public UserJdbcRepository(Properties props) {
//        Properties props = new Properties();
//        try {
//            props.load(new FileInputStream("C:\\Users\\HP\\Desktop\\ANUL II\\SEM2\\MPP\\MPP_lab2_swimmingContest\\src\\main\\resources\\BD.properties"));
//        }
//        catch (IOException e) {
//            System.out.println("Eroare: "+e); }
//        logger.info("Initializing UserJdbcRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count (*) as [SIZE] from Users")) {
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

    public void save(User entity) {
        logger.traceEntry("saving user {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Users(FirstName,LastName,Password) values (?,?,?)")){
            preStmt.setString(1,entity.getFirstName());
            preStmt.setString(2,entity.getLastName());
            preStmt.setString(3,entity.getPassword());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    public void delete(Long longId) {
        logger.traceEntry("deleting user with {}",longId);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Users where Id=?")){
            preStmt.setLong(1,longId);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    public void update(Long longId,User entity) {
        logger.traceEntry("updating user with {}",longId);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Users SET FirstName=? ,LastName=?,Password=? where Id=?")){
            preStmt.setString(1,entity.getFirstName());
            preStmt.setString(2,entity.getLastName());
            preStmt.setString(3,entity.getPassword());
            preStmt.setLong(4,longId);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    public User findOne(Long longId) {
        logger.traceEntry("finding user with {}", longId);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Users where Id=?")) {
            preStmt.setLong(1, longId);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    User user=getUser(result);
                    logger.traceExit(user);
                    return user;
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

    public User findOneByNamePassword(String firstName,String lastName,String password) {
        logger.traceEntry("finding user with {}{}{}", firstName,lastName,password);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Users where FirstName=? AND  LastName=? AND Password=?")) {
            preStmt.setString(1, firstName);
            preStmt.setString(2, lastName);
            preStmt.setString(3, password);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    User user=getUser(result);
                    logger.traceExit(user);
                    return user;
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

    public User findOneByName(String firstName,String lastName) {
        logger.traceEntry("finding user with {}{}", firstName,lastName);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Users where FirstName=? AND  LastName=?")) {
            preStmt.setString(1, firstName);
            preStmt.setString(2, lastName);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    User user=getUser(result);
                    logger.traceExit(user);
                    return user;
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


    public Iterable<User> findAll(){
        Connection con=dbUtils.getConnection();
        List<User> users=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Users")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
//                    String firstName=result.getString("FirstName");
//                    String lastName=result.getString("LastName");
//                    String password=result.getString("Password");
//                    User user=new User(firstName,lastName,password);
//                    user.setId(result.getLong("Id"));
                    User user=getUser(result);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(users);
        return users;
    }
}
