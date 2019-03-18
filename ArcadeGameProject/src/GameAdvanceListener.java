import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAdvanceListener implements ActionListener {

	private Environment gameComponent;
	private int delay=0;

	public GameAdvanceListener(Environment gameComponent) {
		this.gameComponent = gameComponent;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
	}

	public void advanceOneTick() {
		if(!this.gameComponent.isPause){
			this.gameComponent.updateMonster();
			this.gameComponent.updateStone();
			this.gameComponent.updateHero();
			this.gameComponent.updateFruit();
			this.gameComponent.repaint();
		}
		return;
	}
}
