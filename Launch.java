import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.rmi.*;
import java.awt.event.WindowListener;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author J.Lo
 */
public class Launch extends JFrame{
    
    Canvas main, current;
    ServerInt serv;
    int ID;
    
    public static void main(String args[])
    {
        Launch a = new Launch();
    }
    
    public Launch()
    {
        setUpFrame();
    }
    
    public void setUpFrame()
    {
        setSize(1280,720);
        main = new MainMenu(this);
        add(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        
        addWindowListener(new WindowListener(){

            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    serv.logOut(ID);
                } catch(Exception x)
                {
                    
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
    }
    
    public void setCanvas(Canvas a)
    {
        this.remove(current);
        this.add(a);
        current = a;
    }
    
    public void setUpConnection(String ip) throws Exception
    {
            serv = (ServerInt) Naming.lookup("rmi://"+ip+"/GameServer");
            ID = serv.logIn();
    }
    
}
