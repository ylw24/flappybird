package flappybird;

import java.awt.*;
import java.io.*;
import hsa_ufa.Console;

public class SinglePlayer {

	// throws exception include both interrupt, IO, and other exceptions
	// need for thread.sleep and scannner
	public static void run() throws Exception {
		// resolved: console MUST BE IN METHOD to enable repeat
		Console c = new Console(500, 500, "Flappy Bird - SINGLE PLAYER MODE");

		// choice to store highscore
		int player = 0;

		// object player 1 - assign attributes
		Player player1 = new Player();
		player1.identity = 1;
		player1.x = 70;
		player1.y = 220;
		player1.vel_y = 10;
		player1.jump = false;
		player1.score = 0;

		// object pipe 1
		Pipe pipe1 = new Pipe();
		pipe1.x = 440;
		// -780 ~ -500 is a fair space
		pipe1.y = -700;
		pipe1.identity = 1;
		int round = 1;

		// called it birdo cuz it sounds better :)
		Image flappyBirdo;
		Image background;

		Image pipeUp;
		Image pipeDown;

		flappyBirdo = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/flappy.png"));
		background = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/flappybackground1.png"));
		pipeUp = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/singlepipe.png"));
		pipeDown = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/singlepiped.png"));

		c.setColor(new Color(230, 255, 255));
		c.setFont(new Font("Impact", Font.BOLD, 20));

		c.drawImage(background, 0, 0);
		c.drawString("HOW TO PLAY + NOTES", 150, 45); // Title

		// player 1 control
		c.setFont(new Font("Impact", Font.BOLD, 20));
		c.drawString("To jump: spacebar or \"0\" on number pad", 10, 100);
		c.setFont(new Font("Impact", Font.PLAIN, 15));
		c.drawString("Hold down you key to continue flying up", 10, 150);
		c.drawString("Speed will increase every 5 rounds until it reaches maximum speed", 10, 200);
		c.drawString("Enter player # '1' or '2' to begin playing", 10, 255);
		c.setColor(Color.white);
		c.setBackgroundColor(new Color(45, 45, 45, 0));
		c.setCursor(14, 40);
		// wait for user input
		c.setFont(new Font("Calibri", Font.ITALIC, 15));
		while (true) {

			// use keyboard input and not buttons in case wish to add more player options
			// to avoid a senario of crowded buttons
			if ((player == 1) || (player == 2)) {
				break;
			} else {
				c.setCursor(14, 40);
				c.setColor(Color.white);
				player = c.readInt();
				// white out error
				c.setColor(new Color(0, 153, 204, 255));
				c.fillRect(270, 240, 2000, 25);
				// ask for correct input
				c.setColor(Color.white);
				c.drawString("Please enter a valid number (1 or 2)", 10, 270);
			}
		}

		String[] highscores = new String[2];
		String fileName = "HighScores.txt";
		try {
			highscores = Main.readFile(fileName, 2);
		} catch (IOException e) {
			System.out.println("Failed to read new highscore.");
		}

		player1.jump = true;

		while (true) {
			synchronized (c) {
				c.clear();
				// background image
				c.drawImage(background, 0, 0);

				// drawing flappy 1 - image, x, y, width, height
				c.drawImage(flappyBirdo, player1.x, player1.y, 60, 50);

				// ---pipes - share same y coord for game fairness
				// player 1's pipes
				c.drawImage(pipeDown, pipe1.x, pipe1.y);
				c.drawImage(pipeUp, pipe1.x, pipe1.y + 990);

				// display scores
				c.setColor(new Color(230, 255, 255));
				c.setFont(new Font("Impact", Font.PLAIN, 20));
				c.drawString("Score: " + player1.score, 10, 490);
				// highscore
				if (player == 1) {
					c.drawString("Highscore: " + highscores[0], 10, 470);
				} else if (player == 2) {
					c.drawString("Highscore: " + highscores[1], 10, 470);
				}

				c.drawString("Round #" + round, 400, 470);

				if (player1.collide == true) {
					c.setColor(new Color(105, 105, 105, 128));
					c.fillRect(0, 0, 500, 500);
				}

				if ((round == 5) || (round == 10)) {
					c.setColor(Color.white);
					c.drawString("Level Up!", 250, 245);
				}

			}
			Thread.sleep(30);

			if ((player1.collide == true)) {

				c.setColor(new Color(230, 255, 255));
				// display ending

				c.setFont(new Font("Impact", Font.PLAIN, 50));
				c.drawString("The END", 100, 100); // Title

				c.setFont(new Font("Impact", Font.PLAIN, 30));
				c.drawString("Score: " + player1.score, 100, 250);
				// highscore
				if (player == 1) {
					c.drawString("Highscore: " + highscores[0], 100, 290);
				} else if (player == 2) {
					c.drawString("Highscore: " + highscores[1], 100, 290);
				}

				c.setFont(new Font("Impact", Font.PLAIN, 20));
				// store as new highscore if score is higher than previous
				// and display to winning player

				if (player == 1) {
					if (player1.score > Integer.parseInt(highscores[0])) {
						highscores[0] = String.valueOf(player1.score);
						c.drawString("Your score is now the new highscore!!", 50, 360);
					}
				} else if (player == 2) {
					if (player1.score > Integer.parseInt(highscores[1])) {
						highscores[1] = String.valueOf(player1.score);
						c.drawString("Your score is now the new highscore!!", 50, 360);
					}
				}

				try {
					Main.writeArrayFile(fileName, highscores, 2);
				} catch (IOException e) {
					System.out.println("Failed to record highscore for Single Player Mode.");
				}
				Thread.sleep(3000);
				c.clear();
				// end of game
				break;
			}

			// **********--player movement----
			if ((c.getKeyChar() == ' ') || (c.isKeyDown(Console.VK_NUMPAD0))) {
				player1.vel_y = 10;
				player1.jump = true;
			}

			// does not let user make movements if he already lost
			if (player1.collide == false) {

				// user keyboard movement control
				Movements.jumping(player1);
				Movements.boundary(player1);

				// check if fell
				if (Movements.deathGround(player1) == true) {
					// end game for player
					player1.collide = true;
					pipe1.x = -1000;
				}

				// ***********---pipe movement-----

				// move pipe if player is still alive
				// and check collision

				// level of difficulty: increase with round
				if (round < 5) {
					pipe1.x -= 8;
				} else if (round < 10) {
					pipe1.x -= 10;
				} else {
					pipe1.x -= 12;
				}

				Movements.collision(player1, pipe1);
			}

			// check pipe at end: if yes, find new random location
			if (Movements.outOfConsole(pipe1) == true) {
				// reset all if both are alive
				if (player1.collide == false) {
					Movements.newLocationSingle(pipe1);

					// add score since pipe is "passed"
					player1.score++;
					round++;
				}
			}

		} // end of while loop

		c.close();
	} // end of main

}
