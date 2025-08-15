package dev.danvega.sb3.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.boot.logging.structured.StructuredLogFormatter;

public class MyCustomLogger implements StructuredLogFormatter<ILoggingEvent> {

    @Override
    public String format(ILoggingEvent event) {
        return "time=" + event.getTimeStamp() + " level=" + event.getLevel() + " message=" + event.getMessage() + "\n";
    }

}
