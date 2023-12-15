package cs5004.animator.view;

import cs5004.animator.model.Shape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Outputs the animation a graphics panel format.
 */
public class GraphicsBuilder implements AnimationOutput {

  private JFrame f;
  private HashMap<Integer, ArrayList<Shape>> frameList;
  Timer timer;

  private GraphicsDraw drawing;

  private JButton start;
  private JButton pause;
  private JButton restart;
  private JButton loop;
  private JButton speedUp;
  private JButton speedDown;
  private JLabel notify;

  /**
   * Constructor for GraphicsAnimation. Initializes the JFrame and Panel, as well as the attributes
   * required for repainting/timing the canvas.
   *
   * @param frameList  the hashmap from the model of the frame key and shapes list.
   * @param dimensions the WxH of the panel window.
   */
  public GraphicsBuilder(HashMap<Integer, ArrayList<Shape>> frameList, int[] dimensions,
      int tempo) {
    this.frameList = frameList;

    this.f = new JFrame("2DAnimator Playback");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setLayout(new BorderLayout());

    this.drawing = this.buildAnimation();
    this.drawing.setPreferredSize(new Dimension(dimensions[0], dimensions[1]));

    JPanel gui = this.buildPanel();
    JPanel cue = this.buildCueBox();

    // Makes a mini SplitPanel to fit the cue box and GUI in.
    JSplitPane miniP = new JSplitPane();
    miniP.setOrientation(JSplitPane.VERTICAL_SPLIT);
    miniP.setPreferredSize(new Dimension(900, 60));
    miniP.add(gui, JSplitPane.BOTTOM);
    miniP.add(cue, JSplitPane.TOP);

    // SplitPlane to add to frame later, but for button/animation separation
    JSplitPane p = new JSplitPane();
    p.setOrientation(JSplitPane.VERTICAL_SPLIT);
    p.setPreferredSize(new Dimension(900, 900));

    f.setPreferredSize(new Dimension(1000, 1000));
    f.setLayout(new GridLayout());

    p.add(drawing, JSplitPane.BOTTOM);
    p.add(miniP, JSplitPane.TOP);

    f.getContentPane().add(p);
    f.pack();
    f.setVisible(true);

    LoopStatus.setLoopStatus(false);
    this.timer = new Timer(tempo, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        f.repaint();
      }
    });
  }

  @Override
  public void out() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }

  /**
   * Called by out. Starts the timer from the runnable.
   */
  private void createAndShowGUI() {
    this.timer.start();
  }

  /**
   * Helper method to build the panel that the animation will be displayed on, and return that
   * panel.
   *
   * @return the animation JPanel.
   */
  private GraphicsDraw buildAnimation() {
    GraphicsDraw j = new GraphicsDraw(this.frameList);
    return j;
  }

  /**
   * Helper method to build the panel and its buttons, and return that panel. Creates the start,
   * pause, resume, restart; and looping/speed toggles.
   *
   * @return the animation JPanel.
   */
  private JPanel buildPanel() {
    this.start = new JButton("Start");
    start.setActionCommand("start");
    this.pause = new JButton("Pause");
    pause.setActionCommand("pause");
    this.restart = new JButton("Restart");
    restart.setActionCommand("restart");
    this.loop = new JButton("Toggle Loop");
    loop.setActionCommand("loop");
    this.speedDown = new JButton("-");
    speedDown.setActionCommand("speeddown");
    JLabel speed = new JLabel("Speed");
    this.speedUp = new JButton("+");
    speedUp.setActionCommand("speedup");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(start);
    buttonPanel.add(pause);
    buttonPanel.add(restart);
    buttonPanel.add(loop);
    buttonPanel.add(speedDown);
    buttonPanel.add(speed);
    buttonPanel.add(speedUp);
    return buttonPanel;
  }

  /**
   * Builds the banner that shows what the user clicked.
   *
   * @return the JPanel with a JLabel of what was clicked.
   */
  private JPanel buildCueBox() {
    this.notify = new JLabel();
    JPanel cueBox = new JPanel();
    cueBox.add(notify);
    return cueBox;
  }

  /**
   * Sets the action listener that is called when one of the buttons are pressed.
   *
   * @param e the ActionListener object.
   */
  public void setListener(ActionListener e) {
    this.start.addActionListener(e);
    this.pause.addActionListener(e);
    this.restart.addActionListener(e);
    this.loop.addActionListener(e);
    this.speedUp.addActionListener(e);
    this.speedDown.addActionListener(e);
  }

  /**
   * Action call on start button press.
   */
  public void startButton() {
    this.notify.setText("Started animation.");
    this.out();
  }

  /**
   * Action call on restart button press.
   */
  public void restartButton() {
    this.notify.setText("Restarted animation.");
    this.drawing.setFrameNum(0);
    this.timer.start();
  }

  /**
   * Action call on pause button press.
   */
  public void pauseButton() {
    this.notify.setText("Paused animation.");
    this.timer.stop();
  }

  /**
   * Action call on loop button press.
   */
  public void loopButton() {
    this.notify.setText("Loop status set to " + !LoopStatus.getLoopStatus() + ".");
    LoopStatus.setLoopStatus(!LoopStatus.getLoopStatus());
  }

  /**
   * Action call on increase speed button press.
   */
  public void speedUpButton() {

    this.timer.stop();
    int newDelay = timer.getDelay() - 200;
    if (newDelay <= 0) {
      this.timer.setDelay(1);
    } else {
      this.timer.setDelay(newDelay);
    }
    this.notify.setText("Animation sped up to " + this.timer.getDelay() + ".");
    this.timer.start();
  }

  /**
   * Action call on decrease speed button press.
   */
  public void speedDownButton() {
    this.timer.stop();
    this.timer.setDelay(timer.getDelay() + 200);
    this.notify.setText("Animation slowed to " + this.timer.getDelay() + ".");
    this.timer.start();
  }
}


