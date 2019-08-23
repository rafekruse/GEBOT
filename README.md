# GEBOT

GEBOT is a WIP automated trading AI for the MMORPG Old School Runescape. It relies on a public API created by OSBuddy that tracks current trading prices of all items that can be traded on Runescapes in-game market, the [Grand Exchange](http://services.runescape.com/m=itemdb_oldschool/). This repository has some files intentially missing to avoid abuse and botting, the goal of the application was to see if a "trading AI" would be possible on in game markets similiarily to real markets.

The application pulls the data from the api then parses the ~20k possible items to a short list that can be sorted down to <8 viable items for purchase.

The first filtering process uses the possible margin, trade volume, needed capital, etc. This filters out the vast majority of items and usually results in a list < 50.

The second filtering process makes use of the constraint programming library [Choco-solver](http://www.choco-solver.org/). The goal of this step is to decide the optimal items based on current capital and open trade windows(a player is limited to 8). This is a form of the knapsack problem that is optimized for maximum profit.

When creating the application I wanted to avoid any tools or OSRS clients that make use of injected code or api hooks. This was a personal limitation to see if I could create an a tool entirely independent of the Runescape client.

This project also has a self made input system for typing and mousue input making use of the Java Robot Class as well as controlling HWNDs
to allow for the program to train and test without dominating control of the mouse.
