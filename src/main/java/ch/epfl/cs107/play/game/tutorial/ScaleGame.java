/*
 *	Author:      Capucine Berger
 *	Date:        21 nov. 2017
 */

package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.window.Window;

import java.awt.event.KeyEvent;

public class ScaleGame implements Game {
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity ball;
    private Entity block;
    private Entity plank;
    
    // Graphical representation of the bodies
    private ImageGraphics ballGraphics;
    private ImageGraphics blockGraphics;
    private ImageGraphics plankGraphics;
    
    
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World () ;
        
        //Gravity setting
        world.setGravity(new Vector (0.0f, -9.81f)) ;


            /** Building the block **/

        // To create an object , you need to use a builder
        EntityBuilder entityBuilder = world.createEntityBuilder();
        
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
	    // Note : we do not need to keep a reference on partBuilder
	    
	    blockGraphics = new ImageGraphics("stone.broken.4.png", 10, 1);
        blockGraphics.setParent(block);


            /** Building the plank **/
        
	    //The plank moves on its axis
	    entityBuilder.setFixed(false);
	    entityBuilder.setPosition(new Vector (-2.5f, 0.8f));
	    
	    plank = entityBuilder.build();
	    partBuilder = plank.createPartBuilder();
	    polygon = new Polygon(
	    new Vector (0.0f, 0.0f), //lower left corner
	    new Vector (5.0f, 0.0f), //lower right corner
	    new Vector (5.0f, 0.2f), //upper right corner
	    new Vector (0.0f, 0.2f)	//upper left corner
	    ) ;
	    partBuilder.setShape(polygon) ;
	    partBuilder.build () ;
	    
	    plankGraphics = new ImageGraphics("wood.3.png", 5, 0.2f);
        plankGraphics.setParent(plank);


            /** Building the ball **/

	    //The ball is a moving object
        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector (0.5f, 4.f));
        
        ball = entityBuilder.build();
        partBuilder = ball.createPartBuilder() ;
        float ballRadius = 0.5f;
        Circle circle = new Circle(ballRadius);
	    // Finally , do not forget the following line.
        partBuilder.setShape(circle);
        partBuilder.setFriction(10000);
	    partBuilder.build () ;
	    // Note : we do not need to keep a reference on partBuilder
        
	    ballGraphics = new ImageGraphics("explosive.11.png", 2.0f*0.5f, 2.0f*0.5f, new Vector(0.5f, 0.5f));
        ballGraphics.setParent(ball);


            /** Building the constraint **/

        RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
        revoluteConstraintBuilder.setFirstEntity(block);
        revoluteConstraintBuilder.setFirstAnchor(new Vector(10.f /2.f, (1.0f *7.f) /4.f));
        revoluteConstraintBuilder.setSecondEntity(plank);
        revoluteConstraintBuilder.setSecondAnchor(new Vector(5.f /2.f, 0.2f /2.f));
        revoluteConstraintBuilder.setInternalCollision(true);
        revoluteConstraintBuilder.build();
        
        // Successfully initiated
        return true;
    }
    

    public void update(float deltaTime) {

        // Apply an angular force if we press the LEFT or RIGHT key
    	if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
    		ball.applyAngularForce(10.0f) ;
    	} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
    		ball.applyAngularForce( -10.0f) ;
    	}

    	world.update(deltaTime) ;

    	// we must place the camera where we want
    	// We will look at the origin (identity) and increase the view size a bit
    	window.setRelativeTransform(Transform.I.scaled (10.0f)) ;
    	// Rendering the scene
    	blockGraphics.draw(window);
    	plankGraphics.draw(window);
    	ballGraphics.draw(window);

        // The actual rendering will be done now, by the program loop
    }


	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}
