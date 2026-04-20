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
}