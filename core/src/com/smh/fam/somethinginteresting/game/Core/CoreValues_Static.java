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

    public static final float FORCE_MULTIPLYER_CONSTANT = 1.50f; // increasing this will make the force stronger faster when draggin finger.
    public static final float FORCE_MAX = 3.0f; // Max force, otherwise it will fly around the map like a retarded owl

    // calculates the distance that needs to be dragged before reaching the distance that will generate max force, in this case 3.0f
    public static final float MAX_FORCE_DISTANCE = (CoreValues_Static.FORCE_MAX * CoreValues_Static.PPM) / CoreValues_Static.FORCE_MULTIPLYER_CONSTANT;
    // Calculate from max force distance which is screen coords to coordinates relative to the world
    public static final float MAX_FORCE_DISTANCE_IN_PPM = (CoreValues_Static.MAX_FORCE_DISTANCE / CoreValues_Static.PPM);

}

