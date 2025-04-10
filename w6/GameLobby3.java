import java.util.ArrayList;
import java.util.List;

// Player interface with required methods and a getter for the player's name.
interface Player {
    void joinGame();
    void leaveGame();
    void sendMessage(String message);
    void receiveMessage(String message);
    String getPlayerType();
    String getName();
}

// AbstractPlayer provides common behavior for all players.
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
    
    @Override
    public void joinGame() {
        lobby.registerPlayer(this);
    }
    
    @Override
    public void leaveGame() {
        lobby.removePlayer(this);
    }
    
    @Override
    public void sendMessage(String message) {
        System.out.println("[" + name + "] sends: \"" + message + "\"");
        lobby.sendMessage(message, this);
    }
    
    @Override
    public void receiveMessage(String message) {
        System.out.println("[" + name + "] received: \"" + message + "\"");
    }
    
    @Override
    public abstract String getPlayerType();
}

// Concrete player: HumanPlayer.
class HumanPlayer extends AbstractPlayer {
    public HumanPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }
    
    @Override
    public String getPlayerType() {
        return "HumanPlayer";
    }
}

// Concrete player: AIPlayer.
class AIPlayer extends AbstractPlayer {
    public AIPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }
    
    @Override
    public String getPlayerType() {
        return "AIPlayer";
    }
}

// Concrete player: Spectator.
// Spectators can join/leave and receive messages, but cannot send messages.
class Spectator extends AbstractPlayer {
    public Spectator(String name, GameLobby lobby) {
        super(name, lobby);
    }
    
    @Override
    public void sendMessage(String message) {
        System.out.println("[GameLobby] Spectators cannot send messages.");
    }
    
    @Override
    public String getPlayerType() {
        return "Spectator";
    }
}

// Concrete player: AdminPlayer.
// Admin players have an extra method to kick other players.
class AdminPlayer extends AbstractPlayer {
    public AdminPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }
    
    @Override
    public String getPlayerType() {
        return "AdminPlayer";
    }
    
    // Kicks a player by name.
    public void kickPlayer(String targetName) {
        lobby.kickPlayer(targetName, this);
    }
}

// Mediator: GameLobby manages registration, messaging, match starting, and kicking.
class GameLobby {
    private List<Player> players;
    
    public GameLobby() {
        players = new ArrayList<>();
    }
    
    public void registerPlayer(Player player) {
        players.add(player);
        System.out.println("[GameLobby] " + player.getPlayerType() + " " + player.getName() + " has joined the lobby.");
    }
    
    public void removePlayer(Player player) {
        players.remove(player);
        System.out.println("[GameLobby] " + player.getPlayerType() + " " + player.getName() + " has left the lobby.");
    }
    
    // Sends a message from the sender to all other players.
    public void sendMessage(String message, Player sender) {
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
    
    // Starts the match with eligible players (only HumanPlayer and AIPlayer).
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
    
    // Allows an AdminPlayer to kick another player by name.
    public void kickPlayer(String targetName, AdminPlayer admin) {
        if (admin.getName().equals(targetName)) {
            System.out.println("[GameLobby] Admins cannot kick themselves.");
            return;
        }
        
        Player target = null;
        for (Player p : players) {
            if (p.getName().equals(targetName)) {
                target = p;
                break;
            }
        }
        if (target != null) {
            System.out.println("[GameLobby] Admin " + admin.getName() + " kicked " 
                    + target.getPlayerType() + " " + target.getName() + " from the lobby.");
            removePlayer(target);
        } else {
            System.out.println("[GameLobby] Player " + targetName + " not found.");
        }
    }
}

// Factory class: PlayerFactory creates different types of players based on the provided type.
class PlayerFactory {
    public static Player createPlayer(String type, String name, GameLobby lobby) {
        switch (type.toLowerCase()) {
            case "human":
                return new HumanPlayer(name, lobby);
            case "ai":
                return new AIPlayer(name, lobby);
            case "spectator":
                return new Spectator(name, lobby);
            case "admin":
                return new AdminPlayer(name, lobby);
            default:
                throw new IllegalArgumentException("Invalid player type: " + type);
        }
    }
}
