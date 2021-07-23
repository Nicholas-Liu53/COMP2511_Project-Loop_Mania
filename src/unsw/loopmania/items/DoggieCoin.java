package unsw.loopmania.items;

import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.path.PathPosition;

/**
 * Class representing doggie coin item
 */
public class DoggieCoin extends Item {
    public DoggieCoin(Pair<Integer, Integer> position) {
        super(position);
    }

    public DoggieCoin(PathPosition position) {
        super(position);
    }

    public DoggieCoin() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public int getSellPrice() {
        return (50 + new Random().nextInt(950));
    }

    public int getPurchasePrice() {
        return 0;
    }
}
