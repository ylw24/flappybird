package flappybird;

import java.awt.*;
import java.io.*;
import hsa_ufa.Console;

public class TwoPlayer {

	// throws exception include both interrupt, IO, and other exceptions
	// need for thread.sleep and scannner
	public static void run() throws Exception {
		// resolved: console MUST BE IN METHOD to enable repeat
		Console c = new Console(1000, 500, "Flappy Bird - TWO PLAYER MODE");

		String[] highscores = new String[2];
		String fileName = "HighScores.txt";
		try {
			highscores = Main.readFile(fileName, 2);
		} catch (IOException e) {
			System.out.println("Failed to read new highscore.");
		}

		// object player 1 - assign attributes
		Player player1 = new Player();
		player1.identity = 1;
		player1.x = 70;
		player1.y = 220;
		player1.vel_y = 10;
		player1.jump = false;
		player1.score = 0;

		// object player 2 - assign attributes
		Player player2 = new Player();
		player2.identity = 2;
		player2.x = 870;
		player2.y = 220;
		player2.vel_y = 10;
		player2.jump = false;
		player2.score = 0;

		// object pipe 1
		Pipe pipe1 = new Pipe();
		pipe1.x = 440;
		// -780 ~ -500 is a fair space
		pipe1.y = -700;
		pipe1.identity = 1;

		// object pipe 2
		Pipe pipe2 = new Pipe();
		pipe2.x = 440;
		pipe2.y = -700;
		pipe2.identity = 2;

		int round = 1;

		// called it birdo cuz it sounds better :)
		Image flappyBirdo;
		Image flappyBirdo2;
		Image background;

		Image pipeUp;
		Image pipeDown;

		flappyBirdo = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/flappy.png"));
		flappyBirdo2 = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/flappy2.png"));
		background = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/flappybackground1.png"));
		pipeUp = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/singlepipe.png"));
		pipeDown = Toolkit.getDefaultToolkit()
				.getImage(c.getClass().getClassLoader().getResource("images/singlepiped.png"));

		c.setColor(new Color(230, 255, 255));
		c.setFont(new Font("Impact", Font.BOLD, 20));

		c.drawImage(background, 0, 0);
		c.drawString("HOW TO PLAY + NOTES", 200, 45); // Title

		// player 1 control
		c.setFont(new Font("Impact", Font.BOLD, 20));
		c.drawString("To jump", 10, 150);
		c.setFont(new Font("Impact", Font.PLAIN, 20));
		c.drawString("Player 1: spacebar", 10, 200);
		c.drawString("Player 2: \"0\" on number pad", 10, 250);

		// player 2 control
		c.drawString("Hold down you key to continue flying up", 400, 150);
		c.drawString("Speed will increase every 5 rounds until it reaches maximum speed", 400, 200);
		c.drawString("Press \"enter\" twice to begin playing", 400, 250);
		c.setCursor(-100, -100);
		c.readLine();

		player1.jump = true;
		player2.jump = true;

		while (true) {
			synchronized (c) {
				c.clear();

				// background image
				c.drawImage(background, 0, 0);

				// drawing flappy 1 - image, x, y, width, height
				c.drawImage(flappyBirdo, player1.x, player1.y, 60, 50);
				c.drawImage(flappyBirdo2, player2.x, player2.y, 60, 50);

				// ---pipes - share same y coord for game fairness
				// player 1's pipes
				c.drawImage(pipeDown, pipe1.x, pipe1.y);
				c.drawImage(pipeUp, pipe1.x, pipe1.y + 990);

				// player 2's pipes
				c.drawImage(pipeDown, pipe2.x, pipe2.y);
				c.drawImage(pipeUp, pipe2.x, pipe2.y + 990);

				// display scores
				c.setColor(new Color(230, 255, 255));

				c.drawString("Score: " + player1.score, 10, 490);
				c.drawString("Score: " + player2.score, 920, 490);
				// highscore
				c.drawString("Highscore: " + highscores[0], 10, 470);
				c.drawString("Highscore: " + highscores[1], 890, 470);

				c.drawString("Round #" + round, 450, 470);

				if (player1.collide == true) {
					c.setColor(new Color(105, 105, 105, 128));
					c.fillRect(0, 0, 500, 500);
				}

				if (player2.collide == true) {
					c.setColor(new Color(105, 105, 105, 128));
					c.fillRect(500, 0, 500, 500);
				}

				if ((round == 5) || (round == 10)) {
					c.setColor(Color.white);
					c.drawString("Level Up!", 450, 245);
				}

			}
			Thread.sleep(30);

			if ((player1.collide == true) && (player2.collide == true)) {
				c.setColor(new Color(230, 255, 255));
				// display ending

				c.setFont(new Font("Impact", Font.PLAIN, 50));
				c.drawString("The END", 420, 100); // Title

				c.setFont(new Font("Impact", Font.ITALIC, 40));
				c.drawString("Player 1", 100, 200);
				c.setFont(new Font("Impact", Font.PLAIN, 30));
				c.drawString("Score: " + player1.score, 100, 250);
				c.drawString("Highscore: " + highscores[0], 100, 290);

				c.setFont(new Font("Impact", Font.ITALIC, 40));
				c.drawString("Player 2", 740, 200);
				c.setFont(new Font("Impact", Font.PLAIN, 30));
				c.drawString("Score: " + player2.score, 740, 250);
				c.drawString("Highscore: " + highscores[1], 740, 290);

				c.setFont(new Font("Impact", Font.PLAIN, 20));
				// store as new highscore if score is higher than previous
				// and display to winning player
				if (player1.score > Integer.parseInt(highscores[0])) {
					highscores[0] = String.valueOf(player1.score);
					c.drawString("Your score is now the new highscore!!", 50, 360);
				}
				if (player2.score > Integer.parseInt(highscores[1])) {
					highscores[1] = String.valueOf(player2.score);
					c.drawString("Your score is now the new highscore!!", 670, 360);
				}

				try {
					Main.writeArrayFile(fileName, highscores, 2);
				} catch (IOException e) {
					System.out.println("Failed to record highscore for Two Player Mode.");
				}
				Thread.sleep(3000);
				c.clear();
				// end of game
				break;
			}

			// **********--player movement----
			if (c.getKeyChar() == ' ') {
				player1.vel_y = 10;
				player1.jump = true;
			}

			if (c.isKeyDown(Console.VK_NUMPAD0)) {
				player2.vel_y = 10;
				player2.jump = true;
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

			if (player2.collide == false) {

				// user keyboard movement control
				Movements.jumping(player2);
				Movements.boundary(player2);

				// check if fell
				if (Movements.deathGround(player2) == true) {
					// end game for player
					// collide can also mean end game in this case
					player2.collide = true;
					pipe2.x = 1000;
				}

				// level of difficulty: increase with round
				if (round < 5) {
					pipe2.x += 8;
				} else if (round < 10) {
					pipe2.x += 10;
				} else {
					pipe2.x += 12;
				}

				Movements.collision(player2, pipe2);
			}

			// check pipe at end: if yes, find new random location
			if ((Movements.outOfConsole(pipe1) == true) && (Movements.outOfConsole(pipe2) == true)) {
				// reset all if both are alive
				if ((player1.collide == false) && (player2.collide == false)) {
					Movements.newLocation(pipe1);
					pipe2.x = pipe1.x;
					pipe2.y = pipe1.y;
					// add score since pipe is "passed"
					player1.score++;
					player2.score++;
					// if only player 1 is alive
				} else if (player1.collide == false) {
					Movements.newLocation(pipe1);
					player1.score++;
					// if only player 2 is alive
				} else if (player2.collide == false) {
					Movements.newLocation(pipe2);
					player2.score++;
				}
				round++;
			}

		} // end of while loop

		c.close();
	} // end of main

}
