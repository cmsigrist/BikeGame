package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Coin extends Trigger implements Actor{

    // Attributes
    private ImageGraphics imageCoin;
    private int pointsNumber;

    // Constructor
    public Coin(ActorGame actorGame, Vector position, String colour) throws IllegalArgumentException {
        super(actorGame, true, position);

        // If the String of the color is empty or is not linked to our defined colours, throws an IllegalArgumentException
        if (colour.isEmpty() || (!colour.equals("gold") && !colour.equals("silver") && !colour.equals("bronze") && !colour.equals("diamond"))) {
            throw new IllegalArgumentException("gold, silver, bronze or diamond are expected as colours");
        }

        // Sets the image string to nothing
        String image = "";

        buildShape(new Polygon(0.f, 0.f, 0.f, 1.f, 1.f, 1.f, 1.f, 0.f), true, 4);

        // We change image and points of the coin with a String argument in the constructor
        switch(colour) {
            case "gold" :
                image = "coin.gold.png";
                pointsNumber = 100;
                break;
            case "silver" :
                image = "coin.silver.png";
                pointsNumber = 50;
                break;
            case "bronze" :
                image = "coin.bronze.png";
                pointsNumber = 25;
                break;
            case "diamond" :
                image = "coin.diamond.png";
                pointsNumber = 500;
                break;
        }

        this.imageCoin = createGraphics(image, 1.f, 1.f);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // If the coin is in contact with something, we check if it can be the bike
        if (listener.hasContacts()) {
            for (Entity entity : listener.getEntities()) {
                if (entity.getCollisionGroup() == 0) {
                    // Contact with Bike
                    collision = true;
                }
            }
        }
    }

    // Returns wether the coin is collided by the bike
    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    // Returns the number of points you get collecting a coin
    public int getPointsNumber(){
        return pointsNumber;
    }

    @Override
    public void draw(Canvas canvas) {
        imageCoin.draw(canvas);
    }
}
