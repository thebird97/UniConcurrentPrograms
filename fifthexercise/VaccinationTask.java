package fifthexercise;

import java.util.ArrayList;
import java.util.List;

enum PatientState {
    READY,
    GOING_HOME
}

public class VaccinationTask {
    static int MAX_WAIT_MSECS = 2000;
    static int vaccines = 0;
    static PatientState[] patientStates = {PatientState.READY};
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
        System.out.println("Remaining vaccines " + vaccines);

    }

    private static void doctorAction() {
        Patient patient = waitForPatientAndVaccinate();
        while(patient.st != PatientState.GOING_HOME){
            //vaccinates
            patient.notify();
        }
        System.out.println("Doc done");
    }

    private static Patient waitForPatientAndVaccinate() {
    Patient patient = null;
    boolean gotVaccine = false;

    while (!gotVaccine || patient == null){
        //Todo
    }
        return patient;
    }

    private static void patientAction() {
        Patient patient = new Patient(0);
        sleepSomeTime();
        waitForPatientToArrive();
        System.out.println("Patient at doc's");
        waitForShot(patient);
        System.out.println("patient done");

    }



    private static void pharmaAction() {
        sleepSomeTime();
        waitForShotToArrive();
        System.out.println("Pharma done");

    }

    private static void waitForShotToArrive() {
    }


    private static void sleepSomeTime() {
    }

    private static void waitForPatientToArrive() {
    }

    private static void waitForShot(Patient patient) {
    }



}
