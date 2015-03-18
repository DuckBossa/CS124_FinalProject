public class UseItem implements Command{
	Player p;

	public UseItem(Player p){
		this.p = p;
	}

	public void execute(){
		p.itemActivate();
	}

}