package cs5004.animator.model;

/**
 * Represents a general animation that occurs. Animations have starting times and ending times, but
 * have different parameters, which can include changing color, size, coordinates, or even just
 * building a shape to start.
 */
public interface Animation {

  /**
   * Returns the starting time value for the animation.
   *
   * @return the start time for the animation as an integer.
   */
  int getStart();

  /**
   * Returns the ending time value for the animation.
   *
   * @return the end time for the animation as an integer.
   */
  int getEnd();

  /**
   * Returns the shape name the animation is referring to/applied to.
   *
   * @return the shape name that the animation applies to.
   */
  String getShapeName();

  /**
   * Returns the type of shape as a string.
   *
   * @return the type of shape.
   */
  String getType();

  /**
   * Returns a deep copy of the current animation object.
   *
   * @return deep copy of animation object.
   */
  Animation deepCopy();

  /**
   * Sets the shape that the animation has to a different one. Is used in the view to modify the
   * list its given (deep copied).
   *
   * @param s the shape to set it to.
   */
  void setShape(Shape s);

  /**
   * Returns a string representation of the animation.
   *
   * @return string representation of the animation.
   */
  String toString();

}
