import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.Color;
import org.junit.Before;
import org.junit.Test;


/**
 * Testing for the model.
 */
public class TestModel {

  private AnimationModelImpl m;

  /**
   * Setup for testing.
   */
  @Before
  public void init() {
    m = new AnimationModelImpl();
  }

  /**
   * Testing regularly adding animations, whether list sorts, etc.
   */
  @Test
  public void addAnimations() {
    m.addShape("r1", "rectangle", 2, 2, 0, 0, new Color(0, 0, 0), 0, 100);
    m.addShape("e1", "ellipse", 35, 20, 1, 4, new Color(255, 0, 0), 2, 95);

    m.changeColor("e1", new Color(0, 255, 0), 5, 50);

    assertEquals("Rectangle r1 is created from t=0 to t=100.\n"
            + "Ellipse e1 is created from t=2 to t=95.\n"
            + "\n"
            + "e1 changes from color (R:255 G:0 B:0) to (R:0 G:255 B:0) from t=5 to t=50.",
        m.animationListToString()); // a regular toString call after adding.

    m.addShape("aklsjf", "ellipse", 20, 20, 5, 5, new Color(255, 10, 10), 0, 5);

    assertEquals("Rectangle r1 is created from t=0 to t=100.\n"
            + "Ellipse aklsjf is created from t=0 to t=5.\n"
            + "Ellipse e1 is created from t=2 to t=95.\n"
            + "\n"
            + "e1 changes from color (R:255 G:0 B:0) to (R:0 G:255 B:0) from t=5 to t=50.",
        m.animationListToString());

    m.changeSize("r1", 100, 100, 1, 11);

  }

  @Test(expected = IllegalArgumentException.class)
  public void addAnimationError() {
    try {
      m.addShape("rectangle", "reccangle", 1, 3, 0, 0, new Color(0, 0, 0), 100, 101);
      fail();
    } catch (IllegalArgumentException e) {
      m.addShape("ellipse", "easdflag", 1, 3, 0, 2, new Color(10, 10, 10), 0, 403);
    }
  }

}
