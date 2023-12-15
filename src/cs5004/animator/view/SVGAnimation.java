package cs5004.animator.view;

import cs5004.animator.model.Animation;
import cs5004.animator.model.AnimationAddShape;
import cs5004.animator.model.AnimationChangeColor;
import cs5004.animator.model.AnimationChangeCoordinate;
import cs5004.animator.model.AnimationChangeSize;
import cs5004.animator.model.Shape;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Generates an SVG file/string representation of the animation.
 */
public class SVGAnimation implements AnimationOutput {

  private HashMap<String, String> svgList;

  private HashMap<String, Shape> shapeList;
  private ArrayList<Animation> animationList;
  private String svgString;
  private String outFile;
  private int time;

  /**
   * Constructor for an SVG Animation. Takes in the necessary information to generate a
   * document/string that maps out an SVG animation.
   *
   * @param animationList the list of animations to perform.
   * @param shapeList     the list of shapes in a hashmap of names and objects.
   * @param outFile       the name of the output file.
   * @param time          the time multiplier. Higher makes it slower.
   */
  public SVGAnimation(ArrayList<Animation> animationList, HashMap<String, Shape> shapeList,
      String outFile, int time) {
    this.svgList = new HashMap<>();
    this.shapeList = shapeList;
    this.animationList = animationList;
    svgString = "";
    this.outFile = outFile;
    this.time = time;
  }

  @Override
  public void out() throws IOException {
    String text = convertToSVG();
    if (this.outFile == null) {
      System.out.println(text);
    } else {
      try {
        FileWriter writer = new FileWriter(this.outFile);
        writer.write(text);
        writer.close();
      } catch (IOException ioe) {
        throw new IOException("Error: SVG write failed.");
      }
    }
  }

  /**
   * Constructs the actual SVG string. The meat of the class.
   *
   * @return the string representation/document of an SVG animation.
   */
  private String convertToSVG() {
    this.svgString += "<svg width=\"1200\" height=\"1200\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n\n";

    for (Animation v : animationList) {
      if (v.getType().equals("addShape")) {
        addShape((AnimationAddShape) v);
      } else if (v.getType().equals("changeColor")) {
        changeColor((AnimationChangeColor) v);
      } else if (v.getType().equals("changeCoordinate")) {
        changeCoordinate((AnimationChangeCoordinate) v);
      } else if (v.getType().equals("changeSize")) {
        changeSize((AnimationChangeSize) v);
      }
    }

    svgList.forEach((k, v) -> {
      svgString += v;
      if (shapeList.get(k).getShapeType().equals("rectangle")) {
        svgString += "</rect>\n";
      } else {
        svgString += "</ellipse>\n";
      }
    });

    svgString += "</svg>\n";
    return svgString;
  }

  /**
   * Processes an AddShape animation to SVG format.
   *
   * @param a the AddShape animation.
   */
  private void addShape(AnimationAddShape a) {
    String shapeType;
    String x;
    String y;
    String width;
    String height;
    if (shapeList.get(a.getShapeName()).getShapeType().equals("rectangle")) {
      shapeType = "rect";
      x = "x";
      y = "y";
      width = "width";
      height = "height";
    } else {
      shapeType = "ellipse";
      x = "cx";
      y = "cy";
      width = "rx";
      height = "ry";
    }

    String svgAnimation = String.format("<%s id=\"%s\" %s=\"%.0f\" %s=\"%.0f\" %s=\"%.0f\" "
            + "%s=\"%.0f\" fill=\"rgb(%.0f,%.0f,%.0f)\" visibility=\"visible\" >\n",
        shapeType, a.getShapeName(), x, shapeList.get(a.getShapeName()).getX(),
        y, shapeList.get(a.getShapeName()).getY(), width,
        (double) shapeList.get(a.getShapeName()).getWidth(), height,
        (double) shapeList.get(a.getShapeName()).getHeight(),
        (double) shapeList.get(a.getShapeName()).getColor().getRed(),
        (double) shapeList.get(a.getShapeName()).getColor().getGreen(),
        (double) shapeList.get(a.getShapeName()).getColor().getBlue());

    this.svgList.put(a.getShapeName(), svgAnimation);

  }

