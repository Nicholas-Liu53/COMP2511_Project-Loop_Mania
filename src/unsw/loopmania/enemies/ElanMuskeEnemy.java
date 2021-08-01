package unsw.loopmania.enemies;

import javafx.scene.media.AudioClip;
import unsw.loopmania.path.PathPosition;

/**
 * Elan Muske enemy type
 */
public class ElanMuskeEnemy extends Enemy {
    
    public ElanMuskeEnemy(PathPosition position) {
        super(position, 150, 2, 2, 40, 30);
        playBossSpawnSound();
        playElanSpawnSound();
        playElanSong();
    }
    
    private AudioClip bossSpawned;
    private AudioClip elanSpawned;
    private AudioClip elanSong;

    private void playBossSpawnSound() {
        bossSpawned = new AudioClip("file:src/sounds/bossspawnsound.wav");
        bossSpawned.play();
    }

    private void playElanSpawnSound() {
        elanSpawned = new AudioClip("file:src/sounds/elanspawnsound.wav");
        elanSpawned.play();
    }

    private void playElanSong() {
        elanSong = new AudioClip("file:src/sounds/elanalivesound.wav");
        elanSong.play();
    }

    public void stopElanSong() {
        elanSong.stop();
    }
}
