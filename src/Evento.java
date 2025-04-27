public class Evento {
    private String type;
    private double time;
    private Fila fila1;
    private Fila fila2;

    public Evento(String type, double time, Fila fila1, Fila fila2) {
        this.type = type;
        this.time = time;
        this.fila1 = fila1;
        this.fila2 = fila2;
    }

    public String getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    public Fila getFila1() {
        return fila1;
    }

    public Fila getFila2() {
        return fila2;
    }
}
