package com.smh.fam.somethinginteresting.game.Game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Axel on 2017-03-25.
 */

public class Obstacle {
    private Body simulationBody;
    public Obstacle(World world, Vector2 position1, Vector2 position2){
        // Adjusting positions in order to make sure position1 has the smallest coordinates
        if (position2.x < position1.x) {float tempCoord = position2.x; position2.x = position1.x; position1.x = tempCoord;}
        if (position2.y < position1.y) {float tempCoord = position2.y; position2.y = position1.y; position1.y = tempCoord;}

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position1);

        simulationBody = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(position2.x-position1.x, position2.y-position1.y);

        Fixture fixture = simulationBody.createFixture(boxShape,0.0f);

        boxShape.dispose();
    }
}
