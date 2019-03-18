import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Fruit {
	
	private int x = 260;
	private int y = 230;
	public Rectangle2D.Double fruitbody;
	private static int WIDTH = 30;
	private static int HEIGHT = 30;
	private Hero player;
	protected boolean islife;
	private String fileName = "./picture/fruit.png";
	
	public Fruit(Hero player){
		this.fruitbody = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.player = player;
		this.islife = false;
	}
	
	public Fruit(Hero player,String a){
		this.islife = true;
		Random rand=new Random();
		this.x = rand.nextInt(570);
		this.y = rand.nextInt(550) + 150;
		this.fruitbody = new Rectangle2D.Double(this.x, this.y, WIDTH, HEIGHT);
		this.fileName = "./picture/heart.png";
		this.player = player;
	}
	
	public boolean hitHero(){
		if(this.fruitbody.intersects(this.player.body)){
			return true;
		}
		return false;
	}
	
	public void drawOn(Graphics2D g2){
		BufferedImage img;
		try {
			img = ImageIO.read(new File(fileName));
			g2.drawImage(img, this.x, this.y, WIDTH, HEIGHT, null);
		} catch (IOException e) {}
	}
	
	
	

}
