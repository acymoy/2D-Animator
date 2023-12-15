package cs5004.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of the AnimationModel. Handles all the logic regarding generating the data
 * structures necessary to make animations.
 */
public class AnimationModelImpl implements AnimationModel {

  private ArrayList<Animation> animations;
  private HashMap<String, Shape> shapeList;

  private ArrayList<AnimationAddShape> addShapesList;
  private int[] bounds;

  /**
   * Constructor for AnimationModelImpl.
   */
  public AnimationModelImpl() {
    animations = new ArrayList<>();
    shapeList = new HashMap<>();
    addShapesList = new ArrayList<>();
    bounds = new int[4];
  }

  @Override
  public void addShape(String shapeName, String shapeType, int width, int height, int x, int y,
      Color color, int startTime, int endTime) throws IllegalArgumentException {
    Shape shape;
    verifyShape(shapeName, startTime, endTime, width, height);
    switch (shapeType) {
      case "rectangle":
        shape = new Rectangle(shapeName, width, height, color, x, y);
        break;
      case "ellipse":
        shape = new Ellipse(shapeName, width, height, color, x, y);
        break;
      default:
        throw new IllegalArgumentException("Invalid shape type.");
    }
    addShapesList.add(
        new AnimationAddShape(startTime, endTime, x, y, shapeName, shape, color, width, height));
    animations.add(
        new AnimationAddShape(startTime, endTime, x, y, shapeName, shape, color, width, height));
    shapeList.put(shapeName, shape);
  }

  /**
   * Helper method to determine if there is an error adding a shape to the animation (for
   * addShape).
   *
   * @param shapeName the shape name.
   * @throws IllegalArgumentException if the shape attempting to be added have the same name.
   */
  private void verifyShape(String shapeName, int start, int end, int w, int h)
      throws IllegalArgumentException {
    if (shapeList.containsKey(shapeName)) {
      throw new IllegalArgumentException(
          "Error adding shape: two shapes cannot have the same name.");
    }
    if (end <= start) {
      throw new IllegalArgumentException(
          "Error adding shape: end time cannot be less than or equal to start time.");
    }
    if (w <= 0 || h <= 0) {
      throw new IllegalArgumentException(
          "Error adding shape: dimensions cannot be less than or equal to zero.");
    }
  }

  @Override
  public void changeColor(String shapeName, Color toColor, int startTime, int endTime)
      throws IllegalArgumentException {
    try {
      shapeList.get(shapeName);
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot change color: shape does not exist.");
    }
    verifyAnimation("changeColor", shapeName, startTime, endTime);
    animations.add(
        new AnimationChangeColor(startTime, endTime, shapeName, toColor, shapeList.get(shapeName)));
  }

  @Override
  public void changeSize(String shapeName, int width, int height, int startTime, int endTime)
      throws IllegalArgumentException {
    try {
      shapeList.get(shapeName);
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot change size: shape does not exist.");
    }
    verifyAnimation("changeSize", shapeName, startTime, endTime);
    animations.add(new AnimationChangeSize(startTime, endTime, shapeName, width, height,
        shapeList.get(shapeName)));
  }

  @Override
  public void changeCoordinate(String shapeName, int toX, int toY, int startTime, int endTime)
      throws IllegalArgumentException {
    try {
      shapeList.get(shapeName);
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot change coordinate: shape does not exist.");
    }
    verifyAnimation("changeCoordinate", shapeName, startTime, endTime);
    animations.add(new AnimationChangeCoordinate(startTime, endTime, shapeName, toX, toY,
        shapeList.get(shapeName)));
  }

  /**
   * Helper method to determine if there is an error when adding an animation.
   *
   * @param animationType the type of animation as a string.
   * @param shapeName     the shape name.
   * @param startTime     the start time.
   * @param endTime       end time.
   * @throws IllegalArgumentException if the times between the same animation on the same shape
   *                                  overlap.
   */
  private void verifyAnimation(String animationType, String shapeName, int startTime, int endTime)
      throws IllegalArgumentException {
    for (Animation a : animations) {
      if (a.getType() == animationType && shapeName == a.getShapeName()) {
        if ((a.getStart() < startTime && a.getEnd() > startTime) || (startTime < a.getEnd()
            && endTime > a.getEnd())) {
          throw new IllegalArgumentException(
              "Error adding animation: Times overlap same animation.");
        }
      }
    }
    if (startTime >= endTime) {
      throw new IllegalArgumentException(
          "Error adding animation: end time cannot be less than or equal to start time.");
    }
  }

