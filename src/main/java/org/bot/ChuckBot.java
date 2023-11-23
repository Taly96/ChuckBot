package org.bot;

import org.bot.utils.TranslatorText;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static org.bot.utils.Consts.*;


public class ChuckBot extends TelegramLongPollingBot {

    private final TranslatorText translator = new TranslatorText();

    private long userID = -1;
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
        String selectedLang = "";
        String[] splitMessage = msgFromUser.split(" ");
        String msgToUser = INVALID_INPUT;

        if(splitMessage.length == 3 &&
                splitMessage[0].equalsIgnoreCase("set") &&
                splitMessage[1].equalsIgnoreCase("language")
        ){
            selectedLang = splitMessage[2];
            msgToUser  = translator.Post(NO_PROBLEM, selectedLang);
        }

        sendText(userID, msgToUser);
    }

    public void sendText(Long who, String what) throws TelegramApiException {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();

        execute(sm);
    }
}
