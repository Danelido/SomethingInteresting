package com.smh.fam.somethinginteresting.game.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
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
 * Created by Axel on 2017-03-26.
 */

public class Target {
    private Texture texture;
    private Body simulationBody;

    private float radius;

    public Target(World world, TextureStorage textureStorage, Vector2 position){
        try {
            texture = textureStorage.getTexture("target.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        radius = 20;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position.scl(1f/CoreValues_Static.PPM));

        simulationBody = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius/CoreValues_Static.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = 0x0001;
        fixtureDef.filter.maskBits  = 0x0001;

        Fixture fixture = simulationBody.createFixture(fixtureDef);

        circleShape.dispose();
    }


    public void render(Batch batch){
        float[] batchPlacement =
                convertToBatchPlacement(CoordinateTransformer.ScreenTexturePosition(simulationBody.getPosition(), new Vector2(radius,radius)),
                new Vector2(radius, radius), 0);
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
}
