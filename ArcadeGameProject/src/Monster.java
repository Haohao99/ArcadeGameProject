import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class Monster {

	private int x,y,oX,oY;
	private static int WIDTH = 30;
	private static int HEIGHT = 30;
	private static int GHOSTSPEED=2;
	protected Rectangle2D.Double body;
	private Color color = Color.BLACK;
	protected int speed = 2;
	private ArrayList<DirtObject> soilsList = new ArrayList<DirtObject>();
	private ArrayList<DirtObject> herotunnel = new ArrayList<DirtObject>();
	private ArrayList<DirtObject> stoneList = new ArrayList<DirtObject>();
	private Hero hero;
	protected String monsterdirection = "U";
	protected boolean isAttack = false;
	protected int soilLifeUD,soilLifeLR ;

	public Monster(int x, int y, ArrayList<DirtObject> soilList, ArrayList<DirtObject> stoneList, Hero hero,ArrayList<DirtObject> soilRemoveList) {
		Random rand = new Random();
		this.soilLifeLR = rand.nextInt(5);
		this.soilLifeUD = rand.nextInt(5);
		this.soilsList = soilList;
		this.stoneList = stoneList;
		this.herotunnel= soilRemoveList;
		this.oX=x;
		this.oY=y;
		this.x = x;
		this.y = y;
		this.body = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.hero = hero;
	}

	public Rectangle2D.Double getHeroBody() {
		return this.body;
	}

	public void drawOn(Graphics2D g2) {
		if(this.monsterdirection!="g"){
		Random rand = new Random();
		int random = rand.nextInt(10);
		if (random == 0) {
			this.Attack();
		}
		}

		if (isAttack) {
			this.drawFire(g2);
		}

		String fileName = "";
		fileName = "./picture/" + this.getClass().getName() +"_"+this.monsterdirection+ ".png";
		if(this.monsterdirection=="g"){
			
			fileName = "./picture/ghost.png";
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

	public int getLocationX() {
		return this.x;
	}

	public int getLocationY() {
		return this.y;
	}

	public void move() {
		if (this.isAttack) {
			return;
		}
		if (this.soilLifeUD > 0) {
			moveHelper("U");
		} else if(this.soilLifeLR > 0){
			moveHelper("L");
		}
		if(this.soilLifeLR==0 && this.soilLifeUD==0){
			moveHelper("g");
		}
	}

	private void moveHelper(String direction) {
		if (direction == "U" || direction == "D") {
			if (Hitdirt() || Hitstone()) {
				this.soilLifeUD--;
				this.speed = -this.speed;
			}
			if (this.speed > 0) {
				this.monsterdirection = "U";
			} else {
				this.monsterdirection = "D";
			}
			this.y = this.y - this.speed;
		}
		
		if (direction == "L" || direction == "R") {
			if (Hitdirt() || Hitstone()) {
				this.soilLifeLR--;
				this.speed = -this.speed;
			}
			if (this.speed > 0) {
				this.monsterdirection = "R";
			} else {
				this.monsterdirection = "L";
			}
			this.x=this.x+this.speed;
		}
		
		if(direction=="g"){
			this.monsterdirection="g";
			int detX=this.hero.getHeroLocationX()-this.x;
			int detY=this.hero.getHeroLocationY()-this.y;
			if(this.inHeroTunnel()){
				this.monsterdirection="D";
				this.soilLifeLR=2;
				this.soilLifeUD=2;
				return;
			}
			if(detY<2){
				this.y=this.y-GHOSTSPEED;
			}else if(detY>2){
				this.y=this.y+GHOSTSPEED;
			}
			
			if(detY==2){
				if(detX<2){
					this.x=this.x-GHOSTSPEED;
				}else if(detX>2){
					this.x=this.x+GHOSTSPEED;
				}
			}
			
		}
		
		this.body.setRect(this.x, this.y, WIDTH, HEIGHT);
		return;
	}
	
	public boolean inHeroTunnel(){
		for(DirtObject soil:this.herotunnel){
			if(soil.getbody().intersects(this.body)&&!Hitdirt()){
				return true;
			}
		}
		return false;
	}

	public boolean Hitdirt() {
		for (DirtObject soil : this.soilsList) {
			if (soil.underCharacter(this.body)) {
				return true;
			}
		}
		if (this.y == 150 || this.y == 600 || this.x == 0 || this.x == 570) {
			return true;
		}
		return false;
	}

	public boolean Hitstone() {
		for (DirtObject stone : this.stoneList) {
			if (stone.underCharacter(this.body)) {
				return true;
			}
		}
		return false;
	}

	public abstract void Attack();

	public abstract void drawFire(Graphics2D g2);

	public boolean Hithero() {
		if (this.body.intersects(this.hero.body)) {
			return true;
		}
		return false;
	}

	public void reset(){
		this.x=this.oX;
		this.y=this.oY;
		setLocation(this.oX,this.oY);
		this.soilLifeLR=3;
		this.soilLifeLR=3;
	}
}
