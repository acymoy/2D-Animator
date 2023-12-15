package cs5004.animator.model;

/**
 * Animation for changing the coordinate of the shape.
 */
public class AnimationChangeCoordinate extends AbstractAnimation {

  private double fromX;
  private double fromY;
  private double toX;
  private double toY;
  private double incrementX;
  private double incrementY;

  /**
   * Constructor for AnimationChangeCoordinate.
   *
   * @param startTime the starting time of animation.
   * @param endTime   the ending time of animation.
   * @param shapeName the name of the shape it applies to.
   * @param toX       the ending X coordinate.
   * @param toY       the ending Y coordinate.
   * @param shape     the shape object.
   */
  public AnimationChangeCoordinate(int startTime, int endTime, String shapeName, double toX,
      double toY, Shape shape) {
    super(startTime, endTime, shapeName, shape);
    this.fromX = shape.getX();
    this.fromY = shape.getY();
    this.toX = toX;
    this.toY = toY;
    this.type = "changeCoordinate";
  }

  /**
   * Returns the original X value of the animation.
   *
   * @return the original x coordinate.
   */
  public double getFromX() {
    return this.fromX;
  }

  /**
   * Returns the original Y value.
   *
   * @return the original y coordinate.
   */
  public double getFromY() {
    return this.fromY;
  }

  /**
   * Returns the ending X value.
   *
   * @return the ending X value.
   */
  public double getToX() {
    return this.toX;
  }

  /**
   * Returns the ending Y value.
   *
   * @return the ending y value.
   */
  public double getToY() {
    return this.toY;
  }

  /**
   * Sets the original x value for calculations.
   *
   * @param fromX the origin x.
   */
  public void setFromX(double fromX) {
    this.fromX = fromX;
  }

  /**
   * Sets the original y value for calculations.
   *
   * @param fromY the origin y.
   */
  public void setFromY(double fromY) {
    this.fromY = fromY;
  }

  @Override
  public Animation deepCopy() {
    return new AnimationChangeCoordinate(startTime, endTime, shapeName, toX, toY, shape);
  }

  /**
   * Calculates the incremental change in value over time.
   *
   * @param currCoords the current coordinates of the shape.
   */
  public void calcIncrement(double[] currCoords) {
    this.fromX = currCoords[0];
    this.fromY = currCoords[1];
    this.incrementX = (toX - currCoords[0]) / totalTime;
    this.incrementY = (toY - currCoords[1]) / totalTime;
  }

  /**
   * Gets the x/y coordinate of the shape at any given frame.
   *
   * @param frame the frame to find the coordinates for.
   * @return the x/y coordinate as an integer array. Formatted as [x, y].
   */
  public double[] getFrame(int frame) {
    frame = frame - startTime;
    return new double[]{this.fromX + (incrementX * frame), this.fromY + (incrementY * frame)};
  }

  @Override
  public String toString() {
    return String.format("%s moves from (%.1f, %.1f) to (%.1f, %.1f) from t=%s to t=%s.", shapeName,
        this.fromX, this.fromY,
        toX, toY, startTime, endTime);
  }
}
