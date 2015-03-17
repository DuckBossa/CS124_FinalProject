import java.awt.geom.Rectangle2D;
public class Player extends Character{
	public Item item;
	

	public Player(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		super(atk,def,vx,vy,x,y,w,h,lvl);
	}
	
	public Rectangle2D attack(){
		Rectangle2D temp =  null;
		// check where character is facing
		// get the rectangle that is the same height and width as the character but 
		int hitx = 0;
		int hity = 0;
		switch(face){
			case 0: //up
				hitx = x; 
				hity = y - h;
				break;
			case 1: //left
				hitx = x - h;
				hity = y + w/3;
				break;
			case 2: //down
				hitx = x;
				hity = y + h;			
				break;
			case 3: //right
				hitx = x + w;
				hity = y + w/3;			
				break;
		}
		if(face%2 == 0){
			temp = new Rectangle2D.Double(hitx,hity,w,h);
		}
		else{
			temp = new Rectangle2D.Double(hitx,hity,h,w);
		}
		return temp;
	}



}