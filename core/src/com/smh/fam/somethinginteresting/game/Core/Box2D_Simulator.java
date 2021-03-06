package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Axel on 2017-03-24.
 */

public class Box2D_Simulator {
    private float simulatedTime = 0.0f;
    private final float timeScale = 1.0f;
    private World world;
    public Box2DDebugRenderer debugRenderer;


    private final int VELOCITY_ITERATIONS = 2;
    private final int POSITION_ITERATIONS = 2;

    public Box2D_Simulator(){
        Box2D.init();
        world = new World(new Vector2(0f, 0f), true);

        debugRenderer = new Box2DDebugRenderer();
    }

    public void simulate(float deltaT){
        deltaT *= timeScale;
        deltaT = Math.min(deltaT, 0.25f); // To avoid stepping too far on slow devices
        simulatedTime += deltaT;

        world.step(deltaT, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    public void setGravity(Vector2 gravity){
        world.setGravity(gravity);
    }
    public Vector2 getGravity(){
        return world.getGravity();
    }

    public float getSimulatedTime(){
        return simulatedTime;
    }
    public World getWorld(){ return world;}
}
