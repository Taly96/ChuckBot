package bot.scraper;

import bot.utils.Consts;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;

public class ChuckNorrisJokesScraper {

    public String getJokeByNumber(int jokeNumber){
        String joke = Consts.NO_JOKE;

        try {
            WebClient webClient = new WebClient();

            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.addRequestHeader("User-Agent", Consts.USER_AGENT);

            HtmlPage htmlPage = webClient.getPage(Consts.JOKES_URL);
            HtmlElement jokesContainer = htmlPage.getBody().getFirstByXPath(Consts.JOKES_OL_XPATH);
            List<HtmlElement> jokes = jokesContainer.getByXPath(".//li");

            if (!jokes.isEmpty()) {
                int i = 1;

                for (HtmlElement jokeInList : jokes) {
                    i++;

                    if(i > jokeNumber){
                        joke = jokeNumber + ". " + jokeInList.getTextContent().trim();
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