  /**
   * Converts a ChangeColor animation to an SVG format string.
   *
   * @param a the ChangeColor animation.
   */
  private void changeColor(AnimationChangeColor a) {
    double time = (a.getEnd() - a.getStart()) * this.time;
    double start = a.getStart() * this.time;
    double startRed = shapeList.get(a.getShapeName()).getColor().getRed();
    double startGreen = shapeList.get(a.getShapeName()).getColor().getGreen();
    double startBlue = shapeList.get(a.getShapeName()).getColor().getBlue();
    double endRed = a.getEndColor().getRed();
    double endGreen = a.getEndColor().getGreen();
    double endBlue = a.getEndColor().getBlue();

    String svgAnimation = String.format("\t<animate attributeType="
            + "\"xml\" begin=\"%.0fms\" dur=\"%.0fms\" "
            + "attributeName=\"fill\" from=\"rgb(%.0f,%.0f,%.0f)\" "
            + "to=\"rgb(%.0f,%.0f,%.0f)\" fill=\"freeze\" />\n",
        start, time, startRed, startGreen, startBlue, endRed, endGreen, endBlue);

    String prev = this.svgList.get(a.getShapeName());
    this.svgList.replace(a.getShapeName(), prev + svgAnimation);
    this.shapeList.get(a.getShapeName()).setColor(a.getEndColor());
  }

  /**
   * Converts a ChangeCoordinate animation into an SVG format string.
   *
   * @param a the ChangeCoordinate animation.
   */
  private void changeCoordinate(AnimationChangeCoordinate a) {
    String a1;
    String a2;
    String svgAnimation = "";
    if (shapeList.get(a.getShapeName()).getShapeType().equals("rectangle")) {
      a1 = "x";
      a2 = "y";
    } else {
      a1 = "cx";
      a2 = "cy";
    }
    double time = (a.getEnd() - a.getStart()) * this.time;
    double start = a.getStart() * this.time;
    double fromX = shapeList.get(a.getShapeName()).getX();
    double fromY = shapeList.get(a.getShapeName()).getY();
    if (a.getToX() != shapeList.get(a.getShapeName()).getX()) {
      svgAnimation += String.format("\t<animate attributeType=\""
              + "xml\" begin=\"%.0fms\" dur=\"%.0fms\" "
              + "attributeName=\"%s\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
          start, time, a1, fromX, a.getToX());
    }
    if (a.getToY() != shapeList.get(a.getShapeName()).getY()) {
      svgAnimation += String.format("\t<animate attributeType=\""
              + "xml\" begin=\"%.0fms\" dur=\"%.0fms\" "
              + "attributeName=\"%s\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
          start, time, a2, fromY, a.getToY());
    }
    String prev = this.svgList.get(a.getShapeName());
    this.svgList.replace(a.getShapeName(), prev + svgAnimation);
    this.shapeList.get(a.getShapeName()).setCoord(a.getToX(), a.getToY());
  }

  /**
   * Converts a ChangeSize animation to an SVG format string.
   *
   * @param a the ChangeSize animation.
   */
  private void changeSize(AnimationChangeSize a) {
    String svgAnimation = "";
    String attribute1;
    String attribute2;

    if (shapeList.get(a.getShapeName()).getShapeType().equals("rectangle")) {
      attribute1 = "width";
      attribute2 = "height";
    } else {
      attribute1 = "rx";
      attribute2 = "ry";
    }

    double time = (a.getEnd() - a.getStart()) * this.time;
    double start = a.getStart() * this.time;
    double startWidth = shapeList.get(a.getShapeName()).getWidth();
    double startHeight = shapeList.get(a.getShapeName()).getHeight();

    if (a.getStartWidth() != a.getEndWidth()) {
      svgAnimation += String.format(
          "\t<animate attributeType=\"" + "xml\" begin=\"%.0fms\" dur=\"%.0fms\" "
              + "attributeName=\"%s\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
          start, time, attribute1, startWidth,
          (double) a.getEndWidth());
    }
    if (a.getStartHeight() != a.getEndHeight()) {
      svgAnimation += String.format(
          "\t<animate attributeType=\"" + "xml\" begin=\"%.0fms\" dur=\"%.0fms\" "
              + "attributeName=\"%s\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
          start, time, attribute2, startWidth,
          (double) a.getEndWidth());
    }

    String prev = this.svgList.get(a.getShapeName());
    this.svgList.replace(a.getShapeName(), prev + svgAnimation);
    this.shapeList.get(a.getShapeName()).setSize(a.getEndWidth(), a.getEndHeight());
  }
}
