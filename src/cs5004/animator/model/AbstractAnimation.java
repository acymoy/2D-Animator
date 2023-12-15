package cs5004.animator.model;

/**
 * Abstract class that inherits an Animation. Defines the common functionality between all animation
 * subclasses.
 */
public abstract class AbstractAnimation implements Animation {

  protected int startTime;
  protected int endTime;
  protected String shapeName;
  protected Shape shape;
  protected String type;

  protected int totalTime;

  /**
   * Constructor for AbstractAnimation. Takes the default valid animation parameters, listed below.
   *
   * @param startTime start time of animation.
   * @param endTime   end time of animation.
   * @param shapeName name of shape animation is being performed on.
   * @param shape     the shape object.
   */
  public AbstractAnimation(int startTime, int endTime, String shapeName, Shape shape) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.shapeName = shapeName;
    this.shape = shape;
    this.totalTime = endTime - startTime;
  }

  @Override
  public int getStart() {
    return this.startTime;
  }

  @Override
  public int getEnd() {
    return this.endTime;
  }

  @Override
  public String getShapeName() {
    return this.shapeName;
  }

  @Override
  public String getType() {
    return this.type;
  }

  @Override
  public void setShape(Shape s) {
    this.shape = s;
  }
}
