package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PlayerBall extends Player {

    private BodyDef playerBodyDef;
    private Body playerBody;
    private Fixture fixture;
    private boolean hasCollided = false;
    private Sprite playerSprite;

    private boolean right = true;
    private float timePassed = 0.0f;
    private float colourChangeDuration = 3; //3 seconds
    private boolean playerPurple = false;

    private float jumpForce = 5000.0f;

    Sound bounceSound;

    @Override
    public void play() {

    }

    @Override
    public void update() {
        timePassed += Gdx.graphics.getDeltaTime();

        playerSprite.setOrigin(0+20/2,0+20/2);
        playerSprite.setPosition(playerBody.getPosition().x - 20/2,playerBody.getPosition().y - 20/2);
        playerSprite.setRotation(-playerBody.getAngle() * MathUtils.radDeg);
    }

    @Override
    public void dispose() {
        bounceSound.dispose();
        playerPurple = false;
    }

    ;

    PlayerBall(String name, World world,String spritePath) {
        this.name = name;
        playerBodyDef = new BodyDef();

        playerBodyDef.position.set(0f,43f);
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;

        playerBody = world.createBody(playerBodyDef);

        CircleShape playerShape = new CircleShape();
        playerShape.setRadius(10f);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        fixture = playerBody.createFixture(fixtureDef);
        playerShape.dispose();

        bounceSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/bounce.mp3"));

        playerSprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
        playerSprite.setSize(20,20);

    }

    public void renderPlayerSprite(MyGdxGame game) {
       playerSprite.draw(game.batch);
    }

    public boolean isRight() {
        return right;
    }

    public Vector2 getPosition() {
        return playerBody.getPosition();
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void applyForce(float force) {
        playerBody.setAngularVelocity(force);
    }


    public void setPlayerBallColor(Color color,boolean hasReset) {
        //Set the player ball color for a duration of a time
        if(!hasReset) {
            timePassed = 0;
        }
            if(timePassed < colourChangeDuration) {
                playerSprite.setColor(color);
                playerPurple = true;
            } else {
                playerSprite.setColor(Color.WHITE);
                playerPurple = false;
            }

    }

    public void jump(boolean finished) {
        if(finished == false) {
            playerBody.applyForceToCenter(400,jumpForce,true);
        }
    }


    public Body getPlayerBody() {
        return playerBody;
    }

    public void setHasCollided(boolean b) {
       hasCollided = b;
    }

    public boolean getHasCollided() {
        return hasCollided;
    }

    public boolean getIsPlayerPurple() {
        return playerPurple;
    }

}
