
public class BasicGizmo extends Gizmo {

	protected int x;
	protected int y;

	public BasicGizmo(){

	}

	public boolean collide(Ball ball){
		return false;
	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}

}