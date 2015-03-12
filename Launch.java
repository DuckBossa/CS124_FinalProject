import javax.swing.*;
import java.awt.*;
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
    
}
