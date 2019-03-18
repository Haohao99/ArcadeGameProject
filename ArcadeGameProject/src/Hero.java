import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Hero {
	protected int x = 260;
	protected int y = 260;
	private int num = 1;
	private static int WIDTH = 30;
	private static int HEIGHT = 30;
	protected Rectangle2D.Double body;
	private Color color = Color.BLACK;
	private int speed = 2;
	private ImageIcon picture;
	protected boolean isMoving;
	protected String direction = "R";
	private boolean isHit = false;
	protected int lifeTimes = 4;
	private ArrayList<DirtObject> stoneList;
	private ArrayList<Monster> MonsterList;
	private Weapon w = new Weapon(this, this.MonsterList);
	protected boolean isAttacking = false;

	public Hero(ArrayList<DirtObject> s, ArrayList<Monster> m) {
		this.body = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.isMoving = false;
		this.stoneList = s;
		this.MonsterList = m;
	}

	public Rectangle2D.Double getHeroBody() {
		return this.body;
	}

	public boolean isHit() {
		if (this.y < 150 || this.y > 600 || this.x < 0 || this.x > 570) {
			this.isMoving = false;
			return true;
		}

		for (DirtObject stone : this.stoneList) {
			if (stone.underCharacter(this.body)) {
				this.isMoving = false;
				return true;
			}
		}
		this.isMoving = true;
		return false;
	}

	public void drawOn(Graphics2D g2) {
		this.w.weaponRemover();
		if (this.isAttacking) {
			this.w.weaponAttacking();
			this.w.drawOn(g2);
		}
		String fileName = "";
		if (this.isMoving == false) {
			fileName = "./picture/hero_still_"+this.direction+ ".jpg";
		} else if (num % 2 == 1) {
			num++;
			fileName = "./picture/hero_running_"+this.direction+ ".jpeg";
		} else if (num % 2 == 0) {
			num++;
			fileName = "./picture/hero_run2_"+this.direction+ ".png";
		}
		BufferedImage img;
		try {
			img = ImageIO.read(new File(fileName));
			g2.drawImage(img, this.x, this.y, 30, 30, null);
		} catch (IOException e) {
			// nothing
		}
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.body.setRect(this.x, this.y, WIDTH, HEIGHT);
	}

	public int getHeroLocationX() {
		return this.x;
	}

	public int getHeroLocationY() {
		return this.y;
	}

	public void up() {
		this.direction = "U";
		this.y = this.y - this.speed;
		this.body.setRect(this.x, this.y, WIDTH, HEIGHT);
		if (isHit()) {
			this.setLocation(this.x, this.y + 3);
		}
	}

	public void down() {

		this.direction = "D";
		this.y = this.y + this.speed;
		this.body.setRect(this.x, this.y, WIDTH, HEIGHT);
		if (isHit()) {
			this.setLocation(this.x, this.y - 3);
		}
	}

	public void left() {

		this.direction = "L";
		this.x = this.x - this.speed;
		this.body.setRect(this.x, this.y, WIDTH, HEIGHT);
		if (isHit()) {
			this.setLocation(this.x + 3, this.y);
		}

	}

	public void right() {

		this.direction = "R";
		this.x = this.x + this.speed;
		this.body.setRect(this.x, this.y, WIDTH, HEIGHT);
		if (isHit()) {
			this.setLocation(this.x - 3, this.y);
		}
	}

	public void reset() {
		this.x = 260;
		this.y = 260;
		this.body.setRect(this.x, this.y, WIDTH, HEIGHT);
	}

	public boolean heroKill() {
		this.lifeTimes--;
		this.reset();
		if (this.lifeTimes == 0) {
			this.lifeTimes = 4;
			return true;
		}
		return false;
	}

	public boolean killMonster(Monster m) {
		if (this.w.body.intersects(m.getHeroBody())) {
			return true;
		}
		return false;
	}

}
