//concrete Armor Item

public class Dress implements Item {
	public boolean isWeapon () {
		return false;
	}
	public boolean isArmor () {
		return true;
	}
	public boolean isAccessory () {
		return false;
	}
	public void use (Character c) {
		c.def += 10;
	}
	public void remove (Character c) {
		c.def -= 10;
	}
	public void activate (Character c) {}
}
