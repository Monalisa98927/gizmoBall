import java.io.Serializable;

public class BasicGizmo extends Gizmo implements Serializable {


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