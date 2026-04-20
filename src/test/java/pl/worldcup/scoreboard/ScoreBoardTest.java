package pl.worldcup.scoreboard;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    @Test
    void shouldReturnEmptySummaryWhenNoGamesStarted() {
        ScoreBoard board = new ScoreBoard();

        List<Match> summary = board.getSummary();

        assertTrue(summary.isEmpty());
    }

    @Test
    void shouldStartGameWithZeroScore() {
        ScoreBoard board = new ScoreBoard();

        board.startGame("Mexico", "Canada");

        List<Match> summary = board.getSummary();

        assertEquals(1, summary.size());
        assertEquals(0, summary.getFirst().score().home());
        assertEquals(0, summary.getFirst().score().away());
    }

    @Test
    void shouldRemoveOnlySpecificMatch() {
        ScoreBoard board = new ScoreBoard();

        board.startGame("A", "B");
        board.startGame("C", "D");

        board.finishGame("A", "B");

        List<Match> result = board.getSummary();

        assertEquals(1, result.size());
        assertEquals("C", result.getFirst().homeTeam());
        assertEquals("D", result.getFirst().awayTeam());
    }

    @Test
    void shouldUpdateScoreCorrectly() {
        ScoreBoard board = new ScoreBoard();

        board.startGame("Spain", "Brazil");
        board.updateScore("Spain", "Brazil", 3, 2);

        Match match = board.getSummary().getFirst();

        assertEquals(3, match.score().home());
        assertEquals(2, match.score().away());
    }

    @Test
    void shouldThrowExceptionWhenMatchNotFoundOnUpdate() {
        ScoreBoard board = new ScoreBoard();

        assertThrows(MatchNotFoundException.class, () ->
                board.updateScore("A", "B", 1, 1)
        );
    }

    @Test
    void shouldSortByTotalScoreDescending() {
        ScoreBoard board = new ScoreBoard();

        board.startGame("Mexico", "Canada");
        board.updateScore("Mexico", "Canada", 0, 5);

        board.startGame("Spain", "Brazil");
        board.updateScore("Spain", "Brazil", 10, 2);

        List<Match> result = board.getSummary();

        assertEquals("Spain", result.get(0).homeTeam());
        assertEquals("Mexico", result.get(1).homeTeam());
    }

    @Test
    void shouldSortByMostRecentWhenScoreIsEqual() {
        ScoreBoard board = new ScoreBoard();

        board.startGame("A", "B");
        board.updateScore("A", "B", 2, 2);

        board.startGame("C", "D");
        board.updateScore("C", "D", 2, 2);

        List<Match> result = board.getSummary();

        assertEquals("C", result.getFirst().homeTeam());
    }

    @Test
    void shouldThrowExceptionWhenTeamsAreInvalid() {
        ScoreBoard board = new ScoreBoard();

        assertThrows(IllegalArgumentException.class, () ->
                board.startGame("", "Brazil")
        );

        assertThrows(IllegalArgumentException.class, () ->
                board.startGame("Spain", "Spain")
        );
    }

    @Test
    void shouldThrowExceptionWhenScoresAreInvalid() {
        ScoreBoard board = new ScoreBoard();
        board.startGame("Spain", "Brazil");

        assertThrows(IllegalArgumentException.class, () ->
                board.updateScore("Spain", "Brazil", -1, 0)
        );

        assertThrows(IllegalArgumentException.class, () ->
                board.updateScore("Spain", "Brazil", 1, -2)
        );
    }

    @Test
    void shouldThrowExceptionWhenStartingDuplicateMatch() {
        ScoreBoard board = new ScoreBoard();
        board.startGame("Spain", "Brazil");

        assertThrows(IllegalArgumentException.class, () ->
                board.startGame("Spain", "Brazil")
        );
    }
}