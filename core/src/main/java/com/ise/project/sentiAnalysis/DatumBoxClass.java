package com.ise.project.sentiAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by India on 09-May-16.
 */
class DatumBoxClass {
    Logger logger = LoggerFactory.getLogger(DatumBoxClass.class);
//    Processor processor = new Processor();

    public List<Integer> getOfflineDatumResult(String fileName) throws IOException {
        List<Integer> list = new ArrayList<>();
        Integer positive=0, neutral=0 ,negative=0, temp=0;
        Optional<String> data ;
//        Scanner scanner = null;
        logger.warn("File Name {}",fileName);
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/" + fileName)));

                    while (bReader.readLine()!=null){
                        data = Optional.of(bReader.readLine());
                        data = Optional.of(Application.processor.sanitizeText(data.get()));
                        logger.debug("Tweet --- {}",data);

                        temp = Application.processor.getDatumBoxValuation(data.get());
                    //    logger.info("{} ---> {}", data.get(), temp);

                    switch (temp) {
                        case -1:
                            negative++;
                            break;
                        case 0:
                            neutral++;
                            break;
                        case +1:
                            positive++;
                            break;
                    }
                }
            } catch (IOException e) {
            logger.error("Error to open file");

        } finally {
            list.add(negative);
            list.add(neutral);
            list.add(positive);
//            scanner.close();
        }

        return list;
    }
  }

