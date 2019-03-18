import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Weapon {
	private Hero hero;
	private ArrayList<Monster> monsters;
	protected Rectangle2D.Double body;
	private int x, y;

	public Weapon(Hero hero, ArrayList<Monster> monsters) {
		this.hero = hero;
		this.monsters = monsters;
		this.body = new Rectangle2D.Double(0, 0, 30, 30);
	}

	public void drawOn(Graphics2D g2) {

		String fileName = "./picture/Monster_fire_"+this.hero.direction+".png";
		BufferedImage img;
		try {
			img = ImageIO.read(new File(fileName));
			g2.drawImage(img, this.x, this.y, 30, 30, null);
		} catch (IOException e) {
			// nothing
		}
	}
	
	public void weaponAttacking(){

		if (this.hero.direction.equals("U")) {
			this.x = this.hero.getHeroLocationX();
			this.y = this.hero.getHeroLocationY() - 30;
		}
		if (this.hero.direction.equals("D")) {
			this.x = this.hero.getHeroLocationX();
			this.y = this.hero.getHeroLocationY() + 30;
		}
		if (this.hero.direction.equals("L")) {
			this.x = this.hero.getHeroLocationX() - 30;
			this.y = this.hero.getHeroLocationY();
		}
		if (this.hero.direction.equals("R")) {
			this.x = this.hero.getHeroLocationX() + 30;
			this.y = this.hero.getHeroLocationY();
		}
		this.body.setRect(this.x, this.y, 30, 30);
	}

	public void weaponRemover() {
		this.body.setRect(0, 0, 0, 0);
	}

}
