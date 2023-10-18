package pl.zajacp.concurrency.demo.problems;

import lombok.SneakyThrows;

import java.util.concurrent.Executors;

public record Fox(String name) {

  record Food() { }
  record Water() { }

  void eatAndDrink(Food food, Water water) {
    synchronized (food) {
      System.out.println(name() + " Got Food!");
      move();
      synchronized (water) {
        System.out.println(name() + " Got Water!");
      }
    }
  }

  void drinkAndEat(Food food, Water water) {
    synchronized (water) {
      System.out.println(name() + " Got Water!");
      move();
      synchronized (food) {
        System.out.println(name() + " Got Food!");
      }
    }
  }

  @SneakyThrows
  public void move() {
    Thread.sleep(100);
  }

  public static void main(String[] args) {
    var urwisek = new Fox("Urwisek");
    var chytrusek = new Fox("Chytrusek");
    var food = new Food();
    var water = new Water();

    try (var service = Executors.newCachedThreadPool()) {
      service.submit(() -> urwisek.eatAndDrink(food, water));
      service.submit(() -> chytrusek.drinkAndEat(food, water));
    }
  }
}