package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Axel on 2017-03-25.
 */

public class RenderingHelper {

    /**
     * Converts world vectors and angle to values which can
     * be used when calling batch.draw()
     * return values are in order:
     * {x-position, y-position, width, height, angle}
     * @return
     */
    public static float[] convertToBatchPlacement(Vector2 position, Vector2 dimensions, float angle){
        float[] returnValue = new float[5];
        returnValue[0] = position.x-dimensions.x; returnValue[1] = position.y-dimensions.y;
        returnValue[2] = dimensions.x*2f; returnValue[3] = dimensions.y*2f;
        returnValue[4] = (float) Math.toDegrees(angle);
        return returnValue;
    };
}
