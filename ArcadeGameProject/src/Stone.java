import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Stone extends DirtObject{
	protected static int WIDTH = 30;
	protected static int HIGH = 30;
	protected int oX;
	protected int oY;
	protected int x;
	protected int y;
	protected boolean isStone;
	private String fileName;
	protected Rectangle2D.Double body;
	private  ArrayList<DirtObject> soilsList;
	protected boolean life=true;
	private Hero player;
	private ArrayList<Monster> monsterList;
	private ArrayList<Monster> monsterRemoverList;
	protected boolean isStoneMoving;
	protected boolean killable=false;
	private int deLay=0;
	
	public Stone(int x, int y, ArrayList<DirtObject> soilsList, Hero player, ArrayList<Monster> monsterList, ArrayList<Monster> monsterRemoverList, boolean isSoil,boolean isStone) {
		super(x,y,isSoil,isStone);
		this.soilsList = soilsList;
		this.x = x;
		this.oY=y;
		this.y = y;
		this.isStone = isStone;
		this.fileName = "./picture/stone.PNG";
		this.body=new Rectangle2D.Double(x, y, 30, 30);
		this.player = player;
		this.monsterList = monsterList;
		this.monsterRemoverList = monsterRemoverList;
		
	}
	
	public boolean haveMoved(){
		if(this.y!=this.oY){
			return true;
		}
		return false;
	}
	
	@Override
	public void drawOn(Graphics2D g2) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File(fileName));
			g2.drawImage(img, this.x, this.y, 30, 30, null);
		} catch (IOException e) {
			//nothing
		}	
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
	
	public void setLocation(int x,int y){
		this.x=x;
		this.y=y;
		this.body.setRect(this.x, this.y, WIDTH, HIGH);
	}
	
	public boolean Hitdirt(){
		for(DirtObject soil:this.soilsList){
			this.body.setRect(this.x, this.y+1, WIDTH, HIGH);
			if(soil.underCharacter(this.body)){
				return true;
			}
		}
		if(this.y==150||this.y==600||this.x==0||this.x==570){
			return true;
		}
		return false;
	}
	
	public boolean drop() {
		if(Hitdirt()){
			this.isStoneMoving=false;
			return false;
		}
		this.deLay++;
		if(this.deLay>25){
		this.isStoneMoving=true;
		this.y = this.y + 3;
		this.body.setRect(this.x, this.y, WIDTH, HIGH);
		this.killable=true;
		return true;
		}
		this.isStoneMoving=true;
		return true;

	}
	
	public boolean killhero(){
		if (this.body.intersects(this.player.body) && this.killable==true){
			this.player.heroKill();
			return true;
		}
		return false;
	}
	
	public boolean killMonster(){
		for (Monster monster:this.monsterList){
			if (this.killable==true&&this.body.intersects(monster.body)){
				this.monsterRemoverList.add(monster);
				return true;
			}
		}
		return false;
	}
	
}
