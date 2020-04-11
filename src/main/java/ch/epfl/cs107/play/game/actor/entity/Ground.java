package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Ground extends GameEntity{

    // Attribute
    private ShapeGraphics shapeGraphics;

    // Constructor
    public Ground(ActorGame actorGame, boolean fixed, Polyline polyline, Color filling, Color colorLine, float thickness, float friction) throws IllegalArgumentException {
        super(actorGame, fixed, new Vector(0.f, 0.f));

        // Throws an exception if the polyline is null
        if (polyline == null) {
            throw new IllegalArgumentException("A polyline is required");
        }

        buildShape(polyline, friction, false, 1);
        shapeGraphics = createShapeGraphics(polyline, filling, colorLine, thickness, 1.f, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        shapeGraphics.draw(canvas);
    }
}
