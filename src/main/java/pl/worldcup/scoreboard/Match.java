package pl.worldcup.scoreboard;

public record Match(String homeTeam, String awayTeam, Score score, Integer sequence) {

    public boolean matches(String home, String away) {
        return homeTeam.equals(home) && awayTeam.equals(away);
    }
}