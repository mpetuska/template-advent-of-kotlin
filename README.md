# Advent of Kotlin Template

A template repository providing a simple framework for [Advent of Code](https://adventofcode.com) puzzles.

## Content

After you create a new project based on the current template repository using the [**Use this template
**](https://github.com/mpetuska/template-advent-of-kotlin/generate) button,
a bare minimal scaffold will appear in your GitHub account with the following structure:

```
.
├── README.md               README file
├── build.gradle.kts        Gradle configuration created with Kotlin DSL
├── gradle
│   └── wrapper             Gradle Wrapper
├── gradle.properties       Gradle configuration properties
├── gradlew                 *nix Gradle Wrapper script
├── gradlew.bat             Windows Gradle Wrapper script
├── src                     Generic framework utilities
└── calendar                Your very own sandbox to solve the puzzles
    └── dayX                Each day has a dedicated package for it
        ├── DayX.kt         An empty implementation class for the AoC day X [1-25]
        ├── part1.txt       AoC day X [1-25] input data for part 1
        └── part2.txt       AoC day X [1-25] input data for part 2
```

> Note: All task input files are empty in the repository – we should not post them publicly, as
> Eric Wastl asks for: [Tweet](https://twitter.com/ericwastl/status/1465805354214830081).
>
> To help with that, the template contains a git hook that prevents committing anything if input files are not empty.
> There's also `./gradlew cleanInputs` task for easy cleanup.
>
> Please make sure not to commit input data once after you fill those files in.

## Usage

Here's the overview of your daily routine while solving AoC puzzles:

1. Open `./calendar/dayX` directory for the day you're solving.
2. Paste your input for part1 into `part1.txt`.
3. Open `DayX.kt` file and implement your solution in `part1` method and return your answer.
4. Check your solution for part1 by running `./gradlew test --tests='dayX.DayX.part1'`
5. Paste your input for part2 into `part2.txt` (only if it's different frm `part1`, otherwise `part1.txt` will be used.
6. Open `DayX.kt` file and implement your solution in `part2` method and return your answer.
7. Check your solution for part2 by running `./gradlew test --tests='dayX.DayX.part2'`
8. Check both your solutions by running `./gradlew test --tests='dayX.DayX'`
9. Submit your answers to [AoC](https://adventofcode.com)
10. Clean up inputs by running `./gradlew cleanInputs`
11. Commit your code by running `git commit -a -m "AoC DayX"`
12. Push the changes by running `git push`
13. Profit??

> Often inputs for `part1` and `part2` are the same. The framework handles that by falling back to `part1` input file
> if `part2` input is left empty.
