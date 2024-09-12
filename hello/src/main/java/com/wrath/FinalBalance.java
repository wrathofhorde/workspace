package com.wrath;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FinalBalance {
  private String balance;
  private final String url = "https://btc1.trezor.io/xpub/"
    + "zpub6qedu3AoJCAsvojZsSHzmn5kZ7zsCQSeJHHFxTEz1pkJ4sk"
    + "PKBmYbBcsZo6D7jD45CpVKEADspFzJcqruif8P6GWrNvAx7vfLpcYJ9JaoQn";
  private final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " 
      + "AppleWebKit/537.36 (KHTML, like Gecko) "
      + "Chrome/128.0.0.0 Safari/537.36";

  public FinalBalance() {
    balance = "error";
  }

  public String get() {
    try {
      Document doc = Jsoup.connect(url).userAgent(userAgent).get();
      var trs = doc.select("tr");
      
      for (var tr : trs) {
        var tds = tr.getAllElements();
        // System.out.println(tds);
        // System.out.println(tds.size());
        // System.out.println(tds.get(0).text());

        var texts = tds.get(0).text().split(" ");
        if (tds.size() == 11 && texts[0].equals("Final") && texts[1].equals("Balance")) {
            balance = texts[2];
            break;
        }
      }
    } catch (Exception e) {
      balance = "error";
      System.out.println(e);
    }
    
    return balance;
  }
}
