package com.alacritysys.developer.cucumber;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;


@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.alacritysys.developer.cucumber")
public class CucumberRunnerTest {

	@Test
	public void testCucumberWorks() {
		Assertions.assertThat(this.getClass().getAnnotations()).isNotEmpty();
	}
}
