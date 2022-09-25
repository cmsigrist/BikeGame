package game.actor.entity;

import game.actor.ActorGame;
import game.actor.ImageGraphics;
import math.Polygon;
import math.Vector;
import window.Canvas;

public class Block extends GameEntity {

    // Attribute
    private ImageGraphics blockGraphics;

    // Constructor
    public Block(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        Polygon temp = new Polygon(new Vector (0.0f, 0.0f), new Vector (10.0f, 0.0f), new Vector (10.0f, 1.0f), new Vector (0.0f, 1.0f));
        blockGraphics = createGraphics("src/main/resources/stone.broken.4.png", 1.f, 10.f);
        buildShape(temp);
    }

    @Override
    public void draw(Canvas canvas) {
        blockGraphics.draw(canvas);
    }
}
