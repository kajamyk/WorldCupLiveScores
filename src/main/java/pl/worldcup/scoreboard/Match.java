package pl.worldcup.scoreboard;

import java.time.Instant;

public record Match(String homeTeam, String awayTeam, Score score, Instant createdAt) {

    public boolean matches(String home, String away) {
        return homeTeam.equals(home) && awayTeam.equals(away);
    }
}