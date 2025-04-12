import java.util.*;
import java.util.stream.Collectors;

public class NetworkSimulation {

    static class CellTower {
        private String towerId;
        private double x;
        private double y;
        private double coverageRadius;
        private Set<Client> connectedClients;

        public CellTower(String towerId, double x, double y, double coverageRadius) {
            this.towerId = towerId;
            this.x = x;
            this.y = y;
            this.coverageRadius = coverageRadius;
            this.connectedClients = new HashSet<>();
        }

        public String getTowerId() { return towerId; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getCoverageRadius() { return coverageRadius; }
        public Set<Client> getConnectedClients() { return connectedClients; }
        public int getClientCount() { return connectedClients.size(); }

        public void addClient(Client client) {
            connectedClients.add(client);
        }

        public void removeClient(Client client) {
            connectedClients.remove(client);
        }
    }

    static class Operator {
        private String name;
        private Set<Client> clients;
        private Set<CellTower> towers;

        public Operator(String name) {
            this.name = name;
            this.clients = new HashSet<>();
            this.towers = new HashSet<>();
        }

        public String getName() { return name; }
        public Set<Client> getClients() { return clients; }
        public Set<CellTower> getTowers() { return towers; }

        public void addClient(Client client) {
            clients.add(client);
        }

        public void removeClient(Client client) {
            clients.remove(client);
        }

        public int getSubscriberCount() {
            return clients.size();
        }

        public void addTower(CellTower tower) {
            towers.add(tower);
        }

        public void removeTower(CellTower tower) {
            towers.remove(tower);
        }
    }

    static class Client {
        private String phoneNumber;
        private double x;
        private double y;
        private Operator operator;
        private CellTower currentTower;

        public Client(String phoneNumber, Operator operator, double x, double y) {
            this.phoneNumber = phoneNumber;
            this.operator = operator;
            this.x = x;
            this.y = y;
            this.currentTower = null;
        }

        public String getPhoneNumber() { return phoneNumber; }
        public double getX() { return x; }
        public double getY() { return y; }
        public Operator getOperator() { return operator; }
        public CellTower getCurrentTower() { return currentTower; }

        public void setLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void setOperator(Operator operator) {
            this.operator = operator;
        }

        public void setCurrentTower(CellTower tower) {
            if (this.currentTower != null) {
                this.currentTower.removeClient(this);
            }
            this.currentTower = tower;
            if (tower != null) {
                tower.addClient(this);
            }
        }
    }

    static class Network {
        private Map<String, CellTower> towers = new HashMap<>();
        private Map<String, Operator> operators = new HashMap<>();
        private Map<String, Client> clients = new HashMap<>();

        public void addTower(CellTower tower) {
            towers.put(tower.getTowerId(), tower);
        }

        public void removeTower(String towerId) {
            CellTower tower = towers.remove(towerId);
            if (tower != null) {
                for (Operator op : operators.values()) {
                    op.removeTower(tower);
                }
                List<Client> clientsToUpdate = new ArrayList<>(tower.getConnectedClients());
                for (Client client : clientsToUpdate) {
                    client.setCurrentTower(null);
                    CellTower newTower = findBestTowerForClient(client);
                    client.setCurrentTower(newTower);
                }
            }
        }

        public void addOperator(Operator operator) {
            operators.put(operator.getName(), operator);
        }

        public Operator getOperator(String name) {
            return operators.get(name);
        }

        public void addClient(Client client) {
            clients.put(client.getPhoneNumber(), client);
            client.getOperator().addClient(client);
            CellTower bestTower = findBestTowerForClient(client);
            client.setCurrentTower(bestTower);
        }

        public void removeClient(String phoneNumber) {
            Client client = clients.remove(phoneNumber);
            if (client != null) {
                client.getOperator().removeClient(client);
                if (client.getCurrentTower() != null) {
                    client.getCurrentTower().removeClient(client);
                }
            }
        }

        public CellTower getTower(String towerId) {
            return towers.get(towerId);
        }

        public Client getClient(String phoneNumber) {
            return clients.get(phoneNumber);
        }

        public Map<String, Operator> getOperators() {
            return operators;
        }

        public CellTower findBestTowerForClient(Client client) {
            Operator operator = client.getOperator();
            if (operator == null) {
                return null;
            }
            Set<CellTower> operatorTowers = operator.getTowers();
            CellTower bestTower = null;
            double bestDistance = Double.MAX_VALUE;
            int bestClientCount = Integer.MAX_VALUE;

            for (CellTower tower : operatorTowers) {
                double dx = client.getX() - tower.getX();
                double dy = client.getY() - tower.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance > tower.getCoverageRadius()) {
                    continue;
                }

                if (distance < bestDistance) {
                    bestTower = tower;
                    bestDistance = distance;
                    bestClientCount = tower.getClientCount();
                } else if (distance == bestDistance) {
                    int currentCount = tower.getClientCount();
                    if (currentCount < bestClientCount) {
                        bestTower = tower;
                        bestClientCount = currentCount;
                        bestDistance = distance;
                    } else if (currentCount == bestClientCount) {
                        if (bestTower == null) {
                            bestTower = tower;
                        } else if (tower.getTowerId().compareTo(bestTower.getTowerId()) < 0) {
                            bestTower = tower;
                        }
                    }
                }
            }
            return bestTower;
        }
    }

