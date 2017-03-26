package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Alexander on 2017-03-26.
 */

public class CoordinateTransformer {


    // Convert regular screen coords to game relative coords
    public static Vector2 convertToGameCoords(int x, int y)
    {
        int gx = (int)(((float)x / (float)(Gdx.graphics.getWidth()) ) * CoreValues_Static.VIRTUAL_WIDTH);
        int gy = (int)(((float)y / (float)(Gdx.graphics.getHeight()) ) * CoreValues_Static.VIRTUAL_HEIGHT);

        return new Vector2(gx,gy);
    }

    public static Vector2 convertToGameCoords(float x, float y)
    {
        float gx = ((x / (float)(Gdx.graphics.getWidth()) ) * CoreValues_Static.VIRTUAL_WIDTH);
        float gy = ((y / (float)(Gdx.graphics.getHeight()) ) * CoreValues_Static.VIRTUAL_HEIGHT);

        return new Vector2(gx,gy);
    }

    public static Vector2 convertToGameCoords(Vector2 xy)
    {
        Vector2 temp = new Vector2();
        temp.x = ((xy.x / (float)(Gdx.graphics.getWidth()) ) * CoreValues_Static.VIRTUAL_WIDTH);
        temp.y = ((xy.y / (float)(Gdx.graphics.getHeight()) ) * CoreValues_Static.VIRTUAL_HEIGHT);

        return temp;
    }

    // Translate relative game coord to box2d coord system
    public static Vector2 convertFromGameCoordsToBox2DCoords(Vector2 xy)
    {
        Vector2 temp = new Vector2();
        temp.x = xy.x - (CoreValues_Static.VIRTUAL_WIDTH/2);
        temp.y = ((CoreValues_Static.VIRTUAL_HEIGHT) - xy.y) - CoreValues_Static.VIRTUAL_HEIGHT/2; // flip y-axis and calculate height/2 offset
        return temp;
    }

    public static Vector2 fingerPressedInWorldSpace(int screenX, int screenY, Vector2 cameraPosition)
    {
        Vector2 temp = (convertToGameCoords(screenX, screenY));
        temp = convertFromGameCoordsToBox2DCoords(temp);

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
