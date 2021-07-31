package unsw.loopmania.buildings;

import org.javatuples.Pair;

import javafx.scene.media.AudioClip;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.character.Character;

/**
 * represents a village building in the backend game world
 */
public class VillageBuilding extends Building {
    public VillageBuilding(Pair<Integer, Integer> position) {
        super(position, -1);
    }

    @Override
    public void notifyTick(Character mainChar, LoopManiaWorld worldState) {
        restoreHealthIfInVillage(worldState, mainChar);
    }

    private void restoreHealthIfInVillage(LoopManiaWorld world, Character mainChar) {
        if (getX() == (mainChar.getX()) && getY() == (mainChar.getY())) {
            AudioClip restoredHP = new AudioClip("file:src/sounds/village.wav");
            restoredHP.play();
            mainChar.restoreHealthPoints();
        }
    }
}
