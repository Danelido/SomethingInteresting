package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander on 2017-03-26.
 */

public class CoordinateTransformer {


    // Convert regular screen coords to game relative coords
    public static Vector2 convertToGameCoords(int x, int y, float cameraZoomAmount)
    {
        if(Float.isNaN(cameraZoomAmount))cameraZoomAmount = 1.0f;

        int gx = (int)(((float)x / (float)(Gdx.graphics.getWidth()) ) * (CoreValues_Static.VIRTUAL_WIDTH * cameraZoomAmount));
        int gy = (int)(((float)y / (float)(Gdx.graphics.getHeight()) ) * CoreValues_Static.VIRTUAL_HEIGHT * cameraZoomAmount);

        return new Vector2(gx,gy);
    }

    public static Vector2 convertToGameCoords(float x, float y, float cameraZoomAmount)
    {
        if(Float.isNaN(cameraZoomAmount))cameraZoomAmount = 1.0f;
        float gx = ((x / (float)(Gdx.graphics.getWidth()) ) * (CoreValues_Static.VIRTUAL_WIDTH * cameraZoomAmount));
        float gy = ((y / (float)(Gdx.graphics.getHeight()) ) * (CoreValues_Static.VIRTUAL_HEIGHT * cameraZoomAmount));

        return new Vector2(gx,gy);
    }

    public static Vector2 convertToGameCoords(Vector2 xy, float cameraZoomAmount)
    {
        if(Float.isNaN(cameraZoomAmount))cameraZoomAmount = 1.0f;
        Vector2 temp = new Vector2();
        temp.x = ((xy.x / (float)(Gdx.graphics.getWidth()) ) * (CoreValues_Static.VIRTUAL_WIDTH * cameraZoomAmount));
        temp.y = ((xy.y / (float)(Gdx.graphics.getHeight()) ) * (CoreValues_Static.VIRTUAL_HEIGHT * cameraZoomAmount));

        return temp;
    }

    // Translate relative game coord to box2d coord system
    public static Vector2 convertFromGameCoordsToBox2DCoords(Vector2 xy, float cameraZoomAmount)
    {
        if(Float.isNaN(cameraZoomAmount))cameraZoomAmount = 1.0f;
        Vector2 temp = new Vector2();
        temp.x = xy.x - ((CoreValues_Static.VIRTUAL_WIDTH * cameraZoomAmount)/2);
        temp.y = ((CoreValues_Static.VIRTUAL_HEIGHT * cameraZoomAmount) - xy.y) - (CoreValues_Static.VIRTUAL_HEIGHT * cameraZoomAmount)/2; // flip y-axis and calculate height/2 offset
        return temp;
    }

    public static Vector2 fingerPressedInWorldSpace(int screenX, int screenY, Vector2 cameraPosition, float cameraZoomAmount)
    {
        if(Float.isNaN(cameraZoomAmount))cameraZoomAmount = 1.0f;

        Vector2 temp = (convertToGameCoords(screenX, screenY,cameraZoomAmount));
        temp = convertFromGameCoordsToBox2DCoords(temp,cameraZoomAmount);

        temp.add(cameraPosition.scl(CoreValues_Static.PPM));
        return temp;

    }

    public static Vector2 box2DCoordinatesToWorldCoordinates(Vector2 box2Dposition)
    {
        Vector2 temp = new Vector2();
        temp.x = (box2Dposition.x * CoreValues_Static.PPM);
        temp.y = (box2Dposition.y * CoreValues_Static.PPM);

        return temp;
    }

    public static Vector2 box2DCoordinatesToWorldCoordinates(float x, float y)
    {
        Vector2 temp = new Vector2();
        temp.x = (x * CoreValues_Static.PPM);
        temp.y = (y * CoreValues_Static.PPM);

        return temp;
    }

    // transform box2d coordinates to screen coordinates
    public static Vector2 ScreenTexturePosition(Vector2 position, Vector2 size )
    {
        Vector2 temp = new Vector2();
        temp.x = ((position.x * CoreValues_Static.PPM) - CoreValues_Static.VIRTUAL_WIDTH/2);
        temp.y = ((position.y * CoreValues_Static.PPM) - CoreValues_Static.VIRTUAL_HEIGHT/2);
        return temp;
    }


}
