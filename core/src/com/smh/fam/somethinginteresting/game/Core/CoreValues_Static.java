package com.smh.fam.somethinginteresting.game.Core;

/**
 * Created by Alexander on 2017-03-25.
 */

 // Static class, no functions unless they are static and so on..
public class CoreValues_Static {

    /* IMPORTANT */
    /* This is used to measure box2D meters to pixels
     * When you create something in box2D you will have to divide it with PPM! Position, size etc...
     * 100 pixels = 1 meter */
    public static final float PPM = 100f;

    // Our target resolution, the screen can by any size but this is our "game screen size"
    public static final float VIRTUAL_WIDTH =  (1280);
    public static final float VIRTUAL_HEIGHT = (VIRTUAL_WIDTH / 16 * 9); // 16:9 format

    public static final float GRAVITY_CONSTANT = -9.82f;
    public static final float FORCE_MULTIPLYER_CONSTANT = 4.5f;
    public static final float FORCE_MAX = 3f;
    public static final float MAX_FORCE_DISTANCE = (CoreValues_Static.FORCE_MAX * CoreValues_Static.PPM) / CoreValues_Static.FORCE_MULTIPLYER_CONSTANT;
    public static final float DIRECTION_LINE_SIZE_FIXED_CONSTANT = 0.05f;
    public static final float MAX_FORCE_DIRECTION_LINE_POWER = (CoreValues_Static.MAX_FORCE_DISTANCE / CoreValues_Static.PPM) * DIRECTION_LINE_SIZE_FIXED_CONSTANT;

}