    public static void main(String[] args) {
        Network network = new Network();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            if (command.isEmpty()) {
                continue;
            }
            switch (command) {
                case "MOVE_CLIENT":
                    moveClient(network, scanner);
                    break;
                case "CHANGE_OPERATOR":
                    changeOperator(network, scanner);
                    break;
                case "TOWER_CLIENT_COUNT":
                    towerClientCount(network, scanner);
                    break;
                case "OPERATOR_SUBSCRIBER_COUNT":
                    operatorSubscriberCount(network, scanner);
                    break;
                case "ADD_CLIENT":
                    addClient(network, scanner);
                    break;
                case "REMOVE_CLIENT":
                    removeClient(network, scanner);
                    break;
                case "ADD_TOWER":
                    addTower(network, scanner);
                    break;
                case "REGISTER_OPERATOR_TOWER":
                    registerOperatorTower(network, scanner);
                    break;
                case "REMOVE_TOWER":
                    removeTower(network, scanner);
                    break;
                case "NO_SIGNAL_COUNT":
                    noSignalCount(network);
                    break;
                case "ADD_OPERATOR":
                    addOperator(network, scanner);
                    break;
                default:
                    break;
            }
        }
    }

    private static void moveClient(Network network, Scanner scanner) {
        String phoneNumber = readNonEmptyLine(scanner);
        String xStr = readNonEmptyLine(scanner);
        String yStr = readNonEmptyLine(scanner);
        if (phoneNumber == null || xStr == null || yStr == null) return;
        try {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            Client client = network.getClient(phoneNumber);
            if (client != null) {
                client.setLocation(x, y);
                CellTower bestTower = network.findBestTowerForClient(client);
                client.setCurrentTower(bestTower);
            }
        } catch (NumberFormatException e) {}
    }

    private static void changeOperator(Network network, Scanner scanner) {
        String phoneNumber = readNonEmptyLine(scanner);
        String newOperatorName = readNonEmptyLine(scanner);
        if (phoneNumber == null || newOperatorName == null) return;
        Client client = network.getClient(phoneNumber);
        Operator newOperator = network.getOperator(newOperatorName);
        if (client == null || newOperator == null) return;
        Operator oldOperator = client.getOperator();
        oldOperator.removeClient(client);
        newOperator.addClient(client);
        client.setOperator(newOperator);
        CellTower bestTower = network.findBestTowerForClient(client);
        client.setCurrentTower(bestTower);
    }

    private static void towerClientCount(Network network, Scanner scanner) {
        String towerId = readNonEmptyLine(scanner);
        CellTower tower = network.getTower(towerId);
        System.out.println(tower != null ? tower.getClientCount() : 0);
    }

    private static void operatorSubscriberCount(Network network, Scanner scanner) {
        String operatorName = readNonEmptyLine(scanner);
        Operator operator = network.getOperator(operatorName);
        System.out.println(operator != null ? operator.getSubscriberCount() : 0);
    }

    private static void addClient(Network network, Scanner scanner) {
        String phoneNumber = readNonEmptyLine(scanner);
        String operatorName = readNonEmptyLine(scanner);
        String xStr = readNonEmptyLine(scanner);
        String yStr = readNonEmptyLine(scanner);
        if (phoneNumber == null || operatorName == null || xStr == null || yStr == null) return;
        try {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            Operator operator = network.getOperator(operatorName);
            if (operator == null) return;
            Client client = new Client(phoneNumber, operator, x, y);
            network.addClient(client);
        } catch (NumberFormatException e) {}
    }

    private static void removeClient(Network network, Scanner scanner) {
        String phoneNumber = readNonEmptyLine(scanner);
        if (phoneNumber != null) network.removeClient(phoneNumber);
    }

    private static void addTower(Network network, Scanner scanner) {
        String towerId = readNonEmptyLine(scanner);
        String xStr = readNonEmptyLine(scanner);
        String yStr = readNonEmptyLine(scanner);
        String radiusStr = readNonEmptyLine(scanner);
        if (towerId == null || xStr == null || yStr == null || radiusStr == null) return;
        try {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            double radius = Double.parseDouble(radiusStr);
            network.addTower(new CellTower(towerId, x, y, radius));
        } catch (NumberFormatException e) {}
    }

    private static void registerOperatorTower(Network network, Scanner scanner) {
        String operatorName = readNonEmptyLine(scanner);
        String towerId = readNonEmptyLine(scanner);
        if (operatorName == null || towerId == null) return;
        Operator operator = network.getOperator(operatorName);
        CellTower tower = network.getTower(towerId);
        if (operator != null && tower != null) operator.addTower(tower);
    }

    private static void removeTower(Network network, Scanner scanner) {
        String towerId = readNonEmptyLine(scanner);
        if (towerId != null) network.removeTower(towerId);
    }

    private static void noSignalCount(Network network) {
        Map<String, Integer> counts = new TreeMap<>();
        for (Operator operator : network.getOperators().values()) {
            int count = 0;
            for (Client client : operator.getClients()) {
                if (client.getCurrentTower() == null) count++;
            }
            counts.put(operator.getName(), count);
        }
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " phones without signal.");
        }
    }

    private static void addOperator(Network network, Scanner scanner) {
        String operatorName = readNonEmptyLine(scanner);
        if (operatorName != null) network.addOperator(new Operator(operatorName));
    }

    private static String readNonEmptyLine(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) return line;
        }
        return null;
    }
}
