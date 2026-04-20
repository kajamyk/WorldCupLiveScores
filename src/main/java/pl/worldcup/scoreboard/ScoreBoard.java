package pl.worldcup.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public List<Match> getSummary() {
        return matches;
    }
}