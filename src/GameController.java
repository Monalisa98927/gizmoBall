import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

public class GameController extends JComponent {

    private AnimationEventListener eventListener;
    private Ball ball;
    private Vector circle = new Vector(20);
    private Vector square = new Vector(20);
    private Vector triangle = new Vector(20);
    private Vector trapezium = new Vector(20);
    private Vector flipper = new Vector(20);

    private Timer timer;
    private boolean mode;
    private boolean rotateMode;

    private int circleCount = 0;
    private int squareCount = 0;
    private int triangleCount = 0;
    private int trapeziumCount = 0;
    private int flipperCount = 0;

    private boolean circlePress = false;
    private boolean squarePress = false;
    private boolean trianglePress = false;
    private boolean trapeziumPress = false;
    private boolean flipperPress = false;
    private int index = 0;

    /**
     * @effects initializes this to be in the off mode.
     */
    public GameController() {

        super();
        ball = new Ball(this);
        eventListener = new AnimationEventListener();
        addMouseListener(eventListener);
        addMouseMotionListener(eventListener);
        addKeyListener(eventListener);
        timer = new Timer(1 , eventListener);
        mode = false;
        rotateMode = false;
    }

    @Override
    public void paintComponent(Graphics g) {

        ball.paint(g);
        for(int i=0; i<circleCount; i++){
            ((Circle)circle.get(i)).draw((Graphics2D)g);
        }
        for(int j=0; j<squareCount; j++){
            ((Square)square.get(j)).draw((Graphics2D)g);
        }
        for(int j=0; j<triangleCount; j++){
            ((Triangle)triangle.get(j)).draw((Graphics2D)g);
        }
        for(int j=0; j<trapeziumCount; j++) {
            ((Trapezium)trapezium.get(j)).draw((Graphics2D)g);
        }
        for(int j=0; j<flipperCount; j++) {
            ((Flipper)flipper.get(j)).draw((Graphics2D)g);
        }

    }

    private void update() {
        Rectangle oldPos = ball.boundingBox();

        ball.startMoving(); // make changes to the logical animation state

        Rectangle repaintArea = oldPos.union(ball.boundingBox());

        repaint(repaintArea.x, repaintArea.y, repaintArea.width, repaintArea.height);

    }

    public void setMode(boolean m) {
        if (mode == m) {
            return;
        }
        if (mode == true) {
            // we're about to change mode: turn off all the old listeners
            removeMouseListener(eventListener);
            removeMouseMotionListener(eventListener);
            removeKeyListener(eventListener);
        }
        mode = m;
        if (mode == true) {
            // the mode is true: turn on the listeners
            addMouseListener(eventListener);
            addMouseMotionListener(eventListener);
            addKeyListener(eventListener);
            requestFocus(); // make sure keyboard is directed to us
            timer.start();
        }
        else {
            timer.stop();
        }
    }

    public void addCircle(){
        circle.add(circleCount,new Circle(this,100,500));
        circleCount++;
        System.out.println(circleCount);
    }

    public Circle getCircle(int index){
        return (Circle)circle.get(index);
    }

    public int getCircleCount(){
        return circleCount;
    }

    public void addSquare(){
        square.add(squareCount,new Square(this,300,400));
        squareCount++;
    }
    public Square getSquare(int index){
        return (Square)square.get(index);
    }
    public int getSquareCount(){
        return squareCount;
    }

    public void addTriangle(){
        triangle.add(triangleCount,new Triangle(this,500,600));
        triangleCount++;
    }
    public Triangle getTriangle(int index){
        return (Triangle)triangle.get(index);
    }
    public int getTriangleCount(){
        return triangleCount;
    }

    public void addTrapezium(){
        trapezium.add(trapeziumCount,new Trapezium(this,700,500));
        trapeziumCount++;
    }
    public Trapezium getTrapezium(int index){
        return (Trapezium)trapezium.get(index);
    }
    public int getTrapeziumCount(){
        return trapeziumCount;
    }

    public void addFlipper(){
        flipper.add(flipperCount,new Flipper(this,550,400));
        flipperCount++;
    }
    public Flipper getFlipper(int index){
        return (Flipper)flipper.get(index);
    }
    public int getFlipperCount(){
        return flipperCount;
    }

    public void setRotateMode(boolean f) {
        if(rotateMode == f){
            return;
        }
        if(rotateMode == true){
            removeMouseListener(eventListener);
            removeMouseMotionListener(eventListener);
            removeKeyListener(eventListener);
        }
        rotateMode = f;
        if(rotateMode == true){
            addMouseListener(eventListener);
            addMouseMotionListener(eventListener);
            addKeyListener(eventListener);
            requestFocus();
        }
    }

