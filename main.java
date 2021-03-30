// *************
		// **main page**
		// *************

		while (true) {
			synchronized (c) {
				c.drawImage(background, 0, 0);

				// set colour for buttons
				c.setColor(new Color(255, 247, 0, 255));
				c.fillRoundRect(b1.x, b1.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);
				c.fillRoundRect(b2.x, b2.y, Buttons.w, Buttons.h, Buttons.r, Buttons.r);

				mouseClick = c.getMouseClick();

				isHover(b1);
				isHover(b2);

				// text colour
				c.setColor(new Color(60, 60, 72));
				c.setFont(new Font("Impact", Font.PLAIN, 15));
				c.drawString("Single Player", b1.x + 30, b1.y + 25);
				c.drawString("Two Player", b2.x + 35, b2.y + 25);

				if (mouseClick != 0) {
					isClicked(b1);
					isClicked(b2);
				}

			}
			Thread.sleep(10);

		} // end of main while loop
