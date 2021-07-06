package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.SlugEnemy;

/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to
 * achieve high coverage. A clickable "Run Test" link should appear if you have
 * installed the Java Extension Pack properly.
 */
public class CharacterTest {
    @Test
    public void constructTest() {
        // Testing construction + basic getters

        // TODO, figure out how to make Character init work
        Character character = new Character();

        // Ensure attributes are correct
        assertEquals(character.getHealth(), 100);
    }

    @Test
    public void takeDamageTest() {
        Character character = new Character();

    }
}