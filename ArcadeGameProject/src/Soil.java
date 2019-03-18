import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class Soil extends DirtObject{
	protected static int WIDTH = 5;
	protected static int HIGH = 5;
	protected Color color;
	private Color AIRCOLOR = new Color(255, 255, 255);
	private Color SOILCOLOR =new Color(102, 51, 0);
	protected Rectangle2D.Double body;
	
	
	
	public Soil(int x, int y, boolean isSoil,boolean isStone){
		super(x,y,isSoil,isStone);
		this.body = new Rectangle2D.Double(x, y, WIDTH, HIGH);
		if (this.isSoil) {
			this.color = SOILCOLOR;
		} else {
			this.color = AIRCOLOR;
		}
	}
	
	public void drawOn(Graphics2D g2) {
//		String fileName = "C:\\EclipseWorkspaces\\csse220\\ArcadeGameProject\\picture\\dirt.png";
//		BufferedImage img;
//		try {
//			img = ImageIO.read(new File(fileName));
//			g2.drawImage(img, this.x, this.y, 5, 5, null);
//		} catch (IOException e) {}
		g2.setColor(this.color);
		g2.fill(this.body);
		g2.draw(this.body);
//		if(this.isSoil){
//			String fileName = "C:\\EclipseWorkspaces\\csse220\\ArcadeGameProject\\picture\\soil.jpg";
//			BufferedImage img;
//			try {			
//				img = ImageIO.read(new File(fileName));
//				g2.drawImage(img, this.x, this.y, 30, 30, null);
//			} catch (IOException e) {
//				//nothing
//			}
//		}else{
//			System.out.println("hi2");
//			g2.setColor(AIRCOLOR);
//			g2.fill(this.soil);
//			g2.draw(this.soil);
//		}
//		}
	}

	@Override
	public boolean underCharacter(Double box) {
		return box.intersects(this.x, this.y, WIDTH, HIGH);
	}

	@Override
	public Double getbody() {
		// TODO Auto-generated method stub.
		return this.body;
	}

	@Override
	public void setLocation(int x2, int i) {
		// TODO Auto-generated method stub.
		
	}
}
