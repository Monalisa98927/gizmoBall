
public class Absorber extends Gizmo  {

	private int height;
	private boolean isHit;
	private int width;
	private int x;
	private int y;


	public Absorber(int x,int y,int width,int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHit() {
		return isHit;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean collide(Ball ball){
		return false;
	}

	public void setPosition(int x, int y){

	}
}

