public class Player extends Character{
	
	public Player(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		super(atk,def,vx,vy,x,y,w,h,lvl);
	}
	
	public enum Movement{
		UP(0),DOWN(1),LEFT(2),RIGHT(3),ATTACK(4);
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
			System.out.println("up");
		}
		else if(dir == Movement.DOWN.getCode()){
			System.out.println("down");
		}
		else if(dir == Movement.LEFT.getCode()){
			System.out.println("left");
		}
		else if(dir == Movement.RIGHT.getCode()){
			System.out.println("right");
		}
		else if(dir == Movement.ATTACK.getCode()){
			System.out.println("attack");
		}
		else{
			System.out.println("nothing");
		}
	}
}