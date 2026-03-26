import random
import pygame
import math

WIDTH, HEIGHT = 1800, 1000
eps = 1e-6

class Boid:
    def __init__(self, x, y):
        self.pos = pygame.math.Vector2(x, y)
        self.vel = pygame.math.Vector2(random.uniform(-1,1), random.uniform(-1,1))
        if self.vel.length() == 0:
            self.vel = pygame.math.Vector2(1,0)
        self.max_speed = 3
        self.max_force = 0.05

        self.size = 10

    def update(self):
        self.pos += self.vel

        if self.pos.x < 0:
            self.pos.x = WIDTH
        elif self.pos.x > WIDTH:
            self.pos.x = 0

        if self.pos.y < 0:
            self.pos.y = HEIGHT
        elif self.pos.y > HEIGHT:
            self.pos.y = 0

    def apply_force(self, force):
        self.vel += force
        if self.vel.length() > eps:
            if self.vel.length() > self.max_speed:
                self.vel.scale_to_length(self.max_speed)


    def seperation(self, boids, radius=25):
        steering = pygame.math.Vector2()
        total = 0

        for other in boids:
            if other is not self:
                dist = self.pos.distance_to(other.pos)
                if dist < radius and dist > 0:
                    diff = self.pos - other.pos
                    diff /= dist
                    steering += diff
                    total += 1
        if total > 0:
            steering /= total
            if steering.length() > eps:
                steering.scale_to_length(self.max_speed)
                steering -= self.vel
                if steering.length() > self.max_force and steering.length() > eps:
                    steering.scale_to_length(self.max_force)

        return steering

    def alignment(self, boids, radius=50):
        steering = pygame.math.Vector2()
        total = 0

        for other in boids:
            if other is not self and self.pos.distance_to(other.pos) < radius:
                steering += other.vel
                total += 1

        if total > 0:
            steering /= total
            if steering.length() > 0:
                steering.scale_to_length(self.max_speed)
                steering -= self.vel
                if steering.length() > self.max_force and steering.length() > 0:
                    steering.scale_to_length(self.max_force)

        return steering

    def cohesion(self, boids, radius=50):
        steering = pygame.math.Vector2()
        total = 0

        for other in boids:
            if other is not self and self.pos.distance_to(other.pos) < radius:
                steering += other.pos
                total += 1

        if total > 0:
            steering /= total
            steering -= self.pos
            if steering.length() > eps:
                steering.scale_to_length(self.max_speed)
                steering -= self.vel
                if steering.length() > self.max_force and steering.length() > eps:
                    steering.scale_to_length(self.max_force)

        return steering

    def flock(self, boids):
        sep = self.seperation(boids) * 1.5
        ali = self.alignment(boids) * 1.0
        coh = self.cohesion(boids) * 1.0

        self.apply_force(sep)
        self.apply_force(ali)
        self.apply_force(coh)

    def draw(self, screen):
        angle = math.degrees(math.atan2(-self.vel.y, self.vel.x))

        p1 = pygame.math.Vector2(self.size, 0)
        p2 = pygame.math.Vector2(-self.size * 0.5, self.size * 0.5)
        p3 = pygame.math.Vector2(-self.size * 0.5, -self.size * 0.5)

        p1 = p1.rotate(angle)
        p2 = p2.rotate(angle)
        p3 = p3.rotate(angle)

        p1 += self.pos
        p2 += self.pos
        p3 += self.pos

        pygame.draw.polygon(screen, (255, 255, 255), [p1, p2, p3])

class Flock:
    def __init__(self, count):
        self.boids = [Boid(random.randint(0, WIDTH), random.randint(0, HEIGHT)) for _ in range(count)]

    def update(self):
        for b in self.boids:
            b.flock(self.boids)
        for b in self.boids:
            b.update()
            b.draw(screen)

pygame.init()

screen = pygame.display.set_mode((WIDTH, HEIGHT))

pygame.display.set_caption("Boids")

BACKGROUND_COLOR = (0, 0, 0)

flock = Flock(200)

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    screen.fill(BACKGROUND_COLOR)

    flock.update()

    pygame.display.flip()

pygame.quit()