package Service;

import Domain.Cursa;
import Domain.Rezervare;
import Domain.Users;
import Repository.IRepository;
import Repository.RepositoryCursaInterface;
import Repository.RepositoryRezervareInterface;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServiceOffice implements ServiceInterface {
    private RepositoryCursaInterface repoCursa;
    private RepositoryRezervareInterface repoRezervare;
    public ServiceOffice(RepositoryCursaInterface repoCursa,RepositoryRezervareInterface repoRezervare)
    {
        this.repoCursa=repoCursa;
        this.repoRezervare=repoRezervare;
    }
    public synchronized void rezerva(Cursa cursa, Rezervare rezervare) throws ServiceException,RemoteException
    {
        int codCursa=repoCursa.cautaCursa(cursa);
        int nrLocuriDisponibile=repoCursa.getNrLocuriDisponibile(codCursa);
        if(nrLocuriDisponibile>=rezervare.getNrLocuri()) {
            repoRezervare.rezerva(codCursa,cursa,rezervare);
            repoCursa.updateNrLocuri(rezervare.getNrLocuri(),codCursa);

        }
    }
    public synchronized List<Rezervare> cautaRezervari(Cursa cursa) throws ServiceException,RemoteException
    {
        return repoRezervare.cautaRezervari(repoCursa.cautaCursa(cursa));
    }
    public synchronized List<Cursa> getAllTrips()throws ServiceException,RemoteException
    {
        return repoCursa.getAll();
    }

    @Override
    public synchronized void checkUser(Users user)throws ServiceException,RemoteException{
    }
}
