Boid Flocking Simulation

Simple bird-like(boids comes from bird-oid object) flocking simulation using simple rules that leads to a very complex outcome

Demonstration
https://youtu.be/3svyb0W7R9Q

The Rules:
Seperation: each boid wants to stay a certain distance away from others
Alignment: each boid aligns its direction and speed with boids around itself to create a cohesive direction
Cohesion: boids steer towards the average direction of its neighbors
These simple rules lead to extremely complex lifelike behavior

Python and pygame library used for implementation

How to run the simulation:
pip install pygame
python boids.py
