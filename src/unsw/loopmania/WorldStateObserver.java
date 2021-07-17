package unsw.loopmania;

/**
 * Interface for observers of the loop mania world state
 * Notified everytime a cycle is completed
 */
public interface WorldStateObserver {
    /**
     * Notification sent to observers every time the character completes a cycle
     * 
     * @param worldState
     */
    public void notifyCycle(LoopManiaWorld worldState);

    /**
     * Notification sent to observers every time a tick is completed
     * mainly used by buildings that track character position
     * 
     * @param mainChar
     */
    public void notifyTick(Character mainChar, LoopManiaWorld world);
}
