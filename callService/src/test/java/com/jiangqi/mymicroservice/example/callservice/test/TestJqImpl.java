package com.jiangqi.mymicroservice.example.callservice.test;

public class TestJqImpl implements TestJq {

	@Override
	public void test()  {
		// TODO Auto-generated method stub
		RuntimeException e= new RuntimeException();
		throw e;
	}

}
