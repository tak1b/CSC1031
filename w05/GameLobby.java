import java.util.ArrayList;
import java.util.List;

// Player interface with all required methods.
// An additional getName() method is added for ease of printing messages.
interface Player {
    void joinGame();
    void leaveGame();
    void sendMessage(String message);
    void receiveMessage(String message);
    String getPlayerType();
    String getName();
}

// AbstractPlayer providing common behavior for all players.
abstract class AbstractPlayer implements Player {
    protected String name;
    protected GameLobby lobby;
    
    public AbstractPlayer(String name, GameLobby lobby) {
        this.name = name;
        this.lobby = lobby;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    // Default implementation: delegate to the lobby.
    @Override
    public void joinGame() {
        lobby.registerPlayer(this);
    }
    
    @Override
    public void leaveGame() {
        lobby.removePlayer(this);
    }
    
    // By default, output the sending line and then forward the message to the mediator.
    @Override
    public void sendMessage(String message) {
        System.out.println("[" + name + "] sends: \"" + message + "\"");
        lobby.sendMessage(message, this);
    }
    
    // Simply prints that this player received a message.
    @Override
    public void receiveMessage(String message) {
        System.out.println("[" + name + "] received: \"" + message + "\"");
    }
    
    // Each concrete player must define its own type.
    @Override
    public abstract String getPlayerType();
}

// Concrete implementation for a human player.
class HumanPlayer extends AbstractPlayer {
    public HumanPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }
    
    @Override
    public String getPlayerType() {
        return "HumanPlayer";
    }
}

// Concrete implementation for an AI player.
class AIPlayer extends AbstractPlayer {
    public AIPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }
    
    @Override
    public String getPlayerType() {
        return "AIPlayer";
    }
}

// Spectator: They are allowed to join or leave but cannot send messages.
class Spectator extends AbstractPlayer {
    public Spectator(String name, GameLobby lobby) {
        super(name, lobby);
    }
    
    // Override sendMessage to block any sending attempt.
    @Override
    public void sendMessage(String message) {
        System.out.println("[GameLobby] Spectators cannot send messages.");
    }
    
    @Override
    public String getPlayerType() {
        return "Spectator";
    }
}

// The GameLobby acts as the mediator.
public class GameLobby {
    private List<Player> players;
    
    public GameLobby() {
        players = new ArrayList<>();
    }
    
    // Register a player and output the join message.
    public void registerPlayer(Player player) {
        players.add(player);
        System.out.println("[GameLobby] " + player.getPlayerType() + " " + player.getName() + " has joined the lobby.");
    }
    
    // Remove a player and output the leave message.
    public void removePlayer(Player player) {
        players.remove(player);
        System.out.println("[GameLobby] " + player.getPlayerType() + " " + player.getName() + " has left the lobby.");
    }
    
    // Send a message from the sender to all other players.
    public void sendMessage(String message, Player sender) {
        // In case a spectator somehow calls this method, ensure no sending occurs.
        if (sender.getPlayerType().equals("Spectator")) {
            System.out.println("[GameLobby] Spectators cannot send messages.");
            return;
        }
        System.out.println("[GameLobby] Message from " + sender.getName() + ": \"" + message + "\"");
        for (Player p : players) {
            if (p != sender) {
                p.receiveMessage(message);
            }
        }
    }
    
    // Starts the match by checking if enough players (human or AI) have joined.
    public void startMatch() {
        List<String> eligiblePlayers = new ArrayList<>();
        for (Player p : players) {
            String type = p.getPlayerType();
            if (type.equals("HumanPlayer") || type.equals("AIPlayer")) {
                eligiblePlayers.add(p.getName());
            }
        }
        
        if (eligiblePlayers.size() < 2) {
            System.out.println("[GameLobby] Not enough players to start a match.");
        } else {
            String playersList = String.join(", ", eligiblePlayers);
            System.out.println("[GameLobby] Starting game with players: " + playersList);
        }
    }
}

