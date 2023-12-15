package cs5004.animator.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface for AnimationModelImpl. Maps out the operations that the model performs, such as adding
 * different animations to the total list of animations. Will use a hashmap to represent each frame
 * and what it should look like, i.e. a list of shapes, their coordinates, colors, etc.
 */
public interface AnimationModel {

  /**
   * Builds an initial shape to the animation list.
   */
  void addShape(String shapeName, String shapeType, int width, int height, int x, int y,
      Color color, int startTime, int endTime);

  /**
   * Builds an animation to change shape shapeName to the specified color from startTime to
   * endTime.
   *
   * @param shapeName the name of the shape to change.
   * @param toColor   the end color.
   * @param startTime when the animation starts.
   * @param endTime   when the animation ends.
   */
  void changeColor(String shapeName, Color toColor, int startTime, int endTime);

  /**
   * Builds an animation to change the shape shapeName's size from startTime to endTime.
   *
   * @param shapeName name of the shape to change.
   * @param width     the ending width.
   * @param height    the ending height.
   * @param startTime the starting time.
   * @param endTime   the ending time.
   */
  void changeSize(String shapeName, int width, int height, int startTime, int endTime);

  /**
   * Builds an animation to change the shape coordinate from the current coords to the 'to'
   * coordinates from startTime to endTime.
   *
   * @param shapeName the name of the shape to move.
   * @param toX       the ending X of the animation.
   * @param toY       the ending Y of the animation.
   * @param startTime the start time of the animation.
   * @param endTime   the ending time.
   */
  void changeCoordinate(String shapeName, int toX, int toY, int startTime, int endTime);

  /**
   * Returns a deep copy clone of the animation list after sorting it by time beginning. Is used to
   * generate the regular text version of the animation.
   *
   * @return a deep copy of the animation list, sorted with the beginning time as key. Returns as an
   *     ArrayList of Animations.
   */
  ArrayList<Animation> getAnimationList();

  /**
   * Returns a deep copy clone of the shape list. It's used in the view.
   *
   * @return a deep copy of the shape hashmap.
   */
  HashMap<String, Shape> getShapeList();

  /**
   * The main output for AnimationModelImpl. Returns a hashmap of integer keys representing the
   * frame number, and an ArrayList of shapes representing the shapes with the correct values that
   * should be drawn every frame.
   *
   * @return the hashmap of frame keys and a list of shapes corresponding to the frame in the
   *     animation.
   */
  HashMap<Integer, ArrayList<Shape>> getFinalFrames();

  /**
   * Converts the animation list into a string detailing the animations/shapes. Is basically
   * outputted to show the console output of an animation.
   *
   * @return string representation of an animation.
   */
  String animationListToString();
}
