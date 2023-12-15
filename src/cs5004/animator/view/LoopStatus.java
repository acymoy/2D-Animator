package cs5004.animator.view;

/**
 * Basically acts as a variable to store whether the loop button has been clicked yet.
 */
public class LoopStatus {

  private static boolean loopStatus;

  /**
   * Constructor that initializes the loopStatus to false.
   */
  public LoopStatus() {
    loopStatus = false;
  }

  /**
   * Sets the loop status.
   *
   * @param b to-set value.
   */
  public static void setLoopStatus(boolean b) {
    loopStatus = b;
  }

  /**
   * Gets the loop status.
   *
   * @return the boolean loop status.
   */
  public static boolean getLoopStatus() {
    return loopStatus;
  }
}
