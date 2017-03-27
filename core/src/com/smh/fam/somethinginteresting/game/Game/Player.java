package com.smh.fam.somethinginteresting.game.Game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
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
    private Texture texture, arrowTexture;
    private Camera camera;
    private float camera_zoom_amount = 1.0f;
    private Vector2 camera_firstTouch = new Vector2();
    // Player width & height box2d
    private final float WIDTH = 30f;
    private final float HEIGHT = 30f;

    // Direction arrow width & height & angle
    private float arrow_width = 128f;
    private float arrow_height = 30f;
    private double arrow_angle = 0.0f;

    private float arrow_scale_minimum_size = 1f/5f;                 // Minimum size of arrow
    private float arrow_size_scale = arrow_scale_minimum_size;      // Scaling of our arrow
    private float arrowThickness = 0.0f;                            // The thickness of the direction sprite will get thicker when the distance between the box and finger gets larger

    private Vector2 force_whereToApplyForceToPlayer = new Vector2(0,0); // Always going to be in the center, atleast for now. Initialized to 0,0 instead of null
    private Vector2 force_currentFingerPositionOnScreen = new Vector2(0,0);
    private boolean playerTargetedByFinger = false;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();


    public Player(World world, TextureStorage textureStorage, Vector2 position, Camera camera){
        this.world = world;
        this.camera = camera;
        try {
            texture = textureStorage.getTexture("player.png");
            arrowTexture = textureStorage.getTexture("directionArrow.png");
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
        fixtureDef.restitution = 0.3f;

        Fixture fixture = simulationBody.createFixture(fixtureDef);

        boxShape.dispose();

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
    public void displayForceDirection(Batch batch)
    {
        // to keep the line centered on player at all times
        force_whereToApplyForceToPlayer = CoordinateTransformer.ScreenTexturePosition(simulationBody.getPosition(), new Vector2(arrow_width, arrow_height));

        batch.setColor(arrow_size_scale ,1.0f - arrow_size_scale, 0.0f, 0.6f);
        batch.draw(arrowTexture,
                force_whereToApplyForceToPlayer.x - arrow_width*  (arrow_size_scale),
                force_whereToApplyForceToPlayer.y - (arrow_height /2f) * arrow_size_scale,
                arrow_width * (arrow_size_scale), (arrow_height /2f) * (arrow_size_scale),
                arrow_width * arrow_size_scale, arrow_height * arrow_size_scale,
                1.f,1.f,
                (float) arrow_angle+180f,
                0,0,
                arrowTexture.getWidth(), arrowTexture.getHeight(),
                false,false);
        batch.setColor(Color.WHITE);
    }

    // NOTE: Right now it is being applied to the center of the player.
    // With that being said force_on_player_location is ALWAYS equal to player center
    // Apply force to player
    private void applyForceToPlayer(Vector2 force_on_player_location, Vector2 fingerReleased)
    {
        float x =  (force_on_player_location.x - fingerReleased.x) * CoreValues_Static.PPM;
        float y =  (force_on_player_location.y - fingerReleased.y) * CoreValues_Static.PPM;

        Vector2 direction = new Vector2(x, y).nor().scl(-1f);
        double c = new Vector2(x,y).len();

        float force = (float)(c * CoreValues_Static.FORCE_MULTIPLYER_CONSTANT) / CoreValues_Static.PPM;

        if(force >= CoreValues_Static.FORCE_MAX) {force = CoreValues_Static.FORCE_MAX;}
        direction.scl((force));
        simulationBody.applyLinearImpulse(direction.x, direction.y, simulationBody.getPosition().x, simulationBody.getPosition().y,true);
    }

    public void applyForceToPlayer(Vector2 direction) {
        simulationBody.applyForce(direction.x, direction.y, simulationBody.getPosition().x, simulationBody.getPosition().y, true);
    }

    public void fingerTouchedScreen(int screenX, int screenY, int pointer, int button)
    {
        camera_firstTouch = CoordinateTransformer.fingerPressedInWorldSpace(screenX, screenY, new Vector2(camera.position.x, camera.position.y), camera_zoom_amount);
        Vector2 playerPoint = CoordinateTransformer.box2DCoordinatesToWorldCoordinates(simulationBody.getPosition());
            // If finger is on the box
             if(camera_firstTouch.x >= playerPoint.x - WIDTH
                     && camera_firstTouch.x <= playerPoint.x + WIDTH
                        && camera_firstTouch.y  >= playerPoint.y - HEIGHT
                            && camera_firstTouch.y <= playerPoint.y + HEIGHT)
             {
                 force_whereToApplyForceToPlayer.set(playerPoint.x, playerPoint.y);
                 force_currentFingerPositionOnScreen.set(camera_firstTouch.x/CoreValues_Static.PPM, camera_firstTouch.y/CoreValues_Static.PPM);
                 arrowThickness = 0.0f;
                 arrow_angle = getDegreesBetweenTwoPosition(camera_firstTouch, playerPoint);
                 playerTargetedByFinger = true;
            }
    }


    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button)
    {
        // Only call this if the box is being targeted by the finger
        if(playerTargetedByFinger) {
            playerTargetedByFinger = false;
            arrowThickness = 0.0f;
            arrow_size_scale = arrow_scale_minimum_size;
            applyForceToPlayer(simulationBody.getPosition(),force_currentFingerPositionOnScreen );
        }
    }

    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer) {
        if (playerTargetedByFinger) {

            Vector2 point = CoordinateTransformer.fingerPressedInWorldSpace(screenX, screenY, new Vector2(camera.position.x, camera.position.y), camera_zoom_amount);
            Vector2 playerPoint = CoordinateTransformer.box2DCoordinatesToWorldCoordinates(simulationBody.getPosition());

            // Create a vector and convert it then use those coords to render the "force line" correctly
            force_currentFingerPositionOnScreen.set((point.x) / CoreValues_Static.PPM, (point.y) / CoreValues_Static.PPM);

            // Retrieves the angle between the box and finger
            arrow_angle = getDegreesBetweenTwoPosition(point, playerPoint);

            // Distance between box and finger
            float deltaDistance = point.dst(playerPoint);

            // Distance cant go higher than the MAX_DISTANCE
            if(deltaDistance >= CoreValues_Static.MAX_FORCE_DISTANCE) deltaDistance =CoreValues_Static.MAX_FORCE_DISTANCE;

            // The thickness of the arrow depends on the distance
            float tempThickness = (deltaDistance/CoreValues_Static.PPM);

            // At that max force distance there is no need to keep increasing power (thickness/size)
            if (tempThickness >= CoreValues_Static.MAX_FORCE_DISTANCE_IN_PPM) {tempThickness = CoreValues_Static.MAX_FORCE_DISTANCE_IN_PPM;}
            if(tempThickness <= 0.0f) tempThickness = 0.0f;

            // After the tempThickness variable have been checked, apply it to arrowThickness;
            arrowThickness = tempThickness;

            // Calculate scale
            arrow_size_scale = ((arrowThickness) / CoreValues_Static.MAX_FORCE_DISTANCE_IN_PPM) + arrow_scale_minimum_size;
            if (arrow_size_scale >= 1.0f) {
                arrow_size_scale = 1.0f;
            }
        }
    }

    // Return a value between -180 and 180
    private double getDegreesBetweenTwoPosition(Vector2 point1, Vector2 point2){

        double temp;
        temp = Math.toDegrees(Math.atan2((point1.y - point2.y), (point1.x - point2.x)));

        // reduce the angle
        temp = temp % 360;

        // force it to be the positive remainder, so that 0 <= angle < 360
        temp = (temp + 360) % 360;

        // force into the minimum absolute value residue class, so that -180 < angle <= 180
        if (temp > 180) temp -= 360;

        return temp;
    }

    public void dispose()
    {
        shapeRenderer.dispose();
    }

    public boolean playerIsTargeted() {return playerTargetedByFinger;}
    public Vector2 getPosition(){return simulationBody.getPosition();}
    public void setCameraZoom(float amount){camera_zoom_amount = amount;}

}
