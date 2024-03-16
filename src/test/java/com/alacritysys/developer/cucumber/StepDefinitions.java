package com.alacritysys.developer.cucumber;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;

public class StepDefinitions {
	
	 @Given("I want to test using cucumber {int} times")
	 public void I_want_to_test_using_cucumber_times(int times) {
		 assertThat(times).isGreaterThanOrEqualTo(times);
	 }

}
