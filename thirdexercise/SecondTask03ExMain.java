package thirdexercise;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/*
 * I have files f1.txt -> f10.txt
 * Each file has it's own thread that reads the file and writes to console
 * use Thread.sleep with random number between 100 and 200
 *
 */
public class SecondTask03ExMain {
    private static final String[] FILE_NAMES = {"thirdexercise/f1.txt", "thirdexercise/f2.txt","thirdexercise/f3.txt","thirdexercise/f4.txt", "thirdexercise/f5.txt",
            "thirdexercise/f6.txt",
            "thirdexercise/f7.txt","thirdexercise/f8.txt","thirdexercise/f9.txt","thirdexercise/f10.txt"};
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Arrays.stream(FILE_NAMES).forEach(name ->{
            new Thread(()-> readAndPrintFiles(name)).start();
        });
    }

    private static void printLinesAndWait(String line){
        System.out.println(line);
        try {
            Thread.sleep(random.nextInt(100)+100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private static void readAndPrintFiles(String name){
        try (BufferedReader br = new BufferedReader(new FileReader(name))){
            br.lines().forEach(SecondTask03ExMain::printLinesAndWait);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
