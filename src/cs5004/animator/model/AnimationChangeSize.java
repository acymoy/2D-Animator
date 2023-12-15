package cs5004.animator.model;


/**
 * Animation object for changing the size of a shape.
 */
public class AnimationChangeSize extends AbstractAnimation {

  private int startWidth;
  private int startHeight;
  private int endWidth;
  private int endHeight;
  private int incrementWidth;
  private int incrementHeight;

  /**
   * Constructor for AnimationChangeSize.
   *
   * @param startTime the starting time of animation.
   * @param endTime   the ending time of animation.
   * @param shapeName the name of the shape animation applies to.
   * @param endWidth  the ending width of the shape.
   * @param endHeight the ending height of the shape.
   * @param shape     the shape object as a reference.
   */
  public AnimationChangeSize(int startTime, int endTime, String shapeName, int endWidth,
      int endHeight, Shape shape) {
    super(startTime, endTime, shapeName, shape);
    this.startWidth = shape.getWidth();
    this.startHeight = shape.getHeight();
    this.endWidth = endWidth;
    this.endHeight = endHeight;
    this.type = "changeSize";
  }

  /**
   * Returns the current starting width.
   *
   * @return the starting width.
   */
  public int getStartWidth() {
    return this.startWidth;
  }

  /**
   * Return the current starting height.
   *
   * @return the starting height.
   */
  public int getStartHeight() {
    return this.startHeight;
  }

  /**
   * Returns the ending width.
   *
   * @return the ending width.
   */
  public int getEndWidth() {
    return this.endWidth;
  }

  /**
   * Returns the ending height.
   *
   * @return the ending height.
   */
  public int getEndHeight() {
    return this.endHeight;
  }


  /**
   * Sets the starting width for future calculations.
   *
   * @param startWidth the starting width to set.
   */
  public void setStartWidth(int startWidth) {
    this.startWidth = startWidth;
  }

  /**
   * Sets the starting height for future calculations.
   *
   * @param startHeight the starting height to set.
   */
  public void setStartHeight(int startHeight) {
    this.startHeight = startHeight;
  }


  @Override
  public Animation deepCopy() {
    return new AnimationChangeSize(startTime, endTime, shapeName, endWidth, endHeight, shape);
  }

  /**
   * Calculates the incremental change over time. Used a little later since animations can happen
   * and change the original value of the shape.
   *
   * @param currSize the current size of the object.
   */
  public void calcIncrement(int[] currSize) {
    this.incrementWidth = (endWidth - currSize[0]) / totalTime;
    this.incrementHeight = (endHeight - currSize[1]) / totalTime;
    this.startHeight = currSize[1];
    this.startWidth = currSize[0];
  }

  /**
   * Gets the dimensions of the shape at a specific frame.
   *
   * @param frame the given frame.
   * @return the dimensions of the shape as an integer array, ie [width, height].
   */
  public int[] getFrame(int frame) {
    frame = frame - startTime;
    return new int[]{this.startWidth + (incrementWidth * frame),
        this.startHeight + (incrementHeight * frame)};
  }

  @Override
  public String toString() {
    return String.format("%s changes size from w=%s, h=%s to w=%s, h=%s from t=%s to t=%s.",
        shapeName, shape.getWidth(), shape.getHeight(), endWidth, endHeight, startTime, endTime);
  }
}
