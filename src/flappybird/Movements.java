package flappybird;

import java.util.Random;

/**
 * Class of a variety of methods regarding movements
 * 
 * @author Linda
 */
public class Movements {

	/**
	 * Check if bird reaches ground and lost
	 * 
	 * @return a boolean indicating if the bird touches the ground
	 * @param p an object of the bird
	 */
	public static boolean deathGround(Player p) {
		if (p.y >= 500) {
			return true;
		}
		return false;

	}

	/**
	 * Check if bird reaches top boundary and set limit
	 * 
	 * @return object of bird after its values are changed
	 * @param p an object of the bird
	 */
	public static Player boundary(Player p) {
		if (p.y <= 0) {
			p.y = 1;
			p.vel_y = 0;
		}
		return p;

	}

	/**
	 * enables jumping movements of bird
	 * 
	 * @return object of the bird after its values are changed
	 * @param p an object of the bird
	 */
	public static Player jumping(Player p) {
		if (p.jump == true) {

			// jumping: stopping with relative to boundaries

			p.y -= p.vel_y;
			p.vel_y -= 1;

			// remember y goes up as you go down
			// = sign is important to avoid jumping extra 10 y-coords
			if (p.y >= Player.boundaries) {
				p.jump = false;
				p.vel_y = 10;
			}

		}

		return p;
	}

	/**
	 * Check bird collides with the pipe
	 * 
	 * @return boolean whether the bird collides or not
	 * @param player an object of the bird
	 * @param pipe   an object of the pipe
	 */
	public static Player collision(Player player, Pipe pipe) {
		// unfinished: check collision between bird and its pipe
		if (((player.x < pipe.x + 110) && (pipe.x < player.x + 60) && (player.y < pipe.y + 790)
				&& (pipe.y < player.y + 50))
				|| ((player.x < pipe.x + 110) && (pipe.x < player.x + 60) && (player.y < pipe.y + 1780)
						&& (pipe.y + 990 < player.y + 50))) {

			// end game for player
			player.collide = true;
			pipe.x = -1000;
		}

		return player;
	}

	/**
	 * find new random pipe position
	 * 
	 * @return pipe an object of the pipe
	 * @param pipe an object of the pipe
	 */
	public static Pipe newLocation(Pipe pipe) {
		Random r = new Random();

		pipe.x = 440;
		// format: r.nextInt((max - min) + 1) + min;
		pipe.y = -1 * (r.nextInt((780 - 500) + 1) + 500);

		return pipe;
	}

	/**
	 * find new random pipe position for single player - difference from
	 * newLocation(pipe): pipe starts out of console instead of the middle
	 * 
	 * @return pipe an object of the pipe
	 * @param pipe an object of the pipe
	 */
	public static Pipe newLocationSingle(Pipe pipe) {
		Random r = new Random();

		pipe.x = 500;
		// format: r.nextInt((max - min) + 1) + min;
		pipe.y = -1 * (r.nextInt((780 - 500) + 1) + 500);

		return pipe;
	}

	/**
	 * check if pipe is out of console
	 * 
	 * @return true is pipe is gone, false if not
	 * @param pipe an object of the pipe
	 */
	public static boolean outOfConsole(Pipe pipe) {
		if (pipe.x < -150) {
			return true;
		} else if (pipe.x > 1000) {
			return true;
		} else {
			return false;
		}
	}

}
