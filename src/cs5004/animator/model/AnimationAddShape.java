package cs5004.animator.model;

/**
 * Animation representing the construction of a shape. Extends AbstractAnimation.
 */
public class AnimationAddShape extends AbstractAnimation {

  private int x;
  private int y;
  private Color color;
  private int w;
  private int h;

  /**
   * Constructor for AnimationAddShape. Takes the required parameters, so the times, start
   * coordinate, shape name, and shape object.
   *
   * @param startTime the time the shape will appear.
   * @param endTime   the time the shape will disappear.
   * @param x         the x coordinate.
   * @param y         the y coordinate.
   * @param shapeName the name of the shape.
   * @param shape     the shape type.
   */
  public AnimationAddShape(int startTime, int endTime, int x, int y, String shapeName,
      Shape shape, Color color, int w, int h) {
    super(startTime, endTime, shapeName, shape);
    this.x = x;
    this.y = y;
    this.type = "addShape";
  }

  @Override
  public Animation deepCopy() {
    return new AnimationAddShape(startTime, endTime, x, y, shapeName, shape, color, w, h);
  }

  @Override
  public String toString() {
    return String.format("%s %s %s is created from t=%s to t=%s.", shape.getShapeType(), shapeName,
        shape.getColor(), startTime, endTime);
  }
}
