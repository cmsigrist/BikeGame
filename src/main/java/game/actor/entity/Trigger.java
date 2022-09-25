package game.actor.entity;

import game.actor.ActorGame;
import math.*;
import window.Canvas;

public class Trigger extends GameEntity {

    // Attributes
    protected BasicContactListener listener;
    protected boolean collision;

    // Constructor
    public Trigger(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        listener = new BasicContactListener();
        this.getEntity().addContactListener(listener);
    }

    // Returns whether the Trigger is collided by something
    public boolean getCollision() {
        return collision;
    }

    @Override
    public void draw(Canvas canvas) {
        // Nothing to draw here
    }

}

