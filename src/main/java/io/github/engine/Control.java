package io.github.engine;

import javax.swing.*;
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
			case KeyEvent.VK_Q:
				if(LevelMaker2.isStarter && LevelMaker2.idBloque > 2){
					LevelMaker2.idBloque--;
				}
				break;
			case KeyEvent.VK_E:
				if (LevelMaker2.isStarter && LevelMaker2.idBloque < 100){
					LevelMaker2.idBloque++;
				}
				break;
			case KeyEvent.VK_R:
				if (LevelMaker2.isStarter){
					Mundo.cambioDePantalla(KeyEvent.VK_R);
					System.out.println("IZQUIERDA");
				}
				break;
			case KeyEvent.VK_T:
				if (LevelMaker2.isStarter){
					Mundo.cambioDePantalla(KeyEvent.VK_T);
					System.out.println("DERECHA");
				}
				break;
			case KeyEvent.VK_Y:
				if (LevelMaker2.isStarter){
                    try {
						//BLANCO = NO SOLIDO, ROJO = SOLIDO
						if (Mundo.isSolid){
							Mundo.isSolid = false;
							LevelMaker2.cursor.setTexture(new Texture(Mundo.asignarTexturaAlBloque(0)));
							//AHORA NO ES SOLIDO
						} else {
							Mundo.isSolid = true;
							LevelMaker2.cursor.setTexture(new Texture(Mundo.asignarTexturaAlBloque(1)));
							//AHORA ES SOLIDO
						}
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
				break;
			case KeyEvent.VK_U:
				if (LevelMaker2.isStarter){
					if (Mundo.layer < 2){
						Mundo.layer++;
						LevelMaker2.label.setText("L=" + Mundo.layer);
					} else {
						Mundo.layer = 0;
						LevelMaker2.label.setText("L=" + 0);
					}
				}
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