  /**
   * Sorts the list of animations and the added shapes list according to starting time.
   */
  private void sortLists() {
    animations.sort(Comparator.comparing(Animation::getStart));
    addShapesList.sort(Comparator.comparing(Animation::getStart));
  }

  /**
   * Sets the bounds for the window.
   *
   * @param x      the x bound.
   * @param y      the y bound.
   * @param width  width of the window.
   * @param height height of the window.
   */
  public void setBounds(int x, int y, int width, int height) {
    this.bounds = new int[]{x, y, width, height};
  }

  /**
   * Gets the bounds for the window.
   *
   * @return the bounds as an array.
   */
  public int[] getBounds() {
    return this.bounds;
  }

  @Override
  public ArrayList<Animation> getAnimationList() {
    ArrayList<Animation> cl = new ArrayList<>();
    for (Animation a : animations) {
      cl.add(a.deepCopy());
    }
    cl.sort(Comparator.comparing(Animation::getStart));
    return cl;
  }

  @Override
  public HashMap<String, Shape> getShapeList() {
    HashMap<String, Shape> cl = new HashMap<>();
    shapeList.forEach((k, v) -> {
      cl.put(k, v.cloned());
    });
    return cl;
  }

  @Override
  public HashMap<Integer, ArrayList<Shape>> getFinalFrames() {
    HashMap<Integer, HashMap<String, Shape>> shapeFrameList = editShapeList();
    HashMap<Integer, ArrayList<Shape>> finalList = new HashMap<>();
    shapeFrameList.forEach((k, v) ->
        v.forEach((k2, v2) -> {
          if (!finalList.containsKey(k)) {
            finalList.put(k, new ArrayList<>());
          }
          finalList.get(k).add(v2.cloned());
        }));
    // Reverses the list so it paints backwards.
    finalList.forEach((k, v) -> {
      Collections.reverse(v);
    });
    return finalList;
  }

  /**
   * Modifies the current shapeFrameList so that the tween values of animations are applied to the
   * duplicate shapes. Mainly used as a helper method in getFinalFrames().
   *
   * @return the edited shapeFrameList.
   * @throws IllegalStateException if there is an error editing the shape frame list.
   */
  private HashMap<Integer, HashMap<String, Shape>> editShapeList() throws IllegalStateException {
    HashMap<Integer, HashMap<String, Shape>> shapeFrameList = getShapeFrames();
    HashMap<Integer, ArrayList<Animation>> animationList = getAnimationFrames();
    String firstTry = "";

    for (Map.Entry<Integer, HashMap<String, Shape>> frame : shapeFrameList.entrySet()) {
      int frameNum = frame.getKey();

      for (Animation a : animationList.get(frameNum)) {
        if (a.getType().equals("changeColor")) {
          AnimationChangeColor colorChange = (AnimationChangeColor) a;
          if (colorChange.getStart() == frameNum) { // if it's the start of the animation
            colorChange.calcIncrement(
                shapeFrameList.get(frameNum).get(a.getShapeName())
                    .getColor()); // set incremental change
          }
          shapeFrameList.get(frameNum).get(a.getShapeName())
              .setColor(colorChange.getFrame(frameNum));
          shapeFrameList.get(frameNum).get(a.getShapeName()).setChangedColor(true);
        } else if (!shapeFrameList.get(frameNum).get(a.getShapeName()).getChangedColor()) {
          // these try-catches serve to simply ensure an error isn't thrown if the frameNum before
          // current one doesn't exist. No problem if not.
          try {
            Color oldColor = shapeFrameList.get(frameNum - 1).get(a.getShapeName()).getColor();
            shapeFrameList.get(frameNum).get(a.getShapeName()).setColor(oldColor);
          } catch (Exception e) {
            firstTry = "";
          }
        }

        if (a.getType().equals("changeCoordinate")) {
          AnimationChangeCoordinate coordChange = (AnimationChangeCoordinate) a;
          if (coordChange.getStart() == frameNum) { // if it's the start of the animation
            coordChange.calcIncrement(
                new double[]{shapeFrameList.get(frameNum).get(a.getShapeName()).getX(),
                    shapeFrameList.get(frameNum).get(a.getShapeName()).getY()});
          }
          double[] currCoord = coordChange.getFrame(frameNum);
          shapeFrameList.get(frameNum).get(a.getShapeName()).setCoord(currCoord[0], currCoord[1]);
          shapeFrameList.get(frameNum).get(a.getShapeName()).setChangedCoord(true);
        } else if (!shapeFrameList.get(frameNum).get(a.getShapeName()).getChangedCoord()) {
          try {
            double[] prevCoord = {shapeFrameList.get(frameNum - 1).get(a.getShapeName()).getX(),
                shapeFrameList.get(frameNum - 1).get(a.getShapeName()).getY()};
            shapeFrameList.get(frameNum).get(a.getShapeName()).setCoord(prevCoord[0], prevCoord[1]);
          } catch (Exception e) {
            firstTry = "";
          }
        }

        if (a.getType().equals("changeSize")) {
          AnimationChangeSize sizeChange = (AnimationChangeSize) a;
          if (sizeChange.getStart() == frameNum) { // if it's the start of the animation
            sizeChange.calcIncrement(
                new int[]{shapeFrameList.get(frameNum).get(a.getShapeName()).getWidth(),
                    shapeFrameList.get(frameNum).get(a.getShapeName()).getHeight()});
          }
          int[] currSize = sizeChange.getFrame(frameNum); // WIDTH THEN HEIGHT
          shapeFrameList.get(frameNum).get(a.getShapeName())
              .setSize(currSize[1], currSize[0]); // ARGS TAKE H, W
          shapeFrameList.get(frameNum).get(a.getShapeName()).setChangedSize(true);
        } else if (!shapeFrameList.get(frameNum).get(a.getShapeName()).getChangedSize()) {
          try {
            int[] prevSize = {shapeFrameList.get(frameNum - 1).get(a.getShapeName()).getHeight(),
                shapeFrameList.get(frameNum - 1).get(a.getShapeName()).getWidth()};
            shapeFrameList.get(frameNum).get(a.getShapeName()).setSize(prevSize[0], prevSize[1]);
          } catch (Exception e) {
            firstTry = "";
          }
        }
      }
    }
    return shapeFrameList;
  }

