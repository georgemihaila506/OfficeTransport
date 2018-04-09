package Repository;

import Domain.Cursa;
import Domain.Rezervare;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepositoryRezervare implements RepositoryRezervareInterface {
    private RepoUtils repoUtils;
    private static final Logger logger = LogManager.getLogger();
    public RepositoryRezervare(Properties properties) {
        logger.info("Initializing RepositoryCursa with properties {}", properties);
        repoUtils = new RepoUtils(properties);
    }
    public void rezerva(int codCursa,Cursa cursa, Rezervare rezervare)
    {
        logger.traceEntry();
        Connection con=repoUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into Rezervari(Nume, NrLocuri, CodCursa) VALUES(?,?,?)")) {
                preparedStatement.setString(1,rezervare.getNume());
                preparedStatement.setInt(2,rezervare.getNrLocuri());
                preparedStatement.setInt(3,codCursa);
                preparedStatement.execute();
                } catch (SQLException e) {
                e.printStackTrace(); }
        }
    public List<Rezervare> cautaRezervari(int codCursa)
    {
        logger.traceEntry();
        List<Rezervare> rezervares=new ArrayList<>();
        Connection con=repoUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select Nume,NrLocuri from Rezervari where CodCursa=?"))
        {
            preparedStatement.setInt(1,codCursa);
            try(ResultSet resultSet=preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    rezervares.add(new Rezervare(resultSet.getString(1),resultSet.getInt(2)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezervares;
    }

    @Override
    public void addEntity(Rezervare entity) {

    }

    @Override
    public void deleteEntity(Rezervare entity) {

    }

    @Override
    public List<Rezervare> getAll() {
        return null;
    }

    @Override
    public void findOne(Rezervare entity) {

    }
}
