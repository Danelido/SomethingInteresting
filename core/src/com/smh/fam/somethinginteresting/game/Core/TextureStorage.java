package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Axel on 2017-03-25.
 */

public class TextureStorage {

    private Map<String, Texture> storage = new HashMap<String, Texture>();

    public Texture getTexture(String textureName) throws FileNotFoundException {
        Texture texture = storage.get(textureName);
        if (texture != null){
            return texture;
        }
        else {
            addTexture(textureName);
            return storage.get(textureName);
        }
    }

    public void addTexture(String textureName) throws FileNotFoundException {
        FileHandle file = Gdx.files.internal(textureName);
        if (file.exists()){
            storage.put(textureName, new Texture(file));
        }
        else {
            throw new FileNotFoundException();
        }
    }
}