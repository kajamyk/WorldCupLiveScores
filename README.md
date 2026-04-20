# Football World Cup Score Board

Simple in-memory library that manages live football matches.

## Features

- Start match (initial score 0-0)
- Update score
- Finish match
- Get scoreboard summary

## Assumptions

- Each match is uniquely identified by (homeTeam, awayTeam)
- No duplicate matches allowed
- Score cannot be negative (not enforced, but assumed valid input)
- Most recent match = last inserted into system

## Design decisions

- Used in-memory List for simplicity and fast access
- Used Java records for Match and Score to ensure immutability
- Score is modeled as a value object
- Match is immutable – score updates create a new instance
- Sequence number is used instead of timestamps to ensure deterministic sorting

## Sorting rule

1. Total score descending
2. If equal → most recently added first

## Testing

JUnit + TDD approach