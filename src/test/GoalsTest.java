package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.character.Character;
import unsw.loopmania.goals.BossBaseGoal;
import unsw.loopmania.goals.ComplexGoalComponent;
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
        testWorld.setGoals(cycleGoal);

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

        assertEquals("2 Cycles", cycleGoal.getGoalString());
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

        assertEquals("100 Gold", goldGoal.getGoalString());
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

        assertEquals("100 XP", xpGoal.getGoalString());
    }

    @Test
    public void bossGoalsTest() {
        // Testing the basic xp goal
        LoopManiaWorld testWorld = TestHelper.generateWorld();
        BossBaseGoal bossGoal = new BossBaseGoal();

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
        testWorld.setGoals(bossGoal);

        boolean goalAchieved = bossGoal.achieved(testWorld);
        assertEquals(false, goalAchieved);

        // Move character up to post 40 cycles and give xp
        for (int i = 0; i < 5000; i++) {
            testWorld.runTickMoves();
        }

        mainChar.giveExperiencePoints(11000);

        assertEquals(true, bossGoal.achieved(testWorld));

        assertEquals("Defeat Bosses", bossGoal.getGoalString());
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
        ComplexGoalComposite complexGoal = new ComplexGoalComposite("AND");
        complexGoal.add(xpGoal);
        complexGoal.add(goldGoal);

        assertEquals(false, complexGoal.achieved(testWorld));

        // Give xp and test
        mainChar.giveExperiencePoints(100);
        assertEquals(false, complexGoal.achieved(testWorld));

        // Give gold and test
        testWorld.giveGold(100);
        assertEquals(true, complexGoal.achieved(testWorld));

        assertEquals("( 100 XP AND 100 Gold )", complexGoal.getGoalString());

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
        ComplexGoalComposite complexGoal = new ComplexGoalComposite("OR");
        complexGoal.add(xpGoal);
        complexGoal.add(goldGoal);

        assertEquals(false, complexGoal.achieved(testWorld));

        // Give xp and test
        mainChar.giveExperiencePoints(100);
        assertEquals(true, complexGoal.achieved(testWorld));

        // Give gold and test
        testWorld.giveGold(100);
        assertEquals(true, complexGoal.achieved(testWorld));

        assertEquals("( 100 XP OR 100 Gold )", complexGoal.getGoalString());

        // Remove goal and test
        complexGoal.remove(goldGoal);
        assertEquals(true, complexGoal.achieved(testWorld));
    }

    @Test
    public void parserTestOR() {
        // Testing that goals can be successfully parsed from JSON
        
        JSONObject json = null;
        
        // Getting JSON
        try {
            json = new JSONObject(new JSONTokener(new FileReader("bin/test/Resources/complex_goal3.json")));
        } catch (FileNotFoundException e) {
            try {
                json = new JSONObject(new JSONTokener(new FileReader("src/test/Resources/complex_goal3.json")));
            } catch (FileNotFoundException ee) {
                assertEquals(true, false);
            }
        }

        // Creating goal
        ComplexGoalComponent complexGoal = TestHelper.loadGoals(json.getJSONObject("goal-condition"));

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

        //Constructing world
        LoopManiaWorld testWorld = TestHelper.generateWorld();
        Character mainChar = new Character(position);
        testWorld.setCharacter(mainChar);
        testWorld.setGoals(complexGoal);

        assertEquals(false, complexGoal.achieved(testWorld));

        // Give xp and test
        mainChar.giveExperiencePoints(100);
        assertEquals(false, complexGoal.achieved(testWorld));

        // Give gold and test
        testWorld.giveGold(100);
        assertEquals(true, complexGoal.achieved(testWorld));

        mainChar.giveExperiencePoints(1234567);
        assertEquals(true, complexGoal.achieved(testWorld));

        assertEquals("( 100 Gold OR 123456 XP )", complexGoal.getGoalString());
    }

    @Test
    public void parserTestAND() {
        // Testing that goals can be successfully parsed from JSON
        
        JSONObject json = null;
        
        // Getting JSON
        try {
            json = new JSONObject(new JSONTokener(new FileReader("bin/test/Resources/complex_goal0.json")));
        } catch (FileNotFoundException e) {
            try {
                json = new JSONObject(new JSONTokener(new FileReader("src/test/Resources/complex_goal0.json")));
            } catch (FileNotFoundException ee) {
                assertEquals(true, false);
            }
        }

        // Creating goal
        ComplexGoalComponent complexGoal = TestHelper.loadGoals(json.getJSONObject("goal-condition"));

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

        //Constructing world
        LoopManiaWorld testWorld = TestHelper.generateWorld();
        Character mainChar = new Character(position);
        testWorld.setCharacter(mainChar);
        testWorld.setGoals(complexGoal);

        assertEquals(false, complexGoal.achieved(testWorld));

        // Give xp and test
        mainChar.giveExperiencePoints(1234567);
        assertEquals(false, complexGoal.achieved(testWorld));

        // Give gold and test
        testWorld.giveGold(900001);
        assertEquals(false, complexGoal.achieved(testWorld));

        // Do cycles and test
        for (int i = 0; i < 1000; i++) {
            testWorld.runTickMoves();
        }
        assertEquals(true, complexGoal.achieved(testWorld));

        assertEquals("( 2 Cycles AND ( 123456 XP OR 900000 Gold ) )", complexGoal.getGoalString());
    }
}