  /**
   * Gets a Hashmap of integer keys and hashmap values of the shape name and shape object. Used as a
   * helper method in making the final frame list to animate.
   *
   * @return hashmap of integer keys and a nested hashmap of shape name key and shape object.
   */
  private HashMap<Integer, HashMap<String, Shape>> getShapeFrames() {
    HashMap<Integer, HashMap<String, Shape>> shapeFrameList = new HashMap<>();
    for (Animation a : addShapesList) {
      for (int startTime = a.getStart(); startTime <= a.getEnd(); startTime++) {
        // if the frame key doesn't exist, add it and add the corresponding shape.
        if (!shapeFrameList.containsKey(startTime)) {
          shapeFrameList.put(startTime, new HashMap<String, Shape>());
          shapeFrameList.get(startTime)
              .put(a.getShapeName(), shapeList.get(a.getShapeName()).cloned());
        } else if (!shapeFrameList.get(startTime).containsKey(a.getShapeName())) {
          shapeFrameList.get(startTime)
              .put(a.getShapeName(), shapeList.get(a.getShapeName()).cloned());
        }
      }
    }
    return shapeFrameList;
  }


  /**
   * Gets a HashMap of frame keys and an arraylist of animations to perform every frame. Helper
   * method when trying to make the final list of frames for animating.
   *
   * @return hashmap with integer frame keys and an arraylist of animations for each key.
   */
  private HashMap<Integer, ArrayList<Animation>> getAnimationFrames() {
    HashMap<Integer, ArrayList<Animation>> animationFrameList = new HashMap<>();
    for (Animation a : animations) {
      for (int time = a.getStart(); time <= a.getEnd(); time++) {
        if (!animationFrameList.containsKey(time)) {
          animationFrameList.put(time, new ArrayList<Animation>());
        }
        animationFrameList.get(time).add(a);
      }
    }
    return animationFrameList;
  }

  /**
   * Converts the animation list to a string for output into a file or the console from the view.
   *
   * @return a string version of the animation.
   */
  @Override
  public String animationListToString() {
    String s = "";
    this.sortLists();
    for (Animation a : addShapesList) {
      s += a.toString() + "\n";
    }
    for (Animation a : animations) {
      if (!a.getType().equals("addShape")) {
        s += "\n" + a.toString();
      }
    }
    return s;
  }
}
