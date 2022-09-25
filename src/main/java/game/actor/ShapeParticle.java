package game.actor;

import math.*;
import window.Canvas;
import java.awt.Color;

public class ShapeParticle implements Graphics, Positionable {

    // Attributes
    private Vector position;
    private Vector velocity;
    private Shape shape;
    private Color fillColor;
    private float alpha;
    private float speed = 0f;
    private boolean destroy = false;
    private int time = 0;
    private int lifetime = 10;
    private Vector gravity = new Vector(0f, -9.81f * 0.003f);

    // Constructor
    public ShapeParticle(
            Vector position,
            Vector velocity,
            Shape shape,
            Color fillColor,
            float alpha,
            float speed
    ) throws IllegalArgumentException {

        // If the position, the velocity or the shape are null, throws an IllegalArgumentException
        if (position == null || velocity == null || shape == null) {
            throw new IllegalArgumentException("A position / a velocity / a shape are required");
        }

        this.position = position;
        this.velocity = velocity;
        this.shape = shape;
        this.fillColor = fillColor;
        this.alpha = alpha;
        this.speed = speed;
    }

    // Returns if the particle needs to be destroyed
    public boolean getDestroy() {
        return destroy;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(
                shape,
                Transform.I.translated(position),
                fillColor,
                null,
                0, alpha,
                2
        );
    }

    @Override
    public Transform getTransform() {
        return null;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    public void update(float deltaTime) {

        // We increase the value of lifetime and then, if time value is bigger than lifetime value,
        // we change the position and the velocity of the particle. If it is not the case, we need to destroy the particle
        time += deltaTime;
        if (time < lifetime) {
            velocity = velocity.add(gravity.mul(deltaTime));
            position = position.add(velocity.mul(deltaTime));
        } else {
            destroy = true;
        }
    }

}
