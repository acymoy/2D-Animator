package cs5004.animator.view;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Outputs the animation in a text format, whether that be into the console or a file name.
 */
public class TextAnimation implements AnimationOutput {

  private String output;
  private String fileName;

  /**
   * Constructor for TextAnimation.
   *
   * @param output   the data to output about the animation.
   * @param fileName the file name output. If left empty, will output to the console.
   */
  public TextAnimation(String output, String fileName) {
    if (output == null) {
      throw new IllegalArgumentException("Data is null.");
    }
    this.output = output;
    this.fileName = fileName;
  }

  @Override
  public void out() throws IOException {
    if (fileName == null || fileName.equals("")) {
      System.out.println(output);
    } else {
      try {
        FileWriter w = new FileWriter(this.fileName);
        w.write(output);
        w.close();
      } catch (IOException e) {
        throw new IOException("IOException: File write fail.");
      }
    }
  }
}
