public class Fila {
    public int servidores;
    public int capacidade;
    public int filaSize;
    public int lostClient;

    public double minArrival;
    public double maxArrival;
    public double minService;
    public double maxService;

    public Double[] times;

    public Fila(int servidores, int capacidade, double minArrival, double maxArrival, double minService,
            double maxService) {
        this.servidores = servidores;
        this.capacidade = capacidade;
        this.filaSize = 0;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minService = minService;
        this.maxService = maxService;
        this.times = new Double[capacidade + 1];
        this.lostClient = 0;

        this.initializationArray(times);
    }

    public void initializationArray(Double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] = 0.0;
        }
    }
}