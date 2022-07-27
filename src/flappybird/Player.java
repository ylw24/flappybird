package flappybird;

public class Player {
	// bottom floor: same for all
	static int boundaries = 500;

	// character positions
	int x;
	int y;
	boolean jump;

	// y velocity - controls acceleration
	int vel_y;
	byte identity;
	int score;
	boolean collide = false;

}

