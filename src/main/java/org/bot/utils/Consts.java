package org.bot.utils;

public class Consts {

    public static final int MIN_JOKE_NUM = 1;

    public static final String JOKES_URL = "https://parade.com/968666/parade/chuck-norris-jokes";

    public static final String JOKES_OL_XPATH = "/html/body/phoenix-page/div[1]/div/div[4]/section[2]/article/div/div[2]/section/div[1]/div[3]/ol";

    public static final String USER_AGENT ="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36";

    public static final int MAX_JOKE_NUM = 101;

    public static final String NO_PROBLEM = "No problem";

    public static final String SET_LANGUAGE = "set language";

    public static final String TEL_USER = System.getenv("TEL_USER");

    public static final String TEL_KEY = System.getenv("TEL_KEY");

    public static final String AZ_KEY = System.getenv("AZ_KEY");

    public static final String AZ_LOCATION = System.getenv("AZ_LOCATION");

    public static final String AZ_URL = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0";

    public static final String NO_JOKE = "When Chuck Norris jokes are elusive, it means Chuck is too busy fighting off ninjas." +
            System.lineSeparator() +"Check back later, and he might share his ninja-defying humor with us.";

    public static final String INVALID_INPUT_LANGUAGE = "Chuck Norris never gets invalid input." +
            System.lineSeparator() + "Please roundhouse kick it into proper format." +
            System.lineSeparator() +  "For example: Set language english";

    public static final String NO_TRANSLATION = "When Chuck Norris tried to use the online translator," +
            System.lineSeparator() + " the translator apologized and asked Chuck Norris to teach it the language instead." +
            System.lineSeparator() + "While the translator is learning, try a different language";

    public static final String SET_LANGUAGE_FIRST = "Chuck Norris speaks all languages, which is why you should pick one first." +
            System.lineSeparator() +  "For example: Set language english";

    public static final String INVALID_INPUT_NUMBER = "Chuck Norris never gets invalid input," +
            System.lineSeparator() + "your keyboard should apologize." +
            System.lineSeparator() + "Or try typing a valid number between 1 - 101";


}
