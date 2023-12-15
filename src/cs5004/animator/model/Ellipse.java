package cs5004.animator.model;

/**
 * Represents the shape ellipse. Extends AbstractShape and implements Shape.
 */
public class Ellipse extends AbstractShape {

  /**
   * Constructor for Ellipse. Initializes the shape object.
   *
   * @param name   the name of the shape.
   * @param width  the width.
   * @param height the height.
   * @param color  the color.
   * @param x      the current x coordinate.
   * @param y      the current y coordinate.
   */
  public Ellipse(String name, int width, int height, Color color, double x, double y) {
    super(name, width, height, "ellipse", color, x, y);
  }

  /**
   * Returns a new cloned object.
   *
   * @return the cloned ellipse object.
   */
  public Ellipse cloned() {
    return new Ellipse(name, width, height, color, x, y);
  }

}
