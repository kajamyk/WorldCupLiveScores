package pl.worldcup.scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory Football World Cup Score Board.
 *
 * <p>Provides operations to:
 * <ul>
 *     <li>start a new match</li>
 *     <li>update an ongoing match score</li>
 *     <li>finish a match</li>
 *     <li>retrieve a summary of ongoing matches</li>
 * </ul>
 *
 * <p>The summary is sorted by:
 * <ul>
 *     <li>Total score (home + away) in descending order</li>
 *     <li>Most recently started match first when scores are equal</li>
 * </ul>
 *
 * <p>This implementation is in-memory and not thread-safe.
 */
public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();
    private int sequence = 0;

    /**
     * Returns sorted scoreboard summary.
     */
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

    /**
     * Starts a new match with score 0-0.
     */
    public void startGame(String home, String away) {
        validateTeams(home, away);
        matches.add(new Match(home, away, new Score(0, 0), sequence++));
    }

    /**
     * Finishes a match and removes it from scoreboard.
     */
    public void finishGame(String home, String away) {
        matches.removeIf(match -> match.matches(home, away));
    }

    /**
     * Updates score of a match.
     *
     * @throws MatchNotFoundException if match does not exist
     */
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