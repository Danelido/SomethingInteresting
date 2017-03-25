package com.smh.fam.somethinginteresting.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;

import java.io.FileNotFoundException;



import static com.smh.fam.somethinginteresting.game.Core.RenderingHelper.convertToBatchPlacement;

/**
 * Created by Axel on 2017-03-25.
 */

public class Player {
    private Body simulationBody;
    private Texture texture;

    private final float WIDTH = 30f; // Box2D coordinates
    private final float HEIGHT = 30f;

    public Player(World world, TextureStorage textureStorage, Vector2 position){
        try {
            texture = textureStorage.getTexture("player.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
        float[] batchPlacement = convertToBatchPlacement(simulationBody.getPosition(), new Vector2(WIDTH, HEIGHT), simulationBody.getAngle());

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


    public void fingerTouchedScreen(int screenX, int screenY, int pointer, int button)
    {
        // Transform screen coord to game coord
        int xRelativeToGame =  (int)(((float)screenX / (float)(Gdx.graphics.getWidth()) ) * CoreValues_Static.VIRTUAL_WIDTH);
        int yRelativeToGame =  (int)(((float)screenY / (float)(Gdx.graphics.getHeight()) ) * CoreValues_Static.VIRTUAL_HEIGHT);
        Gdx.app.log("TouchEvent","GameX: " + xRelativeToGame + " GameY: " + yRelativeToGame );
    }


    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button)
    {
        //Gdx.app.log("TouchEvent", "Finger released at X: " + screenX + " Y: " + screenY);
    }

    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer)
    {
        //Gdx.app.log("DragEvent", "Finger at position X: " + screenX + " Y: " + screenY);
    }

}
