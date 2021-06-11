package sixthExecutors.solution;

import concurrent.labs.Soldier;

import java.util.Objects;

public class Attacker extends Soldier {

    // Since attackers always hit first, they have a bit of disadvantage
    public Attacker(){
        this.maxAttackPoint = 17;
        this.minAttackPoint = 12;
    }
}
