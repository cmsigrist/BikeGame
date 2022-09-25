/*
 *	Author:      Kilian Schneiter
 *	Date:        28 nov. 2017
 */

package game.actor.crate;

import game.actor.ActorGame;
import game.actor.ImageGraphics;
import game.actor.entity.GameEntity;
import math.Polygon;
import math.Vector;
import window.Canvas;

public class Crate extends GameEntity {

	// Attribute
	private ImageGraphics image;

	// Constructor
	public Crate(ActorGame actorGame, boolean fixed, Vector position, float friction, float height, float width) {
		super(actorGame, fixed, position);
		Polygon temp = new Polygon(0, 0, width, 0, width, height, 0, height);
		this.image = createGraphics("src/main/resources/box.4.png", height, width);
		buildShape(temp, false, 6 );
	}

	@Override
	public void draw(Canvas canvas) {
		image.draw(canvas);
	}
}
