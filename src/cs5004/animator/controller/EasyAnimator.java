package cs5004.animator.controller;

import java.io.IOException;

/**
 * The main class to execute/start the program. Merely creates the controller and passes off from
 * there.
 */
public final class EasyAnimator {

  /**
   * The main method. Takes arguments.
   *
   * @param args the arguments for the program to run correctly.
   * @throws IOException if the controller experiences a write-error.
   */
  public static void main(String[] args) throws IOException {
    AnimationControllerImpl c = new AnimationControllerImpl(args);

    // Hand off to the controller.
    c.run();
  }
}
