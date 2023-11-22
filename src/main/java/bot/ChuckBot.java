package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChuckBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return ""; // todo - delete before push
    }

    @Override
    public void onUpdateReceived(Update update) {

        System.out.println(update);
    }
}
