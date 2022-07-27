package flappybird;

import java.awt.Color;

import hsa_ufa.Console;

public class Buttons {
	// character positions
	int x;
	int y;
	static int w = 145; // width
	static int h = 40; // height
	static int r = 15; // radius
	boolean l;
	static boolean gameOver = false;

	/**
	 * check if button is being hovered
	 *
	 * @param b the object that is being checked
	 * @param c console to allow mouse checking
	 * @return the object after being checked
	 */
	public static Buttons isHover(Buttons b, Console c) {
		// if mouse in button area
		if (((c.getMouseX() >= b.x) && (c.getMouseX() <= b.x + Buttons.w))
				&& ((c.getMouseY() >= b.y) && (c.getMouseY() <= b.y + Buttons.h))) {
			// make button dark to indicate area
			c.setColor(new Color(255, 153, 0, 255));
			c.fillRoundRect(b.x, b.y, 145, 40, 15, 15);

		}

		return b;
	}

	/**
	 * check if button is clicked
	 *
	 * @param b the object that is being checked
	 * @param c console to allow mouse checking
	 * @return the object after being checked
	 */
	public static Buttons isClicked(Buttons b, Console c) {
		// if button is clicked
		if (((c.getMouseX() >= b.x) && (c.getMouseX() <= b.x + Buttons.w))
				&& ((c.getMouseY() >= b.y) && (c.getMouseY() <= b.y + Buttons.h))) {

			// start looping subpage of the button
			b.l = true;
		}

		return b;
	}
}
