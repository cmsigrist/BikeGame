/*
 *  Author:		 Kilian Schneiter
 *	Author:      Capucine Berger
 *	Date:        23 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.*;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.math.Shape;

import java.awt.*;

public abstract class GameEntity implements Actor {

	// Attributes
	private Entity entity;
	private ActorGame actorGame;

	// Constructor
	public GameEntity(ActorGame actorGame, boolean fixed, Vector position) throws IllegalArgumentException {

		// Throws an IllegalArgumentException if actorGame or position are null
		if (actorGame == null || position == null) {
			throw new IllegalArgumentException("An ActorGame and/or a vector of position are required");
		}
		this.actorGame = actorGame;
		entity = actorGame.initialise(position, fixed);
	}
	
	// Overloaded constructor without position
	public GameEntity(ActorGame actorGame, boolean fixed) throws IllegalArgumentException {

		// Throws an IllegalArgumentException is actorGame is null
		if (actorGame == null) {
			throw new IllegalArgumentException("An ActorGame is required");
		}
		this.actorGame = actorGame;
		entity = actorGame.initialise(fixed);
	}

	// Destroyer
	public void destroy() {
		if(entity != null) {
			entity.destroy();
		}
	}

	// Returns the Entity of the GameEntity
	protected Entity getEntity() {
		return entity;
	}

	// Returns the ActorGame of the GameEntity
	public ActorGame getOwner() {
		return actorGame;
	}

	// Builds the shape of the entity
	public void buildShape(Shape shape) throws IllegalArgumentException {

		// Throws an exception if the shape is null
		if (shape == null) {
			throw new IllegalArgumentException("A shape is required");
		}

		buildShape(shape, -1, false, 0);
	}

	// Overloaded buildShape with ghost collisions
	public void buildShape(Shape shape, boolean ghost, int collisionGroup) throws IllegalArgumentException {

		// Throws an exception if the shape is null
		if (shape == null) {
			throw new IllegalArgumentException("A shape is required");
		}
		buildShape(shape, -1, ghost, collisionGroup);
	}

	// Overloaded buildShape with friction
	public void buildShape(Shape shape, float friction) throws IllegalArgumentException {

		// Throws an exception if the shape is null
		if (shape == null) {
			throw new IllegalArgumentException("A shape is required");
		}
		buildShape(shape, friction, false, 0);
	}

	// Overloaded buildShape with friction and collisionGroup
	public void buildShape(Shape shape, float friction, boolean ghost, int collisionGroup) throws IllegalArgumentException {

		// Throws an exception if the shape is null
		if (shape == null) {
			throw new IllegalArgumentException("A shape is required");
		}

		PartBuilder partbuilder = entity.createPartBuilder();
		partbuilder.setShape(shape);
		partbuilder.setFriction(friction);
		partbuilder.setGhost(ghost);
		partbuilder.setCollisionGroup(collisionGroup);
		partbuilder.build();
	}

	// Builds the ImageGraphics of the entity
	public ImageGraphics createGraphics(String image, float width, float height) {
		ImageGraphics imageGraphics = new ImageGraphics(image, height, width);
		imageGraphics.setParent(entity);
		return imageGraphics;
	}

	// Builds the ShapeGraphics of the entity
	public ShapeGraphics createShapeGraphics(Shape shape, Color filling, Color colorLine, float thick, float alpha, float depth) throws IllegalArgumentException {

		// Throws an exception if the shape is null
		if (shape == null) {
			throw new IllegalArgumentException("A shape is required");
		}

		ShapeGraphics shapeGraphics = new ShapeGraphics(shape, filling, colorLine, thick, alpha, depth);
		shapeGraphics.setParent(entity);
		return shapeGraphics;
	}

	// Builds the ImageGraphics of the entity with a settable vector of position
	public ImageGraphics createImageGraphics(String image, float width, float height, Vector vector) {
		ImageGraphics imageGraphics = new ImageGraphics(image, width, height, vector);
		imageGraphics.setParent(entity);
		return imageGraphics;
	}

	// Builds the ImageGraphics of the entity with a settable vector of position, an alpha and a depth
	public ImageGraphics createImageGraphics(String image, float width, float height, Vector vector, float alpha, float depth) {
		ImageGraphics imageGraphics = new ImageGraphics(image, width, height, vector, alpha, depth);
		imageGraphics.setParent(entity);
		return imageGraphics;
	}

	@Override
	public Vector getVelocity() {
		return entity.getVelocity();
	}

	@Override
	public Transform getTransform() {
		return entity.getTransform();
	}
}