package fifthexercise.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class VaccinationTask {

    static int MAX_WAIT_MSEC = 2000;
    static int vaccines = 0;
    static PatientState[] patientState = { PatientState.READY };
    static List<Patient> patientThreads = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        var doc = new Thread(VaccinationTask::doctorAction);
        var patient = new Thread(VaccinationTask::patientAction);
        var pharma = new Thread(VaccinationTask::pharmaAction);

        for (Thread thread : List.of(pharma, doc, patient)) {
            thread.start();
        }

        for (Thread thread : List.of(pharma, doc, patient)) {
            thread.join();
        }

        System.out.println("Remaining vaccines: " + vaccines);
    }

    private static void doctorAction(){
        Patient patient = waitForPatientAndVaccine();

        while (patient.st != PatientState.GOING_HOME) {
            synchronized (patient) {
                patient.notify(); // give the shot
            }
        }

        System.out.println("Doc done");
    }

    private static Patient waitForPatientAndVaccine(){
        Patient patient = null;
        boolean gotVaccine = false;

        while (!gotVaccine || patient == null) {
            synchronized (VaccinationTask.class){
                waitOn(VaccinationTask.class);
            }
        }
        return patient;
    }

    private static void patientAction(){
        Patient patient = new Patient(0);

        sleepSomeTime();
        waitForPatientToArrive(patient);
        System.out.println("Patient at doc's");
        waitForShot(patient);
        System.out.println("Patient done");
    }

    private static void waitForPatientToArrive(Patient patient){
        //TODO
    }

    private static void waitForShot(Patient patient){
        //TODO
    }

    private static void pharmaAction(){
        sleepSomeTime();
        waitForShotToArrive();
        System.out.println("Pharma done");
    }

    private static void waitForShotToArrive(){
        synchronized (VaccinationTask.class) {
            ++vaccines;
            VaccinationTask.class.notify();
        }
    }

    private static void waitOn(Object lock) {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleepSomeTime() {
        int msec = ThreadLocalRandom.current().nextInt(MAX_WAIT_MSEC);
        sleepForMsec(msec);
    }

    private static void sleepForMsec(int msec) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
