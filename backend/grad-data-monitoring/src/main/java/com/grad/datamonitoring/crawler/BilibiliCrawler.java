package com.grad.datamonitoring.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BilibiliCrawler {
  public List<String> crawlData(String url, String selector) {
    List<String> results = new ArrayList<>();
    try {
      Document document = Jsoup.connect(url).get();
      Elements elements = document.select(selector);
      for (Element element : elements) {
        results.add(element.text());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }
}