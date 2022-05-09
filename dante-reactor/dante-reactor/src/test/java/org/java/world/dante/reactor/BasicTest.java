package org.java.world.dante.reactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.java.world.dante.reactor.subscriber.SampleSubscriber;
import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BasicTest {

	@Test
	public void testMapAndFlatMap() {
		List<String> words = new ArrayList<>();
		words.add("Hello");
		words.add("World");

		List<String[]> strList1 = words.stream()
					.map(str -> str.split(""))
					.distinct()
					.collect(Collectors.toList());
		System.out.println(strList1);

		List<String> strList2 = words.stream()
				.flatMap(w -> Arrays.stream(w.split("")))
				.distinct()
				.collect(Collectors.toList());
		System.out.println(strList2);
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testFluxAndMono() {
		Flux<String> seq1 = Flux.just("dante", "snake", "yuna");
		seq1.subscribe(System.out::println);
		List<String> strs = Arrays.asList("dante", "snake", "yuna");
		Flux<String> seq2 = Flux.fromIterable(strs);
		
		Mono<String> noData = Mono.empty(); 
		Mono<String> data = Mono.just("dante");
		
		SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
		Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 10);
		numbersFromFiveToSeven.subscribe(
			n -> {
				System.out.println("n -> " + n);
				if(n == 20) {
					throw new RuntimeException("Got to 20"); 
				}
			},
			err -> System.out.println("err: " + err),
			() -> System.out.println("Done."),
			s -> ss.request(10)
		);
		numbersFromFiveToSeven.subscribe(ss);
	}
	
	@Test
	public void genrate() {
		Flux.generate(
		    () -> 0, 
		    (state, sink) -> {
		      sink.next("3 x " + state + " = " + 3*state); 
		      if (state == 10) sink.complete(); 
		      return state + 1; 
	    });
	}

	@Test
	public void filterNull() {
		Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
			    .handle((i, sink) -> {
			        String letter = alphabet(i); 
			        if (letter != null) 
			            sink.next(letter); 
			    });

			alphabet.subscribe(System.out::println);
	}


	public String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
                return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
	}

	
}


