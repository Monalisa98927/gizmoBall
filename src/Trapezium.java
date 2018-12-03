import java.awt.Color;
import java.awt.Polygon;

public class Trapezium extends BasicGizmo {

	private int a;
	private int b;
	private int h;
	private int rotate;
	private Color color = new Color(146, 189, 255);
	private GameController gameController;

	public Trapezium(GameController gameController,int x,int y){
		this.gameController = gameController;
		this.x = x;
		this.y = y;
		this.a = 50;
		this.h = 50;
		this.b = 100;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getRotate() {
		return rotate;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}

	public void draw(java.awt.Graphics2D g)
	{
		Polygon filledPolygon=new Polygon();
		filledPolygon.addPoint(x,y);
		filledPolygon.addPoint(x + a,y);
		filledPolygon.addPoint(x + b, y + h);
		filledPolygon.addPoint(x - a,y + h);
		g.setColor(color);
		g.fillPolygon(filledPolygon);
		g.drawPolygon(filledPolygon);
	}

	public boolean collide(Ball ball){

		double squX = (ball.getX() - this.x - this.a/2) * (ball.getX() - this.x - this.a/2);
		double squY = (ball.getY() - this.y - this.h/2) * (ball.getY() - this.y - this.h/2);
		double squR = ball.r + this.a;

		if(squX + squY > squR * squR){
			return false;
		}
		else {
			return true;
		}
	}
}