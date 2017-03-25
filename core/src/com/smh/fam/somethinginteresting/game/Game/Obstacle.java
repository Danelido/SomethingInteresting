package com.smh.fam.somethinginteresting.game.Game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Axel on 2017-03-25.
 */

public class Obstacle {
    private Body simulationBody;
    public Obstacle(World world, Vector2 position1, Vector2 position2){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position1);

        simulationBody = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(position1.x-position2.x, position1.y-position2.y);

        Fixture fixture = simulationBody.createFixture(boxShape,0.0f);

        boxShape.dispose();
    }
}
