//Concrete Accessory Item

public class StaminaBelt implements Item {
	public String getName () {
		return "Stamina Belt (Accessory)";
	}
	public boolean isWeapon () {
		return false;
	}
	public boolean isArmor () {
		return false;
	}
	public boolean isAccessory () {
		return true;
	}
	public void use (Character c) {
		c.vx += 2;
		c.vy += 2;
	}
	public void remove (Character c) {
		c.vx -= 2;
		c.vy -= 2;
	}
	public void activate (Character c) {}
}
