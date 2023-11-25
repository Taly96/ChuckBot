package org.bot.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;

import static org.bot.utils.Consts.*;

public class ChuckNorrisJokesScraper {

    public String getJokeByNumber(int jokeNumber){

        String joke = NO_JOKE;

        try {
            WebClient webClient = new WebClient();

            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.addRequestHeader("User-Agent", USER_AGENT);

            HtmlPage htmlPage = webClient.getPage(JOKES_URL);
            HtmlElement jokesContainer = htmlPage.getBody().getFirstByXPath(JOKES_OL_XPATH);
            List<HtmlElement> jokes = jokesContainer.getByXPath(".//li");

            if (!jokes.isEmpty()) {
                int i = 1;

                for (HtmlElement jokeInList : jokes) {
                    String jokeText = jokeInList.getTextContent().trim();
                    System.out.println("Joke " + jokeNumber + ": " + jokeText);//TODO:DELELTE
                    i++;

                    if(i > jokeNumber){
                        joke = jokeInList.getTextContent();
                        break;
                    }
                }
            }

            webClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return joke;
    }

}




