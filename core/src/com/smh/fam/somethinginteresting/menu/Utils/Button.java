package com.smh.fam.somethinginteresting.menu.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.CoordinateTransformer;
import com.smh.fam.somethinginteresting.menu.Enums.BUTTON_LIST;

import java.io.FileNotFoundException;

import static com.smh.fam.somethinginteresting.menu.Core.MenuState.textureStorage;

/**
 * Created by Alexander on 2017-04-08.
 */

public class Button {

    private BUTTON_LIST _BUTTON_TYPE;
    private Texture texture;
    private Vector2 position, openGLposition, size; // Position - world coordinates,     openGLposition - openGL coordinates
    private boolean activated = false;

    // Variables for effect purpose
    private float defaultScale  = 1.0f;
    private float maxScaleX     = 1.05f,         maxScaleY     = 1.05f;
    private float buttonScaleX  = defaultScale,  buttonScaleY  = defaultScale;

    /* IMPORTANT! */
    /* Position MUST be world coordinates! Range between 0 - VirtualWidth or VirtualHeight */
    public Button(Vector2 position, Vector2 size, String PathToTexture, boolean centerPositionOrigin, BUTTON_LIST _BUTTON_TYPE)
    {
        this.position = position;
        this.size = size;
        this._BUTTON_TYPE = _BUTTON_TYPE;
        // Load texture
        try {
            texture = textureStorage.getTexture(PathToTexture);
        }catch(FileNotFoundException e){
            System.out.println("Couldn't find texture at: " + PathToTexture);
            e.printStackTrace();
        }
        // Check if position origin should be centered
        if(centerPositionOrigin) {
            this.position.sub(size.x/2, -size.y/2);
        }
        openGLposition = CoordinateTransformer.convertGameCoordToBatchCoords(this.position);

    }

    /* IMPORTANT! */
    /* fingerPosition MUST be world coordinates! Range between 0 - VirtualWidth or VirtualHeight */
    public boolean beingPressed(Vector2 fingerPosition)
    {
        if(fingerPosition.x >= position.x && fingerPosition.x  <= (position.x+size.x) &&
                fingerPosition.y >= (position.y-size.y) && fingerPosition.y <= position.y){

            /* If the button is already activated and this code runs again
               (which gets done when their finger is being released inside the button bounds)
               means that it's time to perform some action handled in current submenu.*/
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

    public void render(SpriteBatch batch)
    {
        batch.draw(texture,openGLposition.x,openGLposition.y,
                size.x/2,size.y/2,size.x,size.y,
                buttonScaleX,buttonScaleY,
                0.0f,
                0,0,
                texture.getWidth(),texture.getHeight(),
                false,false);
    }

    private void basicPressingEffect()
    {
        activated = true;
        if(buttonScaleX != maxScaleX) buttonScaleX = maxScaleX;
        if(buttonScaleY != maxScaleY) buttonScaleY = maxScaleY;
    }

    private void resetBasicEffect()
    {
        if(activated) activated = false;
        if(buttonScaleX != defaultScale) buttonScaleX = defaultScale;
        if(buttonScaleY != defaultScale) buttonScaleY = defaultScale;
    }

    // Method for altering maxScale (Optional)
    public void setNewMaxScaleValue(float newMaxScaleX, float newMaxScaleY)
    {
        maxScaleX = newMaxScaleX;
        maxScaleY = newMaxScaleY;
    }

    public BUTTON_LIST getButtonType(){ return _BUTTON_TYPE; }
    public boolean isActivated(){ return activated; }
}