    class AnimationEventListener extends MouseAdapter implements
            MouseMotionListener, KeyListener, ActionListener, MouseListener {

        double mouseX;
        double mouseY;
        boolean markC = false;   //鼠标悬挂小球上方
        boolean markM = false;   //鼠标点击小球

        @Override public void mouseClicked(MouseEvent e) {
            this.mouseX = e.getX();
            this.mouseY = e.getY();

            for(int i=0; i<circleCount; i++){
                if((this.mouseX)*(this.mouseX-getCircle(i).x)
                        +(this.mouseY-getCircle(i).y)*(this.mouseY-getCircle(i).y)
                        <= getCircle(i).getR()*getCircle(i).getR()){
                    markC = true;
                }
                else markC = false;
            }

        }

        public void mousePressed(MouseEvent e) {           //判断鼠标是否点击
            System.out.println("mousePressed");
            int pressX = e.getX();
            int pressY = e.getY();
            for(int i=0; i<circleCount; i++){
                int distance = (pressX -((Circle)circle.get(i)).getX()) * (pressX-((Circle)circle.get(i)).getX())
                                +(pressY -((Circle)circle.get(i)).getY()) * (pressY-((Circle)circle.get(i)).getY());
                if (distance <= ((Circle)circle.get(i)).getR()*((Circle)circle.get(i)).getR()){
                    circlePress = true;
                    System.out.println("press circle "+i);
                    index = i;
                    break;
                }
            }

            for(int j=0; j<squareCount; j++){
                int distance = (pressX -((Square)square.get(j)).getX()) * (pressX-((Square)square.get(j)).getX())
                                +(pressY -((Square)square.get(j)).getY()) * (pressY-((Square)square.get(j)).getY());
                if (distance <= ((Square)square.get(j)).getWidth()*((Square)square.get(j)).getWidth()){
                    squarePress = true;
                    System.out.println("press square "+j);
                    index = j;
                    break;
                }
            }

            for(int j=0; j<triangleCount; j++){
                int distance = (pressX -((Triangle)triangle.get(j)).getX()) * (pressX-((Triangle)triangle.get(j)).getX())
                                +(pressY -((Triangle)triangle.get(j)).getY()) * (pressY-((Triangle)triangle.get(j)).getY());
                if (distance <= ((Triangle)triangle.get(j)).getWidth()*((Triangle)triangle.get(j)).getWidth()){
                    trianglePress = true;
                    System.out.println("press triangle "+j);
                    index = j;
                    break;
                }
            }

            for(int j=0; j<trapeziumCount; j++){
                int distance = (pressX -((Trapezium)trapezium.get(j)).getX()) * (pressX-((Trapezium)trapezium.get(j)).getX());
                if (distance <= ((Trapezium)trapezium.get(j)).getA()*((Trapezium)trapezium.get(j)).getA()){
                    trapeziumPress = true;
                    System.out.println("press trapezium "+j);
                    index = j;
                    break;
                }
            }

            for(int j=0; j<flipperCount; j++){
                int distance = (pressX -((Flipper)flipper.get(j)).getX()) * (pressX-((Flipper)flipper.get(j)).getX());
                if (distance <= ((Flipper)flipper.get(j)).getHeight()*((Flipper)flipper.get(j)).getHeight()){
                    flipperPress = true;
                    System.out.println("press flipper "+j);
                    index = j;
                    break;
                }
            }
        }

        public void mouseReleased(MouseEvent e){
            int releasex = e.getX();
            int releasey = e.getY();

            System.out.println("mouseReleased");
            if(circlePress == true){
                ((Circle)circle.get(index)).setPosition(releasex,releasey);
                repaint();
                circlePress = false;
            }
            if(squarePress == true){
                ((Square)square.get(index)).setPosition(releasex,releasey);
                repaint();
                squarePress = false;
            }
        }

        public void mouseDragged(MouseEvent e) {

            System.out.println("mouseDragged");

            for(int i=0; i<circleCount; i++){
                if(markC==true&&markM==true){
                    getCircle(i).setPosition(e.getX(),e.getY());
                    repaint();
                    markM = false;
                    markC = false;
                }
            }
        }

        public void mouseMoved(MouseEvent e) {

            System.out.println("mouseMoved");
            this.mouseX = e.getX();
            this.mouseY = e.getY();

            for(int i=0; i<circleCount; i++){
                if((this.mouseX)*(this.mouseX-getCircle(i).x)
                        +(this.mouseY-getCircle(i).y)*(this.mouseY-getCircle(i).y)
                        <= getCircle(i).getR()*getCircle(i).getR()){
                    markM = true;
                }
                else {
                    markM = false;
                    markC = false;
                }
            }

        }

        public void keyPressed(KeyEvent e) {
            //
            int keynum = e.getKeyCode();
            if ((keynum >= 65) && (keynum <= 74)) {
                System.out.println("keypress " + e.getKeyCode());
                //ball.randomBump();
            }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

        public void actionPerformed(ActionEvent e) {
            update();
        }
    }

}