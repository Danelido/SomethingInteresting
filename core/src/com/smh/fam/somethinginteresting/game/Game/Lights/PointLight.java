package com.smh.fam.somethinginteresting.game.Game.Lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;

import box2dLight.RayHandler;

/**
 * Created by Alexander on 2017-03-27.
 */

public class PointLight extends box2dLight.PointLight {

    private float x, y;
    private String id;

    public PointLight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y, String id) {
        super(rayHandler, rays, color, distance, x, y);
        this.x = x;
        this.y = y;
        this.id = id;

        this.soft = false;
        this.xray = false;
        this.softShadowLength = 0;
    }


    public Vector2 getLightPosition(){return new Vector2(x * CoreValues_Static.PPM,y * CoreValues_Static.PPM);} // Converting it back to world coords
    public String getID() {return id;}

}
