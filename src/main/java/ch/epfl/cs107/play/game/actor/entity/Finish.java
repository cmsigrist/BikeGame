package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Finish extends Trigger implements Actor{

    // Attributes
    private ImageGraphics imageFinishLine;

    // Constructor
    public Finish(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        buildShape(new Polygon(0.f, 0.f, 0.f, 1.5f, 1.5f, 1.5f, 1.5f, 0.f) , -1, true, 3);
        this.imageFinishLine = createGraphics("flag.red.png", 1.5f, 1.5f);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // If the finish line is in contact with something, we check if it is in contact with the bike
        if (listener.hasContacts()) {
            for (Entity entity : listener.getEntities()) {
                if (entity.getCollisionGroup() == 0) {
                    // Contact with Bike
                    collision = true;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        imageFinishLine.draw(canvas);
    }
}
