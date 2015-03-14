public class Player extends Character{
	
	public Player(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		super(atk,def,vx,vy,x,y,w,h,lvl);
	}
	


	public enum Movement{ // maybe you can change the code to the keycode of the character? maybe.
		UP(0),DOWN(2),LEFT(1),RIGHT(3),ATTACK(4);
		private int code;

		private Movement(int code){
			this.code = code;
		}

		public int getCode(){
			return code;
		}
	}

	public void move(int dir){
		if(dir == Movement.UP.getCode()){
			y-=vy;
		}
		else if(dir == Movement.DOWN.getCode()){
			y+=vy;
		}
		else if(dir == Movement.LEFT.getCode()){
			x-= vx;
		}
		else if(dir == Movement.RIGHT.getCode()){
			x+= vx;
		}
		else if(dir == Movement.ATTACK.getCode()){
			System.out.println("attack");
		}
		else{
		}
		if(dir == face){
			seq++;
			if(seq >= 3)
				seq = 0;
		}
		else{
			seq = 0;
			face = dir;
		}
		updateRectangle();
	}
}