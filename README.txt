Easy 2DAnimator
Andrew Moy
8/15/2022

If you want a quick overview, I've included "In Summary" blurbs at the bottom of each section :)

This README lacks a changelog from previous iterations because this is the only iteration that was written in a four day bender of caffeine, console errors, and spaghetti. Apologies in advance :)

When creating this application, I tried to maintain as much abstraction and object oriented design as possible. Here are the design details:

The program is separated into the model, the view, and the controller. The model is responsible with calculating and generating the data structures required to run the program (for all views); the view is responsible for exporting files and the showing of the animation, whether it be textually of visually, and the controller mediates the two and controls the operations.

> The Model

I started with the model. The model is controlled by class AnimationModelImpl, implementing the AnimationModel interface. The implemented model contains several structures: an ArrayList<Animation> (which holds the list of animations to be performed), HashMap<String, Shape> (Name of shapes as keys and the shape object as values), and an ArrayList<AnimationAddShape> (AddShape animations to add to insert into the beginning) to start out. These are initialized in the constructor. The other classes exist to hold data regarding animations and the shapes that the animations are applied to.

There exist in the model methods to add different animations to the data structures (addShape, changeColor, changeCoordinate, and changeSize). After that, we generate a data structures for the view to use. getAnimationList() creates a deepcopy of the animation list and sorts it by start time for the text and svg output; and getFinalFrames() creates a HashMap<Integer, ArrayList<Shapes>> of frame keys and an arraylist of shapes for the visual/playback view. Most of the logic falls into the editShapeList() method, which returns the current frame hashmap list with the values adjusted so it's ready for the view. 

In summary:

- AnimationModelImpl and AnimationModel: class and interface that perform the necessary operations to generate the required data structures. 
- Animation interface and Animation classes: store data regarding the animations to be performed on them. Contain deep copies of the shapes. 
- Shape classes: Contain the attributes of the shapes that the animation has.
- Color: stores information about the color of the shape/animation. Are mostly located in shapes and animation classes.
- Data structures used: HashMap<Integer (frame number), ArrayList<Shapes>> for the playback view. ArrayList<Animation> sorted by beginning time for text and svg output.

> The Controller

The controller is the director of the program. In my rush to finish the project, I actually overlooked the fact that we were given AnimationBuilder and AnimationReader, which ended with me unfortunately creating my own classes to read the data in, consuming a lot of extra time. Regardless, I started with AnimationControllerImpl, which is the main controller for the program. It takes in the arguments of the program and parses them to determine input file, output file, view mode, speed, etc. It invokes the model to calculate the final frames for the playback mode, as well as the outputs for the text and svg formats. It initializes the view and invokes the corresponding mode.

AnimationBuilder and AnimationBuilderImpl processes inputs and creates corresponding animation data, which are stored as AnimationData by the AnimationReader class, then utilized in the created model and passed back into the controller.

Finally, EasyAnimator serves to start the program, create the controller, and hand off control to it. 

In summary:

- AnimationController and AnimationControllerImpl: interface and class that control the program. Invokes the model to generate data structure, and passes that data structure to the view for it to use with whichever format. Also processes the arguments and actions in playback view.
- AnimationBuilder and AnimationBuilderImpl: interface and class that creates the model and passes the data from AnimationReader/AnimationData into it. Returns the model to the controller.
- AnimationReader: Reads/parses the animation data input and stores in AnimationData.
- AnimationData: stores data about animations/shapes.
- EasyAnimator: creates the controller and runs the program.

> The View

The view still has some functionality issues, but works for most animations. All three types implement the AnimationOutput interface, which just contain the method to make them run. 

The GraphicsBuilder and GraphicsDraw method are for the playback view--GraphicsBuilder builds the JPanel and Buttons required for that specific view, as well as implement the timer for it. GraphicsDraw holds the frame hashmap data from the model and repaints the image frame by frame every time repaint() is called. It is assisted by LoopStatus, a static class that holds whether the loop button has been pressed or not.

VisualBuilder is the old view that just initializes on -view visual. Shows the animation and not much else. Same process of initializing the JFrame and everything.

SVGAnimation is responsible for the SVG output of the file. It converts the list of animations to shape objects and adds animations to them. After that, it creates the big string that is made into the SVG output document.

TextAnimation is the easiest of the three--it takes the output string generated from the animationList by the contoller invoking the required method in the model, and saves it into the output file (outputting to console if no output file is specified). 

In summary: 

- AnimationOutput: interface that details the method to run each view. Implemented by GraphicsBuilder, SVGAnimation, and TextAnimation.
- GraphicsBuilder and GraphicsDraw: Builder creates the Panels and buttons and timer, and GraphicsDraw is responsible for repainting the animation at each frame. LoopStatus holds whether the loop button is on/off.
- SVGAnimation: creates and outputs the SVG animation file.
- TextAnimation: creates and outputs the text animation file.
- VisualBuilder: the old view, just shows the animation and nothing else.

That said, there are still a few problems with the program as a whole. They are as follows:

- Bounds are not properly done with the window in terms of x and y.
- When painting certain animations, depending on the way the objects/shapes are parsed in, there are shapes being painted over each other. This is shown in buildings.txt, where the background/moons paint over each other often.
- Testing hasn't been implemented.

Thanks much for reading and for your guys' help over the course of the semester. Have a good rest of your summer!!
