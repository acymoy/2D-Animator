package cs5004.animator.view;

import cs5004.animator.model.Ellipse;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 * Draws the objects in the animation.
 */
public class GraphicsDraw extends JPanel {

  private int frameNum;
  private HashMap<Integer, ArrayList<Shape>> frameList;
  ArrayList<Shape> shapeList;

  /**
   * Constructor for drawing the objects. Takes in the list of shapes in the hashmap generated from
   * the model.
   *
   * @param frameList hashmap of frame key values and arraylist of shape objects.
   */
  public GraphicsDraw(HashMap<Integer, ArrayList<Shape>> frameList) {
    this.frameList = frameList;
    this.frameNum = 0;
    this.shapeList = new ArrayList<>();
  }

  /**
   * Sets the frame number. Used in restarting the animation.
   *
   * @param frameNum the frame number (typically 0).
   */
  public void setFrameNum(int frameNum) {
    this.frameNum = frameNum;
  }

  /**
   * Where all the painting goes. Is called every time that the panel is repainted and is invoked by
   * paint.
   *
   * @param g the graphics object.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setBackground(Color.lightGray);
    int frameEnd = frameList.size();
    if (frameNum >= frameEnd - 1) {
      if (LoopStatus.getLoopStatus()) {
        frameNum = 0;
      } else {
        return;
      }
    }

    if (frameList.containsKey(frameNum)) {
      shapeList = frameList.get(frameNum);
    }
    frameNum++;

    for (Shape s : shapeList) {
      if (s.getShapeType().equals("rectangle")) {
        paintRectangle(g, (Rectangle) s);
      } else {
        paintEllipse(g, (Ellipse) s);
      }
    }

  }

  /**
   * Draws the rectangle according to the Rectangle object passed in.
   *
   * @param g graphics object.
   * @param s the rectangle object to paint.
   */
  private void paintRectangle(Graphics g, Rectangle s) {
    Graphics2D g2d = (Graphics2D) g;

    cs5004.animator.model.Color rectColor = s.getColor();
    Color c = new Color(rectColor.getRed(), rectColor.getGreen(), rectColor.getBlue());
    g2d.setColor(c);

    g2d.fill(new Rectangle2D.Double(s.getX(), s.getY(), s.getWidth(), s.getHeight()));
  }

  /**
   * Draws the ellipse according to the Ellipse object passed in.
   *
   * @param g the graphics object.
   * @param s the ellipse object to paint.
   */
  private void paintEllipse(Graphics g, Ellipse s) {
    Graphics2D g2d = (Graphics2D) g;

    cs5004.animator.model.Color ellColor = s.getColor();
    Color c = new Color(ellColor.getRed(), ellColor.getGreen(), ellColor.getBlue());
    g2d.setColor(c);

    g2d.fill(new Ellipse2D.Double(s.getX(), s.getY(), s.getWidth(), s.getHeight()));
  }

}
