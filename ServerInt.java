import java.rmi.*;
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
    
    public int logIn() throws RemoteException;
    public void logOut(int i) throws RemoteException;
}
