public abstract class Player extends Enemy implements Controllable, Collidable {
	//jump force affects how much the player will jump, by multiplying the base jump speed
	private int jumpForce=1;
	public static final int jumpConstant=(-3);
	private boolean movingRight;
	//physics is the physics engine being used by the player,


	public Player(int x, int y, AbstractTile tile) {
		super(x, y, tile);

	}

	public Player(int x, int y, int width, int height, boolean solid, int layer) {
		super(x, y, width, height, solid, layer);
	}

	public int getJumpForce() {
		return jumpForce;
	}

	public void setJumpForce(int jumpForce) {
		this.jumpForce = jumpForce;
	}

	public boolean isMovingRight() {
		return movingRight;
	}
	protected void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}
}
