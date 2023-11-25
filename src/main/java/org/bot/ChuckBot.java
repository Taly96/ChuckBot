package org.bot;

import org.bot.utils.TextTranslator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static org.bot.utils.Utils.*;

import java.io.IOException;

import static org.bot.utils.Consts.*;


public class ChuckBot extends TelegramLongPollingBot {

    private final TextTranslator translator = new TextTranslator();

    private long userID = -1;

    private String langCode = null;

    private boolean isLanguageSelected = false;

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
            System.out.println(update); // todo: delete
            try {
                handleUpdate(msgFromUser);
            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleUpdate(String msgFromUser) throws IOException, TelegramApiException {
        String msgToUser = "";

        if (!isLanguageSelected){
            if(msgFromUser.toLowerCase().startsWith("set language")){
                msgToUser = setLanguage(msgFromUser);
            }
            else{
                msgToUser = INVALID_INPUT;
            }
        }else{
            if(isValidJokeNumber(msgFromUser)){
                int jokeNumber = Integer.parseInt(msgFromUser);
                msgToUser = getChuckNorrisJoke(jokeNumber, langCode);
            }
            msgToUser = INVALID_INPUT_NUMBER;
        }

        sendText(userID, msgToUser);
    }

    private String getChuckNorrisJoke(int jokeNumber, String langCode) {

        return null; //todo
    }

    private boolean isValidJokeNumber(String msgFromUser) {
        boolean isValidJokeNumber = false;

        try {
            int jokeNumber = Integer.parseInt(msgFromUser);
            isValidJokeNumber =  jokeNumber >= MIN_JOKE_NUM && jokeNumber <= MAX_JOKE_NUM;
        } catch (NumberFormatException e) {
            isValidJokeNumber = false;
        }

        return isValidJokeNumber;
    }

    private String setLanguage(String msgFromUser) throws IOException {
        String selectedLang = msgFromUser.toLowerCase().substring("set language".length()).trim();
        String translatedText = INVALID_INPUT;
        langCode = findLanguageCode(selectedLang);

        if(null != langCode) {
            translatedText = translator.Post(NO_PROBLEM, langCode);
            isLanguageSelected = true;
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
