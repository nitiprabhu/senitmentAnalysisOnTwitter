package com.ise.project.sentiAnalysis;


import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ise.project.sentiAnalysis.OurAlgorithm.Preprocessing;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//import com.ise.project.sentiAnalysis.OurAlgorithm.Model.ListInfo;

/**
 * Created by nitish 01/03/2016.
 */
@Service
public class Processor {
    private static final Logger logger = LoggerFactory.getLogger(Processor.class);
    public static String URL_REGEX = "(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.," +
            "@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
    private static int TWEETS_QUANTITY = 10;
    SendRequest sendRequest;



    @Autowired
    public Preprocessing preprocessing;

    private String DATUMBOX_API_KEY;
    private String DATUMBOX_API_ENDPOINT;
    private String TWITTER_APP_ID;
    private String TWITTER_APP_SECRET;
    private String TWITTER_ACCESS_TOKEN;
    private String TWITTER_ACCESS_TOKEN_SECRET;
    @SuppressWarnings("unchecked")
    public List<String> list = new ArrayList();
    public static List<String> controllerList;
    public static List<Integer> datumResultList;
    public static List<Integer> algoResultList;
    static int count = 0,tryPass=1, catchPass=1;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    @Qualifier("myProperties")
    private Properties myProps;
    private String[] trends;

    @Bean
    public Processor getProcessor() {
        sendRequest = new SendRequest();
        initProperties();
        return this;
    }

    @Bean(name = "myProperties")
    public Properties getMyProperties() throws IOException {
        return PropertiesLoaderUtils.loadProperties(new ClassPathResource("/application.properties"));
    }

    private void initProperties() {
        this.TWITTER_APP_ID = myProps.getProperty("spring.social.twitter.appId");
        this.TWITTER_APP_SECRET = myProps.getProperty("spring.social.twitter.appSecret");
        this.TWITTER_ACCESS_TOKEN = myProps.getProperty("spring.social.twitter.access.token");
        this.TWITTER_ACCESS_TOKEN_SECRET = myProps.getProperty("spring.social.twitter.access.token.secret");
        this.DATUMBOX_API_KEY = myProps.getProperty("datumbox.api.key");
        this.DATUMBOX_API_ENDPOINT = myProps.getProperty("datumbox.api.endpoint");
    }

    public void analyzeTrends(String trends, Integer TWEETS_QUANTITY) throws Exception {
        System.out.println("Starting to analyze...");
        System.out.println("Asking for " + TWEETS_QUANTITY + " number of tweets per query.");
        int neutrals = 0;
        controllerList = new ArrayList<>();
        datumResultList = new ArrayList<>();
        algoResultList = new ArrayList<>();
        run(trends, TWEETS_QUANTITY);
        //   System.out.println("API performance:ha " + 100 * ((float) neutrals / (float) (TWEETS_QUANTITY)) + "%");
    }

    private void run(String input, Integer TWEETS_QUANTITY) throws Exception {
        Twitter twitter = new TwitterTemplate(TWITTER_APP_ID, TWITTER_APP_SECRET, TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET);
        SearchResults searchResults = twitter.searchOperations().search(input, TWEETS_QUANTITY);
        int result = 0, result2 = 0;
        int negative = 0, negative2 = 0;
        int neutral = 0, neutral2 = 0;
        int positive = 0, positive2 = 0;

        for (Tweet tweet : searchResults.getTweets()) {
            int temp = 0, temp2 = 0;



            try {
                if (tweet != null && tweet.getText() != null) {
                    //  result += getDatumBoxValuation(sanitizeText(tweet.getText()));

                    logger.info("Tweet - " + tweet.getText());
                    controllerList.add(tweet.getText());

//                    try{ Thread.sleep(100);  }
//                    catch (Exception e) {}


                    temp = getDatumBoxValuation(sanitizeText(tweet.getText()));

                    Optional<String> S = Optional.of(tweet.getText());
                    temp2 = Preprocessing.ourAlgorithm(S.or("NULL is here"));

                    switch (temp) {

                        case -1:
                            negative++;
                            break;
                        case 0:
                            neutral++;
                            break;
                        case 1:
                            positive++;
                            break;
                    }
                    //              writer.write(temp + "--->" + s + "\n");
                    System.out.println("1st check completed");
//                    System.out.println(((int)Math.signum(temp2));
                    switch ((int) Math.signum(temp2)) {

                        case -1:
                            negative2++;
                            break;
                        case 0:
                            neutral2++;
                            break;
                        case 1:
                            positive2++;
                            break;
                    }
//                    writer.write(temp2 + "--->" + s + "\n");
                    System.out.println("2nd check completed");
                    result += temp;
                    result2 += temp2;
                    System.out.print(".");
                }
            } catch (Exception e) {
                System.out.println("error = " + e);
            }
        }

        datumResultList.add((negative));
        datumResultList.add(neutral);
        datumResultList.add((positive));
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        algoResultList.add((negative2));
        algoResultList.add((neutral2));
        algoResultList.add(positive2);
        System.out.println();
        System.out.println(input + " = " + result);
        System.out.println("Total analyzed for " + input + " = " + searchResults.getTweets().size());
        System.out.println("Negatives for " + input + " = " + negative);
        System.out.println("Neutrals for " + input + " = " + neutral);
        System.out.println("Positives for " + input + " = " + positive);
        System.out.println("-------------------\n");
        System.out.printf("----------------------\n");
        System.out.println("API performance:OurAlgorithm " + 100 * ((float) neutral2 / (float) (TWEETS_QUANTITY * trends.length)) + "%");
        System.out.println("API performance:Datum " + 100 * ((float) neutral / (float) (TWEETS_QUANTITY * trends.length)) + "%");

//        System.out.println("Total analyzed for " + input + " = " + searchResults.getTweets().size());
        System.out.println("Negatives for " + input + " = " + negative2);
        System.out.println("Neutrals for " + input + " = " + neutral2);
        System.out.println("Positives for " + input + " = " + positive2);


        //     return neutral;
    }//}

