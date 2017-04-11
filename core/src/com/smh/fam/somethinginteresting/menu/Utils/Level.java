package com.smh.fam.somethinginteresting.menu.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.CoordinateTransformer;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;

import java.io.FileNotFoundException;

/**
 * Created by Alexander on 2017-04-11.
 */

public class Level {

    /* TODO:
        *Create 4 images( base 3-star image ) and then 1 star filled out of 3, and so on.
        *Database handling ( current stars on each level )   MySQL on device
    */

    private String pathToLevelFile;
    private Texture texture;
    private Vector2 position, size, openGLposition,containerScale;
    private float maxScaleX = 1.05f, maxScaleY = 1.05f, defaultScale = 1.0f;

    protected boolean activated;

    public Level(Vector2 position, Vector2 size, String textureFilepath, TextureStorage textureStorage, String pathToLevelFile){
        this.position = position;
        this.size = size;
        this.pathToLevelFile = pathToLevelFile;
        containerScale = new Vector2(defaultScale ,defaultScale);
        openGLposition = CoordinateTransformer.convertGameCoordToBatchCoords(this.position);
        try {
            texture = textureStorage.getTexture(textureFilepath);
        }catch(FileNotFoundException e){
            System.out.println("Couldn't find texture at: " + textureFilepath);
            e.printStackTrace();
        }
        activated = false;
        init();
    }

    public void init(){}
    public void render(SpriteBatch batch)
    {
        batch.draw(texture,openGLposition.x,openGLposition.y,
                size.x/2,size.y/2,size.x,size.y,
                containerScale.x,containerScale.y,
                0.0f,
                0,0,
                texture.getWidth(),texture.getHeight(),
                false,false);
    }




    private void basicPressingEffect()
    {
        activated = true;
        if(containerScale.x != maxScaleX) containerScale.x = maxScaleX;
        if(containerScale.y != maxScaleY) containerScale.y = maxScaleY;
    }

    private void resetBasicEffect()
    {
        if(activated) activated = false;
        if(containerScale.x != defaultScale) containerScale.x = defaultScale;
        if(containerScale.y != defaultScale) containerScale.y = defaultScale;
    }

    // Method for altering maxScale (Optional)
    public void setNewMaxScaleValue(float newMaxScaleX, float newMaxScaleY)
    {
        maxScaleX = newMaxScaleX;
        maxScaleY = newMaxScaleY;
    }

    /* IMPORTANT! */
    /* fingerPosition MUST be world coordinates! Range between 0 - VirtualWidth or VirtualHeight */
    public boolean beingPressed(Vector2 fingerPosition)
    {
        if(fingerPosition.x >= position.x && fingerPosition.x  <= (position.x+size.x) &&
                fingerPosition.y >= (position.y-size.y) && fingerPosition.y <= position.y){

            /* If the button is already activated and this code runs again
               (which gets done when their finger is being released inside the button bounds)
               means that it's time to perform some action handled in current stage.
               Note that this is also checked in current stage file*/
            if(!activated) {
                basicPressingEffect();
            }
            else {
                resetBasicEffect();
            }

            return true;
        }
        resetBasicEffect();
        return false;
    }

    public String getPathToLevel(){return pathToLevelFile;}

}
