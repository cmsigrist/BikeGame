package game.actor.entity;

import game.actor.ActorGame;
import game.actor.ShapeParticle;
import math.Circle;
import math.Shape;
import math.Vector;
import window.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Emitter extends GameEntity {

    // Atributes
    private ArrayList<ShapeParticle> particles = new ArrayList<>();
    private Shape area;
    private int particlesPerSecond = 100;
    private int numberOfParticles = 0;
    private float speed = 3;
    private float angle = (float) Math.PI / 2;
    private float angleVariation;
    private float speedVariation;
    private float lifetimeVariation;
    private Vector position;

    // Constructor
    public Emitter(ActorGame actorGame, boolean fixed, Vector position, Shape area) throws IllegalArgumentException {
        super(actorGame, fixed, position);

        // If area is null, throws an IllegalArgumentException
        if (area == null) {
            throw new IllegalArgumentException("An area is required");
        }

        this.area = area;
        angleVariation = (float) (Math.PI / 16);
        speedVariation = .1f;
        lifetimeVariation = 1.f;
        this.position = position;
    }

    // Method to make particles of the geyser spawn
    private void spawnParticle() {
        Random random = new Random();
        float tempAngle = (angle - angleVariation) + random.nextFloat() * ((angle + angleVariation) - (angle - angleVariation));
        float tempSpeed = (speed - speedVariation) + random.nextFloat() * ((speed + speedVariation) - (speed - speedVariation));
        Vector tempVelocity = new Vector((float) Math.cos(tempAngle) * tempSpeed, (float) Math.sin(tempAngle) * tempSpeed);
        particles.add(
                new ShapeParticle(
                        position,
                        tempVelocity,
                        new Circle(.1f),
                        new Color(94, 238, 188),
                        .5f,
                        tempSpeed
                )
        );
    }

    @Override
    public void update(float deltaTime) {

        // We update the number of particles with deltaTime
        numberOfParticles = (int) (particlesPerSecond * deltaTime) ;

        // If the number of particles is bigger than 0, we spawn more of them
        if (particlesPerSecond > 0) {
            for (int i = 0; i < numberOfParticles; i++) {
                spawnParticle();
            }
        }

        // Makes a new ArrayList that will contain particles which aren't destroyed
        ArrayList<ShapeParticle> particles2 = new ArrayList<>();
        for (ShapeParticle shapeParticle : particles) {
            if (!shapeParticle.getDestroy()) {
                particles2.add(shapeParticle);
            }
        }

        // The first ArrayList contains what we want now (particles that aren't destroyed)
        particles = particles2;

        // Updates each particle of the ArrayList
        for (ShapeParticle particle : particles) {
            particle.update(deltaTime);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (ShapeParticle particle : particles) {
            particle.draw(canvas);
        }
    }
}
