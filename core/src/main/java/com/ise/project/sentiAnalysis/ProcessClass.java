package com.ise.project.sentiAnalysis;

import com.ise.project.sentiAnalysis.OurAlgorithm.Preprocessing;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by India on 09-May-16.
 */
public class ProcessClass {
    @Autowired
    Preprocessing preprocessing;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(ProcessClass.class);

    public List<Integer> getOurAlgoResult(String fileName)
    {
        String tweet;
        Integer temp=0, neg=0, neu=0, pos=0;
        List<Integer> list = new ArrayList();
        Optional<String> optional ;

            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(
                        this.getClass().getResourceAsStream("/" + fileName)));
                while ((tweet=bReader.readLine() )!= null) {
                    optional = Optional.ofNullable(tweet);
                    temp = preprocessing.ourAlgorithm(optional.get());
                    logger.info("{} ---> {}", optional.orElse("Null is here"), temp);
                    switch ((int) Math.signum(temp)) {
                        case -1:
                            neg++;
                            break;
                        case 0:
                            neu++;
                            break;
                        case 1:
                            pos++;
                            break;
                    }
                }

        }catch (Exception e)
        {
            logger.error("Error to open file {}, {}",e.getMessage(),e);
        }


        list.add(neg);
        list.add(neu);
        list.add(pos);
        return list;
    }

}
