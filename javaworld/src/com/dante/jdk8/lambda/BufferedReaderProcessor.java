package com.dante.jdk8.lambda;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface BufferedReaderProcessor {
	public String process(BufferedReader reader) throws IOException;
}
