package unsw.loopmania.enemies;

import unsw.loopmania.path.PathPosition;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.media.AudioClip;
import unsw.loopmania.character.Character;

public class DoggieEnemy extends Enemy {
    /**
     * Doggie enemy simply uses basic enemy behaviour
     */
    public DoggieEnemy(PathPosition position) {
        super(position, 110, 2, 2, 25, 20);
        playBossSpawnSound();
        playDoggieSong();
    }

    private AudioClip bossSpawned;
    private AudioClip doggieSong;

    /**
     * Uses parent class attack functionality while adding special 'stun attack'
     * @param mainChair = Main Character
     */
    @Override
    public boolean launchAttack(Character mainChar) {
        // Perform basic attack first
        super.launchAttack(mainChar);
        if (new Random().nextInt(100) <= 30) {
            mainChar.receiveStunAttack();
        }
        return false;
    }

    private void playBossSpawnSound() {
        bossSpawned = new AudioClip("file:src/sounds/bossspawnsound.wav");
        bossSpawned.play();
    }

    private void playDoggieSong() {
        doggieSong = new AudioClip("file:src/sounds/doggiealive.wav");
        doggieSong.play();
    }

    public void stopDoggieSong() {
        doggieSong.stop();
    }
}
