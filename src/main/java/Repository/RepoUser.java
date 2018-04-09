package Repository;

import Domain.Rezervare;
import Domain.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoUser implements RepoUserInterface {
    private static final Logger logger = LogManager.getLogger();
    private RepoUtils repoUtils;
    public RepoUser(Properties properties)
    {
        logger.info("Initializing RepositoryCursa with properties {}", properties);
        repoUtils = new RepoUtils(properties);
    }
    @Override
    public boolean checkUser(Users users) {
        logger.traceEntry();
        List<Rezervare> rezervares=new ArrayList<>();
        Connection con=repoUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select Username,Password from Users where Username=? and Password=?"))
        {
            preparedStatement.setString(1,users.getUsername());
            preparedStatement.setString(2,users.getPassword());
            try(ResultSet resultSet=preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    return  true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addEntity(Users entity) {

    }

    @Override
    public void deleteEntity(Users entity) {

    }

    @Override
    public List<Users> getAll() {
        return null;
    }

    @Override
    public void findOne(Users entity) {

    }
}
