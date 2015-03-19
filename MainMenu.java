import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author J.Lo
 */
public class MainMenu extends Canvas{
    
    Launch frame;
    boolean start, load, exit;
    
    public MainMenu(Launch x)
    {
        frame = x; 
        this.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
               
                if(e.getY() >= 640 && e.getY() <= 680)
                {
                    if( e.getX() >= 20 && e.getX() <= 420 )
                    {
                        System.out.println("Started Game");
                        popUp();
                        //start = true; // start game
                    } else if( e.getX() >= 440 && e.getX() <= 840 )
                    {
                        System.out.println("Loading Game");
                        popUp();
                        //load = true; // load game
                    } else if( e.getX() >= 860 && e.getX() <= 1260)
                    {
                        //exit = true; // exit game
                        System.out.println("exiting");
                        System.exit(0);
                    } else
                    {
                        //start = load = exit = false;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //System.out.println("("+e.getX()+","+e.getY()+"");
                /**if(e.getY() >= 640 && e.getY() <= 680)
                {
                    if( e.getX() >= 20 && e.getX() <= 420 )
                    {
                        start = true; // start game
                    } else if( e.getX() >= 440 && e.getX() <= 840 )
                    {
                        load = true; // load game
                    } else if( e.getButton() >= 860 && e.getX() <= 1260)
                    {
                        exit = true; // exit game
                    } else
                    {
                        start = load = exit = false;
                    }
                    repaint();
                }**/
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
    
        });
    }
    
    public void paint(Graphics g)
    {
        g.fillRect(20, 640, 400, 40);
        g.fillRect(440, 640, 400, 40);
        g.fillRect(860, 640, 400, 40);
        
        /*g.setColor(Color.YELLOW);
        if(start){
        g.fillRect(20, 640, 400, 40);
        } else if(load){
        g.fillRect(440, 640, 400, 40);
        } else if(exit){
        g.fillRect(860, 640, 400, 40);
        }*/
        
    }
    
    public void popUp()
    {
        JFrame x = new JFrame("Server IP");
        x.setLayout(new BorderLayout());
        JPanel buttons = new JPanel();
        JTextField IPs = new JTextField("Enter Server IP");
        JButton ok = new JButton("OK");
        JButton ex = new JButton("CANCEL");
        ok.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                        try{
                            frame.setUpConnection(IPs.getText());
                            x.dispose();
                        } catch(Exception e)
                        {
                            IPs.setText("ERROR WITH I.P.");
                            e.printStackTrace();
                        }
                        
                    }
        });
        ex.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                        x.dispose();
                    }
        });
        buttons.add(ok);
        buttons.add(ex);
        x.add(IPs, BorderLayout.PAGE_START);
        x.add(buttons, BorderLayout.PAGE_END);
        x.setSize(350,80);
        x.setVisible(true);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
