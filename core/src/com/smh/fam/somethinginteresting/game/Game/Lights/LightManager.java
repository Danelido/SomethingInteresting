package com.smh.fam.somethinginteresting.game.Game.Lights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;

import java.util.concurrent.CopyOnWriteArrayList;

import box2dLight.RayHandler;

/**
 * Created by Alexander on 2017-03-27.
 */



public class LightManager {

    private RayHandler rayHandler;
    private World world;
    private CopyOnWriteArrayList<PointLight> pointLights;


    public LightManager(World world)
    {
        this.world = world;
        rayHandler = new RayHandler(world);
        pointLights = new CopyOnWriteArrayList<PointLight>();

    }

    public void updateCamera(OrthographicCamera box2dCamera )
    {
        if(pointLights != null) {
            if (!pointLights.isEmpty()) {
                rayHandler.setCombinedMatrix(
                        box2dCamera.combined,
                        box2dCamera.position.x,
                        box2dCamera.position.y,
                        box2dCamera.viewportWidth * box2dCamera.zoom,
                        box2dCamera.viewportHeight * box2dCamera.zoom);
            }
        }


    }


    public void pointLights_updateAndRender()
    {
            if(pointLights != null){
                if(!pointLights.isEmpty()){
                    rayHandler.updateAndRender();
                }
            }
    }


    public RayHandler getRayHandler()
    {
        return rayHandler;
    }

    public void addPointLight(Vector2 position, int rays, Color color, float radius, String id){
        if(id == null) id = "regular";
        pointLights.add(new PointLight(rayHandler,rays,color,radius / 100,position.x / CoreValues_Static.PPM,position.y/ CoreValues_Static.PPM, id));
    }

    public PointLight getLightByID(String id)
    {
        PointLight temp = null;
        if(!pointLights.isEmpty()) {
            for (int i = 0; i < pointLights.size(); i++) {
                if(pointLights.get(i).getID().trim().equalsIgnoreCase(id.trim())) {
                    temp = pointLights.get(i);
                    return temp;
                }

            }
        }
        return temp;
    }

    public void moveLights(float x, float y){
        for(int i = 0; i < pointLights.size(); i++)
        {
            pointLights.get(i).setPosition(pointLights.get(i).getX() + (x / CoreValues_Static.PPM) ,pointLights.get(i).getY() + (y / CoreValues_Static.PPM) );
        }
    }

    public void disposeAll() {
        if(pointLights != null){
            if(!pointLights.isEmpty()) {
                for(int i = 0; i < pointLights.size(); i++){
                    pointLights.get(i).dispose();
                }
            }
        }
    }


}
