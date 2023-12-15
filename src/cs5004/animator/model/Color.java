package cs5004.animator.model;

/**
 * Represents the color of the shape object/animation. Contains red/green/blue values.
 */
public class Color {

  private int red;
  private int green;
  private int blue;

  /**
   * Constructor for a color.
   *
   * @param red   the R value.
   * @param green G value.
   * @param blue  B value.
   */
  public Color(int red, int green, int blue) {
    if (red > 255 || green > 255 || blue > 255) {
      throw new IllegalArgumentException("RGB values cannot be greater than 255.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Gets the red value.
   *
   * @return the red value.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets the green value.
   *
   * @return the green value.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the blue value.
   *
   * @return the blue value.
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Adds two colors together.
   *
   * @param other the color to add.
   * @return the resulting new color as an object.
   */
  public Color add(Color other) {
    return new Color(this.red + other.red, this.green + other.green, this.blue + other.blue);
  }

  /**
   * Subtracts two colors. Used as OTHER OBJECT - THIS OBJECT.
   *
   * @param other the color to subtract.
   * @return a new color object with the resulting values.
   */
  public Color subtract(Color other) {
    return new Color(other.red - this.red, other.green - this.green, other.blue - this.blue);
  }

  /**
   * Returns sting representation of the color object as 'R:{} G:{} B:{}'.
   *
   * @return string representation.
   */
  public String toString() {
    return "(R:" + this.red + " G:" + this.green + " B:" + this.blue + ")";
  }
}
