package cs5004.animator.model;

/**
 * An animation to change the color of the Shape.
 */
public class AnimationChangeColor extends AbstractAnimation {

  private Color startColor;
  private Color endColor;
  private Color increment;

  /**
   * Constructor for AnimationChangeColor. Takes the coordinates, the shapeName, and the color.
   * Also calculates the incremental change in value every time a frame passes. Can use this to
   * calculate the current supposed value for the animation given a certain frame. DESIGN NOTE: not
   * sure if need to change startTime/endTime to frame time instead, since this would just get the
   * change that would occur every second.
   *
   * @param startTime the starting time of the animation.
   * @param endTime   the endtime of animation.
   * @param shapeName the name of the shape.
   * @param endColor  the ending color.
   */
  public AnimationChangeColor(int startTime, int endTime, String shapeName, Color endColor,
      Shape shape) {
    super(startTime, endTime, shapeName, shape);
    this.startColor = shape.getColor();
    this.endColor = endColor;
    this.type = "changeColor";
  }

  /**
   * Returns the starting color of the animation.
   *
   * @return the starting color.
   */
  public Color getStartColor() {
    return this.startColor;
  }

  /**
   * Returns the ending color of the animation.
   *
   * @return the ending color.
   */
  public Color getEndColor() {
    return this.endColor;
  }

  /**
   * Sets the start color for future calculations.
   *
   * @param c the color to set.
   */
  public void setStartColor(Color c) {
    this.startColor = c;
  }


  @Override
  public Animation deepCopy() {
    return new AnimationChangeColor(startTime, endTime, shapeName, endColor, shape);
  }

  /**
   * Calculates the incremental change in value over time.
   *
   * @param currColor the current color of the shape.
   */
  public void calcIncrement(Color currColor) {
    this.startColor = currColor;
    Color gap = this.startColor.subtract(this.endColor);
    this.increment = new Color(gap.getRed() / totalTime, gap.getGreen() / totalTime,
        gap.getBlue() / totalTime);
  }

  /**
   * Gets the object color at that specific frame.
   *
   * @return the corresponding color of the object in that specific frame.
   */
  public Color getFrame(int frame) {
    frame = frame - startTime;

    return new Color(startColor.getRed() + (increment.getRed() * frame),
        startColor.getGreen() + (increment.getGreen() * frame),
        startColor.getBlue() + (increment.getBlue() * frame));
  }

  @Override
  public String toString() {
    return String.format("%s changes from color %s to %s from t=%s to t=%s.", shapeName, startColor,
        endColor, startTime, endTime);
  }
}
