package com.unison.batch;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CountDownLatch;

public class Test {

    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        WebClient client = WebClient.create("https://jsonplaceholder.typicode.com");
        client.get()
                .uri("/posts/{id}",1)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(i -> {
                    System.out.println(i);
                    latch.countDown();
                });

        System.out.println("main finish");
        latch.await();
    }
}
