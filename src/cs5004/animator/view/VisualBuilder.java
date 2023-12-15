package cs5004.animator.view;

import cs5004.animator.model.Shape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * The old visual view for the animator.
 */
public class VisualBuilder implements AnimationOutput {

  private JFrame f;
  private Timer timer;

  /**
   * Constructor for VisualBuilder. Takes the required data structure and info for making the
   * panel.
   *
   * @param frameList  hashmap of integer frame keys and the corresponding arraylist of shapes.
   * @param tempo      the speed of the animation.
   * @param dimensions the bounds of the animation panel.
   */
  public VisualBuilder(HashMap<Integer, ArrayList<Shape>> frameList, int tempo, int[] dimensions) {
    this.f = new JFrame("2DAnimator View");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setLayout(new BorderLayout());
    f.setPreferredSize(new Dimension(dimensions[0], dimensions[1]));
    GraphicsDraw painter = new GraphicsDraw(frameList);
    painter.setPreferredSize(new Dimension(dimensions[0], dimensions[1]));

    JScrollPane scroller = new JScrollPane(painter, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    this.timer = new Timer(tempo, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        f.repaint();
      }
    });
    f.getContentPane().add(scroller);
    f.pack();
    f.setVisible(true);
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
}
