package cs5004.animator.model;

/**
 * Abstract class for Shapes. Extended by Ellipse and Rectangle. Holds the overlapping methods for
 * those objects.
 */
public abstract class AbstractShape implements Shape {

  protected String name;
  protected int width;
  protected int height;
  protected String shapeType;
  protected Color color;
  protected double x;
  protected double y;
  protected boolean changedColor;
  protected boolean changedCoord;
  protected boolean changedSize;

  /**
   * Constructor for AbstractShape. Takes in the overlapping attributes of the shapes and
   * initializes them.
   *
   * @param name      the name of the object.
   * @param width     the width.
   * @param height    height.
   * @param shapeType the shape type (ellipse or rectangle) as a string.
   * @param color     the color as a color object.
   * @param x         the x coordinate.
   * @param y         the y coordinate.
   */
  public AbstractShape(String name, int width, int height, String shapeType, Color color, double x,
      double y) {
    this.name = name;
    this.width = width;
    this.height = height;
    this.shapeType = shapeType;
    this.color = color;
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException(
          "Cannot generate shape: dimensions cannot be less than or equal to zero.");
    }
    this.x = x;
    this.y = y;

    this.changedColor = false;
    this.changedCoord = false;
    this.changedSize = false;
  }

  @Override
  public boolean getChangedSize() {
    return this.changedSize;
  }

  @Override
  public boolean getChangedCoord() {
    return this.changedCoord;
  }

  @Override
  public boolean getChangedColor() {
    return this.changedColor;
  }

  @Override
  public void setChangedColor(boolean b) {
    this.changedColor = b;
  }

  @Override
  public void setChangedCoord(boolean b) {
    this.changedCoord = b;
  }

  @Override
  public void setChangedSize(boolean b) {
    this.changedSize = b;
  }

  @Override
  public String getShapeType() {
    return this.shapeType;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public void setColor(Color c) {
    this.color = c;
  }

  @Override
  public void setSize(int h, int w) {
    this.height = h;
    this.width = w;
  }

  @Override
  public void setCoord(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("%s %s.\nDim: %dx%d\nColor: %s\nCoords:%.2f, %.2f\n", getShapeType(), name,
        width, height, color, x, y);
  }

}