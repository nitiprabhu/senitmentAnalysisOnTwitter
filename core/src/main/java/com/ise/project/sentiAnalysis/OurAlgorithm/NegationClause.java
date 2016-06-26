package com.ise.project.sentiAnalysis.OurAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vishwa on 4/6/2016.
 */
public class NegationClause {
   public static Map<String,Integer> negativeClauseMap;
    public static void generateNegativeClause()
    {
        negativeClauseMap = new HashMap<>();
        negativeClauseMap.put("not",-1);
        negativeClauseMap.put("never",-2);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cnt",-3);
        negativeClauseMap.put("can't",-3);
        negativeClauseMap.put("nt",-1);
        negativeClauseMap.put("wont",-3);
        negativeClauseMap.put("willnot",-3);
        negativeClauseMap.put("won't",-3);
        negativeClauseMap.put("didn’t ",-2);
        negativeClauseMap.put("shouldnot",-3);
        negativeClauseMap.put("shoudnt",-3);
        negativeClauseMap.put("shdnt",-3);
        negativeClauseMap.put("shoudnt",-3);
        negativeClauseMap.put("shdn't",-3);
        negativeClauseMap.put("shldnt",-3);
        negativeClauseMap.put("shldn't",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
        negativeClauseMap.put("cannot",-3);
    }
    public static boolean isNegativeClause(String negative)
    {        generateNegativeClause();
        return negativeClauseMap.containsKey(negative);
    }
}
