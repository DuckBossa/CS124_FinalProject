import java.util.ArrayList;
public class EnemyRangedState implements State{
	ArrayList<Arrow> arrow;
	Enemy e;
	Player p;
	public int recharge;
	public int currMove;
	public EnemyRangedState(Enemy e, Player p,ArrayList<Arrow> arrow){
		this.e = e;
		this.p = p;
		this.arrow = arrow;
		recharge = 0;
		currMove = Math.abs(e.random.nextInt()%4);
	}

	public void handle(){
		if(p.collide(e.fov_rect)){
			if(recharge <= 0){
				recharge = 40;
				double colx = ( (p.x + p.w/2) - (e.x + e.w/2) );
				double coly = ( (p.y + p.h/2) - (e.y + e.h/2) ); 
				double mag =  Math.sqrt(colx*colx + coly*coly);
				arrow.add(new Arrow(e.x,e.y,colx/mag*3,coly/mag*3,5,GameWindow.ARROW_W,GameWindow.ARROW_H));
			}
			else{
				recharge--;
			}
		}
		else{
			if(e.roll()){
				int ranMove = Math.abs(e.random.nextInt()%4);
				currMove = ranMove;
			}
			e.move(currMove);
		}
	}
}