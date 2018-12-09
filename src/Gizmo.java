import java.awt.dnd.DropTargetListener;
import java.io.Serializable;

public class Gizmo implements Serializable {

	protected boolean isHit;
	protected String type;
	protected int x;
	protected int y;

	public Gizmo(){

	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean hit) {
		isHit = hit;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean collide(Ball ball){
		return false;
	}

	public void setPosition(int x, int y){
		this.x=x;
		this.y=y;
	}

}