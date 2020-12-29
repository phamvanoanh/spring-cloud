package com.edu.springbootapachecamal;


import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FtpDownloadFileFilter implements GenericFileFilter<Object> {

    private static Logger logger = LoggerFactory.getLogger(FtpDownloadFileFilter.class);

    @Value("${ftp.local.data.dir}")
    private String localDir;

    /**
     * Filter download files
     * @author sunk
     */
    @Override
    public boolean accept(GenericFile<Object> file) {

        try {
            String fileName = file.getFileName();
            long lastModified = file.getLastModified();

            return isLatestFile(lastModified, 3) && !isInLocalDir(fileName) ? true : false;
        } catch (Exception e) {
            logger.error("ftp download file filter error !", e);
            return false;
        }
    }

    /**
     * Is the file within the last few days
     * <p> Used to filter some historical data
     * @author sunk
     */
    private boolean isLatestFile(long lastModified, int dateNum) {

        Calendar calLastMod = Calendar.getInstance();
        calLastMod.setTimeInMillis(lastModified);

        Calendar calThreshold = Calendar.getInstance();
        calThreshold.add(Calendar.DATE, - dateNum);

        return calLastMod.compareTo(calThreshold) > 0 ? true : false;
    }

    /**
     * Whether the file is already in the local directory
     * @author sunk
     */
    private boolean isInLocalDir(String fileName) {
        try {
            //Get the downloaded file name in the local folder
            List<String> localFileNames = Files.walk(Paths.get(localDir))
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            //logger.info("local downloaded files : " + Arrays.toString(localFileNames.toArray()));

            return localFileNames.contains(fileName) ? true : false;
        } catch (Exception e) {
            logger.error("get local downloaded files fail !", e);
            return true;
        }
    }
}
