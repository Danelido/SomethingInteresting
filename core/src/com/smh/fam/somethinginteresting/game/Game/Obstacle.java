package com.smh.fam.somethinginteresting.game.Game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.smh.fam.somethinginteresting.game.Core.RenderingHelper.convertToBatchPlacement;

/**
 * Created by Axel on 2017-03-25.
 */

public class Obstacle {
    private Body simulationBody;
    private float width;
    private float height;

    public Obstacle(World world, Vector2 position1, Vector2 position2, float angle){
        // Adjusting positions in order to make sure position1 has the smallest coordinates
        if (position2.x < position1.x) {float tempCoord = position2.x; position2.x = position1.x; position1.x = tempCoord;}
        if (position2.y < position1.y) {float tempCoord = position2.y; position2.y = position1.y; position1.y = tempCoord;}

        width = position2.x-position1.x;
        height = position2.y-position1.y;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position1);
        bodyDef.angle = (float) Math.toRadians(angle);

        simulationBody = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(width, height);

        Fixture fixture = simulationBody.createFixture(boxShape,0.0f);

        boxShape.dispose();
    }

    public void render(Batch batch, ShapeRenderer shapeRenderer){
        float[] batchPlacement = convertToBatchPlacement(simulationBody.getPosition(), new Vector2(width, height), simulationBody.getAngle());

        shapeRenderer.setColor(0.0f, 0.3f, 0.8f, 1f);

        shapeRenderer.rect(
                batchPlacement[0], batchPlacement[1],
                batchPlacement[2]/2f, batchPlacement[3]/2f,
                batchPlacement[2], batchPlacement[3],
                1f, 1f,
                batchPlacement[4] );



    }
}
