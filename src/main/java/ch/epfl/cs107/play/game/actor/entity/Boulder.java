package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Boulder extends GameEntity {
    // Attribute
    private ImageGraphics boulderGraphics;

    // Constructor
    public Boulder(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        Circle temp = new Circle(1.5f);
        buildShape(temp, 1, true, 1);
        boulderGraphics = createImageGraphics("stone.11.png", 2.0f * 1.5f, 2.0f * 1.5f, new Vector(0.5f, 0.5f));
    }

    @Override
    public void draw(Canvas canvas) {
        boulderGraphics.draw(canvas);
    }
}
