import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public abstract class DirtObject {
	protected int x;
	protected int y;
	protected boolean isSoil;
	protected boolean isStone;
	
	public DirtObject(int x, int y, boolean isSoil, boolean isStone) {
		this.x = x;
		this.y = y;
		this.isSoil = isSoil;
		this.isStone = isStone;
	}

	public abstract boolean underCharacter(Rectangle2D.Double box);
	
	public abstract void drawOn(Graphics2D g2);

	public abstract Double getbody();

	public abstract void setLocation(int x2, int i);

}
