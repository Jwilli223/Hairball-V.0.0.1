Main library used was libGDX
-It came with an available "add-on" / another library that works with libGDX and that is Box2D.

As mentioned in my presentation, the other main software I used were texturepacker and unpacking tools, and Tiled
(map editing).

As far as organization-
-You can find all of my code under the "core" panel in android studio.

-For activities- I have different "screens". The start game screen, the actual game, and the game over. They basically just call one-another
and destroy the old one upon calling the new one. There is at least one other way of doing this, I have some code where I attempeted another
route, but the screens method worked best for me. The other method- is a "gameStateManager" where you basically have a stack of activities you
work with, and pop/push the stack as necessary.

-Most of my classes have general functions- update, render, dispose, etc. Update has some sort of timer and I passed dt/ or delta time as the paramater.
Update is used in many cases- for instance updating frames with animations, used with input handling; you can set variables with the time
and make certain actions expire/ commence after a given time, and so on.

-The render method is pretty obvious, making images appear on the screen. Box2d actually has a debug render option, where you can show all
of the created shapes and layers from Tile (will get back to this).

-Dispose is more or less what it sounds like- cleaning up memory when you are done with something. If you don't do this, you will run into all
sorts of problems with the heap/ bufferoverflows, and so on.


-The game allows you to use cameras and there differnt viewports that can give you different aspect ratios/ effects. I used ExtendedViewport
for my game- it looked best to me. You can specify how big of an area the camera shows and where it follows. Mine follows my character on
both the x an y axis. You can also make a game where the camera basically kills you if the back end touched you, and just moves at a constance speed.
That type of game would have been much easier to create, but I wanted to work with enemies and AI.

-Movement is handled with linear velocity type functions, where you can basically say if x >0, you are moving right, <0 left and you can specify
the speed either as an int or float. This can be done for the Y axis as well- only used for jumping in my case. There is also a built in "flipX"
method that can automatically flip the sprite for you (if your sprite images are facing right, can make it face left).

-Most things in the game are handled based off of an x, y axis. My input-handling was quite a difficult task and it is not the best, in that it is not
"universal". It needs to be adjusted between screen sizes. I am sure there is a better way of doing it, but the documentation for libGDX isn't the best
and many things are quite complicated and hard to grasp. I didn't want to create a joystick type thing, so I made all movements/ attacks based off of
postioning and types of clicks. For instance, cursor drag positive x below head is run right, double click under head, left or right shoots fireballs, and
so on.

-Box2D is the physics engine. You can create shapes and "attach" them to things(such as characters). Movements and such can be assigned to them, as 
well as collision and other effects. The shapes are hidden during rendering and these effects make it looks like they are simply happening to what
the shapes are attached to. For instance, the fireballs are just an array of circles preety much that shoot out at a certain speed and if they touch an
enemy "circle" is counts as a hit.. and you can say like if hitCount == 5, die. (see Final questions for more detail)

-Sprites/ Tiled- (See Final Questions)

-One of the most difficult aspects of this project for me was working with the enemy AI. The documentation for libGDX isn't the best and I pretty much
tried to figure things out by trial and error. I spent a great deal of time and effort with the "frank" enemy's AI. I made it so when the character and him
come within a certain distance, he speeds up. I made "knock-back" effects for both the char and frank.. which was very difficult as well.. basically changing
speed with a timer on impact.. and it is difficult making the sprite face the proper direction during this. Lastly, I made the frank sprite increase speed even more
when he has been hit. I transferred over some of this implementation to the spider and left the cat and skeleton with dumb AI.

-As far as issues where I had problems.. well I had many. Throughout the entire project, I have had problems with the "heap". I have changed the xmx size like 10 times. I think
some thing want 512, and in other cases, the system needs more. Anyways, this caused me to think there were problems with my code on several occasions, but it was actually this.
I was also working with the desktop version and the android version. The desktop display is bigger and loads faster, so I started off using that mostly for testing. I had forgot
that android doesn't like capital letters in the filenames for the assets, whereas with the desktop version.. it doesn't matter. Found that I had to "refractor" through android 
studio to change the names... changing the names manually in the folder doesn't work. Also, working with the "build-gradle" was new to me.. pretty much all of the libraries do
their updates this way and you can add new add-ons with this. Was just a big learning curve for me.




