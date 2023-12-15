package cs5004.animator.controller;

import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.Shape;
import cs5004.animator.view.AnimationOutput;
import cs5004.animator.view.GraphicsBuilder;
import cs5004.animator.view.SVGAnimation;
import cs5004.animator.view.TextAnimation;
import cs5004.animator.view.VisualBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 * Implementation of the AnimationController interface. Acts as the main controller performing
 * exchanges between the model and view.
 */
public class AnimationControllerImpl extends JFrame implements ActionListener {

  private GraphicsBuilder v;
  private Readable in;
  private String[] args;
  private String fromFile;
  private String toFile;
  private String view;
  private int tempo;
  private int[] bounds;

  /**
   * Constructor for AnimationControllerImpl. Takes a readable to parse in using AnimationReader,
   * then gives the info to the model and creates the view from there.
   *
   * @param args the arguments.
   */
  public AnimationControllerImpl(String[] args)
      throws IOException {
    this.in = null;
    this.fromFile = null;
    this.toFile = null;
    this.view = null;
    this.tempo = 1;
    this.args = args;
    this.bounds = new int[4];
  }

  /**
   * The main controller for the program. Initializes and utilizes the model and the view.
   *
   * @throws IOException if a write-error occurs.
   */
  public void run() throws IOException {
    parseArgs(args);
    this.in = this.getFileIn();

    //createAnimation(m); // adds animation objects to the model.
    AnimationBuilder builder = new AnimationBuilderImpl();

    AnimationModelImpl m = (AnimationModelImpl) AnimationReader.parseFile(this.in, builder);

    this.bounds = m.getBounds();

    HashMap<Integer, ArrayList<Shape>> frames = m.getFinalFrames(); // get the final frame structure

    // Determining the view type and create the view.
    int tempo = 1000 / this.tempo;
    if (view.equalsIgnoreCase("playback")) {
      // System.out.println("bounds " + bounds[2] + " " + bounds[3]);
      this.v = new GraphicsBuilder(frames, new int[]{bounds[2], bounds[3]},
          tempo); // initialize graphics
      v.setListener(this);
      v.startButton();
    } else if (view.equalsIgnoreCase("visual")) {
      VisualBuilder v = new VisualBuilder(frames, tempo, new int[]{bounds[2], bounds[3]});
      v.out();
    } else if (view.equalsIgnoreCase("text")) {
      AnimationOutput v = new TextAnimation(m.animationListToString(), toFile);
      try {
        v.out();
      } catch (IOException e) {
        throw new IOException("Error outputting text to file.");
      }
    } else if (view.equalsIgnoreCase("svg")) {
      AnimationOutput v = new SVGAnimation(m.getAnimationList(), m.getShapeList(), toFile, tempo);
      try {
        v.out();
      } catch (Exception e) {
        throw new IOException("Error generating SVG file.");
      }

    } else {
      throw new IllegalArgumentException("Error: Unable to determine what view.");
    }
  }

  /**
   * Creates and returns a new input stream for the file in.
   *
   * @return a new InputStreamReader of the input file.
   * @throws FileNotFoundException if the file doesn't exist.
   */
  public InputStreamReader getFileIn() throws FileNotFoundException {
    FileInputStream fileInStream;
    try {
      fileInStream = new FileInputStream(fromFile);
    } catch (Exception e) {
      throw new FileNotFoundException("Error: Cannot find in file.");
    }
    return new InputStreamReader(fileInStream);
  }

  /**
   * Reads and processes the arguments passed into the program.
   *
   * @param args the arguments as an array of strings.
   */
  public void parseArgs(String[] args) {
    for (int i = 0; i < args.length; i += 2) {
      switch (args[i]) {
        case "-view":
          this.view = args[i + 1];
          break;
        case "-in":
          this.fromFile = args[i + 1];
          break;
        case "-out":
          this.toFile = args[i + 1];
          break;
        case "-speed":
          this.tempo = Integer.parseInt(args[i + 1]);
          break;
        default:
          throw new IllegalArgumentException("Invalid argument.");
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "start":
        v.startButton();
        break;
      case "pause":
        v.pauseButton();
        break;
      case "restart":
        v.restartButton();
        break;
      case "loop":
        v.loopButton();
        break;
      case "speedup":
        v.speedUpButton();
        break;
      case "speeddown":
        v.speedDownButton();
        break;
      default:
        throw new IllegalStateException("Error: Could not parse button.");
    }
  }
}
