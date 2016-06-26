package com.ise.project.sentiAnalysis.OurAlgorithm;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vishwa on 4/5/2016.thod jsd;fl
 * @author  vishwa
 * @param  String
 * @return lk;lkf
 * This me
 */

@Service
public class Preprocessing {



    private static Logger logger = LoggerFactory.getLogger(Preprocessing.class);

    public static Integer ourAlgorithm(String s) throws  Exception {
       PositiveWordsArray positiveWordsArray= new PositiveWordsArray();
        List<String> finalList = new ArrayList<>(); Integer count=0;
        SentiWordList.generateSentiWord(); // generate senti score
        List<String> list = new ArrayList<>();

        list = Splitter.on(CharMatcher.anyOf("  ?@#:!.,\"")).omitEmptyStrings().splitToList(s);

        finalList = list.stream()
                        .map(lists->lists.toLowerCase())
                        .filter(Preprocessing::isConfirm)
                        .collect(Collectors.toList());


        finalList=   finalList.stream()
                              .filter(w->StopWords.isStopWord(w))
                              .collect(Collectors.toList());

        logger.info("---------------------------------------- {}",++count);
        logger.info( "Filtered list: {} ",String.valueOf(finalList));
        logger.info("------------------------------------------");

        return SentiWordList.generateValue(finalList);

   }
        public static boolean isConfirm(String s)
        {

            PositiveWordsArray positiveWordsArray=new PositiveWordsArray();
            NegationWords negationWords= new NegationWords();
            NegativeWordArray negativeWordArray= new NegativeWordArray();
            return PositiveWordsArray.isPositive(s) || NegativeWordArray.isNegative(s) || NegationWords.isNegation(s)|| NegationClause.isNegativeClause(s);
        }

    }



