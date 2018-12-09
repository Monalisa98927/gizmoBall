import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.awt.Color;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trapezium extends BasicGizmo implements Serializable {

	private int a;
	private int b;
	private int h;
	private int rotate;
	private Color color = new Color(146, 189, 255);
	private List<LineSegment> lineSegments=new ArrayList<LineSegment>();
	private GameController gameController;
	private double collisiontime;
	private List<Circle> trapziumCircles=new ArrayList<Circle>();
	private Vect newVelocity;
	private double theta;

	public Trapezium(GameController gameController,int x,int y){
		this.gameController = gameController;
		this.x = x;
		this.y = y;
		this.a = 50;
		this.h = 50;
		this.b = 100;
		newVelocity=new Vect(0,0);
		collisiontime=0.01;
	}

	public List<LineSegment> getLineSegments()
	{
		return this.lineSegments;
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
		filledPolygon.addPoint(x +b, y + h);
		filledPolygon.addPoint(x - a,y + h);
		g.setColor(color);
		g.fillPolygon(filledPolygon);
		g.drawPolygon(filledPolygon);
		setLineSegments(lineSegments);
		setTrapziumCircles(trapziumCircles);
	}

	public void setLineSegments(List<LineSegment> lineSegments) {
		LineSegment line1=new LineSegment(x,y,x+a,y);
		LineSegment line2=new LineSegment(x,y,x-a,y+a);
		LineSegment line3=new LineSegment(x+a,y,x+2*a,y+a);
		LineSegment line4=new LineSegment(x+2*a,y+a,x-a,y+a);
		lineSegments.add(line1);
		lineSegments.add(line2);
		lineSegments.add(line3);
		lineSegments.add(line4);
		this.lineSegments = lineSegments;
	}

	public List<Circle> getTrapeziumCircles() {

		return trapziumCircles;
	}

	public void setTrapziumCircles(List<Circle> trapziumCircles) {
		Circle circle0=new Circle(x,y,0);
		Circle circle1=new Circle(x+a,y,0);
		Circle circle2=new Circle(x+b,y+h,0);
		Circle circle3=new Circle(x-a,y+h,0);
		trapziumCircles.add(circle0);
		trapziumCircles.add(circle1);
		trapziumCircles.add(circle2);
		trapziumCircles.add(circle3);
		this.trapziumCircles = trapziumCircles;
	}

	public  void handlecollide(Ball ball)
	{
		for(int i=getLineSegments().size()-1;i>=getLineSegments().size()-4;i--)
		{
			if(Geometry.timeUntilWallCollision(getLineSegments().get(i),ball.getBallCircle(),ball.getVelocity())<=collisiontime)
			{
				newVelocity=Geometry.reflectWall(getLineSegments().get(i),ball.getVelocity(),0.8);
				ball.setVelocity(newVelocity);
			}
		}
		for(int i=getTrapeziumCircles().size()-1;i>=getTrapeziumCircles().size()-4;i--)
		{
			if(Geometry.timeUntilCircleCollision(getTrapeziumCircles().get(i),ball.getBallCircle(),ball.getVelocity())<=collisiontime)
			{
				newVelocity= Geometry.reflectCircle(getTrapeziumCircles().get(i).getCenter(),ball.getBallCircle().getCenter(),ball.getVelocity(),0.9);
				ball.setVelocity(newVelocity);
			}
		}

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