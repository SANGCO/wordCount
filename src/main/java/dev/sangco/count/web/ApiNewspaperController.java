package dev.sangco.count.web;

import dev.sangco.count.domain.Article;
import dev.sangco.count.domain.ArticleRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/news")
public class ApiNewspaperController {

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/test")
    public ResponseEntity test() throws Exception {
        Document doc;

        // need http protocol
        doc = Jsoup.connect("http://search.chosun.com/search/news.search?query=" + "사회적기업" + "&pageno=1&orderby=news&categoryname=" + "조선일보" + "").get();

        // get page title
//        System.out.println("title : " + doc.title());

        // get page body
//        System.out.println("body : " + doc.body());

        // get all search_news
        Elements elements = doc.getElementsByClass("search_news");
        for (Element element : elements) {

            System.out.println("attr " + element.childNode(3).childNode(0).attr("href").toString());
//            System.out.println("toString : " + elements1.toString());
            articleRepository.save(new Article(element.childNode(3).childNode(0).attr("href").toString(),
                    Jsoup.connect(element.childNode(3).childNode(0).attr("href").toString()).get().getElementsByClass("par").text()));
        }

        return new ResponseEntity(OK);
    }
}
