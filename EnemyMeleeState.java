
import java.io.Serializable;
import java.util.ArrayList;

public class EnemyMeleeState implements State, Serializable{
	Enemy e; 
        Player target;
	int currMove;
	public EnemyMeleeState(Enemy e){
		this.e = e;
		currMove = e.random.nextInt()%4;
                target = null;
	}

	public void handle(ArrayList<Player> pl){
            if(target == null){
              for(int i = 0; i < pl.size(); i++){
                  Player temp = pl.get(i);
                  if(see(temp)){
                      target = temp;
                      break;
                  }
              }
            }
            
            
            if(target != null && target.in && target.isAlive()){
                //System.out.println(target.x+ " "+ target.y);
                if(see(target)){
                    if(collide(target)){
                        target.takeDamage(e.atk);
                    }
                    else{
                        double colx = ( (target.x + target.w/2) - (e.x + e.w/2) );
                        double coly = ( (target.y + target.h/2) - (e.y + e.h/2) ); 
                        double mag =  Math.sqrt(colx*colx + coly*coly);
                        e.x += (colx/mag)*3;
                        e.y += (coly/mag)*3;
                        if(colx < 0){
                            if(e.face != Character.Movement.LEFT.getCode()){
                                e.face = Character.Movement.LEFT.getCode();
                                e.seq = 0;
                            }
                            else{
                                if(++e.seq >= 3){
                                    e.seq = 0;
                                }
                            }
                        }
                        else{
                            if(e.face != Character.Movement.RIGHT.getCode()){
                                e.face = Character.Movement.RIGHT.getCode();
				e.seq = 0;
				}
                            else{
				if(++e.seq >= 3){
                                    e.seq = 0;
				}
                            }
                        }
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

	public boolean see(Player p){
		return p.collide(e.fov_rect);
	}

	public boolean collide(Player p){
		return p.collide(e.hitbox);
	}
}
