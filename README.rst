Thrall
======

A 2d coop roguelike for great justice

Building
--------

#. Clone the project `https://github.com/narfman0/thrall.git`
#. Open eclipse, go to File / Import, Gradle (STS)
#. Select the cloned directory and click clone model (if necessary)
#. Make sure all projects are clicked to import
#. Click `Finish` to import, download dependencies, and build
#. In the Debug or Run menus, click launcher named `DesktopLauncher`
#. Alternatively, open thrall-desktop, expand src/com/etc, right
   click `DesktopLauncher.java`, and Run or Debug. You will need to
   change the default directory as thrall-android/assets for paths
   to work properly.
   
Network code updates

The process is slightly more compelx for changing the network code.
To enable the eclipse protobuf building:

#. Right click on thrall-android / properties
#. Protocol buffer / compiler
#. Check 'Enable project specific' and 'Compile .proto on save"
#. Check Options / "Generate Java"
#. Change java output directory to `../thrall-core/src`

TODO
----

Phase1

#. Multiplayer (establish connection, pass messages)
#. Basic UI/art
#. Maaaybe externalized db/assets for encounter content

Phase2

#. Generate combat arenas
#. More skill based exercises to determine efficacy of task
#. Raids

Phase3

#. Colonies
#. Elderbeasts (endgame)

License
-------

Copyright Jon Robison 2017

See LICENSE for more details
