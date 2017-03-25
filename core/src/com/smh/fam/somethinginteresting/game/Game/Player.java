package com.smh.fam.somethinginteresting.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.io.PipedReader;

/**
 * Created by Axel on 2017-03-25.
 */

public class Player {
    private Body simulationBody;
    private Texture texture;

    private final float WIDTH = 30f; // Box2D coordinates
    private final float HEIGHT = 30f;

    public Player(World world, Vector2 position){
        texture = new Texture(Gdx.files.internal("player.png"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        simulationBody = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(WIDTH, HEIGHT);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = simulationBody.createFixture(fixtureDef);

        boxShape.dispose();

        simulationBody.setLinearVelocity(new Vector2(-20f, 10f));
    }

    public void render(Batch batch){
        batch.draw(texture,
                simulationBody.getPosition().x-WIDTH, simulationBody.getPosition().y-HEIGHT,
                simulationBody.getPosition().x, simulationBody.getPosition().y,
                WIDTH*2f, HEIGHT*2f,
                1.0f, 1.0f,
                simulationBody.getAngle() * 180f/(float) Math.PI,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false );
    }


    public void fingerTouchedScreen(int screenX, int screenY, int pointer, int button)
    {
        Gdx.app.log("TouchEvent", "Finger touched at X: " + screenX + " Y: " + screenY);
    }


    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button)
    {
        Gdx.app.log("TouchEvent", "Finger released at X: " + screenX + " Y: " + screenY);
    }

    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer)
    {
        Gdx.app.log("DragEvent", "Finger at position X: " + screenX + " Y: " + screenY);
    }

}
