import java.rmi.*;
import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author J.Lo
 */
public interface ServerInt extends Remote{
    
    public int logIn(Player a) throws RemoteException;
    public Player logOut(int i) throws RemoteException;
    public ArrayList<Player> getAllCharacters() throws RemoteException;
    public ArrayList<Enemy> getAllEnemies() throws RemoteException;
    public ArrayList<Arrow> getAllArrows() throws RemoteException;
    public void doCommand(int i, int key) throws RemoteException;
}
