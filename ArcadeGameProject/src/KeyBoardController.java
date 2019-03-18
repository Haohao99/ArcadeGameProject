import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardController implements KeyListener {

	private Environment environment = null;
	private Hero player=null;

	public KeyBoardController(Environment e,Hero p) {
		this.environment = e;
		this.player=p;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		this.player.isMoving=true;
		this.player.isAttacking=false;
		
		if (arg0.getKeyCode() == KeyEvent.VK_A) {
			this.player.isMoving=false;
			this.player.isAttacking=true;

		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			this.player.up();
			this.environment.updateSoils();	
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			this.player.down();
			this.environment.updateSoils();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			this.player.left();
			this.environment.updateSoils();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.player.right();
			this.environment.updateSoils();
		}
		if(arg0.getKeyCode() == KeyEvent.VK_SPACE){
			if(this.environment.isPause){
				this.environment.isPause = false;
			}
			else{
				this.environment.isPause = true;
			}
		}
		
		}


	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			this.player.isMoving=false;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			this.player.isMoving=false;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			this.player.isMoving=false;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.player.isMoving=false;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_A) {
			this.player.isAttacking=false;
		}
		this.environment.player.isMoving=false;
		if (arg0.getKeyCode() == KeyEvent.VK_U) {
			this.environment.setLevelUp();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_D) {
			this.environment.setLevelDown();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub.
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_2:
			System.out.println("COOL3");
		}

	}

}