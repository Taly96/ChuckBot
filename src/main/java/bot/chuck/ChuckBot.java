package bot.chuck;

import bot.scraper.ChuckNorrisJokesScraper;
import bot.translator.TextTranslator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static bot.utils.Utils.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static bot.utils.Consts.*;


public class ChuckBot extends TelegramLongPollingBot {

    private ConcurrentHashMap<Long, String> activeUsers = new ConcurrentHashMap<>();

    @Override
    public String getBotUsername() {

        return TEL_USER;
    }

    @Override
    public String getBotToken() {

        return TEL_KEY;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            Message msgFromUser = update.getMessage();

            try {
                handleMessage(msgFromUser);
            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                sendText(update.getChatMember().getFrom().getId(), TRY_ME);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(Message msgFromUser) throws IOException, TelegramApiException {
        String msgToUser = "";
        String textFromUser = msgFromUser.getText();
        long userID = msgFromUser.getFrom().getId();

        if(textFromUser.toLowerCase().startsWith(START)){
            activeUsers.put(userID, NULL);
            msgToUser = TRY_ME;
        }
        else if(textFromUser.toLowerCase().startsWith(SET_LANGUAGE)){
            msgToUser = setLanguage(userID, textFromUser);
        }
        else if (isNumeric(textFromUser)){
            msgToUser = getJokeByNumber(userID, textFromUser);
        }
        else{
            msgToUser = handleWrongInput(userID);
        }
        sendText(userID, msgToUser);
    }

    private String handleWrongInput(long userID) {
        String msgToUser = "";
        String langCode = activeUsers.get(userID);

        if(langCode.equals(NULL)){
            msgToUser = SET_LANGUAGE_FIRST;
        }
        else{
            msgToUser = INVALID_INPUT_NUMBER;
        }

        return msgToUser;
    }

    private String getJokeByNumber(long userID, String textFromUser) throws IOException {
        String msgToUser;
        String langCode = activeUsers.get(userID);

        if(!langCode.equals(NULL)){
            int jokeNumber = Integer.parseInt(textFromUser);

            if(isValidJokeNumber(jokeNumber)){
                ChuckNorrisJokesScraper scraper = new ChuckNorrisJokesScraper();
                TextTranslator translator = new TextTranslator();

                msgToUser = translator.Post(scraper.getJokeByNumber(jokeNumber), langCode);
            }
            else{
                msgToUser = INVALID_INPUT_NUMBER;
            }
        }
        else{
            msgToUser = SET_LANGUAGE_FIRST;
        }
        return msgToUser;
    }

    private String setLanguage(long userID, String textFromUser) throws IOException {
        String msgToUser;
        String langCode = getLangCode(textFromUser);

        if(null != langCode && !langCode.isEmpty()){
            TextTranslator translator = new TextTranslator();

            activeUsers.put(userID, langCode);
            msgToUser = translator.Post(NO_PROBLEM, langCode);
        }
        else{
            msgToUser = INVALID_INPUT_LANGUAGE;
        }

        return msgToUser;
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

    private boolean isValidJokeNumber(int jokeNumber) {

        return jokeNumber >= MIN_JOKE_NUM && jokeNumber <= MAX_JOKE_NUM;
    }

    private String getLangCode(String msgFromUser) {
        String selectedLang = msgFromUser.toLowerCase().substring(SET_LANGUAGE.length()).trim();

        return findLanguageCode(selectedLang);
    }

    public synchronized void sendText(Long who, String what) throws TelegramApiException {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();

        execute(sm);
    }
}
