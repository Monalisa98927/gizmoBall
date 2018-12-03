
public class Wall {

	private int height;
	private boolean isHit;
	private int width;
	private int x;
	private int y;

	public Wall(){

	}

	public boolean isHit() {
		return isHit;
	}

	public boolean collide(Ball ball){
		return false;
	}

	public void setPosition(int x, int y){

	}


}