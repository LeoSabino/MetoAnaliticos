public class Evento {
    private String type;
    private double time;

    public Evento(String type, double time) {
        this.type = type;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public double getTime() {
        return time;
    }
}
