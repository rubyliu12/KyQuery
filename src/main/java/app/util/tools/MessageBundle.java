package app.util.tools;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.StringUtils;

public class MessageBundle {

    private ResourceBundle messages;

    public MessageBundle() {
        this.messages = ResourceBundle.getBundle("localization/messages", Locale.CHINESE);
    }

    //解决中文乱码问题
    public String get(String message) {
//    return messages.getString(message);
        return StringUtils.newStringUtf8(messages.getString(message).getBytes(Charsets.ISO_8859_1));
    }

    public final String get(final String key, final Object... args) {
        return MessageFormat.format(get(key), args);
    }

}
