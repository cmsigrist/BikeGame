package game.actor.entity;

import game.actor.Actor;
import game.actor.ActorGame;
import game.actor.ImageGraphics;
import math.Entity;
import math.Polygon;
import math.Vector;
import window.Canvas;

public class Finish extends Trigger implements Actor{

    // Attributes
    private ImageGraphics imageFinishLine;

    // Constructor
    public Finish(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        buildShape(
                new Polygon(0.f, 0.f, 0.f, 1.5f, 1.5f, 1.5f, 1.5f, 0.f) ,
                -1,
                true,
                3
        );
        this.imageFinishLine = createGraphics("src/main/resources/flag.red.png", 1.5f, 1.5f);
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
