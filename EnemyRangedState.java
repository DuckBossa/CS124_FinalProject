import java.io.Serializable;
import java.util.ArrayList;
public class EnemyRangedState implements State, Serializable{
	ArrayList<Arrow> arrow;
	Enemy e;
        Player target;
	public int recharge;
	public int currMove;
	public EnemyRangedState(Enemy e, ArrayList<Arrow> arrow){
		this.e = e;
		this.arrow = arrow;
		recharge = 0;
                target = null;
		currMove = Math.abs(e.random.nextInt()%4);
	}

	public boolean see(Player p){
		return p.collide(e.fov_rect);
	}


	public void handle(ArrayList<Player> pl){
            if(target == null){
                for(int i = 0; i < pl.size();i++){
                    Player temp = pl.get(i);
                    if(see(temp)){
                        target = temp;
                        break;
                    }
                }
            }
            
            if(target != null && target.in &&target.isAlive()){
                if(see(target)){
                    if(recharge <= 0){
                        recharge = 40;
			double colx = ( (target.x + target.w/2) - (e.x + e.w/2) );
			double coly = ( (target.y + target.h/2) - (e.y + e.h/2) ); 
			double mag =  Math.sqrt(colx*colx + coly*coly);
                        arrow.add(new Arrow(e.x,e.y,colx/mag*3,coly/mag*3,5,GameWindow.ARROW_W,GameWindow.ARROW_H));
                    }
                    else{
                        recharge--;
                    }
                }
                else{
                    target = null;
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
