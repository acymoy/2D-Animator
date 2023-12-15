# 2D Animator
## CS5004: Object Oriented Design

Creating animations takes time, and one must learn the necessary tools that are often difficult to master (eg using Flash, creating web animations, etc.)
This project is a Java-Swing-based application that helps to create simple but effective 2D animations from shapes from a description of animation. 
Here are a few examples:

#### A simple 2D Animation:
![smalldemo](https://github.com/acymoy/2D-Animator/assets/105390880/f7d2030f-cb2e-4409-a7d5-3b2e105cfb6a)

#### The Towers of Hanoi:
![toh-5](https://github.com/acymoy/2D-Animator/assets/105390880/88c6a639-9c6a-4787-ad31-3f9613f33793)

#### Big Bang/Crunch:
![big-bang-big-crunch](https://github.com/acymoy/2D-Animator/assets/105390880/43dce337-c451-402c-99f8-a2c7ad87ce91)

### Running the Application
To run the app, the program takes inputs as CLI arguments. The arguments are in the form of:
`-in "name-of-animation-file" -view "type-of-view" -out "where-output-show-go" -speed "integer-ticks-per-second"`

Example command: `java -jar Final_Project.jar -in hanoi.txt -speed 50 -view playback`

- Input files are text files (some examples are already included in the repo)
- View can be one of four types: `playback` (player controls), `visual` (just the animation), `text`, or `svg`.
- Input file and view are both mandatory. Default output is System.out, and default speed is 1 tick per second (if that specific view requires it).

Examples of valid CLIs:
- `-in smalldemo.txt -view text -speed 2`: use smalldemo.txt for the animation file, and create a text view with its output going to System.out, and a speed of 2 ticks per second.

- `-view svg -out out.svg -in buildings.txt`: use buildings.txt for the animation file, and create an SVG view with its output going to the file out.svg, with a speed of 1 tick per second.

- `-in smalldemo.txt -view text`: use smalldemo.txt for the animation file, and create a text view with its output going to System.out.

- `-in smalldemo.txt -speed 50 -view visual`: use smalldemo.txt for the animation file, and create a visual view to show the animation at a speed of 50 ticks per second.

### Description of Animation
Here is an example description of an animation: 

```
Create red rectangle R with corner at (200,200), width 50 and height 100
Create blue oval C with center at (500,100), radius 60 and 30
 
R appears at time t=1 and disappears at time t=100
C appears at time t=6 and disappears at time t=100
 
R moves from (200,200) to (300,300) from time t=10 to t=50
C moves from (500,100) to (500,400) from time t=20 to t=70
C changes from blue to green from time t=50 to t=80
R moves from (300,300) to (200,200) from time t=70 to t=100
R changes width from 50 to 25 from time t=51 to t=70
```

The application can use these descriptions to:
- Show the animation in various ways: interactively play it, describe it on a timeline, or export the animation as a Flash movie.
- Allow a user to edit or change speed.
- Produce a verbose description for what the animation looks like (for the visually impaired).

### The Structure
The animator was built using the classic MVC architecture. 
