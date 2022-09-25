package game.actor.entity;

import game.actor.ActorGame;
import game.actor.ShapeGraphics;
import math.*;
import math.Shape;
import window.Canvas;

import java.awt.*;

public class Stickman extends GameEntity {

    // Attributes for the head
    private Circle head;
    private ShapeGraphics headGraphics;

    // Attributes for the arms
    private Polyline arms;
    private ShapeGraphics armsGraphics;

    // Attributes for the forearms
    private Polyline forearms;
    private ShapeGraphics forearmsGraphics;

    // Attributes for the thighs
    private Polyline rightThigh;
    private ShapeGraphics rightThighGraphics;
    private Polyline leftThigh;
    private ShapeGraphics leftThighGraphics;

    // Attributes for the calves
    private Polyline rightCalf;
    private ShapeGraphics rightCalfGraphics;
    private Polyline leftCalf;
    private ShapeGraphics leftCalfGraphics;

    // Attributes for the chest
    private Polyline chest;
    private ShapeGraphics chestGraphics;

    // Prismatic Constraint which will allow to attach the stickman to the bike
    private PrismaticConstraint prismaticConstraint;

    // Boolean attributes to know in which direction he needs to be drawn and if he needs to raise his arms
    private boolean right;
    private boolean win = false;

    // Constructor
    public Stickman(ActorGame actorGame, boolean fixed, Vector position, boolean right) {
       super(actorGame, fixed, position);
       createStickman();
       stickmanGraphics();

       // Creates the shape to fix the stickman to the bike hitbox
       Circle fixture = new Circle(.2f);
       this.buildShape(fixture, -1, true, 0);

       this.right = right;

    }

    //Creating the polylines of the stickman
    private void createStickman() {
        head = new Circle(0.2f, getHeadLocation());
        arms = new Polyline(getShoulderLocation(), getHandLocation());
        forearms = new Polyline(Vector.ZERO, Vector.ZERO);
        rightThigh = new Polyline(getThighLocation(), getRightKneeLocation());
        rightCalf = new Polyline(getRightKneeLocation(), getRightFootLocation());
        leftThigh = new Polyline(getThighLocation(), getLeftKneeLocation());
        leftCalf = new Polyline(getLeftKneeLocation(), getLeftFootLocation());
        chest = new Polyline(getShoulderLocation(), getThighLocation());
    }

    // Builds the shape of the stickman
    public void stickmanGraphics() {
        headGraphics = createShapeGraphics(head);
        armsGraphics = createShapeGraphics(arms);
        forearmsGraphics = createShapeGraphics(forearms);
        rightThighGraphics = createShapeGraphics(rightThigh);
        rightCalfGraphics = createShapeGraphics(rightCalf);
        leftThighGraphics = createShapeGraphics(leftThigh);
        leftCalfGraphics = createShapeGraphics(leftCalf);
        chestGraphics = createShapeGraphics(chest);

    }

    // Attach the stickman to the bike
    public void attach(Entity vehicle, Vector anchor, Vector axis) throws IllegalArgumentException {

        // If the Entity is null or the vectors are null, throws an IllegalArgumentException
        if (vehicle == null || anchor == null || axis == null) {
            throw new IllegalArgumentException("A vehicle / an anchor / an axis are required");
        }

        PrismaticConstraintBuilder constraintBuilder = getOwner().getPrismaticConstraintBuilder();
        constraintBuilder.setFirstEntity(vehicle);
        constraintBuilder.setFirstAnchor(anchor);
        constraintBuilder.setSecondEntity(this.getEntity());
        constraintBuilder.setSecondAnchor(Vector.ZERO);
        constraintBuilder.setLimitEnabled(true);
        constraintBuilder.setMotorEnabled(true);
        constraintBuilder.setInternalCollision(false);
        constraintBuilder.setUpperTranslationLimit(0.f);
        constraintBuilder.setLowerTranslationLimit(0.f);
        constraintBuilder.setAxis(axis);
        prismaticConstraint = constraintBuilder.build();
    }

    // Methods returning different body parts locations
    private Vector getHeadLocation() { return new Vector(0.f, 1.75f); }
    private Vector getShoulderLocation() { return new Vector(-0.1f, 1.30f); }
    private Vector getElbowLocation() { return new Vector(0.5f, 1.55f); }
    private Vector getHandLocation() { return new Vector(0.5f, 1.f); }
    private Vector getThighLocation() { return new Vector(-0.45f, 0.55f); }
    private Vector getRightKneeLocation() { return new Vector(-0.1f, 0.1f); }
    private Vector getRightFootLocation() { return new Vector(-0.3f, -0.4f); }
    private  Vector getLeftKneeLocation() { return new Vector(0.4f, 0.5f); }
    private Vector getLeftFootLocation() { return new Vector(0.1f, 0.1f); }

    // Create the ShapeGraphics of the Stickman
    private ShapeGraphics createShapeGraphics(Shape shape) throws IllegalArgumentException {

        // If the shape is null, throws an IllegalArgumentException
        if (shape == null) {
            throw new IllegalArgumentException("A shape is required");
        }

        return super.createShapeGraphics(shape, Color.WHITE, Color.WHITE, 0.15f, 1.f, 0.f);
    }

    // Switches the direction
    public void switchDir() {
        right = !right;
    }

    // Returns if we won or not to raise the stickman's arms
    public void victorySign() { win = true; }

    @Override
    public void update(float deltaTime) {

        if (right) {
            // If the stickan looks to the right
            createStickman();
        } else if(!right) {
            // If the stickman looks to the left, we reverse all the X values of the vectors
            head = new Circle(0.2f, getHeadLocation().oppositeX());
            arms = new Polyline(getShoulderLocation().oppositeX(), getHandLocation().oppositeX());
            rightThigh = new Polyline(getThighLocation().oppositeX(), getRightKneeLocation().oppositeX());
            rightCalf = new Polyline(getRightKneeLocation().oppositeX(), getRightFootLocation().oppositeX());
            leftThigh = new Polyline(getThighLocation().oppositeX(), getLeftKneeLocation().oppositeX());
            leftCalf = new Polyline(getLeftKneeLocation().oppositeX(), getLeftFootLocation().oppositeX());
            chest = new Polyline(getShoulderLocation().oppositeX(), getThighLocation().oppositeX());
        }

        // If we win, the stickman raises his arms
        if (win) {
            head = new Circle(0.2f, getHeadLocation());
            arms = new Polyline(getShoulderLocation(), getElbowLocation());
            forearms = new Polyline(getElbowLocation(), new Vector(0.6f, 2.f));
            rightThigh = new Polyline(getThighLocation(), getRightKneeLocation());
            rightCalf = new Polyline(getRightKneeLocation(), getRightFootLocation());
            leftThigh = new Polyline(getThighLocation(), getLeftKneeLocation());
            leftCalf = new Polyline(getLeftKneeLocation(), getLeftFootLocation());
            chest = new Polyline(getShoulderLocation(), getThighLocation());
        }
        stickmanGraphics();
    }

    @Override
    public void draw(Canvas canvas) {
        headGraphics.draw(canvas);
        armsGraphics.draw(canvas);
        forearmsGraphics.draw(canvas);
        rightThighGraphics.draw(canvas);
        rightCalfGraphics.draw(canvas);
        leftThighGraphics.draw(canvas);
        leftCalfGraphics.draw(canvas);
        chestGraphics.draw(canvas);
    }
}

