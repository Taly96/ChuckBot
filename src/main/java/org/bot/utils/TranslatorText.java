package org.bot.utils;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.Locale;

import static org.bot.utils.Consts.*;

public class TranslatorText {

    private final OkHttpClient client = new OkHttpClient();

    public String Post(String toTranslate, String targetLanguage) throws IOException {
        String translatedText = INVALID_INPUT;
        String languageCode = findLanguageCode(targetLanguage);

        if(null != languageCode){
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(
                    "[{\"Text\": \"" + toTranslate + "\"}]",
                    mediaType);
            Request request = new Request.Builder()
                    .url(AZ_URL + "&from=en&to=" + languageCode.toLowerCase())
                    .post(body)
                    .addHeader("Ocp-Apim-Subscription-Key", AZ_KEY)
                    // location required if you're using a multi-service or regional (not global) resource.
                    .addHeader("Ocp-Apim-Subscription-Region", AZ_LOCATION)
                    .addHeader("Content-type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful() && null != response.body()){
                translatedText = extractTranslatedText(response.body().string());
            }
        }

        return translatedText;
    }

    private String extractTranslatedText(String jsonString) {
        String translatedText = " ";

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode translationNode = rootNode.get(0).get("translations").get(0);
            translatedText = translationNode.get("text").asText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return translatedText;
    }

    private String findLanguageCode(String targetLanguageName) {
        Locale[] availableLocales = Locale.getAvailableLocales();

        for (Locale locale : availableLocales) {

            if (locale.getDisplayLanguage().equalsIgnoreCase(targetLanguageName)) {
                return locale.getLanguage();
            }
        }

        return null;
    }

}