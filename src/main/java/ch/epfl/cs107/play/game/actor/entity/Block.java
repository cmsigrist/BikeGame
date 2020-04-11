package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Block extends GameEntity {

    // Attribute
    private ImageGraphics blockGraphics;

    // Constructor
    public Block(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        Polygon temp = new Polygon(new Vector (0.0f, 0.0f), new Vector (10.0f, 0.0f), new Vector (10.0f, 1.0f), new Vector (0.0f, 1.0f));
        blockGraphics = createGraphics("stone.broken.4.png", 1.f, 10.f);
        buildShape(temp);
    }

    @Override
    public void draw(Canvas canvas) {
        blockGraphics.draw(canvas);
    }
}
