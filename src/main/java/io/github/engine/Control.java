package io.github.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Control implements KeyListener{
	Controllable controllable;
	public Control(Controllable controllable){
		this.controllable=controllable;
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode){
			case 37:
				controllable.press(ButtonNames.LEFT);
				break;
			case 38:
				controllable.press(ButtonNames.UP);
				break;
			case 39:
				controllable.press(ButtonNames.RIGHT);
				break;
			case 40:
				controllable.press(ButtonNames.DOWN);
				break;
			case 88:
				controllable.press(ButtonNames.PRIMARY);
				break;
			case 90:
				controllable.press(ButtonNames.SECONDARY);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
			case 37:
				controllable.release(ButtonNames.LEFT);
				break;
			case 38:
				controllable.release(ButtonNames.UP);
				break;
			case 39:
				controllable.release(ButtonNames.RIGHT);
				break;
			case 40:
				controllable.release(ButtonNames.DOWN);
				break;
			case 88:
				controllable.release(ButtonNames.PRIMARY);
				break;
			case 90:
				controllable.release(ButtonNames.SECONDARY);
				break;
		}
	}
	protected Controllable getControllable() {
		return controllable;
	}
}
