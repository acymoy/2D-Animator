package cs5004.animator.controller;

/**
 * Object to hold the parsed attributes of the given animations.
 */
public class AnimationData {

  public String animationType;
  public String shapeName;
  public String shapeType;
  public int t1;
  public int t2;
  public int w;
  public int h;
  public int r;
  public int g;
  public int b;
  public int x;
  public int y;

  /**
   * Constructor when adding an AddShape animation.
   *
   * @param type the animation type.
   * @param name the name of the shape.
   * @param w    width.
   * @param h    height.
   * @param t1   starting time.
   * @param t2   ending time.
   * @param r    red value.
   * @param g    green value.
   * @param b    blue value.
   * @param x    x coordinate.
   * @param y    y coordinate.
   */
  public AnimationData(String animationType, String type, String name, int w, int h, int t1, int t2,
      int r, int g, int b, int x, int y) {
    this.animationType = animationType;
    this.shapeName = name;
    this.shapeType = type;
    this.t1 = t1;
    this.t2 = t2;
    this.w = w;
    this.h = h;
    this.r = r;
    this.g = g;
    this.b = b;
    this.x = x;
    this.y = y;
  }

  /**
   * Constructor when adding a ChangeColor animation.
   *
   * @param type the animation type.
   * @param name the name of the shape.
   * @param r    the red value.
   * @param g    green value.
   * @param b    blue value.
   * @param t1   start time.
   * @param t2   end time.
   */
  public AnimationData(String type, String name, int r, int g, int b, int t1, int t2) {
    this.animationType = type;
    this.shapeName = name;
    this.r = r;
    this.g = g;
    this.b = b;
    this.t1 = t1;
    this.t2 = t2;
  }

  /**
   * Constructor when adding a ChangeCoordinate or a ChangeSize animation, since both take four
   * arguments.
   *
   * @param type the animation type.
   * @param name the name of the shape.
   * @param d1   dimension one.
   * @param d2   dimension two.
   * @param t1   time start.
   * @param t2   time end.
   */
  public AnimationData(String type, String name, int d1, int d2, int t1, int t2) {
    this.animationType = type;
    this.shapeName = name;
    this.t1 = t1;
    this.t2 = t2;
    if (this.animationType.equals("ChangeSize")) {
      this.w = d1;
      this.h = d2;
    } else if (this.animationType.equals("ChangeCoordinate")) {
      this.x = d1;
      this.y = d2;
    }
  }

  /**
   * Converts the data into a string.
   *
   * @return string representation of the object.
   */
  public String toString() {
    return this.animationType + this.shapeName + shapeType;
  }

}
