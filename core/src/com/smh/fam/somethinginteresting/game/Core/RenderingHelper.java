package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Axel on 2017-03-25.
 */

public class RenderingHelper {

    /**
     *
     * @return
     */
    public static float[] convertToBatchPlacement(Vector2 position, Vector2 dimensions, float angle){
        float[] returnValue = new float[5];
        returnValue[0] = position.x-dimensions.x; returnValue[1] = position.y-dimensions.y;
        returnValue[2] = dimensions.x*2f; returnValue[3] = dimensions.y*2f;
        returnValue[4] = angle*180f/(float) Math.PI; // Convert from degrees to rads
        return returnValue;
    };
}
