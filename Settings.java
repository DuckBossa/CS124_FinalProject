//window for settings

import javax.imageio.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;

public class Settings extends JFrame {
	Player p;
	JTextField text1, text2, text3, text4, text5, text6;
        ServerInt serv;
        int id;
	/* For testing purposes only
	public static void main (String args []) {
		Settings userSettings = new Settings();
	}
	*/
	public Settings ( ServerInt x, int i) {
		this.setSize(200,300);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setLayout(new BorderLayout());
                serv = x;
                id = i;
		JPanel panel = new JPanel(new GridLayout(6,2));
		JLabel label1 = new JLabel ("Move Up");
		JLabel label2 = new JLabel ("Move Down");
		JLabel label3 = new JLabel ("Move Right");
		JLabel label4 = new JLabel ("Move Left");
		JLabel label5 = new JLabel ("Attack");
		JLabel label6 = new JLabel ("Use Item");
		String map1 = p.map.get("up");
		String map2 = p.map.get("down");
		String map3 = p.map.get("right");
		String map4 = p.map.get("left");
		String map5 = p.map.get("attack");
		String map6 = p.map.get("item");
		text1 = new JTextField (map1);
		text2 = new JTextField (map2);
		text3 = new JTextField (map3);
		text4 = new JTextField (map4);
		text5 = new JTextField (map5);
		text6 = new JTextField (map6);

		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(label3);
		panel.add(text3);
		panel.add(label4);
		panel.add(text4);
		panel.add(label5);
		panel.add(text5);
		panel.add(label6);
		panel.add(text6);

		JButton button = new JButton ("Save Settings");
		button.addActionListener(new OpenSetting( this, serv, id));

		this.add(panel, BorderLayout.CENTER);
		this.add(button, BorderLayout.SOUTH);

		this.setVisible(true);
	}
}
class OpenSetting implements ActionListener {
	Player p;
	Settings s;
        ServerInt serv;
        int id;
	public OpenSetting( Settings s, ServerInt serv, int id) {
                this.serv = serv;
                this.id = id;
		this.s = s;
	}
	public void actionPerformed(ActionEvent ae) { 
	try{
								p = serv.getMyPlayer(id);
								} catch (Exception e){}
		String [] cur = new String [6];
		cur[0] = s.text1.getText();
		cur[1] = s.text2.getText();
		cur[2] = s.text3.getText();
		cur[3] = s.text4.getText();
		cur[4] = s.text5.getText();
		cur[5] = s.text6.getText();

		if (cur[0].equals(".") || cur[1].equals(".") || cur[2].equals(".") || 
		cur[3].equals(".") || cur[4].equals(".") || cur[5].equals(".")) {
			s.text1.setText(p.map.get("up"));
			s.text2.setText(p.map.get("down"));
			s.text3.setText(p.map.get("right"));
			s.text4.setText(p.map.get("left"));
			s.text5.setText(p.map.get("attack"));
			s.text6.setText(p.map.get("item"));
			System.out.println ("Cannot use dots (.) as custom keys.");
			return;
		}

		for (int i=0; i<5; i++) {
			char first = cur[i].charAt(0);
			for (int k=i+1; k<6; k++) {
				if (first==cur[k].charAt(0)) {
					s.text1.setText(p.map.get("up"));
					s.text2.setText(p.map.get("down"));
					s.text3.setText(p.map.get("right"));
					s.text4.setText(p.map.get("left"));
					s.text5.setText(p.map.get("attack"));
					s.text6.setText(p.map.get("item"));
					System.out.println ("Cannot use the same character for multiple commands.");
					return;
				}
			}
		}
		
		p.map.clear();
		p.hm.clear();

		p.map.put("up", cur[0]);
		p.map.put("down", cur[1]);
		p.map.put("right", cur[2]);
		p.map.put("left", cur[3]);
		p.map.put("attack", cur[4]);
		p.map.put("item", cur[5]);

		p.hm.put((int)'.', new DoNothing());
		p.hm.put((int)cur[0].charAt(0), new MoveUp(p));
		p.hm.put((int)cur[1].charAt(0), new MoveDown(p));
		p.hm.put((int)cur[2].charAt(0), new MoveRight(p));
		p.hm.put((int)cur[3].charAt(0), new MoveLeft(p));
		p.hm.put((int)cur[4].charAt(0), new Attack(p));
		p.hm.put((int)cur[5].charAt(0), new UseItem(p));

		System.out.println ("New keys (up, down, right, left, attack, item):");
		System.out.println (cur[0] + " " + cur[1] + " " + cur[2] + " " + cur[3] + " " + cur[4] + " " + cur[5]);
		try{
                    serv.refreshPlayer(id, p);
                }catch(RemoteException e){}
	}
}
