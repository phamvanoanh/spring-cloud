package com.edu;

import org.springframework.integration.core.MessageSource;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.messaging.Message;

import java.io.File;

public class DemoSource implements MessageSource<File> {
    @Override
    public Message<File> receive() {
//        SftpInboundFileSynchronizingMessageSource source = new SftpInboundFileSynchronizingMessageSource();
//        source.setLocalDirectory(new File("/path"));
//        source.setAutoCreateLocalDirectory(true);
        return null;
    }
}
