import java.util.ArrayList;
import java.util.List;
import java.util.*;

interface Player
{
    void joinGame();

    void leaveGame();

    void sendMessage(String message);

    void receiveMessage(String message);

    String getPlayerType();

    String getName();

}

abstract class AbstractPlayer implements Player
{
    protected String name;
    protected GameLobby lobby;

    public AbstractPlayer(String name, GameLobby lobby)
    {
        this.name = name;
        this.lobby = lobby;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void sendMessage(String message)
    {
        this.lobby.sendMessage(message, this);
    }

    @Override
    public void receiveMessage(String message)
    {
        System.out.printf("[%s] received: \"%s\"\n", this.name, message);
    }

    public abstract String getPlayerType();
}

class HumanPlayer extends AbstractPlayer
{
    public HumanPlayer(String name, GameLobby lobby)
    {
        super(name, lobby);
    }
    @Override
    public void joinGame()
    {
        lobby.registerPlayer(this);
    }

    @Override
    public void leaveGame()
    {
        lobby.removePlayer(this);
    }

    @Override
    public String getPlayerType()
    {
        return "HumanPlayer";
    }
}

class AIPlayer extends AbstractPlayer
{
    public AIPlayer(String name, GameLobby lobby)
    {
        super(name, lobby);
    }

    @Override
    public void joinGame()
    {
        lobby.registerPlayer(this);
    }

    @Override
    public void leaveGame()
    {
        lobby.removePlayer(this);
    }

    @Override
    public String getPlayerType()
    {
        return "AIPlayer";
    }
}

class Spectator extends AbstractPlayer
{
   public Spectator(String name, GameLobby lobby)
    {
        super(name, lobby);
    }
    @Override
    public void joinGame()
    {
        lobby.registerPlayer(this);
    }

    @Override
    public void leaveGame()
    {
        lobby.removePlayer(this);
    }


    public String getPlayerType()
    {
        return "Spectator";
    }
}

class PlayerFactory
{
    public static Player createPlayer(String type, String name, GameLobby lobby)
    {
        switch (type.toLowerCase())
        {
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

class AdminPlayer extends AbstractPlayer
{
    public AdminPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }

    @Override
    public String getPlayerType() {
        return "AdminPlayer";
    }

    public void kickPlayer(String name) {
        lobby.kickPlayer(name, this);
    }

    @Override
    public void joinGame()
    {
        lobby.registerPlayer(this);
    }

    @Override
    public void leaveGame()
    {
        lobby.removePlayer(this);
    }

}

class GameLobby
{
    LinkedHashSet<Player> players = new LinkedHashSet<>();

    void registerPlayer(Player player)
    {
        players.add(player);
        System.out.printf("[GameLobby] %s %s has joined the lobby.\n", player.getPlayerType(), ((AbstractPlayer)player).name);
    }

    void removePlayer(Player player)
    {
        players.remove(player);
        System.out.printf("[GameLobby] %s %s has left the lobby.\n", player.getPlayerType(), ((AbstractPlayer)player).name);
    }

    void kickPlayer(String name, AdminPlayer admin)
    {
        Player playerToKick = null;

        for(Player p : players)
        {
            if (((AbstractPlayer)p).name.equals(name))
            {
                playerToKick = p;
                break;
            }
        }

        if (playerToKick != null && playerToKick.getPlayerType() != "AdminPlayer")
        {
            players.remove(playerToKick);
            System.out.printf("[GameLobby] Admin %s kicked %s %s from the lobby.\n", admin.name, playerToKick.getPlayerType(), ((AbstractPlayer)playerToKick).name);
            System.out.printf("[GameLobby] %s %s has left the lobby.\n", playerToKick.getPlayerType(), ((AbstractPlayer)playerToKick).name);
    }
        else
        {
            System.out.printf("[GameLobby] Player %s not found.\n", name);
        }

    }

    void sendMessage(String message, Player player)
    {
        if (player.getPlayerType() == "Spectator")
        {
            System.out.printf("[GameLobby] Spectators cannot send messages.\n");
        }
        else
        {
            System.out.printf("[%s] sends: \"%s\"\n", ((AbstractPlayer)player).name, message);
            System.out.printf("[GameLobby] Message from %s: \"%s\"\n", ((AbstractPlayer)player).name, message);
            for (Player p : players)
            {
                if (((AbstractPlayer)player).name != ((AbstractPlayer)p).name)
                {
                    p.receiveMessage(message);
                }
            }
        }
    }

    void startMatch()
    {
        int count = 0;
        String output = "";
        for (Player p : players)
        {
            if (p.getPlayerType() != "Spectator")
            {
                if (p.getPlayerType() != "AdminPlayer")
                {
                    count += 1;
                    output += ", " + ((AbstractPlayer)p).name;
                }
            }
        }

        if (count < 2)
        {
            System.out.printf("[GameLobby] Not enough players to start a match.\n");
        }
        else
        {
            System.out.printf("[GameLobby] Starting game with players: %s\n", output.substring(2));
        }
    }
}