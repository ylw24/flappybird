package flappybird;

import java.awt.*;
import java.io.*;
import java.util.Scanner;
import hsa_ufa.Console;

public class Settings {

	public static void run(Console c) throws Exception {
		c.clear();
		c.enableMouse();
		c.enableMouseMotion();
		c.enableMouseWheel();

		int mouseClick = 0;

		Buttons b1 = new Buttons(); // clear player 1 high score
		Buttons b2 = new Buttons(); // exit game
		Buttons b3 = new Buttons(); // return to main page
		Buttons b4 = new Buttons(); // clear player 2 high score

		String fileName = "HighScores.txt";
		File file = new File(fileName);
		Scanner read = new Scanner(file);

		// arrays that contain as if the number is emptied
		String[] emptyScore1 = new String[2];
		String[] emptyScore2 = new String[2];
		String[] emptyScoreAll = { "0", "0" };

		emptyScore2[0] = read.nextLine();
		emptyScore2[1] = "0";

		emptyScore1[0] = "0";
		emptyScore1[1] = read.nextLine();

		read.close();

		boolean emptied1 = false;
		boolean emptied2 = false;

		// button 1 position
		b1.x = 50;
		b1.y = 130;
		b1.l = false; // if button is pressed

		// button 2 position
		b2.x = 300;
		b2.y = 130;
		b2.l = false; // if button is pressed

		// button 3 position
		b3.x = 300;
		b3.y = 200;
		b3.l = false; // if button is pressed

		// button 4 position
		b4.x = 50;
		b4.y = 200;
		b4.l = false; // if button is pressed

		// object player 1 - assign attributes
		Player player1 = new Player();
		player1.identity = 1;
		player1.x = 600;
		player1.y = 380;
		player1.vel_y = 10;
		player1.jump = false;
		player1.score = 0;
		int count = 0;

		int x = 0;

		Image flappyBirdo;
		Image background;
		background = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/flappybackground1.png"));
		flappyBirdo = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/flappy2.png"));

		// *************
		// **main page**
		// *************

		// set font cannot be in synchronized (or it crashes)
		// in some cases it's ok (depends)
		c.setFont(new Font("Calibri", Font.BOLD, 19));

		while (true) {

			synchronized (c) {

				c.clear();
				c.drawImage(background, 0, 0);
				c.drawImage(flappyBirdo, player1.x - x, player1.y, 60, 50);
				count++;
				x += 4;

				c.setColor(new Color(255, 247, 0, 255));
				c.fillRoundRect(b1.x, b1.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);
				c.fillRoundRect(b2.x, b2.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);
				c.fillRoundRect(b3.x, b3.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);
				c.fillRoundRect(b4.x, b4.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);

				// hover must stay in synchronized
				Buttons.isHover(b1, c);
				Buttons.isHover(b2, c);
				Buttons.isHover(b3, c);
				Buttons.isHover(b4, c);

				c.setColor(Color.white);
				if (emptied1 == true) {
					c.drawString("Highscore history emptied", 40, 188);
				}
				if (emptied2 == true) {
					c.drawString("Highscore history emptied", 40, 255);
				}

				c.drawString("Clear Highscore:", 50, 115);

				// text in button 1
				c.setColor(new Color(60, 60, 72));
				c.drawString("Player 1", b1.x + 40, b1.y + 25);
				c.drawString("Exit Game", b2.x + 35, b2.y + 25);
				c.drawString("Return to Game", b3.x + 11, b3.y + 25);
				c.drawString("Player 2", b4.x + 40, b4.y + 25);

			}
			Thread.sleep(10);

			mouseClick = c.getMouseClick();

			if (mouseClick != 0) {
				Buttons.isClicked(b1, c);
				Buttons.isClicked(b2, c);
				Buttons.isClicked(b3, c);
				Buttons.isClicked(b4, c);
			}

			// do not call methods from other classes inside synchronized
			if (b1.l == true) {
				Main.writeArrayFile("HighScores.txt", emptyScore1, 2);
				emptied1 = true;
				b1.l = false;
			}

			if (b2.l == true) {
				Buttons.gameOver = true;
				b2.l = false;
				break;
			}

			if (b3.l == true) {
				b3.l = false;
				break;
			}

			if (b4.l == true) {
				Main.writeArrayFile("HighScores.txt", emptyScore2, 2);
				emptied2 = true;
				b4.l = false;
			}

			// use emptied because it never gets "turned off"
			// (never returns to false once true)
			if ((emptied1 == true) && (emptied2 == true)) {
				Main.writeArrayFile("HighScores.txt", emptyScoreAll, 2);
			}

			// ------------ bird animation
			Movements.jumping(player1);
			// every 2x count = vel_y 1x increment
			if (count >= 21) {
				player1.vel_y = 10;
				player1.jump = true;
				count = 0;
			}

			// reset bird animation location
			if (x >= 670) {
				x = 0;
			}

		} // end of main while loop

	} // end of main

}
