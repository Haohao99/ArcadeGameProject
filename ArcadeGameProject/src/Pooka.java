import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Pooka extends Monster{
	private Color color = Color.BLACK;

	public Pooka(int x, int y, ArrayList<DirtObject> soillist, ArrayList<DirtObject> stonelist, Hero hero
			,ArrayList<DirtObject> soilRemoveList) {
		super(x,y, soillist, stonelist, hero,soilRemoveList);
		// TODO Auto-generated constructor stub.
	}

	@Override
	public void Attack() {
		//Do nothing
		
	}

	@Override
	public void drawFire(Graphics2D g2) {
		//Do nothing
		
	}

	
}