package demo.taint;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ExecParameterTokenizer {
  private final String str;

  /** The list of tokens, which will take account of quoted sections. */
  private List<String> tokens;

  public ExecParameterTokenizer(String str) {
    this.str = str;
  }

  /**
   * This method returns the tokens in a parameter string. Any tokens not contained within single or
   * double quotes will be tokenized in the normal way i.e. by using whitespace separators and the
   * standard StringTokenizer algorithm. Any tokens which are contained within single or double
   * quotes will be returned as single String instances and will have their quote marks removed.
   *
   * <p>See above for examples.
   *
   * @throws NullPointerException if the string to be tokenized was null.
   */
  public List<String> getAllTokens() {
    if (this.str == null) {
      throw new NullPointerException("Illegal null string cannot be tokenized.");
    }
    if (tokens == null) {
      tokens = new ArrayList<>();
      // Preserve original behaviour from RuntimeExec.
      if (str.indexOf('\'') == -1 && str.indexOf('"') == -1) {
        // Contains no quotes.
        for (StringTokenizer standardTokenizer = new StringTokenizer(str);
            standardTokenizer.hasMoreTokens(); ) {
          tokens.add(standardTokenizer.nextToken());
        }
      } else {
        // There are either single or double quotes or both.
        // So we need to identify the quoted regions within the string.
        List<Pair<Integer, Integer>> quotedRegions = new ArrayList<>();
        for (Pair<Integer, Integer> next = identifyNextQuotedRegion(str, 0); next != null; ) {
          quotedRegions.add(next);
          next = identifyNextQuotedRegion(str, next.getSecond() + 1);
        }
        // Now we've got a List of index pairs identifying the quoted regions.
        // We need to get substrings of quoted and unquoted blocks, whilst maintaining order.
        List<Substring> substrings = getSubstrings(str, quotedRegions);
        for (Substring r : substrings) {
          tokens.addAll(r.getTokens());
        }
      }
    }
    return this.tokens;
  }

  /**
   * The substrings will be a list of quoted and unquoted substrings. The unquoted ones need to be
   * further tokenized in the normal way. The quoted ones must not be tokenized, but need their
   * quotes stripped off.
   */
  private List<Substring> getSubstrings(
      String str, List<Pair<Integer, Integer>> quotedRegionIndices) {
    List<Substring> result = new ArrayList<>();
    int cursorPosition = 0;
    for (Pair<Integer, Integer> __ : quotedRegionIndices) {
      // ....
    }
    if (cursorPosition < str.length() - 1) {
      // ...
    }
    return result;
  }

  private Pair<Integer, Integer> identifyNextQuotedRegion(String str, int startingIndex) {
    int indexOfNextSingleQuote = str.indexOf('\'', startingIndex);
    int indexOfNextDoubleQuote = str.indexOf('"', startingIndex);
    // ....
    int indexOfNextQuote = Math.max(indexOfNextSingleQuote, indexOfNextDoubleQuote);
    char quoteChar = str.charAt(indexOfNextQuote);
    return findIndexOfClosingQuote(str, indexOfNextQuote, quoteChar);
  }

  private Pair<Integer, Integer> findIndexOfClosingQuote(
      String str, int indexOfStartingQuote, char quoteChar) {
    // So we know which type of quote char we're dealing with. Either ' or ".
    // Now we need to find the closing quote.
    int indexAfterClosingQuote = str.indexOf(quoteChar, indexOfStartingQuote + 1) + 1;
    return new Pair<>(indexOfStartingQuote, indexAfterClosingQuote);
  }

  public interface Substring {
    List<String> getTokens();
  }
}
