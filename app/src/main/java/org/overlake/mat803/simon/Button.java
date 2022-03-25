package org.overlake.mat803.simon;

import java.util.Random;

public enum Button {
   BLUE, GREEN, RED, YELLOW;

   private static final Button[] VALUES = values();
   private static final int SIZE = VALUES.length;
   private static final Random RANDOM = new Random();

   public static Button getRandomButton()  {
     return VALUES[RANDOM.nextInt(SIZE)];
   }
}