package com.smh.fam.somethinginteresting.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smh.fam.somethinginteresting.game.Core.CoordinateTransformer;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;

import java.io.FileNotFoundException;



import static com.smh.fam.somethinginteresting.game.Core.RenderingHelper.convertToBatchPlacement;

/**
 * Created by Axel on 2017-03-25.
 */

public class Player {

    private World world;
    private Body simulationBody;
    private Texture texture;
    private Camera camera;
    // Player width & height box2d
    private final float WIDTH = 30f;
    private final float HEIGHT = 30f;

    private Vector2 force_whereToApplyForceToPlayer = new Vector2(0,0); // Always going to be in the center, atleast for now. Initialized to 0,0 instead of null
    private Vector2 force_currentFingerPositionOnScreen = new Vector2(0,0);
    private boolean playerTargetedByFinger = false;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Player(World world, TextureStorage textureStorage, Vector2 position, Camera camera){
        this.world = world;
        this.camera = camera;
        try {
            texture = textureStorage.getTexture("player.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.scl(1f/CoreValues_Static.PPM));
        bodyDef.allowSleep = false;

        simulationBody = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(WIDTH / CoreValues_Static.PPM, HEIGHT/ CoreValues_Static.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = simulationBody.createFixture(fixtureDef);

        boxShape.dispose();

        //simulationBody.setLinearVelocity(new Vector2(-20f, 10f));
    }

    public void render(Batch batch){
        float[] batchPlacement = convertToBatchPlacement(
                CoordinateTransformer.ScreenTexturePosition(simulationBody.getPosition(),new Vector2(WIDTH,HEIGHT)),
                new Vector2(WIDTH, HEIGHT), simulationBody.getAngle());

        batch.draw(texture,
                batchPlacement[0], batchPlacement[1],
                batchPlacement[2]/2f, batchPlacement[3]/2f,
                batchPlacement[2], batchPlacement[3],
                1.0f, 1.0f,
                batchPlacement[4],
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false );
    }
    // Display which direction you're applying the force
    public void displayForceDirection()
    {
        force_whereToApplyForceToPlayer.set(simulationBody.getPosition().x, simulationBody.getPosition().y); // to keep the line centered on player at all times
        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        Gdx.gl.glLineWidth(5.0f);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(force_whereToApplyForceToPlayer.x ,
                            force_whereToApplyForceToPlayer.y,
                                force_currentFingerPositionOnScreen.x ,
                                    force_currentFingerPositionOnScreen.y);
        shapeRenderer.end();
        Gdx.gl.glLineWidth(1.0f);
    }

    // NOTE: Right now it is being applied to the center of the player.
    // With that being said force_on_player_location is ALWAYS equal to player center
    // Apply force to player
    private void applyForceToPlayer(Vector2 force_on_player_location, Vector2 fingerReleased)
    {
        Vector2 direction = new Vector2();

        float x = force_on_player_location.x - fingerReleased.x;
        float y =  force_on_player_location.y - fingerReleased.y;

        double c = Math.sqrt( (x * x) + (y * y) );
        direction.x = (float)(x / c) * CoreValues_Static.FORCE_MULTIPLYER_CONSTANT;
        direction.y =  (float)(y / c) * CoreValues_Static.FORCE_MULTIPLYER_CONSTANT;
        simulationBody.applyLinearImpulse(direction.x, direction.y, force_on_player_location.x, force_on_player_location.y,true);
    }

    public void applyForceToPlayer(Vector2 direction) {
        simulationBody.applyForce(direction.x, direction.y, simulationBody.getPosition().x, simulationBody.getPosition().y, true);
    }

    public void fingerTouchedScreen(int screenX, int screenY, int pointer, int button)
    {
        Vector2 point = CoordinateTransformer.fingerPressedInWorldSpace(screenX, screenY, new Vector2(camera.position.x, camera.position.y));
        Vector2 playerPoint = CoordinateTransformer.box2DCoordinatesToWorldCoordinates(simulationBody.getPosition());
            // If finger is on the box
             if(point.x >= playerPoint.x - WIDTH
                     && point.x <= playerPoint.x + WIDTH
                        && point.y  >= playerPoint.y - HEIGHT
                            && point.y <= playerPoint.y + HEIGHT)
             {
                 force_whereToApplyForceToPlayer.set(playerPoint.x, playerPoint.y);
                 force_currentFingerPositionOnScreen.set(point.x/CoreValues_Static.PPM, point.y/CoreValues_Static.PPM);
                 playerTargetedByFinger = true;
            }
    }


    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button)
    {
        // Only call this if the box is being targeted by the finger
        if(playerTargetedByFinger) {
        playerTargetedByFinger = false;
        applyForceToPlayer(force_whereToApplyForceToPlayer, force_currentFingerPositionOnScreen);
        }

    }

    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer)
    {
        // Create a vector and convert it then use those coords to render the "force line" correctly
        Vector2 point = CoordinateTransformer.fingerPressedInWorldSpace(screenX, screenY, new Vector2(camera.position.x, camera.position.y));
        force_currentFingerPositionOnScreen.set((point.x) / CoreValues_Static.PPM, (point.y)/ CoreValues_Static.PPM);
    }




    public void dispose()
    {
        shapeRenderer.dispose();
    }

    public boolean playerIsTargeted() {return playerTargetedByFinger;}
    public Vector2 getPosition(){
        return simulationBody.getPosition();
    }

}
