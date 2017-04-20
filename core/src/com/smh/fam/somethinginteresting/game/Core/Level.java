package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.smh.fam.somethinginteresting.game.Game.BlackHole;
import com.smh.fam.somethinginteresting.game.Game.Obstacle;
import com.smh.fam.somethinginteresting.game.Game.Target;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Axel on 2017-03-25.
 */

public class Level {
    private final World world;
    private final TextureStorage textureStorage;

    private Vector2 playerPosition;
    private Vector2 gravityVector;
    private Array<Obstacle> obstacles;
    private Array<Target> targets;
    private Array<BlackHole> blackHoles;

    public Level(World world, TextureStorage textureStorage){
        this.world = world;
        this.textureStorage = textureStorage;

        obstacles = new Array<Obstacle>();
        targets = new Array<Target>();
        blackHoles = new Array<BlackHole>();
        playerPosition = new Vector2(0f, 0f);
        gravityVector = new Vector2(0f, 0f);
    }

    public void readFromXML(String fileName){
        try {
            FileHandle file = Gdx.files.internal(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(file.readString())));
            doc.getDocumentElement().normalize();

            // Get player position
            {
                NodeList nList = doc.getElementsByTagName("player");
                if (nList.getLength() != 0){
                    Element playerNode = (Element) nList.item(0);
                    NodeList position = playerNode.getElementsByTagName("pos");
                    playerPosition.x = Float.parseFloat(position.item(0).getTextContent());
                    playerPosition.y = Float.parseFloat(position.item(1).getTextContent());
                }

            }

            // Get world properties
            {
                NodeList nList = doc.getElementsByTagName("world");
                if (nList.getLength() != 0){
                    Element playerNode = (Element) nList.item(0);
                    NodeList position = playerNode.getElementsByTagName("GravityVec");
                    gravityVector.x = Float.parseFloat(position.item(0).getTextContent());
                    gravityVector.y = Float.parseFloat(position.item(1).getTextContent());
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

                    // Get type if exists
                    Obstacle.Type type = Obstacle.Type.REGULAR;
                    if (obsNode.getElementsByTagName("type").getLength() != 0) {
                        String typeStr = obsNode.getElementsByTagName("type").item(0).getTextContent();
                        if      (typeStr.equals("REGULAR")) type = Obstacle.Type.REGULAR;
                        else if (typeStr.equals("BOUNCE"))  type = Obstacle.Type.BOUNCE;
                        else if (typeStr.equals("BREAK"))  type = Obstacle.Type.BREAK;

                    }
                    obstacles.add(new Obstacle(world, pos1, pos2, angle, type));

                    // Get color if exists
                    if (obsNode.getElementsByTagName("color").getLength() != 0) {
                        String HexColor = obsNode.getElementsByTagName("color").item(0).getTextContent().replace("#","");
                        Color color = new Color( Integer.parseInt(HexColor, 16) );
                        obstacles.get(obstacles.size-1).setColor(color);
                    }
                }
            }

            // Generate targets
            {
                NodeList nList = doc.getElementsByTagName("target");
                for (int i = 0; i < nList.getLength(); i++){
                    Element obsNode = (Element) nList.item(i);

                    // Get positions
                    NodeList position = obsNode.getElementsByTagName("pos");
                    Vector2 pos = new Vector2();
                    pos.x = Float.parseFloat(position.item(0).getTextContent());
                    pos.y = Float.parseFloat(position.item(1).getTextContent());

                    targets.add(new Target(world, textureStorage, pos));
                }
            }

            // Generate black holes
            {
                NodeList nList = doc.getElementsByTagName("blackhole");
                for (int i = 0; i < nList.getLength(); i++){
                    Element obsNode = (Element) nList.item(i);

                    // Get positions
                    NodeList position = obsNode.getElementsByTagName("pos");
                    Vector2 pos = new Vector2();
                    pos.x = Float.parseFloat(position.item(0).getTextContent());
                    pos.y = Float.parseFloat(position.item(1).getTextContent());

                    // Get radius if exists
                    float radius = 64f;
                    if (obsNode.getElementsByTagName("radius").getLength() != 0) {
                        radius = Float.parseFloat(obsNode.getElementsByTagName("radius").item(0).getTextContent());
                    }

                    blackHoles.add(new BlackHole(world, textureStorage, pos, radius));
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
    public Vector2 getGravityVector()  { return gravityVector; }
    public Array<Obstacle> getObstacles(){
        return obstacles;
    }
    public Array<Target> getTargets() { return targets; }
    public Array<BlackHole> getBlackHoles() { return  blackHoles; }
}
