package cs5004.animator.model;

/**
 * Represents a shape object in the animation.
 */
public interface Shape {

  /**
   * Gets whether the size has been changed before.
   *
   * @return boolean of whether size has been changed.
   */
  boolean getChangedSize();

  /**
   * Gets whether the color has been changed before.
   *
   * @return boolean of whether color has been changed before.
   */
  boolean getChangedColor();

  /**
   * Gets whether the coordinates have been changed before.
   *
   * @return boolean of whether coordinates have been changed before.
   */
  boolean getChangedCoord();

  /**
   * Sets the boolean to whether size has been changed.
   *
   * @param b value to change to.
   */
  void setChangedSize(boolean b);

  /**
   * Sets the boolean to whether color has been changed.
   *
   * @param b value to change to.
   */
  void setChangedColor(boolean b);

  /**
   * Sets the boolean to whether coordinate has been changed.
   *
   * @param b value to change to.
   */
  void setChangedCoord(boolean b);

  /**
   * Gets the shape type (Rectangle or Ellipse).
   *
   * @return the shape type as a string.
   */
  String getShapeType();

  /**
   * The name of the object, given in the constructor.
   *
   * @return the name of the object as string.
   */
  String getName();

  /**
   * Returns the width of the object.
   *
   * @return the width as an integer.
   */
  int getWidth();

  /**
   * Returns the height of the object.
   *
   * @return the height as an integer.
   */
  int getHeight();

  /**
   * Returns the current color of the object.
   *
   * @return the color.
   */
  Color getColor();

  /**
   * Returns the x coordinate of the object.
   *
   * @return the X coordinate as an integer.
   */
  double getX();

  /**
   * Returns the y coordinate of the object.
   *
   * @return the Y coordinate as an integer.
   */
  double getY();

  void setColor(Color c);

  void setSize(int w, int h);

  void setCoord(double x, double y);


  /**
   * Returns a cloned object.
   *
   * @return the cloned object.
   */
  Shape cloned();

  /**
   * Converts the shape to a string.
   *
   * @return the string representation of shape.
   */
  String toString();
}
