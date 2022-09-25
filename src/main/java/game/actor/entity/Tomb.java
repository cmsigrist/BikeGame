package game.actor.entity;

import game.actor.ActorGame;
import game.actor.ImageGraphics;
import math.Polygon;
import math.Vector;
import window.Canvas;

public class Tomb extends GameEntity {

    // Attributes
    private ImageGraphics imageTomb;

    // Constructor
    public Tomb(ActorGame actorGame, Vector position) {
        super(actorGame, false, position);
        buildShape(
                new Polygon(0.f, 0.f, 1.5f, 0.f, 1.5f, 0.001f, 0.f, 0.001f),
                1000000000,
                false,
                1
        );
        imageTomb = createImageGraphics(
                "src/main/resources/tombstone.png",
                2.f,
                2.f,
                new Vector(0.f, 0.3f),
                1.f,
                -1
        );
    }

    @Override
    public void draw(Canvas canvas) {
        imageTomb.draw(canvas);
    }
}
