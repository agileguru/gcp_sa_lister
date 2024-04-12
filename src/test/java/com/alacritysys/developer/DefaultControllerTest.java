package com.alacritysys.developer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class DefaultControllerTest {

	private DefaultController controller = new DefaultController();
	
	@Test
	void test() {
	  ResponseEntity<String> resp = controller.sayHello();
	  assertThat(resp).isNotNull();
	}

}
