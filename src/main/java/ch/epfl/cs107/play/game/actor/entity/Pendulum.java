package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Pendulum extends GameEntity {

    // Attributes
    private Block block;
    private Boulder boulder;
    private RopeConstraint constraint;

    // Constructor
    public Pendulum(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        block = new Block(this.getOwner(), true, position);
        boulder = new Boulder(this.getOwner(), false, new Vector(position.getX() - 10.f, position.getY()));
        attach();
    }

    @Override
    public void draw(Canvas canvas) {
        block.draw(canvas);
        boulder.draw(canvas);
        canvas.drawShape(new Polyline(boulder.getPosition(), block.getPosition()), Transform.I, null, new Color(92, 72, 55), .1f, 1.f, -1.f);
    }

    // Method to attach the boulder to the block
    public void attach() {
        RopeConstraintBuilder ropeConstraintBuilder = getOwner().getRopeConstraintBuilder () ;
        ropeConstraintBuilder.setFirstEntity(block.getEntity()) ;
        ropeConstraintBuilder.setFirstAnchor(new Vector(1.0f /2, 1.0f /2)) ;
        ropeConstraintBuilder.setSecondEntity(boulder.getEntity()) ;
        ropeConstraintBuilder.setSecondAnchor(Vector.ZERO) ;
        ropeConstraintBuilder.setMaxLength (15.f) ;
        ropeConstraintBuilder.setInternalCollision(false) ;
        constraint = ropeConstraintBuilder.build () ;
    }

    @Override
    public void destroy() {
        super.destroy();
        constraint.destroy();
        block.destroy();
        boulder.destroy();
    }
}
