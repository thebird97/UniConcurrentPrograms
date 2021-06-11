//kezdheted (Norbi)
package zh0611_MRPBLG;

import javax.sound.midi.Soundbank;

public class Main {
    public static void main(String[] args) {
       Valley valley = new Valley();
       ZombieHorde zombieHorde = new ZombieHorde();
       zombieHorde.start();
       System.out.println(zombieHorde.getZombieNumber());
       valley.register(zombieHorde);

    }
}
