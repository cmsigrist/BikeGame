package game.actor.entity;

import game.actor.Actor;
import game.actor.ActorGame;
import game.actor.ImageGraphics;
import math.Entity;
import math.Polygon;
import math.Vector;
import window.Canvas;

public class Coin extends Trigger implements Actor{

    // Attributes
    private ImageGraphics imageCoin;
    private int pointsNumber;

    // Constructor
    public Coin(ActorGame actorGame, Vector position, String colour) throws IllegalArgumentException {
        super(actorGame, true, position);

        // If the String of the color is empty or is not linked to our defined colours,
        // throws an IllegalArgumentException
        if (colour.isEmpty() ||
                (!colour.equals("gold") && !colour.equals("silver") &&
                !colour.equals("bronze") &&
                !colour.equals("diamond"))
        ) {
            throw new IllegalArgumentException("gold, silver, bronze or diamond are expected as colours");
        }

        // Sets the image string to nothing
        String image = "";

        buildShape(new Polygon(0.f, 0.f, 0.f, 1.f, 1.f, 1.f, 1.f, 0.f), true, 4);

        // We change image and points of the coin with a String argument in the constructor
        switch(colour) {
            case "gold" :
                image = "src/main/resources/coin.gold.png";
                pointsNumber = 100;
                break;
            case "silver" :
                image = "src/main/resources/coin.silver.png";
                pointsNumber = 50;
                break;
            case "bronze" :
                image = "src/main/resources/coin.bronze.png";
                pointsNumber = 25;
                break;
            case "diamond" :
                image = "src/main/resources/coin.diamond.png";
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
