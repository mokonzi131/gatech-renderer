gatech-renderer
===============

Rendering pipeline for 3d graphics objects without GPU acceleration
Author: Michael Landes

Contents (HW1_Michael_Landes.zip):

I. Example Rendered Images

    - example_clipping.jpg
    - example_full.jpg
    - example_plane.jpg
    
II. Source

    - src/
    
III. Program
    
    The program is written in Java. I used part of a simplified 2D game engine I have been working on independently, and
    plugged in the 3d pipeline. Therefore, my program has full support of moving objects in a scene. I have the
    framerate set to 30 fps, which can handle the two provided object files well enough.

    1. cd into the dist/ directory
    2. open "renderer.properties" in a text editor, this is where you can configure the variables
    3. when you are done configuring, save "renderer.properties"
    4. run "renderer.bat" which will call java to launch the program