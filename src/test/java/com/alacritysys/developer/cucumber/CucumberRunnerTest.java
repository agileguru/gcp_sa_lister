package com.alacritysys.developer.cucumber;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;


@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.alacritysys.developer.cucumber")
public class CucumberRunnerTest {

}
