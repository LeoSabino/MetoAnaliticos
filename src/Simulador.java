import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Simulador {

    public int lostClient;
    public double tempoTotal;
    public int lastNumber;
    public Fila fila1;
    public Fila fila2;
    public List<Evento> escalonador;
    public int quantidadeNumerosUsados;

    public Simulador(int semente, Fila fila1, Fila fila2) {
        this.lastNumber = semente;
        this.tempoTotal = 0;
        this.fila1 = fila1;
        this.fila2 = fila2;
        this.lostClient = 0;
        this.escalonador = new ArrayList<>();
        this.quantidadeNumerosUsados = 0;
    }

    public void escalona(Evento evento) {
        if (evento.getType() == "CHEGADA") {
            arrival();
        } else if (evento.getType() == "SAIDA") {
            exit();
        } else if (evento.getType() == "PASSAGEM") {
            transfer();
        }
    }

    public void adicionaEscalonador(double min, double max, String tipo) {
        double randomChegada = (max - min) * nextRandom() + min;
        double fullTimeEvent = tempoTotal + randomChegada;

        Evento evento = new Evento(tipo, fullTimeEvent);

        escalonador.add(evento);

        quantidadeNumerosUsados++;
    }

    public void execucao(Evento primeiroEvento, int contagem) {
        escalonador.add(primeiroEvento);

        while (quantidadeNumerosUsados < contagem) {
            escalonador.sort(Comparator.comparingDouble(Evento::getTime));

            Evento proximoEvento = escalonador.remove(0);

            this.fila1.times[fila1.filaSize] = calculaTempo(fila1, proximoEvento);

            this.fila2.times[fila2.filaSize] = calculaTempo(fila2, proximoEvento);

            this.tempoTotal = proximoEvento.getTime();

            this.escalona(proximoEvento);
        }
    }

    public double calculaTempo(Fila fila, Evento evento) {
        double tempo = fila.times[fila.filaSize] = fila.times[fila.filaSize]
                + (evento.getTime() - this.tempoTotal);

        return tempo;
    }

    public void arrival() {
        if (this.fila1.filaSize < this.fila1.capacidade) {
            this.fila1.filaSize++;

            if (this.fila1.filaSize <= this.fila1.servidores) {
                this.adicionaEscalonador(this.fila1.minService, this.fila1.maxService, "PASSAGEM");
            }
        } else {
            this.fila1.lostClient++;
        }

        this.adicionaEscalonador(this.fila1.minArrival, this.fila1.maxArrival, "CHEGADA");
    }

    public void exit() {
        fila2.filaSize--;

        if (fila2.filaSize >= fila2.servidores) {
            this.adicionaEscalonador(this.fila2.minService, this.fila2.maxService, "SAIDA");
        }
    }

    public void transfer() {
        this.fila1.filaSize--;

        if (this.fila1.filaSize >= this.fila1.servidores) {
            this.adicionaEscalonador(this.fila1.minService, this.fila1.maxService, "PASSAGEM");
        }

        if (this.fila2.filaSize < this.fila2.capacidade) {
            fila2.filaSize++;

            if (fila2.filaSize <= fila2.servidores) {
                this.adicionaEscalonador(this.fila2.minService, this.fila2.maxService, "SAIDA");
            }
        } else {
            fila2.lostClient++;
        }
    }

    public double nextRandom() {
        int a = 123;
        int c = 6;
        int M = 124212;

        int random = (a * lastNumber + c) % M;
        lastNumber = random;
        double normalized = (double) random / M;
        return normalized;
    }
}
