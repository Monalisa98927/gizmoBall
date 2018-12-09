import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class GameBoard extends JFrame implements Serializable{

	private boolean grid[][];
	private int height;
	private int width;
	private JPanel gamePanel = null;
	private JPanel toolsPanel = null;
	private JFileChooser jFileChooser = new JFileChooser(".");
	private JOptionPane jOptionPane = null;
	private ArrayList<Gizmo> list = null;

	protected GameController gameController;
	public Ball m_Ball;
	public Absorber m_Absorber;
	public Wall m_Wall;
	public Gizmo m_Gizmo;


	public GameBoard(){
		super("Gizmo Ball");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		JToolBar toolBar = new JToolBar();
		addButtons(toolBar);
		gamePanel = new JPanel();
		addGizmos(gamePanel);
		toolsPanel = new JPanel();
		addOperationButtons(toolsPanel);
		gameController = new GameController();
		JScrollPane scrollPane = new JScrollPane(gameController);
		JPanel contentPane = new JPanel();

		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(1000, 800));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(gamePanel, BorderLayout.WEST);
		contentPane.add(toolsPanel, BorderLayout.EAST);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setContentPane(contentPane);
	}


	//所有的上方按钮：保存、打开、开始游戏、停止、退出
	protected void addButtons(JToolBar toolBar) {

		final JButton save = new JButton("Save");;
		final JButton open = new JButton("Open");
		final JButton run = new JButton("Run");
		final JButton stop = new JButton("Stop");
		final JButton quit = new JButton("Quit");

		save.setToolTipText("Save the game state to a file");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save pressed");
				int returnVal = jFileChooser.showSaveDialog(GameBoard.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = jFileChooser.getSelectedFile();
					try {
						//写入文件操作
						FileOutputStream fos = new FileOutputStream(file);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(gameController.getCircle(0));
						//JOptionPane.showMessageDialog(null,"save successfully");
						oos.close();
						fos.close();

					} catch (Exception ex) {
						System.err.println("catch Exception");
						ex.printStackTrace();
					}

					System.out.println("保存文件: " + file.getName());
				} else {
					System.out.println("取消保存" );
				}
			}
		});
		toolBar.add(save);

		open.setToolTipText("Open an existing file");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Load pressed");
				int returnVal = jFileChooser.showOpenDialog(GameBoard.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = jFileChooser.getSelectedFile();
					try {
						FileInputStream fos = new FileInputStream(file);
						ObjectInputStream ois = new ObjectInputStream(fos);
						ArrayList<Gizmo> list =(ArrayList<Gizmo>)ois.readObject();
//						for (int i = 0; i <list.size(); i++) {
//							Gizmo gizmo=(Gizmo) list.get(i);
//							this.list.add(gizmo);
//							this.panelcenter.repaint();
//						}

						ois.close();
						//读取文件操作
						fos.close();

					} catch (Exception ex) {
						System.err.println("Exception");
						ex.printStackTrace();
					}
					System.out.println("打开文件: " + file.getName());
				} else {
					System.out.println("取消打开");
				}
			}
		});
		toolBar.add(open);

		run.setToolTipText("Start the animation");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.setMode(true);
				save.setEnabled(false);
				open.setEnabled(false);
			}
		});
		toolBar.add(run);

		stop.setToolTipText("Stop the animation");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.setMode(false);
				gamePanel.setVisible(true);
				toolsPanel.setVisible(true);
				save.setEnabled(true);
				open.setEnabled(true);
			}
		});
		toolBar.add(stop);

		quit.setToolTipText("Quit the game");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		toolBar.add(quit);

	}

	//添加gizmo组件的按钮：工具栏按钮
	protected void addGizmos(JPanel jPanel) {

		JButton circle = new JButton("Circle");
		circle.setToolTipText("Add cirle");
		circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.addCircle();
				gameController.repaint();
			}
		});

		JButton square = new JButton("Square");
		square.setToolTipText("Add square");
		square.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.addSquare();
				gameController.repaint();
			}
		});

		JButton triangle = new JButton("Triangle");
		triangle.setToolTipText("Add triangle");
		triangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.addTriangle();
				gameController.repaint();
			}
		});

		JButton trapezium = new JButton("Trapezium");
		trapezium.setToolTipText("Add trapezium");
		trapezium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.addTrapezium();
				gameController.repaint();
			}
		});

		JButton flipper = new JButton("Flipper");
		flipper.setToolTipText("Add flipper");
		flipper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.addFlipper();
				gameController.repaint();
			}
		});

		JButton absorber = new JButton("Absorber");
		absorber.setToolTipText("Add absorber");
		absorber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.addAbsorber();
				gameController.repaint();
			}
		});

		JButton track = new JButton("Track");
		track.setToolTipText("Add track");
		track.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.addTrack();
				gameController.repaint();
			}
		});

		GroupLayout jPanelLayout = new GroupLayout(jPanel);
		jPanel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(
				jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(circle)
						.addComponent(square)
						.addComponent(triangle)
						.addComponent(trapezium)
						.addComponent(flipper)
						.addComponent(absorber)
						.addComponent(track)
		);
		jPanelLayout.setVerticalGroup(
				jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(circle)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(square)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(triangle)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(trapezium)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(flipper)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(absorber)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(track)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addContainerGap(149, Short.MAX_VALUE))
		);
	}

	//右边的操作按钮
	protected void addOperationButtons(JPanel jPanel) {

		JButton larger = new JButton("larger");
		larger.setToolTipText("Make Gizmo Larger");
		larger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.setLargerMode(true);
			}
		});

		JButton rotate = new JButton("Rotate");
		rotate.setToolTipText("Rotate gizmo");
		rotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.setRotateMode(true);
			}
		});

		JButton delete = new JButton("Delete");
		delete.setToolTipText("Delete gizmo");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameController.setDeleteMode(true);
			}
		});

		GroupLayout j = new GroupLayout(jPanel);
		jPanel.setLayout(j);
		j.setHorizontalGroup(j.createParallelGroup());

		GroupLayout jPanelLayout = new GroupLayout(jPanel);
		jPanel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(
				jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(larger)
						.addComponent(rotate)
						.addComponent(delete)
		);
		jPanelLayout.setVerticalGroup(
				jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(larger)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(rotate)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(delete)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addContainerGap(149, Short.MAX_VALUE))
		);
	}

	public static void main(String[] args){
		GameBoard gameBoard = new GameBoard();
		System.out.println("Welcome to Gizmo Ball!");
		gameBoard.pack();
		gameBoard.setVisible(true);
	}

}