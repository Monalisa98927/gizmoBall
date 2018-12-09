import java.io.Serializable;

public class BasicGizmo extends Gizmo implements Serializable {

	protected int weight;

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