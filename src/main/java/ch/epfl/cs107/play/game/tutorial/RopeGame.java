/*
 *	Author:      Capucine Berger
 *	Date:        21 nov. 2017
 */

package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class RopeGame implements Game {
	 // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity ball;
    private Entity block;
    
    // graphical representation of the bodies
    private ShapeGraphics ballGraphics;
    private ImageGraphics blockGraphics;
    
    
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World () ;
        
        // Note that you should use meters as unit
        world.setGravity(new Vector (0.0f, -9.81f)) ;

        // To create an object , you need to use a builder
        EntityBuilder entityBuilder = world.createEntityBuilder();


            /** Initialising the ball **/

        // Make sure this does not move
        entityBuilder.setFixed(true);
        // This helps you define properties , like its initial location
        entityBuilder.setPosition(new Vector (1.f, 0.5f));
        // Once ready , the body can be built
        block = entityBuilder.build();
        // At this point , your body is in the world , but it has no geometry
        // You need to use another builder to add shapes
        PartBuilder partBuilder = block.createPartBuilder () ;
        // Create a square polygon , and set the shape of the builder to this polygon
	    Polygon polygon = new Polygon(
	    new Vector (0.0f, 0.0f),
	    new Vector (1.0f, 0.0f),
	    new Vector (1.0f, 1.0f),
	    new Vector (0.0f, 1.0f)
	    ) ;
	    partBuilder.setShape(polygon) ;
	    partBuilder.setFriction (0.5f) ;
	    partBuilder.build () ;


	        /** Initialising the ball **/

        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector (0.6f, 4.0f));
        
        ball = entityBuilder.build();
        partBuilder = ball.createPartBuilder () ;
        float ballRadius = 0.6f;
        Circle circle = new Circle(ballRadius);
        partBuilder.setShape(circle);
        partBuilder.setFriction (0.5f) ;
	    partBuilder.build () ;

        ballGraphics = new ShapeGraphics(circle, Color.BLUE, Color.RED, .1f, 1.f, 0);
        ballGraphics.setParent(ball);
        
        blockGraphics = new ImageGraphics("stone.broken.4.png", 1, 1);
        blockGraphics.setAlpha (1.0f);
        blockGraphics.setDepth (1.0f);
        
        blockGraphics.setParent(block);
        
        // plank-block : pivot
        RopeConstraintBuilder ropeConstraintBuilder =
        		world.createRopeConstraintBuilder () ;
        		ropeConstraintBuilder.setFirstEntity(block) ;
        		ropeConstraintBuilder.setFirstAnchor(new Vector(1.0f /2,
        		1.0f /2)) ;
        		ropeConstraintBuilder.setSecondEntity(ball) ;
        		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO) ;
        		ropeConstraintBuilder.setMaxLength (6.0f) ;
        		ropeConstraintBuilder.setInternalCollision(true) ;
        		ropeConstraintBuilder.build () ;
        // Successfully initiated
        return true;
    }
    

    public void update(float deltaTime) {
    	
    	// Game logic comes here
    	// Nothing to do , yet
    	// Simulate physics
    	// Our body is fixed , though , nothing will move
    	world.update(deltaTime) ;
    	
    	// we must place the camera where we want
    	// We will look at the origin (identity) and increase the view size a bit
    	window.setRelativeTransform(Transform.I.scaled (10.0f)) ;
    	// We can render our scene now ,
    	ballGraphics.draw(window);
    	blockGraphics.draw(window);
      
        // The actual rendering will be done now, by the program loop
    }


	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}
