package io.github.game;
import io.github.engine.Display;
import io.github.engine.Physics;
import io.github.engine.UpdateManager;

import java.awt.*;

public class Main {
	public static void main(String[] args) {
		Display.setup("game",800,600);
		Display.start();
		Player player = new Player(10,10,10,10,"player");
		player.setBackground(Color.BLACK);
		Display.addToBuffer(player);
		UpdateManager.startClock();

		/*para comprobar que una entidad está colisionando, se usa el "Y" maximo y el "Y" minimo, si
		el y maximo menor que el y maximo del objeto que esta debajo, entonces se estan intersecctando.
		el sistema debe calcular si en el siguiente paso en la caida por gravedad de la entidad,
		ocurre una interseccion
		y si ocurre una interseccion, entonces se acorta el ultimo paso hasta que calse
		si el "Y" inferior es igual al al "Y" superior de la superficie, entonces no aplica gravedad en
		la entidad.
		No aplica la colision entre Tiles
		para que ocurra la colision, el sistema comprueba que la entidad implemente Collidable
		Al presionar izquierda o derecha, la velocidad x del jugador es puesta inmediatamente
		a "n" unidades, negativas o positivas respectivamente, al dejar de apretar el boton,
		la velocidad va a 0 en x (alternativamente sigue en n y disminuye en razon n-a, hasta que
		llegue a n-n, es decir 0;
		 */
	}
}
