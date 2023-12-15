# 2D Animator
## CS5004: Object Oriented Design

Creating animations takes time, and one must learn the necessary tools that are often difficult to master (eg using Flash, creating web animations, etc.)
This project is an application that helps to create simple but effective 2D animations from shapes from a description of animation.
Here are a few examples:

A simple 2D Animation:\
![smalldemo](https://github.com/acymoy/2D-Animator/assets/105390880/f7d2030f-cb2e-4409-a7d5-3b2e105cfb6a)

The Towers of Hanoi:\
![toh-5](https://github.com/acymoy/2D-Animator/assets/105390880/88c6a639-9c6a-4787-ad31-3f9613f33793)

Big Bang/Crunch:\
![big-bang-big-crunch](https://github.com/acymoy/2D-Animator/assets/105390880/43dce337-c451-402c-99f8-a2c7ad87ce91)

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
