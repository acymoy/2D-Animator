package cs5004.animator.view;

import java.io.IOException;

/**
 * Interface for all the views.
 */
public interface AnimationOutput {

  /**
   * The main class to run any view's output.
   *
   * @throws IOException if there is a write error on input/output.
   */
  void out() throws IOException;
}
