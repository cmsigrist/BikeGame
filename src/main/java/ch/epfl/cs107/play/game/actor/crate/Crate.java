/*
 *	Author:      Kilian Schneiter
 *	Date:        28 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.entity.GameEntity;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Crate extends GameEntity {

	// Attribute
	private ImageGraphics image;

	// Constructor
	public Crate(ActorGame actorGame, boolean fixed, Vector position, float friction, float height, float width) {
		super(actorGame, fixed, position);
		Polygon temp = new Polygon(0, 0, width, 0, width, height, 0, height);
		this.image = createGraphics("box.4.png", height, width);
		buildShape(temp, false, 6 );
	}

	@Override
	public void draw(Canvas canvas) {
		image.draw(canvas);
	}
}
