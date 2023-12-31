package bot.utils;

import java.util.Locale;

public class Utils {

    public synchronized static String findLanguageCode(String targetLanguageName) {
        Locale[] availableLocales = Locale.getAvailableLocales();

        for (Locale locale : availableLocales) {

            if (locale.getDisplayLanguage().equalsIgnoreCase(targetLanguageName)) {

                return locale.getLanguage();
            }
        }

        return null;
    }
}
