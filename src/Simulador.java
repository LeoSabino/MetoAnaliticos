import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Simulador {

    public int lostClient;
    public double tempoTotal;
    public long lastNumber;
    public List<Fila> filas;
    public List<Evento> escalonador;
    public int quantidadeNumerosUsados;

    public Simulador(int semente, List<Fila> filas) {
        this.lastNumber = semente;
        this.tempoTotal = 0;
        this.lostClient = 0;
        this.filas = filas;
        this.escalonador = new ArrayList<>();
        this.quantidadeNumerosUsados = 0;
    }

    public void escalona(Evento evento) {
        if (evento.getType() == "CHEGADA") {
            arrival(evento.getFila1());
        } else if (evento.getType() == "SAIDA") {
            exit(evento.getFila1());
        } else if (evento.getType() == "PASSAGEM") {
            transfer(evento.getFila1(), evento.getFila2());
        }
    }

    public void adicionaEscalonador(double min, double max, String tipo, Fila fila1, Fila fila2) {
        double randomChegada = (max - min) * nextRandom() + min;
        double fullTimeEvent = tempoTotal + randomChegada;

        Evento evento = new Evento(tipo, fullTimeEvent, fila1, fila2);

        escalonador.add(evento);

        quantidadeNumerosUsados++;
    }

    public void execucao(Evento primeiroEvento, int contagem) {
        escalonador.add(primeiroEvento);

        while (quantidadeNumerosUsados < contagem) {
            escalonador.sort(Comparator.comparingDouble(Evento::getTime));

            Evento proximoEvento = escalonador.remove(0);

            for (Fila fila : filas) {
                fila.times[fila.filaSize] = calculaTempo(fila, proximoEvento);
            }

            this.tempoTotal = proximoEvento.getTime();

            this.escalona(proximoEvento);
        }
    }

    public double calculaTempo(Fila fila, Evento evento) {
        double tempo = fila.times[fila.filaSize] = fila.times[fila.filaSize]
                + (evento.getTime() - this.tempoTotal);

        return tempo;
    }

    public void addDestinationQueue(String identifier, Fila fila) {
        if (identifier.equals("exit")) {
            this.adicionaEscalonador(fila.minService, fila.maxService, "SAIDA", fila, null);
        } else {
            Fila filaDestino = null;

            for (Fila filaNomes : filas) {
                if (filaNomes.filaIdentifier.equals(identifier)) {
                    filaDestino = filaNomes;
                }
            }

            this.adicionaEscalonador(fila.minService, fila.maxService, "PASSAGEM", fila, filaDestino);
        }
    }

    public void arrival(Fila fila) {
        if (fila.filaSize < fila.capacidade) {
            fila.filaSize++;

            if (fila.filaSize <= fila.servidores) {
                String identifier = filaDestino(fila);
                this.addDestinationQueue(identifier, fila);
            }
        } else {
            fila.lostClient++;
        }

        this.adicionaEscalonador(fila.minArrival, fila.maxArrival, "CHEGADA", fila, null);
    }

    public void exit(Fila fila) {
        fila.filaSize--;

        if (fila.filaSize >= fila.servidores) {
            String identifier = filaDestino(fila);
            this.addDestinationQueue(identifier, fila);       
        }
    }

    public void transfer(Fila fila1, Fila fila2) {
        fila1.filaSize--;

        if (fila1.filaSize >= fila1.servidores) {
            String identifier = filaDestino(fila1);
            this.addDestinationQueue(identifier, fila1);
        }

        if (fila2.filaSize < fila2.capacidade) {
            fila2.filaSize++;

            if (fila2.filaSize <= fila2.servidores) {
                String identifier = filaDestino(fila2);
                this.addDestinationQueue(identifier, fila2);
            }
        } else {
            fila2.lostClient++;
        }
    }

    public String filaDestino(Fila fila) {
        double sum = 0.0;
        double prob = nextRandom();
        String filaDestino = "";

        for (String network : fila.network) {
            String[] networkSplit = network.split("-");

            if (prob < sum) {
                break;
            } else {
                sum += Double.parseDouble(networkSplit[1]);
                filaDestino = networkSplit[0];
            }
        }

        quantidadeNumerosUsados++;

        return filaDestino;
    }

    public double nextRandom() {
        int a = 1664525;
        int c = 1013904223;
        long M = 4294967296L;

        long random = (a * lastNumber + c) % M;
        lastNumber = random;
        double normalized = (double) random / M;
        return normalized;
    }
}
