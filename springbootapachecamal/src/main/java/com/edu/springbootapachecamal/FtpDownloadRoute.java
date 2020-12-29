package com.edu.springbootapachecamal;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class FtpDownloadRoute extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(FtpDownloadRoute.class);

    @Value("${ftp.server.uri}")
    private String ftpUri;

    @Value("${ftp.local.data.dir}")
    private String localDir;

    @Value("${host.nodes}")
    private String hostNodes;

    @Value("${ftp.task.start}")
    private boolean isStart;
    private FileProcessor fileProceser;

    @Override
    public void configure() throws Exception {

        if (isStart && isExecHost()) {
            from(ftpUri)
                    .process(fileProceser)
                    .to("file:" + localDir)
                    .log(LoggingLevel.INFO, logger, "download file ${file:name} complete.");


        }
    }

        /**
         * Determine whether it is the host performing the task
         * @author sunk
         */
        private boolean isExecHost () {
            String hostName = "";
            try {
                hostName = InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                logger.error("get hostname fail !", e);
                return false;
            }
            return hostName.endsWith(hostNodes.split(",")[0]);
        }
    }
