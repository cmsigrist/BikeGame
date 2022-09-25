package game;

import io.FileSystem;
import math.*;
import window.Window;

/**
 * Represents the external interface of a game, as seen by the main game loop.
 */
public interface Game {
    
	/**
     * Initialises game state.
     * @param window context to use, not null
     * @param fileSystem file system to use, not null
     * @return whether the game was successfully started
     */
    public abstract boolean begin(Window window, FileSystem fileSystem);
    
    /**
     * Simulates a single time step.
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    public abstract void update(float deltaTime);
    
    /** Cleans up things, called even if initialisation failed. */
    public abstract void end();

    // Returns the WheelConstraintBuilder of the world
    public default WheelConstraintBuilder getWheelConstraintBuilder(World world) {
        return world.createWheelConstraintBuilder();
    }

    // Returns the PrismaticConstraintBuilder of the world
    public default PrismaticConstraintBuilder getPrismaticConstraintBuilder(World world) {
        return world.createPrismaticConstraintBuilder();
    }

    // Returns the RevoluteConstraintBuilder of the world
    public default RevoluteConstraintBuilder getRevoluteConstraintBuilder(World world) {
        return world.createRevoluteConstraintBuilder();
    }

    // Returns the RopeConstraintBuilder of the world
    public default RopeConstraintBuilder getRopeConstraintBuilder(World world) {
        return world.createRopeConstraintBuilder();
    }
}
