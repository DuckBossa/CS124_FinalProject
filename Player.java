public class Player extends Character{
	public Player(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		super(atk,def,vx,vy,x,y,w,h,lvl);
	}
	public void move(int dir){
		switch (dir){
			case 0://up
			if(face  == 0 ){
				seq++;
				if(seq >= 4)
					seq = 0;
				y -= vy;
			}
			else
			break;
			case 1://down
			break;
			case 2:// left
			break;
			case 3:// right
			break;
		}
	}
}