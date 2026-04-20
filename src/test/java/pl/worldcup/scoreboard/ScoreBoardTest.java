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
}