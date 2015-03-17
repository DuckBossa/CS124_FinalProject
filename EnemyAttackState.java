public class EnemyAttackState implements State{
	Enemy e;
	Player p;
	public EnemyAttackState(Enemy e, Player p){
		this.e = e;
		this.p = p;
	}

	public void handle(){
		if(see()){
			if(collide()){
				p.takeDamage(e.atk);
			}
			else{
				double colx = ( (p.x + p.w/2) - (e.x + e.w/2) );
				double coly = ( (p.y + p.h/2) - (e.y + e.h/2) ); 
				double mag =  Math.sqrt(colx*colx + coly*coly);
				e.x += (colx/mag)*3;
				e.y += (coly/mag)*3;
				if( colx < 0 ){
					if(e.face != Character.Movement.LEFT.getCode()){
						e.face = 1;
						e.seq = 0;
					}
					else{
						e.seq++;
						if(e.seq >= 3){
							e.seq = 0;
						}
					}
				}
				else{
					if(e.face != Character.Movement.RIGHT.getCode()){
						e.face = 3;
						e.seq = 0;
					}
					else{
						e.seq++;
						if(e.seq >= 3){
							e.seq = 0;
						}
					}
				}
			}
		}
		else{
			e.setState(new EnemyPatrolState(e));
			e.enemyDetect = false;
		}
	}

	public boolean see(){
		return p.collide(e.fov_rect);
	}

	public boolean collide(){
		return p.collide(e.hitbox);
	}

}