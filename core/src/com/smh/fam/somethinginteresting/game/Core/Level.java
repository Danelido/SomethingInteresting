package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.smh.fam.somethinginteresting.game.Game.Obstacle;
import com.smh.fam.somethinginteresting.game.Game.Player;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Axel on 2017-03-25.
 */

public class Level {
    private final World world;
    private Vector2 playerPosition;
    private Array<Obstacle> obstacles;

    public Level(World world){
        this.world = world;

        obstacles = new Array<Obstacle>();
        playerPosition = new Vector2(0f, 0f);
    }

    public void readFromXML(String fileName){
        try {
            FileHandle file = Gdx.files.internal(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file.file());
            doc.getDocumentElement().normalize();

            // Get player-position
            {
                NodeList nList = doc.getElementsByTagName("player");
                if (nList.getLength() != 0){
                    Element playerNode = (Element) nList.item(0);
                    NodeList position = playerNode.getElementsByTagName("pos");
                    playerPosition.x = Float.parseFloat(position.item(0).getTextContent());
                    playerPosition.y = Float.parseFloat(position.item(1).getTextContent());
                }

            }

            // Generate obstacles
            {
                NodeList nList = doc.getElementsByTagName("obs");
                for (int i = 0; i < nList.getLength(); i++){
                    Element obsNode = (Element) nList.item(i);

                    // Get positions
                    NodeList position1 = obsNode.getElementsByTagName("pos1");
                    Vector2 pos1 = new Vector2();
                    pos1.x = Float.parseFloat(position1.item(0).getTextContent());
                    pos1.y = Float.parseFloat(position1.item(1).getTextContent());

                    NodeList position2 = obsNode.getElementsByTagName("pos2");
                    Vector2 pos2 = new Vector2();
                    pos2.x = Float.parseFloat(position2.item(0).getTextContent());
                    pos2.y = Float.parseFloat(position2.item(1).getTextContent());

                    // Get angle if exists
                    float angle = 0f;
                    if (obsNode.getElementsByTagName("angle").getLength() != 0) {
                        angle = Float.parseFloat(obsNode.getElementsByTagName("angle").item(0).getTextContent());
                    }

                    obstacles.add(new Obstacle(world, pos1, pos2, angle));
                }
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector2 getPlayerPosition(){
        return playerPosition;
    }
    public Array<Obstacle> getObstacles(){
        return obstacles;
    }
}
