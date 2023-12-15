package cs5004.animator.model;

/**
 * Represents the Rectangle object. Extends AbstractShape and implements Shape.
 */
public class Rectangle extends AbstractShape {

  /**
   * Constructor for Rectangle. Initializes the shape object.
   *
   * @param name   the name of the shape.
   * @param width  the width.
   * @param height the height.
   * @param color  the color.
   * @param x      the current x coordinate.
   * @param y      the current y coordinate.
   */
  public Rectangle(String name, int width, int height, Color color, double x, double y) {
    super(name, width, height, "rectangle", color, x, y);
  }

  /**
   * Clones a rectangle object.
   *
   * @return the cloned object.
   */
  public Rectangle cloned() {
    return new Rectangle(name, width, height, color, x, y);
  }

}
