package pl.worldcup.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();
    private int sequence = 0;

    public List<Match> getSummary() {
        List<Match> sorted = new ArrayList<>(matches);

        sorted.sort((a, b) -> {
            int scoreCompare = Integer.compare(
                    b.score().total(),
                    a.score().total()
            );

            if (scoreCompare != 0) return scoreCompare;

            return b.sequence().compareTo(a.sequence());
        });

        return sorted;
    }

    public void startGame(String home, String away) {
        validateTeams(home, away);
        matches.add(new Match(home, away, new Score(0, 0), sequence++));
    }

    public void finishGame(String home, String away) {
        matches.removeIf(match -> match.matches(home, away));
    }

    public void updateScore(String home, String away, int homeScore, int awayScore) {
        for (int i = 0; i < matches.size(); i++) {
            Match m = matches.get(i);

            if (m.homeTeam().equals(home) && m.awayTeam().equals(away)) {
                matches.set(i, new Match(home, away, new Score(homeScore, awayScore), m.sequence()));
                return;
            }
        }
        throw new MatchNotFoundException("Match not found: " + home + " vs " + away);
    }

    private void validateTeams(String home, String away) {
        if (home == null || away == null || home.isBlank() || away.isBlank()) {
            throw new IllegalArgumentException("Team names cannot be empty");
        }
        if (home.equals(away)) {
            throw new IllegalArgumentException("Teams must be different");
        }
    }
}