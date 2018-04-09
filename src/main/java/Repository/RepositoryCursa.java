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

public class RepositoryCursa implements RepositoryCursaInterface {
    private RepoUtils repoUtils;
    private static final Logger logger = LogManager.getLogger();

    public RepositoryCursa(Properties properties) {
        logger.info("Initializing RepositoryCursa with properties {}", properties);
        repoUtils = new RepoUtils(properties);
    }

    public void rezerva(Cursa cursa,Rezervare rezervare)
    {
        logger.traceEntry();
        Connection con=repoUtils.getConnection();
        int codCursa=cautaCursa(cursa);
        int nrLocuriDisponibile=getNrLocuriDisponibile(codCursa);
        if(nrLocuriDisponibile>=rezervare.getNrLocuri()) {
            try (PreparedStatement preparedStatement = con.prepareStatement("insert into Rezervari(Nume, NrLocuri, CodCursa) VALUES(?,?,?)")) {
                preparedStatement.setString(1,rezervare.getNume());
                preparedStatement.setInt(2,rezervare.getNrLocuri());
                preparedStatement.setInt(3,codCursa);
                preparedStatement.execute();
                updateNrLocuri(rezervare.getNrLocuri(),codCursa);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateNrLocuri(int nrLocuri,int codCursa) {
        logger.traceEntry();
        Connection con=repoUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("update Curse set NrLocuriDisponibile=NrLocuriDisponibile-? where CodCursa=?")) {
            preparedStatement.setInt(1,nrLocuri);
            preparedStatement.setInt(2,codCursa);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNrLocuriDisponibile(int codCursa)
    {
        logger.traceEntry("Searching");
        Connection con=repoUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select NrLocuriDisponibile from Curse where CodCursa=?"))
        {
            preparedStatement.setInt(1,codCursa);
            try(ResultSet result=preparedStatement.executeQuery())
            {
                if(result.next())
                {
                    return result.getInt("NrLocuriDisponibile");
                }
            }
            catch(SQLException e)
            {
                logger.error(e);
                System.out.println("Error DB "+e);
            }
            logger.traceExit("0 matches");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }
    public int cautaCursa(Cursa cursa)
    {
        logger.traceEntry("Searching");
        Connection con=repoUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select CodCursa from Curse where Destinatie=? and Data=?  and Ora=?"))
        {
            preparedStatement.setString(1,cursa.getDestinatie());
            preparedStatement.setString(2,cursa.getData().toString());
            preparedStatement.setString(3,cursa.getOra().toString());
            try(ResultSet result=preparedStatement.executeQuery())
            {
                if(result.next())
                {
                    return result.getInt("CodCursa");
                }
            }
            catch(SQLException e)
            {
                logger.error(e);
                System.out.println("Error DB "+e);
            }
            logger.traceExit("0 matches");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }

    @Override
    public void addEntity(Cursa entity) {

    }

    @Override
    public void deleteEntity(Cursa entity) {

    }

    @Override
    public List<Cursa> getAll() {

        logger.traceEntry();
        List<Cursa> trips=new ArrayList<>();
        Connection con=repoUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select Destinatie,Data,Ora,NrLocuriDisponibile from Curse"))
        {
            try(ResultSet resultSet=preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    trips.add(new Cursa(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }

    @Override
    public void findOne(Cursa entity) {

    }
}
