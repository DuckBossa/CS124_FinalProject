//window for shop

import javax.imageio.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Shop extends JFrame {
	public FlyweightFactory ff;
	/* For testing purposes only
	public static void main (String args []) {
		FlyweightFactory ff = new FlyweightFactory();
		Shop shop = new Shop(ff);
	}
	*/
	public Shop (FlyweightFactory ff) {
		this.setSize(500,500);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setLayout(new GridLayout(0,3));
		this.ff = ff;

		//http://stackoverflow.com/questions/4898584/java-using-an-image-as-a-button
		JButton b1 = new JButton(new ImageIcon(ff.getImage("test1.jpg")));
		b1.setBorder(BorderFactory.createEmptyBorder());
		b1.setContentAreaFilled(false);
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Accessory 1");
			}
		});

		JButton b2 = new JButton(new ImageIcon(ff.getImage("test2.jpg")));
		b2.setBorder(BorderFactory.createEmptyBorder());
		b2.setContentAreaFilled(false);
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Weapon 1");
			}
		});

		JButton b3 = new JButton(new ImageIcon(ff.getImage("test3.jpg")));
		b3.setBorder(BorderFactory.createEmptyBorder());
		b3.setContentAreaFilled(false);
		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Armor 1");
			}
		});

		JButton b4 = new JButton(new ImageIcon(ff.getImage("test4.jpg")));
		b4.setBorder(BorderFactory.createEmptyBorder());
		b4.setContentAreaFilled(false);
		b4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Accessory 2");
			}
		});

		JButton b5 = new JButton(new ImageIcon(ff.getImage("test5.jpg")));
		b5.setBorder(BorderFactory.createEmptyBorder());
		b5.setContentAreaFilled(false);
		b5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Weapon 2");
			}
		});

		JButton b6 = new JButton(new ImageIcon(ff.getImage("test6.jpg")));
		b6.setBorder(BorderFactory.createEmptyBorder());
		b6.setContentAreaFilled(false);
		b6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Armor 2");
			}
		});

		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(b4);
		this.add(b5);
		this.add(b6);


		this.setVisible(true);
	}
}
