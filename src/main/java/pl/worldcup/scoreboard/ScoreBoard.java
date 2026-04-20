package pl.worldcup.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public List<Match> getSummary() {
        return matches;
    }

    public void startGame(String home, String away) {
        matches.add(new Match(home, away, new Score(0, 0)));
    }

    public void finishGame(String home, String away) {
        matches.removeIf(match -> match.matches(home, away));
    }

    public void updateScore(String home, String away, int homeScore, int awayScore) {
        for (int i = 0; i < matches.size(); i++) {
            Match m = matches.get(i);

            if (m.homeTeam().equals(home) && m.awayTeam().equals(away)) {
                matches.set(i, new Match(home, away, new Score(homeScore, awayScore)));
                return;
            }
        }
        throw new MatchNotFoundException("Match not found: " + home + " vs " + away);
    }
}