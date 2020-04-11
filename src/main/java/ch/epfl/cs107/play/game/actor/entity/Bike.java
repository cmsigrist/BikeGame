package ch.epfl.cs107.play.game.actor.entity;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.*;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Bike extends GameEntity {

    // Attributes
    private Wheel leftWheel;
    private Wheel rightWheel;
    private boolean right = true;
    private Stickman stickman;
    private ActorGame game;
    private BasicContactListener listener;
    private boolean hit = false;
    private boolean win = false;

    // Polygon of the bike
    private Polygon polygon = new Polygon(
            0.0f, 0.5f,
            0.5f, 1.f,
            0.f, 2.f,
            -0.5f, 1.f
    );

    // Constructor
    public Bike(ActorGame actorGame, boolean fixed, Vector position) {
        super(actorGame, fixed, position);
        this.game = actorGame;
        buildShape(polygon, -1, true, 0);
        rightWheel = new Wheel(this.getOwner(), false, new Vector(position.getX() + 1.f, position.getY()));
        leftWheel = new Wheel(this.getOwner(), false, new Vector(position.getX() - 1.f, position.getY()));
        attachWheels();
        stickman = new Stickman(this.getOwner(), false, this.getPosition(), right);
        attachStickman();
        listener = new BasicContactListener();
        this.getEntity().addContactListener(listener);
    }

    // Method to attach both Wheels to the bike
    private void attachWheels() {
        leftWheel.attach(this.getEntity(), new Vector(-1.f, 0.f), new Vector(-0.5f, -1.f));
        rightWheel.attach(this.getEntity(), new Vector(1.f, 0.f), new Vector(0.5f, -1.f));
    }

    // Attaches the stickman
    private void attachStickman() {
        stickman.attach(this.getEntity(), new Vector(0, 0.5f), Vector.Y);
    }

    // Returns a boolean (true if the bike goes to the right)
    private boolean getRight() {
        return right;
    }

    // Returns if the bike is hitted by something that destroys him
    public boolean getHit() {
        return hit;
    }

    // Calls the method of stickman to make his arms go up
    public void armsUp() {
        stickman.victorySign();
    }

    @Override
    public void destroy(){
        super.destroy();
        leftWheel.destroy();
        rightWheel.destroy();
        stickman.destroy();
    }

    @Override
    public void draw(Canvas canvas) {
        leftWheel.draw(canvas);
        rightWheel.draw(canvas);
        stickman.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // If the bike is in contact with something, we check what he's touching
        if(listener.hasContacts()) {
            for (Entity entity : listener.getEntities()) {

                if(entity.getCollisionGroup() == 1) {
                    // Contact with ground
                    hit = true;
                    this.destroy();
                    break;
                } else if(entity.getCollisionGroup() == 3) {
                    // Contact with finish line
                    win = true;
                    break;
                }
            }
        }

        rightWheel.relax();
        leftWheel.relax();

        // If you haven't won yet, you can use keys to control the bike
        if (!(win)) {
            // If you press SPACE, the bike is returned and goes to the opposite direction
            if (game.getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
                right = !right;
                stickman.switchDir();
            }
            // If DOWN is pressed, the bike stops
            if (game.getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
                rightWheel.power(0.f);
                leftWheel.power(0.f);
            }
            // If you press UP, the bike goes in the right direction
            if (game.getKeyboard().get(KeyEvent.VK_UP).isDown()) {
                if (right) {
                    if (leftWheel.getSpeed() < leftWheel.MAX_WHEEL_SPEED) {
                        leftWheel.power(-18.f);
                    }
                } else {
                    if (rightWheel.getSpeed() < rightWheel.MAX_WHEEL_SPEED) {
                        rightWheel.power(18.f);
                    }
                }
            }

            // If LEFT or RIGHT are pressed, an angular force will be applied to the bike to control it in the air
            if (game.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
                this.getEntity().applyAngularForce(10.f);
            }
            if (game.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
                this.getEntity().applyAngularForce(-10.f);
            }
        }

        stickman.update(deltaTime);
    }
}

