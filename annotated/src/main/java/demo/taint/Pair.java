package demo.taint;

import java.io.Serializable;

public final class Pair<F, S> implements Serializable {
  Pair(F first, S second) {}

  F getFirst() {
    return null;
  }

  S getSecond() {
    return null;
  }
}
