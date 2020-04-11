/*
 *	Author: 	 	Kilian Schneiter  
 *	Author:    	Capucine Berger
 *	Date:      	23 nov. 2017
 */

package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.entity.Coin;
import ch.epfl.cs107.play.game.actor.entity.Tomb;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import java.util.ArrayList;

public abstract class ActorGame implements Game {

	// Attributes
	private ArrayList<Actor> actors = new ArrayList<>();
	private ArrayList<Tomb> tombs = new ArrayList<>();
	private ArrayList<Coin> coins = new ArrayList<>();
	private World world;
	private Window window;

	// Viewport properties
	private Vector viewCenter;
	private Vector viewTarget;
	private Positionable viewCandidate;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
	private static final float VIEW_SCALE = 20.0f;

	// Function to add an Actor to the ArrayList
	public void addActor(Actor actor) throws IllegalArgumentException {
		// Throws an exception if the actor is null
		if (actor == null) {
			throw new IllegalArgumentException("An Actor is required");
		}
		actors.add(actor);
	}

	// Function to remove an actor from the ArrayList
	public void removeActor(Actor actor) throws IllegalArgumentException {
		// Throws an exception if the actor is null
		if (actor == null) {
			throw new IllegalArgumentException("An Actor is required");
		}
		actors.remove(actor);
	}

	// Function to removes all Actors of the ArrayList
	public void removeAll() {
		for(int i = 0; i < actors.size(); i++) {
			actors.get(i).destroy();
			actors.remove(actors.get(i));
		}
		actors.clear();

		for(int i = 0; i < coins.size(); i++) {
			coins.get(i).destroy();
			coins.remove(coins.get(i));
		}
		coins.clear();
	}

	// Adds a tomb to the ArrayList of tombs
	public void addTomb(Vector position) throws IllegalArgumentException {

		// If the position is null, throws an exception
		if (position == null) {
			throw new IllegalArgumentException("A vector of position is expected");
		}

		tombs.add(new Tomb(this, position));
	}

	// Adds a coin to the ArrayList of coins
	public void addCoin(Vector position, String colour) {
		coins.add(new Coin(this, position, colour));
	}

	// Removes a coin from the ArrayList of coins
	public void removeCoin(Coin coin) throws IllegalArgumentException {

		// If the coin is null, throws an IllegalArgumentException
		if (coin == null) {
			throw new IllegalArgumentException("A coin is expected");
		}

		coins.remove(coin);
	}

	// Returns the ArrayList of coins
	public ArrayList<Coin> getCoins() {
		return coins;
	}

	// Initialises a given TextGraphics
	public void initialiseTextGraphics(TextGraphics textGraphics) throws IllegalArgumentException {

		// If the TextGraphics is null, throws an IllegalArgumentException
		if (textGraphics == null) {
			throw new IllegalArgumentException("A TextGraphics is required");
		}

		textGraphics.setParent(window);
		textGraphics.setRelativeTransform(Transform.I.translated(0.f, -1.f));
	}

	// Make an actor die
	public void die(Actor actor) throws IllegalArgumentException {

		// If the actor is null, throw an IllegalArgumentException
		if (actor == null) {
			throw new IllegalArgumentException("An actor is required");
		}

		Vector positionTomb = new Vector(actor.getPosition().x, actor.getPosition().y + 3);
		actors.remove(actor);
		addTomb(positionTomb);
	}

	// Returns the keyboard of the window
	public Keyboard getKeyboard() {
		return window.getKeyboard () ;
	}

	// Function to get the window
	public Canvas getCanvas() {
		return window ;
	}

	// Sets the view of the camera (which positionable it has to follow)
	public void setViewCandidate(Positionable positionable) throws IllegalArgumentException {
		viewCandidate = positionable;
	}

	public boolean begin(Window window, FileSystem fileSystem) {
        
		// Initial position of the view
		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;
		
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World();
   
        // Gravity setting
        world.setGravity(new Vector(0.0f, -9.81f));
        
        return true;
	}

	// Initialise an entity with its position
	public Entity initialise(Vector vector, boolean fixed) throws IllegalArgumentException {

		// If the vector is null, throws an exception
		if (vector == null) {
			throw new IllegalArgumentException("A vector is required");
		}

		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(fixed);
		entityBuilder.setPosition(vector);
		return entityBuilder.build();
	}

	// Initialise an entity (overloaded initialise)
	public Entity initialise(boolean fixed) {
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(fixed);
		return entityBuilder.build();
	}

	// Returns the WheelConstraintBuilder of the world
	public WheelConstraintBuilder getWheelConstraintBuilder() {
		return Game.super.getWheelConstraintBuilder(world);
	}

	// Returns the PrismaticConstraintBuilder of the world
	public PrismaticConstraintBuilder getPrismaticConstraintBuilder() {
		return Game.super.getPrismaticConstraintBuilder(world);
	}

	// Returns the RevoluteConstraintBuilder of the world
	public RevoluteConstraintBuilder getRevoluteConstraintBuilder() {
		return Game.super.getRevoluteConstraintBuilder(world);
	}

	// Returns the RopeConstraintBuilder of the world
	public RopeConstraintBuilder getRopeConstraintBuilder() {
		return Game.super.getRopeConstraintBuilder(world);
	}

	@Override
	public void end() {
		
	}

	@Override
	public void update(float deltaTime) {

		// Call the super method update
		world.update(deltaTime);

		// Updates each Actor of the ArrayList
		for(int i = 0; i < actors.size(); i++) {
			actors.get(i).update(deltaTime);
		}
		for(int i = 0; i < coins.size(); i++) {
			coins.get(i).update(deltaTime);
		}
		
    	// Update expected viewport center
    	if (viewCandidate != null) {
    		viewTarget = viewCandidate.getPosition().add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
    	}

    	// Interpolate with previous location
    	float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
    	viewCenter = viewCenter.mixed(viewTarget, ratio) ;

    	// Compute new viewport
    	Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
    	window.setRelativeTransform(viewTransform) ;

    	// Draw each Actor of the ArrayList
    	for(int i = 0; i < actors.size(); i++) {
    		actors.get(i).draw(window);
    	}

    	// Draw each Tomb of the ArrayList
		for(Tomb tomb : tombs) {
			tomb.draw(window);
		}

		// Draw each coin of the ArrayList
		for(Coin coin : coins) {
    		coin.draw(window);
		}
    	
    }
	
}