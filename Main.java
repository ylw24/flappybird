/*Linda Wang
 * March 31, 2021
 * Flappy bird
 * try to last the longest and beat your previous highscore
 */
package flappybird;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

import hsa_ufa.Console;

public class Main {
	static Console c = new Console(500, 500, "Flappy Bird - HOME");

	public static void main(String[] args) throws Exception {

		String[] highscores = new String[2];
		String fileName = "HighScores.txt";

		c.enableMouse();
		c.enableMouseMotion();
		c.enableMouseWheel();

		int mouseClick = 0;

		Buttons b1 = new Buttons();
		Buttons b2 = new Buttons();
		Buttons b3 = new Buttons();

		// button one position
		b1.x = 50;
		b1.y = 150;
		b1.l = false; // sub loop for button page

		// button 2 position
		b2.x = 300;
		b2.y = 150;
		b2.l = false; // sub loop for button page

		// button 3 position
		b3.x = 175;
		b3.y = 210;
		b3.l = false; // if button is pressed

		// object player 1 - assign attributes
		Player player1 = new Player();
		player1.identity = 1;
		player1.x = -100;
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
				.getImage(c.getClass().getClassLoader().getResource("images/flappy.png"));

		// *************
		// **main page**
		// *************

		// avoid set font in synchronized (or it crashes in some cases)
		c.setFont(new Font("Calibri", Font.BOLD, 20));
		player1.jump = true;
		while (true) {
			// sometimes it does throw an error, but still functions as desired
			try {
				highscores = readFile(fileName, 2);
			} catch (IOException e) {
				System.out.println("Failed to read new highscore.");
			}

			synchronized (c) {
				c.clear();
				c.drawImage(background, 0, 0);
				c.drawImage(flappyBirdo, player1.x + x, player1.y, 60, 50);
				count++;
				x += 2;

				c.setColor(new Color(255, 247, 0, 255));
				c.fillRoundRect(b1.x, b1.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);
				c.fillRoundRect(b2.x, b2.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);
				c.fillRoundRect(b3.x, b3.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);

				// hover must stay in synchronized
				Buttons.isHover(b1, c);
				Buttons.isHover(b2, c);
				Buttons.isHover(b3, c);

				// description
				c.setColor(Color.white);
				c.drawString("Flappy Bird", 200, 100);
				c.drawString("Select the gamemode you wish to play:", 90, 125);

				c.drawString("Player-1 highscore: " + highscores[0], 20, 30);
				c.drawString("Player-2 highscore: " + highscores[1], 300, 30);

				// button text
				c.setColor(new Color(60, 60, 72));
				c.drawString("Single Player", b1.x + 22, b1.y + 25);
				c.drawString("Two Player", b2.x + 30, b2.y + 25);
				c.drawString("Settings", b3.x + 40, b3.y + 25);

			}
			Thread.sleep(10);

			mouseClick = c.getMouseClick();
			//check which button has been clicked
			if (mouseClick != 0) {
				Buttons.isClicked(b1, c);
				Buttons.isClicked(b2, c);
				Buttons.isClicked(b3, c);
			}

			Movements.jumping(player1);
			// every 2x count = vel_y 1x increment
			if (count >= 21) {
				player1.vel_y = 10;
				player1.jump = true;
				count = 0;
			}

			// reset bird animation location
			if (x >= 600) {
				x = 0;
			}

			// do not call methods from other classes inside synchronized
			if (b1.l == true) {
				SinglePlayer.run();
				b1.l = false;
			}

			if (b2.l == true) {
				TwoPlayer.run();
				b2.l = false;
			}

			if (b3.l == true) {
				Settings.run(c);
				b3.l = false;
			}

			if (Buttons.gameOver == true) {
				break;
			}

		} // end of main while loop

		c.close();

	} // end of main

	/**
	 * will read the contents of file to array A
	 *
	 * @param fileName name of file being read
	 * @param size     length of the array or how many contents to read
	 * @return arr array that contains contents from file
	 * @throws java.io.IOException in some circumstance
	 */
	public static String[] readFile(String fileName, int size) throws IOException {
		String[] arr = new String[size];
		File file = new File(fileName);
		Scanner read = new Scanner(file);
		// 0 index - player 1 highscore
		// 1 index - player 2 highscore
		for (int i = 0; i < size; i++) {
			arr[i] = read.nextLine();
		}

		read.close();
		return arr;

	}

	/**
	 * will write the contents of the array A from index 0 to i to a file titled
	 * fileName
	 *
	 * @param fileName is the name of the file to be written to
	 * @param A[]      is the array that is to be written to the file
	 * @param num      is the number of elements from A[] that will be written to
	 *                 the file, starting at index 0
	 * @throws java.io.IOException in some circumstance
	 */
	public static void writeArrayFile(String fileName, String[] A, int num) throws IOException {

		// objects to write to a file
		File file = new File(fileName);
		// true is not specified after file: cover previous elements
		FileWriter fw = new FileWriter(file);
		PrintWriter write = new PrintWriter(fw);

		// write into file
		for (int i = 0; i < num; i++) {
			write.println(A[i]);
		}

		// finish writing to file
		write.close();

	}

}
// end of class
