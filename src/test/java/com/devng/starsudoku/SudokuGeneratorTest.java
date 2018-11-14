package com.devng.starsudoku;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class SudokuGeneratorTest {

	@Test
	public void testSolveGrid() {
		final String input = "081600090000000000004037600600400500030000070007002004005210300000000000070004810";

		final String expectedAfterLoad =
				"|0|8|1|6|0|0|0|9|0|\n" +
				"|0|0|0|0|0|0|0|0|0|\n" +
				"|0|0|4|0|3|7|6|0|0|\n" +
				"|6|0|0|4|0|0|5|0|0|\n" +
				"|0|3|0|0|0|0|0|7|0|\n" +
				"|0|0|7|0|0|2|0|0|4|\n" +
				"|0|0|5|2|1|0|3|0|0|\n" +
				"|0|0|0|0|0|0|0|0|0|\n" +
				"|0|7|0|0|0|4|8|1|0|\n";
		final SudokuGrid grid = SudokuGrid.createFromString(input);

		// test the loading
		Assert.assertEquals(grid.toString(), expectedAfterLoad);
		Assert.assertTrue(grid.isGridValid());

		final SudokuGenerator gen = new SudokuGenerator(grid);
		gen.solveGrid();

		final String expectedAfterSolve=
				"|2|8|1|6|4|5|7|9|3|\n" +
				"|7|6|3|9|2|8|1|4|5|\n" +
				"|5|9|4|1|3|7|6|2|8|\n" +
				"|6|2|9|4|7|3|5|8|1|\n" +
				"|4|3|8|5|9|1|2|7|6|\n" +
				"|1|5|7|8|6|2|9|3|4|\n" +
				"|8|4|5|2|1|9|3|6|7|\n" +
				"|3|1|2|7|8|6|4|5|9|\n" +
				"|9|7|6|3|5|4|8|1|2|\n";
		// test the solver
		Assert.assertEquals(gen.getGrid().toString(), expectedAfterSolve);
	}

	@Test
	public void testSolveGridPerformanceTest() throws Exception {
		final int limit = 10;

		final List<SudokuGrid> grids;
		try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource("top50000.sdm").toURI()))) {
			grids = stream.limit(limit).map(SudokuGrid::createFromString).collect(Collectors.toList());
		}

		System.out.println("Testing with " + grids.size() + " sudokus");

		final long start = System.currentTimeMillis();

		grids.parallelStream().map(
			g -> new SudokuGenerator(g).solveGrid()
		).count();

		final long duration = System.currentTimeMillis() - start;
		final long avgDuration = duration / grids.size();

		final String durationStr = String.format(
				"%02d min, %02d sec",
				TimeUnit.MILLISECONDS.toMinutes(duration),
				TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

		System.out.println("Solving " + grids.size() + " sudokus took " + durationStr);
		System.out.println("Average duration is " + avgDuration + " ms/sudoku");
	}
}
