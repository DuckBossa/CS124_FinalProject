//Item interface

public interface Item{
	public boolean isWeapon ();
	public boolean isArmor ();
	public boolean isAccessory ();
	public void use (Character c);
	public void remove (Character c);
	public void activate (Character c);
}
