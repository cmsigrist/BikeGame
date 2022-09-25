/*
 *	Author:      Capucine Berger
 *	Date:        23 nov. 2017
 */

package game.tutorial;

import game.Game;
import game.actor.ImageGraphics;
import game.actor.ShapeGraphics;
import io.FileSystem;
import math.*;
import math.Polygon;
import window.Window;

import java.awt.*;

public class ContactGame implements Game {

	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity ball;
    private Entity block;
    
    // Graphical representation of the bodies
    private ShapeGraphics ballGraphics;
    private ImageGraphics blockGraphics;
    
    // Creation of a contact listener
    private BasicContactListener contactListener ;
    
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World () ;
        
        //Gravity setting
        world.setGravity(new Vector (0.0f, -9.81f)) ;

        // To create an object , you need to use a builder
        EntityBuilder entityBuilder = world.createEntityBuilder();

            /** Initialising the block **/

        // The block doesn't move
        entityBuilder.setFixed(true);
        // Initial location of the block
        entityBuilder.setPosition(new Vector (-5.0f, -1.0f));
        // Building of the body
        block = entityBuilder.build();
        // Adding shape to the block
        PartBuilder partBuilder = block.createPartBuilder() ;
        // Create a square polygon , and set the shape of the builder to this polygon
	    Polygon polygon = new Polygon(
	    new Vector (0.0f, 0.0f),
	    new Vector (10.0f, 0.0f),
	    new Vector (10.0f, 1.0f),
	    new Vector (0.0f, 1.0f)
	    ) ;
	    partBuilder.setShape(polygon) ;
	    // Finally , do not forget the following line.
	    partBuilder.build () ;

	    // Initialise graphics of the block
	    blockGraphics = new ImageGraphics("src/main/resources/stone.broken.4.png", 10, 1);
        blockGraphics.setParent(block);


            /** Initialising the ball **/
        
	    //The ball is a moving object
        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector (0.0f, 2.f));
        
        ball = entityBuilder.build();
        partBuilder = ball.createPartBuilder() ;
        float ballRadius = 0.5f;
        Circle circle = new Circle(ballRadius);
        partBuilder.setShape(circle);
        partBuilder.setFriction(10000);
	    partBuilder.build () ;

	    ballGraphics = new ShapeGraphics(circle, Color.BLUE, Color.BLUE, .1f, 1.f, 0);
        ballGraphics.setParent(ball);
        
        contactListener = new BasicContactListener();
        ball.addContactListener(contactListener);
        
        // Successfully initiated
        return true;
    }
    

    public void update(float deltaTime) {
    	
    	world.update(deltaTime) ;
    	
    	// we must place the camera where we want
    	// We will look at the origin (identity) and increase the view size a bit
    	window.setRelativeTransform(Transform.I.scaled (10.0f)) ;
    	// Rendering the scene
    	blockGraphics.draw(window);
    	
    	// contactListener is associated to ball
    	// contactListener.getEntities () returns the list of entities in collision with ball
    	int numberOfCollisions = contactListener.getEntities().size() ;
    	if (numberOfCollisions > 0){
    		ballGraphics.setFillColor(Color.RED) ;
    	}
    	ballGraphics.draw(window);
      
        // The actual rendering will be done now, by the program loop
    }


	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}
