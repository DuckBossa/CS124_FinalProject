//Concrete Accessory Item

public class StaminaBelt implements Item {
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
		c.stamina += 10;
	}
	public void remove (Character c) {
		c.stamina -= 10;
	}
	public void activate (Character c) {}
}
