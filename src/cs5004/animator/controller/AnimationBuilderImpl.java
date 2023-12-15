package cs5004.animator.controller;

import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementation of AnimationBuilder. Parses in and creates an animation from file.
 */
public class AnimationBuilderImpl implements AnimationBuilder {

  private AnimationModelImpl m;
  private HashMap<String, Integer> appearTime;
  private HashMap<String, Integer> disappearTime;
  private HashMap<String, int[]> appearCoordinate;
  private HashMap<String, int[]> appearDimensions;
  private HashMap<String, Color> appearColor;
  private ArrayList<AnimationData> animationList;
  private HashMap<String, String> declaredShapes;

  /**
   * Constructor for AnimationBuilderImpl. Initializes everything needed.
   */
  public AnimationBuilderImpl() {
    this.m = new AnimationModelImpl();
    this.animationList = new ArrayList<>();
    this.declaredShapes = new HashMap<>();
    this.appearTime = new HashMap<>();
    this.disappearTime = new HashMap<>();
    this.appearCoordinate = new HashMap<>();
    this.appearDimensions = new HashMap<>();
    this.appearColor = new HashMap<>();
  }

  @Override
  public Object build() {
    this.addDeclaredShapes();
    for (AnimationData t : animationList) {
      switch (t.animationType) {
        case "AddShape":
          m.addShape(t.shapeName, t.shapeType, t.w, t.h, t.x, t.y, new Color(t.r, t.g, t.b), t.t1,
              t.t2);
          break;
        case "ChangeColor":
          m.changeColor(t.shapeName, new Color(t.r, t.g, t.b), t.t1, t.t2);
          break;
        case "ChangeCoordinate":
          m.changeCoordinate(t.shapeName, t.x, t.y, t.t1, t.t2);
          break;
        case "ChangeSize":
          m.changeSize(t.shapeName, t.w, t.h, t.t1, t.t2);
          break;
        default:
          throw new IllegalStateException("Error: Cannot build animation.");

      }
    }
    return m;
  }

  @Override
  public AnimationBuilder setBounds(int x, int y, int width, int height) {
    m.setBounds(x, y, width, height);
    return this;
  }

  @Override
  public AnimationBuilder declareShape(String name, String type) {
    declaredShapes.put(name, type);
    return this;
  }

  @Override
  public AnimationBuilder addMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1,
      int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    // if initial color doesn't exist, add it
    if (!appearColor.containsKey(name)) {
      appearColor.put(name, new Color(r1, g1, b1));
    }
    // if initial dimensions don't exist, add it
    if (!appearDimensions.containsKey(name)) {
      appearDimensions.put(name, new int[]{w1, h1});
    }

    // if initial coordinates don't exist, add it
    if (!appearCoordinate.containsKey(name)) {
      appearCoordinate.put(name, new int[]{x1, y1});
    }

    // if appear time in this one doesn't exist, add it
    if (!appearTime.containsKey(name)) {
      appearTime.put(name, t1);
    } else {
      // if it does exist but this animation happens before, change initial attributes of shape.
      if (appearTime.get(name) > t1) {
        appearTime.replace(name, t1);
        appearColor.replace(name, new Color(r1, g1, b1));
        appearDimensions.replace(name, new int[]{w1, h1});
        appearCoordinate.replace(name, new int[]{x1, y1});
      }
    }
    // same deal as above, but for disappear time.
    if (!disappearTime.containsKey(name)) {
      disappearTime.put(name, t2);
    } else {
      if (disappearTime.get(name) < t2) {
        disappearTime.replace(name, t2);
      }
    }

    //parse for size
    if (w1 != w2 || h1 != h2) {
      animationList.add(new AnimationData("ChangeSize", name, w2, h2, t1, t2));
    }

    // parse for motion
    if (x1 != x2 || y1 != y2) {
      animationList.add(new AnimationData("ChangeCoordinate", name, x2, y2, t1, t2));
    }

    // parse for color
    if (r1 != r2 || g1 != g2 || b1 != b2) {
      animationList.add(new AnimationData("ChangeColor", name, r2, g2, b2, t1, t2));
    }
    return this;
  }

  /**
   * Adds AddShape transformation objects after all the given motion animations are added, so we
   * have data that can be used by the model.
   */
  private void addDeclaredShapes() {
    declaredShapes.forEach((k, v) -> {
      animationList.add(0,
          new AnimationData("AddShape", v, k, appearDimensions.get(k)[0],
              appearDimensions.get(k)[1], appearTime.get(k), disappearTime.get(k),
              appearColor.get(k).getRed(), appearColor.get(k).getGreen(),
              appearColor.get(k).getBlue(), appearCoordinate.get(k)[0],
              appearCoordinate.get(k)[1]));
    });
  }
}

