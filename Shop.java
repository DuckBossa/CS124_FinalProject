//window for shop

import javax.imageio.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Shop extends JFrame
{
	public FlyweightFactory ff;
	public Player player;
	/* For testing purposes only
	public static void main (String args []) {
		FlyweightFactory ff = new FlyweightFactory();
		Shop shop = new Shop(ff);
	}
	*/
	public Shop (FlyweightFactory ff, Player p) {
		this.setSize(500,500);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.ff = ff;
		this.player = p;

		JPanel items = new JPanel (new GridLayout(0,3));

		//http://stackoverflow.com/questions/4898584/java-using-an-image-as-a-button
		JButton b1 = new JButton(new ImageIcon(ff.getImage("img/cap.png")));
		b1.setBorder(BorderFactory.createEmptyBorder());
		b1.setContentAreaFilled(false);
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				player.setItem(new StaminaHat());
			}
		});

		JButton b2 = new JButton(new ImageIcon(ff.getImage("img/axe.png")));
		b2.setBorder(BorderFactory.createEmptyBorder());
		b2.setContentAreaFilled(false);
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				player.setItem(new Axe());
			}
		});

		JButton b3 = new JButton(new ImageIcon(ff.getImage("img/dress.png")));
		b3.setBorder(BorderFactory.createEmptyBorder());
		b3.setContentAreaFilled(false);
		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				player.setItem(new Dress());
			}
		});

		JButton b4 = new JButton(new ImageIcon(ff.getImage("img/potion.png")));
		b4.setBorder(BorderFactory.createEmptyBorder());
		b4.setContentAreaFilled(false);
		b4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				player.setItem(new HealingPotion());
			}
		});

		
		JButton b5 = new JButton(new ImageIcon(ff.getImage("img/sword.png")));
		b5.setBorder(BorderFactory.createEmptyBorder());
		b5.setContentAreaFilled(false);
		b5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				player.setItem(new Sword());
			}
		});

		JButton b6 = new JButton(new ImageIcon(ff.getImage("img/shield.png")));
		b6.setBorder(BorderFactory.createEmptyBorder());
		b6.setContentAreaFilled(false);
		b6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				player.setItem(new Shield());
			}
		});

		items.add(b1);
		items.add(b2);
		items.add(b3);
		items.add(b4);
		items.add(b5);
		items.add(b6);
		

		JPanel labels = new JPanel (new GridLayout(1,3));
		labels.add(new JLabel("Accessories",SwingConstants.CENTER));
		labels.add(new JLabel("Weapons",SwingConstants.CENTER));
		labels.add(new JLabel("Armors",SwingConstants.CENTER));

		this.add(labels, BorderLayout.NORTH);
		this.add(items, BorderLayout.CENTER);

		this.setVisible(true);
	}
}