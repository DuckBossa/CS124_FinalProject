import java.awt.geom.Rectangle2D;
public interface CloneEnemy extends Cloneable{
	public Enemy makeCopy();
	public Rectangle2D makeCopyRect(Rectangle2D copy);
}