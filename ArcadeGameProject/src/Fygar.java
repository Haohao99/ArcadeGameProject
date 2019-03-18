import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;


public class Fygar extends Monster {
	private int x;
	private int y;
	private Rectangle2D.Double fire;
	private Graphics2D g2;
	private Hero hero;
	private int delay=0;

	public Fygar(int x, int y, ArrayList<DirtObject> soillist, ArrayList<DirtObject> stonelist, Hero 
			hero,ArrayList<DirtObject> soilRemoveList) {
		super(x, y, soillist, stonelist, hero,soilRemoveList);
		this.x = x;
		this.y = y;
		this.hero = hero;
	}

	@Override
	public void Attack() {
		
		this.isAttack=true;
		
		if (this.monsterdirection.equals("U")) {
			this.x = this.getLocationX();
			this.y = this.getLocationY() - 30;
		}
		if (this.monsterdirection.equals("D")) {
			this.x = this.getLocationX();
			this.y = this.getLocationY() + 30;
		}
		if (this.monsterdirection.equals("L")) {
			this.x = this.getLocationX() - 30;
			this.y = this.getLocationY();
		}
		if (this.monsterdirection.equals("R")) {
			this.x = this.getLocationX() + 30;
			this.y = this.getLocationY();
		}
		this.fire = new Rectangle2D.Double(this.x, this.y, 30, 30);
		if (this.fire.intersects(this.hero.body)) {
			this.hero.reset();
			this.hero.heroKill();
		}
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				isAttack=false;
			}
		}, 1000);
		this.fire.setRect(1000, 1000, 30, 30);
	}

	@Override
	public void drawFire(Graphics2D g2) {
		String fileName = "./picture/Monster_fire_"+this.monsterdirection+".png";
		BufferedImage img;
		try {
			img = ImageIO.read(new File(fileName));
			g2.drawImage(img, this.x, this.y, 30, 30, null);
		} catch (IOException e) {
			// nothing
		}
	}
}
