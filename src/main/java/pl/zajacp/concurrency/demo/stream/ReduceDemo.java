package pl.zajacp.concurrency.demo.stream;

import java.util.stream.Stream;

public class ReduceDemo {

    public static void main(String[] args) {

        var piwo = Stream.of('p', 'i', 'w', 'o')
                .parallel()
                .reduce("",
                        (String s, Character c) -> s + c,
                        (String s1, String s2) -> s1 + s2);

        System.out.println(piwo);
    }
}
