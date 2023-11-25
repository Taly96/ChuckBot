package org.bot;

import org.bot.scraper.ChuckNorrisJokesScraper;
import org.bot.translator.TextTranslator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static org.bot.utils.Utils.*;

import java.io.IOException;

import static org.bot.utils.Consts.*;


public class ChuckBot extends TelegramLongPollingBot {

    private final TextTranslator translator = new TextTranslator();

    private final ChuckNorrisJokesScraper scraper = new ChuckNorrisJokesScraper();

    private long userID = -1;

    private String langCode = null;

    @Override
    public String getBotUsername() {

        return TEL_USER;
    }

    @Override
    public String getBotToken() {

        return TEL_KEY; // todo - delete before push
    }

    @Override
    public void onUpdateReceived(Update update) {
        String msgFromUser = update.getMessage().getText();
        userID = update.getMessage().getFrom().getId();

        if(null != msgFromUser && !msgFromUser.isEmpty()){

            try {
                handleUpdate(msgFromUser);
            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleUpdate(String msgFromUser) throws IOException, TelegramApiException {
        String msgToUser = "";

        if(msgFromUser.toLowerCase().startsWith(SET_LANGUAGE)){
                msgToUser = setLanguage(msgFromUser);
        }
        else if (isNumeric(msgFromUser)){
            if(null != langCode){
                int jokeNumber = Integer.parseInt(msgFromUser);
                if(isValidJokeNumber(jokeNumber)){

                    msgToUser = getChuckNorrisJoke(jokeNumber, langCode);
                }
                else{
                    msgToUser = INVALID_INPUT_NUMBER;
                }
            }
            else{
                msgToUser = SET_LANGUAGE_FIRST;
            }

        }else{
            if(null == langCode){
                msgToUser = SET_LANGUAGE_FIRST;
            }
            else{
                msgToUser = INVALID_INPUT_NUMBER;
            }
        }

        sendText(userID, msgToUser);
    }

    private boolean isNumeric(String msgFromUser){
        boolean isNumeric = true;

        try {
            Integer.parseInt(msgFromUser);
        } catch (NumberFormatException e) {
            isNumeric = false;
        }

        return isNumeric;
    }

    private String getChuckNorrisJoke(int jokeNumber, String langCode) throws IOException {

        return translator.Post(scraper.getJokeByNumber(jokeNumber), langCode);
    }

    private boolean isValidJokeNumber(int jokeNumber) {

        return jokeNumber >= MIN_JOKE_NUM && jokeNumber <= MAX_JOKE_NUM;
    }

    private String setLanguage(String msgFromUser) throws IOException {
        String selectedLang = msgFromUser.toLowerCase().substring(SET_LANGUAGE.length()).trim();
        String translatedText = INVALID_INPUT_LANGUAGE;
        langCode = findLanguageCode(selectedLang);

        if(null != langCode) {
            translatedText = translator.Post(NO_PROBLEM, langCode);
        }

        return translatedText;
    }

    public void sendText(Long who, String what) throws TelegramApiException {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();

        execute(sm);
    }


}
