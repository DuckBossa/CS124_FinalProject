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
    public static final int PLAYER_IMG_WIDTH = 32; //
    public static final int PLAYER_IMG_HEIGHT = 48;
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
    }
    
    public void setCanvas(Canvas a)
    {
        this.remove(current);
        this.add(a);
        current = a;
    }
    
    public void removeCanvas()
    {
        getContentPane().removeAll();
        current = null;
        revalidate();
    }
    
    public void setUpConnection(String ip) throws Exception
    {
            serv = (ServerInt) Naming.lookup("rmi://"+ip+"/GameServer");
            Player x = new Player(100,2,5,5,20,20,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT,1);
            ID = serv.logIn(x);
            new GameWindow(serv, ID);
            dispose();
    }
    
}
