import sun.awt.SunHints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Vector;

public class GameController extends JComponent implements Serializable {

    private AnimationEventListener eventListener;
    private Ball ball;
    private Wall wall;
    private Vector circle = new Vector(20);
    private Vector square = new Vector(20);
    private Vector triangle = new Vector(20);
    private Vector trapezium = new Vector(20);
    private Vector flipper = new Vector(20);
    private Vector absorber = new Vector(20);
    private Vector track = new Vector(20);

    private Timer timer;
    private boolean mode;
    private boolean rotateMode;
    private boolean deletemode=false;
    private boolean largermode=false;
    private boolean gameOver ;       //判断小球被吸收，游戏结束

    private String type;
    private int circleCount = 0;
    private int squareCount = 0;
    private int triangleCount = 0;
    private int trapeziumCount = 0;
    private int flipperCount = 0;
    private int absorberCount = 0;
    private int trackCount = 0;

    private boolean circlePress = false;
    private boolean squarePress = false;
    private boolean trianglePress = false;
    private boolean trapeziumPress = false;
    private boolean flipperPress = false;
    private boolean absorberPress = false;
    private boolean trackPress = false;

    private int index = 0;
    private double theta=0;
    private double increase=0;

    public GameController() {

        super();
        wall = new Wall(this);
        ball = new Ball(this);
        eventListener = new AnimationEventListener();

        addMouseListener(eventListener);
        addMouseMotionListener(eventListener);
        addKeyListener(eventListener);
        requestFocus();
        timer = new Timer(1 , eventListener);
        mode = false;
        rotateMode = false;
        gameOver = false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public void paintComponent(Graphics g) {

        wall.draw((Graphics2D)g);
        ball.draw((Graphics2D)g);
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
        for(int j=0; j<absorberCount; j++){
            ((Absorber)absorber.get(j)).draw((Graphics2D)g);
        }
        for(int j=0; j<trackCount; j++){
            ((Track)track.get(j)).draw((Graphics2D)g);
        }

    }

    public boolean isLargermode() {
        return largermode;
    }

    public void setLargerMode(boolean largermode) {
        this.largermode = largermode;
    }

    public boolean isDeleteMode() {
        return deletemode;
    }

    public void setDeleteMode(boolean deletemode) {
        this.deletemode = deletemode;
    }

    public void removeAllGizmos(){
        circle.clear();
        square.clear();
        flipper.clear();
        triangle.clear();
        trapezium.clear();
        absorber.clear();
        track.clear();

        circleCount = 0;
        squareCount = 0;
        triangleCount = 0;
        trapeziumCount = 0;
        flipperCount = 0;
        absorberCount = 0;
        trackCount = 0;
    }

    //开始游戏，以及判断游戏结束
    public void update() {
        Rectangle oldPos = ball.boundingBox();
        ball.startMoving();
        Rectangle repaintArea = oldPos.union(ball.boundingBox());
        requestFocus();
        repaint();

        //一轮游戏结束后，重新生成一个小球，可以点击run重新开始游戏
        if(this.isGameOver()){
            JOptionPane.showMessageDialog(null,"Game Over!","Tip",JOptionPane.PLAIN_MESSAGE);
            this.setGameOver(false);
            ball=new Ball(this);
            repaint();
        }

    }

    //游戏模式：true表示正在游戏，false表示中止游戏
    public void setMode(boolean m) {
        if (mode == m) {
            return;
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
            removeMouseListener(eventListener);
            removeMouseMotionListener(eventListener);
            removeKeyListener(eventListener);
            timer.stop();
        }
    }

    public void addCircle(){
        type="circle";
        circle.add(circleCount,new Circle(this,100,400));
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
        type="square";
        square.add(squareCount,new Square(this,350,300));
        squareCount++;
    }
    public Square getSquare(int index){
        return (Square)square.get(index);
    }
    public int getSquareCount(){
        return squareCount;
    }

    public void addTriangle(){
        type="triangle";
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
        type="trapezium";
        trapezium.add(trapeziumCount,new Trapezium(this,400,700));
        trapeziumCount++;
    }
    public Trapezium getTrapezium(int index){
        return (Trapezium)trapezium.get(index);
    }
    public int getTrapeziumCount(){
        return trapeziumCount;
    }

    public void addFlipper(){
        type="flipper";
        flipper.add(flipperCount,new Flipper(this,550,400));
        flipperCount++;
    }
    public Flipper getFlipper(int index){
        return (Flipper)flipper.get(index);
    }
    public int getFlipperCount(){
        return flipperCount;
    }

    public void addAbsorber(){
        type="absorber";
        absorber.add(absorberCount,new Absorber(this,600,700));
        absorberCount++;
    }
    public Absorber getAbsorber(int index){
        return (Absorber) absorber.get(index);
    }
    public int getAbsorberCount(){
        return absorberCount;
    }
    public void addTrack(){
        type="track";
        track.add(trackCount,new Track(this,100,550));
        trackCount++;
    }
    public Track getTrack(int index){
        return (Track) track.get(index);
    }
    public int getTrackCount(){
        return trackCount;
    }

    //判断gizmo组件是否需要旋转
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

    class AnimationEventListener extends MouseAdapter implements KeyListener, ActionListener,Serializable {

        double mouseX;
        double mouseY;
        boolean markC = false;  //鼠标悬挂小球上方
        boolean markM = false;  //鼠标点击小球

        @Override
        public void mousePressed(MouseEvent e) {           //判断鼠标是否点击
            //System.out.println("mousePressed");
            double pressX = e.getX();
            double pressY = e.getY();

            for(int i=0; i<circleCount; i++){
                System.out.println(pressX+" "+((Circle)circle.get(i)).getX());
                double distance = (pressX -((Circle)circle.get(i)).getX()) * (pressX-((Circle)circle.get(i)).getX())
                        +(pressY -((Circle)circle.get(i)).getY()) * (pressY-((Circle)circle.get(i)).getY());
                double rSquare=((Circle)circle.get(i)).getR()*((Circle)circle.get(i)).getR();
                System.out.println(distance);
                System.out.println(rSquare);
                if (distance <= rSquare){
                    circlePress = true;
                    System.out.println("press circle "+i);
                    index = i;
                    break;
                }
            }

            for(int j=0; j<squareCount; j++){
                if (pressX>=getSquare(j).getX()&&pressX<=getSquare(j).getX()+getSquare(j).getWidth()&&pressY>=getSquare(j).getY()&&pressY<=getSquare(j).getY()+getSquare(j).getWidth()){
                    squarePress = true;
                    System.out.println("press square "+j);
                    index = j;
                    break;
                }
            }

            for(int j=0; j<triangleCount; j++){
                if(pressX>=getTriangle(j).getX()-getTriangle(j).getWidth()&&pressX<=getTriangle(j).getX()+getTriangle(j).getWidth()&&pressY>=getTriangle(j).getY()-getTriangle(j).getWidth()&&pressY<=getTriangle(j).getY()+getTriangle(j).getWidth()){
                    trianglePress = true;
                    index = j;
                    System.out.println("press triangle "+j);
                    break;
                }
            }


            for(int j=0; j<trapeziumCount; j++){
                if (pressX >= getTrapezium(j).getX()-getTrapezium(j).getA()&&pressX<=getTrapezium(j).getX()+2*getTrapezium(j).getA()&&pressY>=getTrapezium(j).getY()&&pressY<=getTrapezium(j).getY()+getTrapezium(j).getA()){
                    trapeziumPress = true;
                    System.out.println("press trapezium "+j);
                    index = j;
                    break;
                }
            }

            for(int j=0;j<flipperCount;j++)
            {
                System.out.println(flipperCount+"flipper"+getFlipper(j).getX()+" "+getFlipper(j).getY());
                if(pressX>=getFlipper(j).getX()&&pressX<=getFlipper(j).getX()+getFlipper(j).getLength()&&pressY>=getFlipper(j).getY()-5&&pressY<=getFlipper(j).getY()+5)
                {
                    flipperPress=true;
                    index=j;
                    System.out.println("press flipper");
                    break;
                }
            }

            for(int j=0; j<absorberCount; j++){
                if (pressX>=getAbsorber(j).getX()&&pressX<=getAbsorber(j).getX()+getAbsorber(j).getWidth()&&pressY>=getAbsorber(j).getY()&&pressY<=getAbsorber(j).getY()+getAbsorber(j).getHeight()){
                    absorberPress = true;
                    System.out.println("press absorber "+j);
                    index = j;
                    break;
                }
            }

            for(int j=0; j<trackCount; j++){
                if (pressX>=getTrack(j).getX()&&pressX<=getTrack(j).getX()+getTrack(j).getWidth()&&pressY>=getTrack(j).getY()&&pressY<=getTrack(j).getY()+getTrack(j).getWidth()
                        ||pressX>=getTrack(j).getX()&&pressX<=getTrack(j).getX()+getTrack(j).getWidth()&&pressY>=getTrack(j).getY()+getTrack(j).getWidth()&&pressY<=getTrack(j).getY()+getTrack(j).getWidth()+getTrack(j).getWidth()
                        ||pressX>=getTrack(j).getX()+getTrack(j).getWidth()&&pressX<=getTrack(j).getX()+getTrack(j).getWidth()+getTrack(j).getWidth()&&pressY>=getTrack(j).getY()+getTrack(j).getWidth()&&pressY<=getTrack(j).getY()+getTrack(j).getWidth()+getTrack(j).getWidth()){
                    trackPress = true;
                    System.out.println("press track "+j);
                    index = j;
                    break;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e){
            int releasex = e.getX();
            int releasey = e.getY();

            if(circlePress == true){
                if(deletemode)
                {
                    circle.remove(index);
                    circleCount--;
                    deletemode=false;
                }
                else {
                    if(largermode)
                    {
                        getCircle(index).setR(getCircle(index).getR()*2);
                        largermode=false;
                    }
                    ((Circle) circle.get(index)).setPosition((int)(releasex/50)*50, (int)(releasey/50)*50);
                }
                circlePress = false;
                repaint();
            }
            if(squarePress == true){
                if(deletemode)
                {
                    square.remove(index);
                    squareCount--;
                    deletemode=false;
                }
                else {
                    if(largermode)
                    {
                        getSquare(index).setWidth(getSquare(index).getWidth()*2);
                        largermode=false;
                    }
                    ((Square) square.get(index)).setPosition((int)(releasex/50)*50, (int)(releasey/50)*50);
                }
                repaint();
                squarePress = false;

            }
            if(trianglePress==true)
            {
                if(deletemode)
                {
                    triangle.remove(index);
                    triangleCount--;
                    deletemode=false;
                }
                else {
                    if(largermode)
                    {
                        getTriangle(index).setWidth(getTriangle(index).getWidth()*2);
                        largermode=false;
                    }
                    if (rotateMode) {
                        getTriangle(index).setRotate(Math.PI / 2);
                        rotateMode=false;
                    }
                    getTriangle(index).setPosition((int)(releasex/50)*50, (int)(releasey/50)*50);

                }
                repaint();
                trianglePress=false;
            }
            if(trapeziumPress==true)
            {
                if(deletemode)
                {
                    trapezium.remove(index);
                    deletemode=false;
                    trapeziumCount--;
                }
                else {
                    if(largermode)
                    {
                        getTrapezium(index).setA(getTrapezium(index).getA()+50);
                        getTrapezium(index).setB(getTrapezium(index).getB()+50);
                        getTrapezium(index).setH(getTrapezium(index).getH()+50);
                        largermode=false;
                    }
                    getTrapezium(index).setPosition((int)(releasex/50)*50, (int)(releasey/50)*50);
                }
                repaint();
                trapeziumPress=false;
            }
            if(flipperPress==true){
                if(deletemode)
                {
                    flipper.remove(index);
                    deletemode=false;
                    flipperCount--;
                }
                else {
                    if(largermode)
                    {
                        getFlipper(index).setLength(getFlipper(index).getLength()*2);
                        largermode=false;
                    }
                    getFlipper(index).setPosition((int)(releasex/50)*50, (int)(releasey/50)*50);
                }
                repaint();
                flipperPress=false;
            }
            if(absorberPress == true){
                getAbsorber(index).setPosition((int)(releasex/50)*50, (int)(releasey/50)*50);
                repaint();
                absorberPress = false;
            }
            if(trackPress == true){
                if(deletemode)
                {
                    track.remove(index);
                    deletemode=false;
                    trackCount--;
                }
                getTrack(index).setPosition((int)(releasex/50)*50, (int)(releasey/50)*50);
                repaint();
                trackPress=false;
            }
        }

        @Override                                      //鼠标点击拖动gizmo组件
        public void mouseDragged(MouseEvent e) {

            //System.out.println("mouseDragged");

            if(circlePress==true) {
                getCircle(index).setPosition(e.getX(), e.getY());
                repaint();
                markM = false;
                markC = false;
            }

            if(squarePress==true){
                getSquare(index).setPosition(e.getX(),e.getY());
                repaint();
                markM = false;
                markC = false;
            }
            if(trianglePress==true)
            {
                getTriangle(index).setPosition(e.getX(),e.getY());
                repaint();
            }
            if(flipperPress==true){
                getFlipper(index).setPosition(e.getX(),e.getY());
                repaint();
            }
            if(absorberPress==true){
                getAbsorber(index).setPosition(e.getX(),e.getY());
                repaint();
                markM = false;
                markC = false;
            }
            if(trapeziumPress==true)
            {
                getTrapezium(index).setPosition(e.getX(),e.getY());
                repaint();
            }
            if(trackPress==true){
                getTrack(index).setPosition(e.getX(),e.getY());
                repaint();
                markM = false;
                markC = false;
            }

        }

        @Override
        public void keyPressed(KeyEvent e) {

            int keynum = e.getKeyCode();
            if (e.getKeyCode()==KeyEvent.VK_A)
            {
                System.out.println(flipperCount);
                for(int i=0;i<flipperCount;i++) {
                    if(getFlipper(i).getTheta()==-90){
                        getFlipper(i).setTheta(0);
                        getFlipper(i).setRightRotate(true);
                    }
                }
            }
            if (e.getKeyCode()==KeyEvent.VK_D)
            {
                System.out.println(flipperCount);
                for(int i=0;i<flipperCount;i++) {
                    if(getFlipper(i).getTheta()==0) {
                        getFlipper(i).setTheta(-90);
                        getFlipper(i).setLeftRotate(true);
                    }
                }

            }
            if (e.getKeyCode()==KeyEvent.VK_LEFT)
            {
                for(int i=0;i<flipperCount;i++) {
                    getFlipper(i).setPosition(getFlipper(i).getX()-50,getFlipper(i).getY());
                }
            }
            if (e.getKeyCode()==KeyEvent.VK_RIGHT)
            {
                for(int i=0;i<flipperCount;i++) {
                    getFlipper(i).setPosition(getFlipper(i).getX()+50,getFlipper(i).getY());
                }
            }
            if (e.getKeyCode()==KeyEvent.VK_UP)
            {
                for(int i=0;i<flipperCount;i++) {
                    getFlipper(i).setPosition(getFlipper(i).getX(),getFlipper(i).getY()-50);
                }
            }
            if (e.getKeyCode()==KeyEvent.VK_DOWN)
            {
                for(int i=0;i<flipperCount;i++) {
                    getFlipper(i).setPosition(getFlipper(i).getX(),getFlipper(i).getY()+50);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            for(int i=0;i<flipperCount;i++) {
                getFlipper(i).setLeftRotate(false);
                getFlipper(i).setRightRotate(false);
            }
        }

        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            update();
        }
    }

}