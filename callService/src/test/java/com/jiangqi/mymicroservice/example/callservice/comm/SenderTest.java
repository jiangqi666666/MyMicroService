package com.jiangqi.mymicroservice.example.callservice.comm;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jiangqi.mymicroservice.example.callservice.CallService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=CallService.class)
public class SenderTest {
	
	@Autowired
	Sender sender;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testSend() {
		//fail("Not yet implemented");
		sender.send();
	}

}
