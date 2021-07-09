package unsw.loopmania;

/**
 * Interface for observers of the loop mania world state
 * Notified everytime a cycle is completed
 */
public interface WorldStateObserver {
    public void notify(LoopManiaWorld worldState);
}
