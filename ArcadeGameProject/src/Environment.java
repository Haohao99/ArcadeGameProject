import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Environment extends JComponent {
	private int x, y;
	protected boolean initial = true;
	private boolean isNext;
	protected Integer levelnumber = 0;
	private String levelString = "";
	protected int FRAME_WIDTH = 600;
	protected int FRAME_HIGHT = 600;
	protected Hero player;
	private ArrayList<DirtObject> soilsList = new ArrayList<DirtObject>();
	private ArrayList<DirtObject> stoneList = new ArrayList<DirtObject>();
	protected Stone stoneRemove = null;
	private ArrayList<DirtObject> soilsRemoveList = new ArrayList<DirtObject>();
	protected ArrayList<Monster> monsterList = new ArrayList<Monster>();
	protected ArrayList<Monster> monsterRemoverList = new ArrayList<Monster>();
	protected Graphics2D g2;
	protected int adj = 1;
	private ArrayList<Fruit> fruitlist = new ArrayList<>();
	private KeyBoardController listener;
	private int missingstone;
	private JLabel label;
	private int score;
	private JFrame frame;
	private String heartPhoto = "./picture/heart.png";
	protected boolean isPause = false;
	protected boolean life=true;
	
	public Environment(JFrame frame, JLabel label) {
		this.frame=frame;
		this.score = 0;
		this.label = label;
		this.player = new Hero(this.stoneList, this.monsterList);
		this.listener = new KeyBoardController(this, this.player);
		frame.addKeyListener(this.listener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2 = (Graphics2D) g;
		this.g2 = graphics2;


		if (this.initial) {
			fileReader();
			this.resetWholeGame();
			drawInitialMap(this.levelString, graphics2);
			this.initial = false;
		}
		
		if (this.monsterList.isEmpty()) {
			
			if (this.levelnumber == 3) {
				ImageIcon ii=new ImageIcon("./picture/tenor.gif");
				JLabel label=new JLabel(ii);
				this.label.setLocation(200, 200);
				this.frame.add(label);
				this.label.setText("<html>Score:" + this.score+ "+" +this.player.lifeTimes*500 + ""
						+ "<br/>WINNER WINNER CHICKEN DINNER<html>");
				label.setFont(new Font("Dialog", 1, 45));
				return;
				}else{
					this.setLevelUp();
				}
		}


		if (missingstone == 2) {
			missingstone = 0;
			this.fruitlist.add(new Fruit(player));
		}

		for (DirtObject soilelement : this.soilsList) {
			soilelement.drawOn(graphics2);
		}

		for (Monster monster : this.monsterList) {
			monster.drawOn(graphics2);
		}

		for (DirtObject stone : this.stoneList) {
			stone.drawOn(graphics2);
		}
		for (Fruit fruit : this.fruitlist) {
			fruit.drawOn(graphics2);
		}

		for (int i = 0; i < this.player.lifeTimes; i++) {
			BufferedImage img;
			try {
				img = ImageIO.read(new File(this.heartPhoto));
				g2.drawImage(img, 100 + i * 30, 100, 20, 20, null);
			} catch (IOException e) {
				// nothing
			}
		}
		
		if(this.stoneList.isEmpty()&&this.life){
			this.fruitlist.add(new Fruit(this.player,"a"));
			this.life=false;
		}
		this.player.drawOn(graphics2);

		this.label.setText("Score:" + score);

	}

	public void updateSoils() {
		for (DirtObject soil : this.soilsList) {
			if (soil.underCharacter(this.player.body)) {
				this.soilsRemoveList.add(soil);
			}
		}
		for (DirtObject soil : this.soilsRemoveList) {
			this.soilsList.remove(soil);
		}
	}

	public void updateMonster() {

		for (Monster monster : this.monsterList) {
			if (this.player.killMonster(monster)) {
				this.score = this.score + 100;
				this.monsterRemoverList.add(monster);
			}
			monster.move();
		}
		for (Monster m : this.monsterRemoverList) {
			this.monsterList.remove(m);
		}

	}

	public void resetWholeGame() {
		this.initial = true;
		this.missingstone = 0;
		this.player.reset();
		this.fruitlist.clear();
	}

	public void resetCharacter() {
		this.missingstone = 0;
		this.player.reset();
		this.fruitlist.clear();
		for (Monster m : this.monsterList) {
			m.reset();
		}
	}

	public void updateHero() {
		for (Monster m : this.monsterList) {
			if (m.Hithero()) {
				if (this.player.heroKill()) {
					this.resetCharacter();
					return;
				}
				this.resetCharacter();
			}
		}
	}

	public void fileReader() {
		try {
			String levelfile = "level" + this.levelnumber.toString() + ".txt";
			FileReader file = new FileReader(levelfile);
			Scanner s = new Scanner(file);
			this.levelString = "";
			this.soilsList.clear();
			this.soilsRemoveList.clear();
			this.stoneList.clear();
			this.monsterList.clear();
			this.monsterRemoverList.clear();
			while (s.hasNext()) {
				this.levelString = this.levelString + s.next();
			}
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}

	public void drawInitialMap(String string, Graphics2D graphics2) {
		this.x = 0;
		this.y = 150;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == 'x') {

				for (int j = 0; j < 6; j++) {
					for (int q = 0; q < 6; q++) {
						DirtObject soilelement = new Soil(this.x + q * Soil.WIDTH, this.y + j * Soil.HIGH, true, false);
						soilsList.add(soilelement);
						soilelement.drawOn(graphics2);
					}
				}

			}

			if (string.charAt(i) == 'z') {
				DirtObject stoneelement = new Stone(this.x, this.y, this.soilsList, this.player, monsterList,
						monsterRemoverList, false, true);
				stoneList.add(stoneelement);
				stoneelement.drawOn(graphics2);
			}
			if (string.charAt(i) == 'p') {
				Monster pooka = new Pooka(this.x, this.y, this.soilsList, this.stoneList, this.player,
						this.soilsRemoveList);
				pooka.drawOn(graphics2);
				this.monsterList.add(pooka);
			}
			if (string.charAt(i) == 'f') {
				Monster fygar = new Fygar(this.x, this.y, this.soilsList, this.stoneList, this.player,
						this.soilsRemoveList);
				fygar.drawOn(graphics2);
				this.monsterList.add(fygar);
			}
			this.x = this.x + 30;
			if (this.x == 600) {
				this.x = 0;
				this.y = this.y + 30;
			}
		}
	}

	public void setLevelUp() {
		this.initial = true;
		this.levelnumber++;
		this.initial = true;
		if (this.levelnumber > 3) {
			this.levelnumber = 3;
		}
		repaint();
	}

	public void setLevelDown() {
		this.initial = true;
		this.levelnumber--;
		if (this.levelnumber < 0) {
			this.levelnumber = 0;
		}
		repaint();
	}

	public void updateStone() {
		for (DirtObject stone : this.stoneList) {
			if (((Stone) stone).killhero()) {
				this.resetCharacter();
			}
			if (((Stone) stone).killMonster() && ((Stone) stone).killable) {
				this.score = this.score + 100;
			}

			if (((Stone) stone).drop()) {
				this.stoneRemove = (Stone) stone;
			}
		}

		if (this.stoneRemove != null) {
			if (!((Stone) this.stoneRemove).isStoneMoving || ((Stone) this.stoneRemove).killhero()) {
				this.stoneList.remove(this.stoneRemove);
				this.missingstone++;
				this.adj++;
				this.stoneRemove = null;
			}
		}

	}

	public void updateFruit() {
		List<Fruit> fruitremove = new ArrayList<>();
		for (Fruit fruit : this.fruitlist) {
			if (fruit.hitHero()) {
				if(fruit.islife){
					this.player.lifeTimes++;
					fruitremove.add(fruit);
					return;
				}
				else{
					this.score = this.score + 100;
				}
				fruitremove.add(fruit);
			}
		}
		for (Fruit fruit : fruitremove) {
			this.fruitlist.remove(fruit);
		}
		repaint();
	}
}
