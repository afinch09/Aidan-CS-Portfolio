Conway's Game Of Life:

Demo:
https://www.youtube.com/shorts/Y3RDWAi_8n8

Conways game of life is a simple game based on cellular automaton
Each round each cell is checked for simple rules and then marked dead or alive
The rules are as follows:
An alive cell with less than 2 neighbors dies
An alive cell with 2-3 neighbors lives
An alive cell with more than 3 neighbors dies
A dead cell with exactly 3 neighbors comes alive

Through these simples rules extremely complex patterns can emerge
Some of my favorite applications are a functioning cpu and Conway's Game of Life in Conway's Game of Life

How to run:
Have a jdk installed
Create a java project
Place all code into /src folder
Run GOLRunner file

Personal touches:
Increase/decrease fps
Pause(prints current game stats)
Cell age coloration
Board file saving and loading
Mouse interaction to add alive cells or kill cells
On creation a board can be randomized with Math.random or an lcg
Toroidal wrapping(cells come around at the borders)
