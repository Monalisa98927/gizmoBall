import javafx.scene.shape.Ellipse;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball {

	private double a = 0.2;     //重力加速度
	private double direction;
	private double velocityLoss;
	private double x = 20;
	private double y = 20;
	private double velocityX = 10;
	private double velocityY = 0;
	protected double r = 10;
	private GameController gameController;
	private Color color = new Color(80, 76, 79);

	public Ball(GameController gameController){
		this.gameController = gameController;
		this.velocityLoss = 0.2;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelocityLoss() {
		return velocityLoss;
	}

	public void setVelocityLoss(double velocityLoss) {
		this.velocityLoss = velocityLoss;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}


	public void startMoving(){

		x = x + velocityX ;
		if (x <= r) {                         //左边界越界
			x = r;
			velocityX = -velocityX + ( (velocityX > 0)? velocityLoss : -velocityLoss );
		}
		if (x >= gameController.getWidth() - r) {              //右边界
			x = gameController.getWidth() - r;
			velocityX = -velocityX + ( (velocityX > 0)? velocityLoss : -velocityLoss );
		}
		for (int i=0; i<gameController.getCircleCount(); i++){               //与circle发生碰撞
			if (gameController.getCircle(i).collide(this)){
				velocityX = -velocityX + ( (velocityX > 0)? velocityLoss : -velocityLoss );

			}
		}
		for(int j=0; j<gameController.getSquareCount(); j++){                //与square发生碰撞
			if(gameController.getSquare(j).collide(this)){
				velocityX = -velocityX + ( (velocityX > 0)? velocityLoss : -velocityLoss );
			}
		}
		for(int j=0; j<gameController.getTriangleCount(); j++){              //与triangle发生碰撞
			if(gameController.getTriangle(j).collide(this)){
				velocityX = -velocityX + ( (velocityX > 0)? velocityLoss : -velocityLoss );
			}
		}
		for(int j=0; j<gameController.getTrapeziumCount(); j++){              //与Trapezium发生碰撞
			if(gameController.getTrapezium(j).collide(this)){
				velocityX = -velocityX + ( (velocityX > 0)? velocityLoss : -velocityLoss );
			}
		}
		for(int j=0; j<gameController.getFlipperCount(); j++){               //与flipper发生碰撞
			if(gameController.getFlipper(j).collide(this)){
				velocityX = -velocityX + ( (velocityX > 0)? velocityLoss : -velocityLoss );
			}
		}

		y = y + velocityY + a/2;
		velocityY = velocityY + a ;
		if (y <= r) {				//上边界
			y = r;
			velocityY = -velocityY + ( (velocityY > 0)? velocityLoss : -velocityLoss );
		}
		if (y >= gameController.getHeight() - r) {			//下边界
				y = gameController.getHeight() - r;
			    velocityY = -velocityY + ( (velocityY > 0)? velocityLoss : -velocityLoss );
		}
		for (int i=0; i<gameController.getCircleCount(); i++){
			if (gameController.getCircle(i).collide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss : -velocityLoss );
			}
		}
		for(int j=0; j<gameController.getSquareCount(); j++){
			if(gameController.getSquare(j).collide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss : -velocityLoss );
			}
		}
		for(int j=0; j<gameController.getTriangleCount(); j++){
			if(gameController.getTriangle(j).collide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss : -velocityLoss );
			}
		}
		for(int j=0; j<gameController.getTrapeziumCount(); j++){
			if(gameController.getTrapezium(j).collide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss : -velocityLoss );
			}
		}
		for(int j=0; j<gameController.getFlipperCount(); j++){
			if(gameController.getFlipper(j).collide(this)){
				velocityY = -velocityY + ( (velocityY > 0)? velocityLoss : -velocityLoss );
			}
		}

		this.setX(x);
		this.setY(y);

	}

	public void paint(Graphics g) {

		Rectangle clipRect = g.getClipBounds();
		if (clipRect.intersects(this.boundingBox())) {
			g.setColor(color);
			g.fillOval((int)(x - r), (int)(y - r), (int)(r + r), (int)(r + r));
		}
	}

	public Rectangle boundingBox() {
		return new Rectangle(0, 0, 1000, 1000);
	}

}