package zh0611_MRPBLG;

import java.util.Random;

public class ZombieHorde extends Thread{
    private int zombieNumber = getRandomNumber(50,150);
    private Field position = null;

    public void setPosition(Field position) {
        this.position = position;
    }

    public ZombieHorde() {
        this.zombieNumber = getRandomNumber(50,150);
    }

    public int getZombieNumber() {
        return zombieNumber;
    }

    public void run(){
        while(true){
            /*
                Létrehozáskor a kolóniától legtávolabbi mezőre regisztráljuk fel a hordát.
                 Ehhez a Valley osztály register metódusát implementáljuk. A hordákat külön nem kell tárolni, a
                 regisztrációkor megadott sorszámú mező tárolja a zombi horda referenciáját.
             */

        }
    }
    public void func1 (){
        System.out.println("Thread name="+Thread.currentThread().getName());
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void steptoColony(){

    }
}

