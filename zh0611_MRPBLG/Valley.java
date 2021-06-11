package zh0611_MRPBLG;

import java.util.*;

public class Valley extends Thread{
  private   List<Field>  fieldList = new ArrayList<Field>();

  public static void register(){

  }

  public void run(){
    while(true){


    }
  }
  public void func1 (){
    System.out.println("Thread name="+Thread.currentThread().getName());
  }

  public void register(ZombieHorde zombieHorde) {
    zombieHorde.setPosition(Field.SECTION_10);
  }
}
