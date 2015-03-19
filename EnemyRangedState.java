import java.io.Serializable;
import java.util.ArrayList;
public class EnemyRangedState implements State, Serializable{
	ArrayList<Arrow> arrow;
	Enemy e;
	ArrayList<Player> pl;
	public int recharge;
	public int currMove;
	public EnemyRangedState(Enemy e, ArrayList<Player> p,ArrayList<Arrow> arrow){
		this.e = e;
		this.pl = p;
		this.arrow = arrow;
		recharge = 0;
		currMove = Math.abs(e.random.nextInt()%4);
	}

	public void handle(){
            for(int i = 0 ; i < pl.size();i++)
            {
                Player p = pl.get(i);
		if(p.collide(e.fov_rect)){
			if(recharge <= 0){
				recharge = 40;
				double colx = ( (p.x + p.w/2) - (e.x + e.w/2) );
				double coly = ( (p.y + p.h/2) - (e.y + e.h/2) ); 
				double mag =  Math.sqrt(colx*colx + coly*coly);
				arrow.add(new Arrow(e.x,e.y,colx/mag*3,coly/mag*3,5,GameWindow.ARROW_W,GameWindow.ARROW_H));
                                return;
			}
		}
            }
				recharge--;	
		
			if(e.roll()){
				int ranMove = Math.abs(e.random.nextInt()%4);
				currMove = ranMove;
			}
			e.move(currMove);
		}
            
	
}