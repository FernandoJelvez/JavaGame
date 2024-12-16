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
				controllable.leftButton();
				break;
			case 38:
				controllable.upButton();
				break;
			case 39:
				controllable.rightButton();
				break;
			case 40:
				controllable.downButton();
				break;
			case 88:
				controllable.primaryButton();
				break;
			case 90:
				controllable.secondaryButton();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
