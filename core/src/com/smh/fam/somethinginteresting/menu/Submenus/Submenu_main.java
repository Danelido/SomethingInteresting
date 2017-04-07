package com.smh.fam.somethinginteresting.menu.Submenus;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.CoordinateTransformer;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;
import com.smh.fam.somethinginteresting.game.State.PlayState;
import com.smh.fam.somethinginteresting.menu.Managers.SubmenuManager;

import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alexander on 2017-04-07.
 */

public class Submenu_main extends Submenu {

    // Values that needs to be added -> float[0] = x , float[1] = y , float[2] = width , float[3] = height,
    // float[4] & float[5] is scaling values, they are used for the visual effects when a button is being pressed
    // Add more to taste
    private ConcurrentHashMap<String, float[]> buttonData;

    // Textures
    private Texture playbutton_texture, settingsbutton_texture;

    private String active_button_identifier;
    private float buttonWidth;
    private float buttonHeight;

    public Submenu_main(GameStateManager gsm, SubmenuManager smm, Camera camera,TextureStorage textureStorage) {super(gsm, smm, camera,textureStorage);}

    @Override
    public void init() {
        loadTextures();
        buttonData = new ConcurrentHashMap <String, float[]>();
        buttonWidth = 260;
        buttonHeight = 120;
        active_button_identifier = "none";

        newButtonData("Play", new float[]{-buttonWidth/2f, 0 ,buttonWidth, buttonHeight, 1.0f, 1.0f});
        newButtonData("Settings", new float[]{-buttonWidth/2f, -buttonHeight - 25 ,buttonWidth, buttonHeight, 1.0f, 1.0f});

    }

    private void loadTextures()
    {
        try {
            playbutton_texture = textureStorage.getTexture("menuResources/mainMenu_startButton.png");
            settingsbutton_texture = textureStorage.getTexture("menuResources/mainMenu_settingsButton.png");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }


    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        // Playbutton
        batch.draw(playbutton_texture,
                getButtonData("Play")[0], getButtonData("Play")[1],
                buttonWidth/2f, buttonHeight/2f,
                getButtonData("Play")[2], getButtonData("Play")[3],
                getButtonData("Play")[4], getButtonData("Play")[5],
                0.0f,
                0,0,
                playbutton_texture.getWidth(), playbutton_texture.getHeight(),
                false,false);

        // Settingsbutton
        batch.draw(settingsbutton_texture,
                getButtonData("Settings")[0], getButtonData("Settings")[1],
                buttonWidth/2f, buttonHeight/2f,
                getButtonData("Settings")[2], getButtonData("Settings")[3],
                getButtonData("Settings")[4], getButtonData("Settings")[5],
                0.0f,
                0,0,
                playbutton_texture.getWidth(), playbutton_texture.getHeight(),
                false,false);


    }


    @Override
    public void fingerTouchingScreen(int screenX, int screenY, int pointer, int button) {
        Vector2 touched = CoordinateTransformer.convertToGameCoords(screenX, screenY, 1.0f);

        /*
         * Loops through every key and if the finger is in bounds within a button, the identifier gets set and used later in "fingerReleased" to determine
         * if the buttons is still being pressed. If it is then do something depending on which button got pressed.
         */
        for(String key: buttonData.keySet()){

            float[] tempData = buttonData.get(key);
            Vector2 currentButtonCoord = CoordinateTransformer.convertToGameCoords(tempData[0] + (CoreValues_Static.VIRTUAL_WIDTH/2),
                    ((CoreValues_Static.VIRTUAL_HEIGHT/2) - tempData[1]) - tempData[3],
                    1.0f);

            if(touched.x >= currentButtonCoord.x && touched.x <= currentButtonCoord.x+tempData[2]
                    && touched.y >= currentButtonCoord.y && touched.y <= currentButtonCoord.y + tempData[3])
            {
                active_button_identifier = key;
                tempData[4] = 1.05f;
                tempData[5] = 1.05f;
                System.out.println("Button pressed: " + key);
                if(buttonData.putIfAbsent(key, tempData) !=  null)
                    System.out.println("Scale values altered on: " + key);
                    else
                    System.out.println("Scale values NOT altered on: " + key);


            }

        }



    }

    @Override
    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button) {

        Vector2 touched = CoordinateTransformer.convertToGameCoords(screenX, screenY, 1.0f);

            if(!active_button_identifier.equals("none")) {
                float[] tempData = buttonData.get(active_button_identifier);
                Vector2 currentButtonCoord = CoordinateTransformer.convertToGameCoords(tempData[0] + (CoreValues_Static.VIRTUAL_WIDTH/2),
                        ((CoreValues_Static.VIRTUAL_HEIGHT/2) - tempData[1]) - tempData[3],
                        1.0f);

                if (touched.x >= currentButtonCoord.x && touched.x <= currentButtonCoord.x + tempData[2]
                        && touched.y >= currentButtonCoord.y && touched.y <= currentButtonCoord.y + tempData[3]) {

                    if(active_button_identifier.equals("Play")){
                        gsm.changeStateTo(new PlayState(gsm));
                    }

                    if(active_button_identifier.equals("Settings")){

                    }

                }

                tempData[4] = 1.0f;
                tempData[5] = 1.0f;
                if (buttonData.putIfAbsent(active_button_identifier, tempData) != null)
                    System.out.println("Scale values altered back to normal on: " + active_button_identifier);
                active_button_identifier = "none";

            }





    }

    @Override
    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer) {

    }

    private void newButtonData(String identifier, float[] data)
    {
        if(buttonData != null)
        buttonData.put(identifier,data);
        else
            System.out.println("ButtonData is null!");
    }

    private float[] getButtonData(String identifier)
    {
        float[] data = buttonData.get(identifier);
        if(data != null) return data;
        else{
            System.out.println("Invalid identifier, data not found!");
            throw new NullPointerException();

        }
    }



    @Override
    public void dispose() {

    }
}
