package unsw.loopmania.goals;

import java.util.ArrayList;

import unsw.loopmania.LoopManiaWorld;

public class ComplexGoalComposite implements ComplexGoalComponent {
    
    private boolean operation;
    private ArrayList<ComplexGoalComponent> children;

    /**
     * Takes two complex goal compenents along with a comparator for them
     * 
     * @param component1
     * @param component2
     * @param operation true for AND, false for OR
     */
    public ComplexGoalComposite(boolean operation) {
        this.children = new ArrayList<>();
        this.operation = operation;
    }

    /**
     * Adds a component to the compex goal
     * 
     * @param component
     */
    public void add(ComplexGoalComponent component) {
        this.children.add(component);
    }

    /**
     * Removes a component from the complex goal
     * 
     * @param component
     */
    public void remove(ComplexGoalComponent component) {
        this.children.remove(component);
    }

    /**
     * @return list of children goals
     */
    public ArrayList<ComplexGoalComponent> getChildren() {
        return this.children;
    }

    /**
     * @return true is goals have been met, else false
     */
    @Override
    public boolean achieved(LoopManiaWorld world) {
        boolean achieved = false;
        
        if (this.operation) {
            // AND, both component goals must be achieved
            achieved = true;

            for (ComplexGoalComponent child : children) {
                if (!child.achieved(world)) {
                    achieved = false;
                    break;
                } 
            }
        } else {
            // OR, only one component goal must be achieved
            for (ComplexGoalComponent child : children) {
                if (child.achieved(world)) {
                    achieved = true;
                    break;
                }
            }
        }
        
        return achieved;
    }
}
