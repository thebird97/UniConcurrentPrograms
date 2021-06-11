package fifthexercise.solution;

public class Patient {

    public int idx;
    public PatientState st;

    public Patient(int idx) {
        this.idx = idx;
        this.st = PatientState.READY;
    }

}
