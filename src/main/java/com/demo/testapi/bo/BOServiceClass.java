package com.demo.testapi.bo;

public class BOServiceClass {

	private TestBO testBO;
	
	private TestBO spyBO;
	
	
	public int executeInjectedMock(int x, int y) {
		return testBO.adder(x, y);
	}
	
	public int executeInjectedSpy(int x, int y) {
		return spyBO.adder(x, y);
	}
}
