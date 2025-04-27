import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        List<Fila> filasList = new ArrayList<>();

        // System.out.print("Digite o tempo da primeira CHEGADA agendada: ");
        // double firstArrive = scanner.nextDouble();

        // System.out.print("Qual é a quantidade de filas que deseja inserir? ");
        // int quantidadeFilas = scanner.nextInt();

        // System.out.print("Digite o nome da fila: ");
        // String name = scanner.next();

        // System.out.print("Digite a quantidade de servidores da " + name + " fila: ");
        // int servers = scanner.nextInt();

        // System.out.print("Digite a média de tempo de chegada de 1 cliente da " + name
        // + " fila. Ex: 1,4: ");
        // String arrival = scanner.next();
        // int minArrival = Integer.parseInt(arrival.split(",")[0]);
        // int maxArrival = Integer.parseInt(arrival.split(",")[1]);

        // System.out.print("Digite a média de tempo de serviço de 1 cliente da " + name
        // + " fila. Ex: 2,4: ");
        // String service = scanner.next();
        // int minService = Integer.parseInt(service.split(",")[0]);
        // int maxService = Integer.parseInt(service.split(",")[1]);

        // System.out.print("Quantos networks contém essa fila? ");
        // int networkCount = scanner.nextInt();

        // List<String> networkList = new ArrayList<>();

        // for (int j = 0; j < networkCount; j++) {
        // System.out.println("Digite o nome da fila e sua probabilidade, separado por
        // um traço (nomeFila-0.8)");
        // String network = scanner.next();

        // networkList.add(network);
        // }

        // Fila fila3 = new Fila(name, servers, 99999, minArrival, maxArrival,
        // minService, maxService, networkList);
        // filasList.add(fila3);

        // for (int i = 0; i < quantidadeFilas - 1; i++) {
        // System.out.print("Digite o nome da fila: ");
        // String nameFila = scanner.next();

        // System.out.print("Digite a quantidade de servidores da " + nameFila + " fila:
        // ");
        // int serversFila = scanner.nextInt();

        // System.out.print("Digite a capacidade da " + nameFila + " fila: ");
        // int capacity = scanner.nextInt();

        // System.out.print("Digite a média de tempo de serviço de 1 cliente da " +
        // nameFila + " fila. Ex: 2,4: ");
        // String serviceFila = scanner.next();
        // int minServiceFila = Integer.parseInt(serviceFila.split(",")[0]);
        // int maxServiceFila = Integer.parseInt(serviceFila.split(",")[1]);

        // System.out.print("Quantos networks contém essa fila? ");
        // int networkCountFila = scanner.nextInt();

        // List<String> networkListFila = new ArrayList<>();

        // for (int j = 0; j < networkCountFila; j++) {
        // System.out.println("Digite o nome da fila e sua probabilidade, separado por
        // um traço (nomeFila-0.8)");
        // String network = scanner.next();

        // networkListFila.add(network);
        // }

        // Fila fila2 = new Fila(nameFila, serversFila, capacity, minArrival,
        // maxArrival, minServiceFila,
        // maxServiceFila, networkListFila);
        // filasList.add(fila2);
        // }

        // System.out.print("Digite a quantidade de repetições que deseja: ");
        // int repeticoes = scanner.nextInt();

        int firstArrive = 2;
        int repeticoes = 100000;
        List<String> network1 = new ArrayList<>();
        network1.add("q2-0.8");
        network1.add("q3-0.2");
        List<String> network2 = new ArrayList<>();
        network2.add("q1-0.3");
        network2.add("q2-0.5");
        network2.add("exit-0.2");
        List<String> network3 = new ArrayList<>();
        network3.add("q3-0.7");
        network3.add("exit-0.3");

        filasList.add(new Fila("q1", 1, 5, 2, 4, 1, 2, network1));
        filasList.add(new Fila("q2", 2, 5, 0, 0, 4, 8, network2));
        filasList.add(new Fila("q3", 2, 10, 0, 0, 5, 15, network3));

        scanner.close();

        // Primeira chegada na fila
        Evento event = new Evento("CHEGADA", firstArrive, filasList.get(0), null);

        Simulador simulador = new Simulador(73, filasList);

        simulador.execucao(event, repeticoes);

        // Área de prints finais.

        for (Fila fila : filasList) {
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

        }
    }
}
