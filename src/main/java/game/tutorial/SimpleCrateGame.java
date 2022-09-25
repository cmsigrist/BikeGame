/*
 *	Author:      Capucine Berger
 *	Date:        21 nov. 2017
 */

package game.tutorial;

import game.Game;
import game.actor.ImageGraphics;
import io.FileSystem;
import math.Entity;
import math.EntityBuilder;
import math.PartBuilder;
import math.Polygon;
import math.Transform;
import math.Vector;
import math.World;
import window.Window;


public class  SimpleCrateGame implements Game {
	
	 // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity crate;
    private Entity block;
    
    // graphical representation of the bodies
    private ImageGraphics graph1;
    private ImageGraphics graph2;
    
    
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World();
        
        // Note that you should use meters as unit
        world.setGravity(new Vector(0.0f, -9.81f)) ;
        
        // To create an object , you need to use a builder
        EntityBuilder entityBuilder = world.createEntityBuilder();
        // Make sure this does not move
        entityBuilder.setFixed(true);
        // This helps you define properties , like its initial location
        entityBuilder.setPosition(new Vector(1.f, 0.5f));
        // Once ready , the body can be built
        block = entityBuilder.build();
        // At this point , your body is in the world , but it has no geometry
        // You need to use another builder to add shapes
        PartBuilder partBuilder = block.createPartBuilder();
        // Create a square polygon , and set the shape of the builder to this polygon
	    Polygon polygon = new Polygon(
	    new Vector(0.0f, 0.0f),
	    new Vector(2.0f, 0.0f),
	    new Vector(2.0f, 2.0f),
	    new Vector(0.0f, 2.0f)
	    );
	    partBuilder.setShape(polygon);
	    // Finally , do not forget the following line.
	    partBuilder.setFriction(10.0f);
	    partBuilder.build();
	    // Note : we do not need to keep a reference on partBuilder
     
        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector (0.2f, 4.0f));
        
        crate = entityBuilder.build();
        partBuilder = crate.createPartBuilder();
        polygon = new Polygon(
        	    new Vector(0.0f, 0.0f),
        	    new Vector(2.0f, 0.0f),
        	    new Vector(2.0f, 2.0f),
        	    new Vector(0.0f, 2.0f)
        	    ) ;
        partBuilder.setShape(polygon) ;
	    // Finally , do not forget the following line.
        partBuilder.setFriction(10.0f);
	    partBuilder.build();
	    // Note : we do not need to keep a reference on partBuilder
        
        //Two last parameters are used to give the dimension of the picture
        graph1 = new ImageGraphics("src/main/resources/box.4.png", 1, 1);
        // Transparency can be chosen for each drawing (0.0 - transparent , 1.0 - opaque)
        graph1.setAlpha(1.0f);
        // Additionally , you can choose a depth when drawing
        // Therefore , you could draw behind what you have already done
        graph1.setDepth (0.0f);
        graph1.setParent(crate);
        
        graph2 = new ImageGraphics("src/main/resources/stone.broken.4.png", 1, 1);
        graph2.setAlpha(1.0f);
        graph2.setDepth(1.0f);
        
        graph2.setParent(block);

        
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
    	graph1.draw(window);
    	graph2.draw(window);
      
        // The actual rendering will be done now, by the program loop
    }


	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}

