package org.bot.translator;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.bot.utils.Consts.*;

public class TextTranslator {

    private final OkHttpClient client = new OkHttpClient();

    public String Post(String toTranslate, String targetLanguageCode) throws IOException {
        String translatedText = NO_TRANSLATION;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(
                "[{\"Text\": \"" + toTranslate + "\"}]",
                mediaType);
        Request request = new Request.Builder()
                .url(AZ_URL + "&from=en&to=" + targetLanguageCode.toLowerCase())
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", AZ_KEY)
                .addHeader("Ocp-Apim-Subscription-Region", AZ_LOCATION)
                .addHeader("Content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        if(response.isSuccessful() && null != response.body()){
            translatedText = extractTranslatedText(response.body().string());
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



}