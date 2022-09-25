package game.actor;

import game.actor.entity.*;
import io.FileSystem;
import math.Polygon;
import math.Polyline;
import math.Vector;
import window.Window;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BikeGame extends ActorGame {

    // Attributes
    private Bike bike;
    private Finish finish;
    private Ground ground;
    private Pendulum pendulum;
    private Emitter geyser;
    private int timerVictory = 0;
    private int timerDeath = 0;
    private int score = 0;

    // Creates the polyLine of the Ground
    Polyline polyline = new Polyline(
            -1000.f, -1000.0f,
            -1000.f, 0.f,
            -500.f, 1000.f,
            -50.f, 1000.f,
            -50.f, 0.f,
            0.0f, 0.0f,
            3.0f, 1.0f,
            8.0f, 1.0f,
            15.0f, 3.0f,
            16.0f, 3.0f,
            25.0f, 0.0f,
            35.0f, -5.0f,
            50.0f, -5.0f,
            55.0f, -4.0f,
            65.0f, 0.0f,
            6500.0f, -1000.0f
    );

    // Initialises TextGraphics
    private TextGraphics message = new TextGraphics(
            "",
            0.25f,
            new Color(45, 67, 89),
            null,
            0.02f,
            true,
            false,
            new Vector( 0.35f, 0.5f),
            1.f,
            100.f
    );
    private TextGraphics resetMessage =  new TextGraphics(
            "Press-ENTER-to-reset",
            0.03f,
            Color.WHITE,
            null,
            0.02f,
            true,
            false,
            new Vector( 0.35f, -5.f),
            0.f,
            100.f
    );
    private TextGraphics scoreMessage = new TextGraphics(
            "SCORE :",
            0.03f,
            Color.WHITE,
            null,
            0.02f,
            true,
            false,
            new Vector(1.3f, 13.f),
            1.f,
            100.f
    );
    private TextGraphics scoreNumber = new TextGraphics(
            "0000",
            0.03f,
            Color.WHITE,
            null,
            0.02f,
            true,
            false,
            new Vector(0.f, 13.f),
            1.f,
            100.f
    );


    // Creates the color of the ground
    Color colorGround = new Color(58, 157, 35);

    // Creates all the actors
    private void createActors() {
        bike = new Bike(this, false, new Vector(4.f, 5.f));
        ground = new Ground(this, true, polyline, colorGround, null, 0.f, 10.f);
        finish = new Finish(this, true, new Vector(65.f, 0.f));
        pendulum = new Pendulum(this, true, new Vector(45.5f, 12.f));
        geyser = new Emitter(this, true, new Vector(35.f, -5.f), new Polygon(0.f, 0.f, 1.f, 3.f, -1.f, 3.f));

        // Adds all the actors to the ArrayList of ActorGame
        addActor(ground);
        addActor(bike);
        addActor(finish);
        addActor(pendulum);
        addActor(geyser);
    }

    // Method to add all coin to the ArrayList of coins in ActorGame
    public void addCoins() {
        addCoin(new Vector(6.f, 1.5f), "gold");
        addCoin(new Vector(8.f, 1.5f), "silver");
        addCoin(new Vector(10.f, 2.75f), "diamond");
        addCoin(new Vector(12.f, 3.5f), "diamond");
        addCoin(new Vector(15.f, 4.f), "bronze");
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {

        // Calls super method
        super.begin(window, fileSystem);

        // Creates the actors
        createActors();

        // Adds the actors to the ArrayList
        addCoins();

        // Initialises all TextGraphics
        initialiseTextGraphics(message);
        initialiseTextGraphics(resetMessage);
        initialiseTextGraphics(scoreMessage);
        initialiseTextGraphics(scoreNumber);

        // Makes the camera follow the bike
        this.setViewCandidate(bike);

        return true;
    }

    // Method to reset the game (removing, creating all the actors and resetting all the text messages)
    public void reset() {

        // Removes all actors
        this.removeAll();
        pendulum.destroy();

        // Calls the method to create adn add all actors
        createActors();
        score = 0;
        scoreNumber.setText("0000");

        addCoins();

        // Makes the camera follow the bike again
        this.setViewCandidate(bike);

        // Sets the message of Victory/Game Over to an empty String
        message.setText("");

        // Sets the alpha of the reset message to 0
        resetMessage.setAlpha(0.f);

        // Resets timers
        timerDeath = 0;
        timerVictory = 0;
    }

    @Override
    public void update(float deltaTime) {
        // Calls the super update method
        super.update(deltaTime);

        // Draws the scores
        scoreMessage.draw(getCanvas());
        scoreNumber.draw(getCanvas());

        // Does something if the bike collides with a coin
        for(int i = getCoins().size() - 1; i >= 0 ; i--) {
            if (getCoins().get(i).getCollision()) {
                getCoins().get(i).setCollision(false);

                score += getCoins().get(i).getPointsNumber();
                scoreNumber.setText(Integer.toString(score));

                getCoins().get(i).destroy();
                removeCoin(getCoins().get(i));
            }
        }

        // If the bike crosses the flag, ends the game with a Victory
        if (finish.getCollision()) {
            this.setViewCandidate(null);
            timerVictory++;
            message.setFillColor(new Color(45, 67, 89));
            message.setText("VICTORY");
            message.draw(getCanvas());
            resetMessage.draw(getCanvas());
            resetMessage.setAlpha(1.f);
            bike.armsUp();

            if (timerVictory > 150) {
                removeActor(bike);
            }

            if (this.getKeyboard().get(KeyEvent.VK_ENTER).isPressed()) {
                reset();
            }
        }

        //If the bike gets hit with any other object, ends the game with a Game Over
        else if (bike.getHit()) {
            message.setAnchor(new Vector(0.5f, 0.5f));
            message.setFillColor(new Color(174, 16, 0));
            message.setText("GAME OVER");
            message.draw(getCanvas());
            resetMessage.draw(getCanvas());
            resetMessage.setAlpha(1.f);

            this.setViewCandidate(null);

            if (timerDeath <= 0) {
                die(bike);
                timerDeath++;
            }

            if (this.getKeyboard().get(KeyEvent.VK_ENTER).isPressed()) {
                reset();
            }
        }
    }
}



