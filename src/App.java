import java.util.Scanner;

public class App {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o tempo da primeira CHEGADA agendada: ");
        double firstArrive = scanner.nextDouble();

        System.out.print("Digite a quantidade de servidores da primeira fila: ");
        int servers = scanner.nextInt();

        System.out.print("Digite a capacidade da primeira fila: ");
        int capacity = scanner.nextInt();

        System.out.print("Digite a média de tempo de chegada de 1 cliente da primeira fila. Ex: 1,4: ");
        String arrival = scanner.next();
        int minArrival = Integer.parseInt(arrival.split(",")[0]);
        int maxArrival = Integer.parseInt(arrival.split(",")[1]);

        System.out.print("Digite a média de tempo de serviço de 1 cliente da primeira fila. Ex: 2,4: ");
        String service = scanner.next();
        int minService = Integer.parseInt(service.split(",")[0]);
        int maxService = Integer.parseInt(service.split(",")[1]);

        System.out.print("Digite a quantidade de servidores da segunda fila: ");
        int servers2 = scanner.nextInt();

        System.out.print("Digite a capacidade da segunda fila: ");
        int capacity2 = scanner.nextInt();

        System.out.print("Digite a média de tempo de serviço de 1 cliente da segunda fila. Ex: 2,4: ");
        String service2 = scanner.next();
        int minService2 = Integer.parseInt(service2.split(",")[0]);
        int maxService2 = Integer.parseInt(service2.split(",")[1]);

        System.out.print("Digite a quantidade de repetições que deseja: ");
        int repeticoes = scanner.nextInt();

        scanner.close();

        Fila fila = new Fila(servers, capacity, minArrival, maxArrival, minService, maxService);
        Fila fila2 = new Fila(servers2, capacity2, 0, 0, minService2, maxService2);

        // Primeira chegada na fila
        Evento event = new Evento("CHEGADA", firstArrive);

        Simulador simulador = new Simulador(73, fila, fila2);

        simulador.execucao(event, repeticoes);

        // Área de prints finais.

        System.out.println("\n------------------- Informações da fila -------------------");

        System.out.println("Fila: (G/G/" + fila.servidores + "/" + fila.capacidade + ")");
        System.out.println("Chegadas entre: " + fila.minArrival + " ... " + fila.maxArrival);
        System.out.println("Atendimento entre: " + fila.minService + " ... " + fila.maxService);

        System.out.println("------------------- Tabela de Porcentagem -------------------");

        for (int i = 0; i < fila.capacidade + 1; i++) {
            System.out.println(i + ": " +
                    String.format("%.2f", fila.times[i]) + " ("
                    + String.format("%.2f", (fila.times[i] / simulador.tempoTotal) * 100)
                    + "%)");
        }

        System.out.println("------------------- Tempo Total da Simulação -------------------");

        System.out.println("Tempo Total: " + String.format("%.2f", (simulador.tempoTotal)));

        System.out.println("------------------- Clientes Perdidos -------------------");

        System.out.println("Clientes Perdidos: " + fila.lostClient + "\n");

        System.out.println("=================================================================");

        System.out.println("\n------------------- Informações da segunda fila -------------------");

        System.out.println("Fila: (G/G/" + fila2.servidores + "/" + fila2.capacidade + ")");
        System.out.println("Atendimento entre: " + fila2.minService + " ... " + fila2.maxService);

        System.out.println("------------------- Tabela de Porcentagem -------------------");

        for (int i = 0; i < fila2.capacidade + 1; i++) {
            System.out.println(i + ": " +
                    String.format("%.2f", fila2.times[i]) + " ("
                    + String.format("%.2f", (fila2.times[i] / simulador.tempoTotal) * 100)
                    + "%)");
        }

        System.out.println("------------------- Tempo Total da Simulação -------------------");

        System.out.println("Tempo Total: " + String.format("%.2f", (simulador.tempoTotal)));

        System.out.println("------------------- Clientes Perdidos -------------------");

        System.out.println("Clientes Perdidos: " + fila2.lostClient + "\n");
    }
}
