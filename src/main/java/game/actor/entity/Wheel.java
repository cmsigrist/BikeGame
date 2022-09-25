package game.actor.entity;

import game.actor.ActorGame;
import game.actor.ImageGraphics;
import math.*;
import window.Canvas;

public class Wheel extends GameEntity {

    // Attributes
    protected final float MAX_WHEEL_SPEED = 20.f;
    private ImageGraphics wheelGraphics;
    private WheelConstraint constraint;

    // Constructor
    public Wheel(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        Circle temp = new Circle(0.5f);
        buildShape(temp, 1, false, 1);
        wheelGraphics = createImageGraphics(
                "src/main/resources/wheel.png",
                2.0f * 0.5f,
                2.0f * 0.5f,
                new Vector(0.5f, 0.5f)
        );
    }

    // Attaches the wheels to the bike
    public void attach(Entity vehicle, Vector anchor, Vector axis) throws IllegalArgumentException {

        if (vehicle == null || anchor == null || axis == null) {
            throw new IllegalArgumentException("A vehicle / an anchor / an axis are required");
        }

        WheelConstraintBuilder constraintBuilder = getOwner().getWheelConstraintBuilder();
        constraintBuilder.setFirstEntity(vehicle);
        constraintBuilder.setFirstAnchor(anchor);
        constraintBuilder.setSecondEntity(this.getEntity());
        constraintBuilder.setSecondAnchor(Vector.ZERO);
        constraintBuilder.setAxis(axis);
        constraintBuilder.setFrequency(3.0f);
        constraintBuilder.setDamping(0.5f);
        constraintBuilder.setMotorMaxTorque(10.0f);
        constraint = constraintBuilder.build();
    }

    // Switch on the wheel's motor
    public void power(float speed) {
        constraint.setMotorEnabled(true);
        constraint.setMotorSpeed(speed);
    }

    // Switch off the wheel's motor
    public void relax() {
        constraint.setMotorEnabled(false);
    }

    // Destroy the constraint between the vehicle and the wheel
    public void detach() {
        constraint.destroy();
    }

    // Returns relative rotation speed, in radians per second
    public float getSpeed() {
        return constraint.getSecondBody().getAngularVelocity();
    }

    @Override
    public void draw(Canvas canvas) {
        wheelGraphics.draw(canvas);
    }
}