    public final static String sanitizeText(String input) {
        return input.replaceAll(URL_REGEX, "");
    }

    /**
     * @param text
     * @return -1 if negative valuation
     * 0 if neutral valuation
     * 1 if positive valuation
     * @throws IOException
     */
    public int getDatumBoxValuation(String text) throws IOException {
        URL url = new URL(DATUMBOX_API_ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();



        int response = 0;

        try {
            String parameters = "api_key=" + DATUMBOX_API_KEY + "&text=" + URLEncoder.encode(text, "UTF-8");

            count = count + 1 ;

//            logger.error("Exception from 1st line {}, {}", n, n.getMessage());
            InputStream inputStream = sendRequest.postURL(connection, url, parameters, DATUMBOX_API_ENDPOINT);

         //   logger.warn("Input Stream {}", inputStream);
            String jsonTxt = IOUtils.toString(inputStream);
            logger.info("Tweet :  {}",text);
            logger.warn("Json text {}", jsonTxt);
            String responseData = parse(jsonTxt).replace("\"", "");
            logger.warn("Count of Entry {}", count);

            //System.out.println("responseData = " + responseData);
            response = 0;
            switch (responseData) {
                case "negative":
                    response = -1;
                    break;
                case "neutral":
                    response = 0;
                    break;
                case "positive":
                    response = 1;
                    break;
            }
        } catch (Exception e) {
            logger.error("Exception parsing {}, {}", e, e.getLocalizedMessage());
        }
        return response;
    }

    private String parse(String jsonLine) throws NullPointerException{

        try {
            JsonElement jelement = new JsonParser().parse(jsonLine);
            JsonObject jobject = jelement.getAsJsonObject();
            jobject = jobject.getAsJsonObject("output");
            logger.warn("Return before {}",tryPass++);
            return jobject.get("result").toString();
        }catch (NullPointerException e)
        {
            logger.error("Null pointer Exception in parse {}, {}",e.getMessage(),e);
        }
         logger.warn("Strings to NullEmpty {}", Strings.nullToEmpty(jsonLine));
        logger.warn("Try after, null passing {}",catchPass++);
         return Strings.nullToEmpty(jsonLine);

    }


    public List<Integer> getOfflineDatumResult(String fileName) throws IOException {
        List<Integer> list = new ArrayList<>();
        Integer positive = 0, neutral = 0, negative = 0, temp = 0;
        String data;
//        Scanner scanner = null;
//        logger.warn("File Name {}", fileName);
        String tweet;
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/" + fileName)));

            while ((tweet=bReader.readLine() )!= null) {
//                data = bReader.readLine();
//                data = sanitizeText(data);
//                logger.debug("Tweet --- {}", data);
//
//                try { Thread.sleep(20000);}
//                catch (Exception e) {}


                temp = getDatumBoxValuation(sanitizeText(tweet));
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
            logger.error("Error {}, {}", e.getMessage(),e);

        }
            list.add(negative);
            list.add(neutral);
            list.add(positive);
            logger.warn("Result in DatumBox {}",list);
            return list;
    }
}
