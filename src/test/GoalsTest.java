package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.goals.ComplexGoalComposite;
import unsw.loopmania.goals.CyclesBaseGoal;
import unsw.loopmania.goals.GoldBaseGoal;
import unsw.loopmania.goals.XpBaseGoal;
import unsw.loopmania.path.PathPosition;

public class GoalsTest {
    @Test
    public void cycleGoalsTest() {
        // Testing the basic cycle goal
        LoopManiaWorld testWorld = TestHelper.generateWorld();
        CyclesBaseGoal cycleGoal = new CyclesBaseGoal(2);

        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using gradle, different path is needed
            try {
                position = TestHelper.generatePathPosition("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                // Failed to generate PathPostion
                assertTrue(false);
            }
        }

        Character mainChar = new Character(position);
        testWorld.setCharacter(mainChar);

        boolean goalAchieved = cycleGoal.achieved(testWorld);
        assertEquals(false, goalAchieved);

        // Move character until goal is achieved
        for (int i = 0; i < 1000; i++) {
            testWorld.runTickMoves();

            if (cycleGoal.achieved(testWorld)) {
                goalAchieved = true;
                break;
            }
        }

        assertEquals(true, goalAchieved);
    }

    @Test
    public void goldGoalsTest() {
        // Testing the basic gold goal
        LoopManiaWorld testWorld = TestHelper.generateWorld();
        GoldBaseGoal goldGoal = new GoldBaseGoal(100);

        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using gradle, different path is needed
            try {
                position = TestHelper.generatePathPosition("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                // Failed to generate PathPostion
                assertTrue(false);
            }
        }

        Character mainChar = new Character(position);
        testWorld.setCharacter(mainChar);

        boolean goalAchieved = goldGoal.achieved(testWorld);
        assertEquals(false, goalAchieved);

        // Give gold and test
        testWorld.giveGold(50);
        goalAchieved = goldGoal.achieved(testWorld);
        assertEquals(false, goalAchieved);

        testWorld.giveGold(50);
        goalAchieved = goldGoal.achieved(testWorld);
        assertEquals(true, goalAchieved);

        testWorld.giveGold(50);
        goalAchieved = goldGoal.achieved(testWorld);
        assertEquals(true, goalAchieved);
    }

    @Test
    public void xpGoalsTest() {
        // Testing the basic xp goal
        LoopManiaWorld testWorld = TestHelper.generateWorld();
        XpBaseGoal xpGoal = new XpBaseGoal(100);

        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using gradle, different path is needed
            try {
                position = TestHelper.generatePathPosition("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                // Failed to generate PathPostion
                assertTrue(false);
            }
        }

        Character mainChar = new Character(position);
        testWorld.setCharacter(mainChar);

        boolean goalAchieved = xpGoal.achieved(testWorld);
        assertEquals(false, goalAchieved);

        // Give xp and test
        mainChar.giveExperiencePoints(50);
        goalAchieved = xpGoal.achieved(testWorld);
        assertEquals(false, goalAchieved);

        mainChar.giveExperiencePoints(50);
        goalAchieved = xpGoal.achieved(testWorld);
        assertEquals(true, goalAchieved);

        mainChar.giveExperiencePoints(50);
        goalAchieved = xpGoal.achieved(testWorld);
        assertEquals(true, goalAchieved);
    }

    @Test
    public void complexAndTest() {
        // Testing a complex goal using AND
        LoopManiaWorld testWorld = TestHelper.generateWorld();
        XpBaseGoal xpGoal = new XpBaseGoal(100);
        GoldBaseGoal goldGoal = new GoldBaseGoal(100);

        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using gradle, different path is needed
            try {
                position = TestHelper.generatePathPosition("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                // Failed to generate PathPostion
                assertTrue(false);
            }
        }

        Character mainChar = new Character(position);
        testWorld.setCharacter(mainChar);

        // Constructing complex goal
        ComplexGoalComposite complexGoal = new ComplexGoalComposite(true);
        complexGoal.add(xpGoal);
        complexGoal.add(goldGoal);

        assertEquals(false, complexGoal.achieved(testWorld));

        // Give xp and test
        mainChar.giveExperiencePoints(100);
        assertEquals(false, complexGoal.achieved(testWorld));

        // Give gold and test
        testWorld.giveGold(100);
        assertEquals(true, complexGoal.achieved(testWorld));

        // Remove goal and test
        complexGoal.remove(goldGoal);
        assertEquals(true, complexGoal.achieved(testWorld));
    }

    @Test
    public void complexOrTest() {
        // Testing a complex goal using OR
        LoopManiaWorld testWorld = TestHelper.generateWorld();
        XpBaseGoal xpGoal = new XpBaseGoal(100);
        GoldBaseGoal goldGoal = new GoldBaseGoal(100);

        PathPosition position = null;

        try {
            position = TestHelper.generatePathPosition("bin/test/Resources/world_with_twists_and_turns.json");
        } catch (FileNotFoundException e) {
            // Using gradle, different path is needed
            try {
                position = TestHelper.generatePathPosition("src/test/Resources/world_with_twists_and_turns.json");
            } catch (FileNotFoundException ee) {
                // Failed to generate PathPostion
                assertTrue(false);
            }
        }

        Character mainChar = new Character(position);
        testWorld.setCharacter(mainChar);

        // Constructing complex goal
        ComplexGoalComposite complexGoal = new ComplexGoalComposite(false);
        complexGoal.add(xpGoal);
        complexGoal.add(goldGoal);

        assertEquals(false, complexGoal.achieved(testWorld));

        // Give xp and test
        mainChar.giveExperiencePoints(100);
        assertEquals(true, complexGoal.achieved(testWorld));

        // Give gold and test
        testWorld.giveGold(100);
        assertEquals(true, complexGoal.achieved(testWorld));

        // Remove goal and test
        complexGoal.remove(goldGoal);
        assertEquals(true, complexGoal.achieved(testWorld));
    }
}
