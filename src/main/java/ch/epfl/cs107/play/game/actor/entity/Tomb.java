package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Tomb extends GameEntity {

    // Attributes
    private ImageGraphics imageTomb;

    // Constructor
    public Tomb(ActorGame actorGame, Vector position) {
        super(actorGame, false, position);
        buildShape(new Polygon(0.f, 0.f, 1.5f, 0.f, 1.5f, 0.001f, 0.f, 0.001f),1000000000, false, 1);
        imageTomb = createImageGraphics("tombstone.png", 2.f, 2.f, new Vector(0.f, 0.3f), 1.f, -1);
    }

    @Override
    public void draw(Canvas canvas) {
        imageTomb.draw(canvas);
    }
}
