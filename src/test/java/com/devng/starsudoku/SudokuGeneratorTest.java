package com.devng.starsudoku;

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
		final SudokuGrid grid = new SudokuGrid();
		grid.loadSudokuString(input);

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
		Assert.assertEquals(grid.toString(), expectedAfterSolve);
	}
}