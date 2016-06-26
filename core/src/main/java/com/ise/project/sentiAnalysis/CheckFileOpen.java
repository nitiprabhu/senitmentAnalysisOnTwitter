//package com.ise.project.sentiAnalysis;
//
//import com.swabunga.spell.event.SpellChecker;
//import jdk.nashorn.internal.parser.Token;
//import org.apache.solr.spelling.SpellCheckCorrection;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//
///**
// * Created by India on 09-May-16.
// */
//class CheckFileOpen {
//    public void m() throws IOException {
//
//
////
////        BufferedWriter bufferedWriter = new BufferedWriter((new InputStreamReader(this.getClass().getResourceAsStream("/"+"3Input"))));
////           BufferedReader bReader = new BufferedReader(new InputStreamReader(
////                    this.getClass().getResourceAsStream("/" + "swach")));
////            while (bReader.readLine()!=null)
////            {
////                System.out.println(bReader.readLine()+ "--->");
////
////            }
////
// int a;
//
//     }
//
//    public static void main(String args[]) throws IOException {
//        CheckFileOpen checkFileOpen = new CheckFileOpen();
//        checkFileOpen.a();
//    }
//
//    public void a() {
//        String s=null;
//        int count=0;
//        String cat = "sentiWordMap.put(\"";
//      try {
//          FileReader fileReader = new FileReader("C:\\Users\\India\\Desktop\\twitter-analysis-mastespringboot\\core\\src\\main\\resources\\startUpIndia");
//          FileWriter fileWriter = new FileWriter("C:\\Users\\India\\Desktop\\twitter-analysis-mastespringboot\\core\\src\\main\\resources\\swach2");
//
//
//          BufferedReader bufferedReader = new BufferedReader(fileReader);
//          while ((s=bufferedReader.readLine())!=null)
//          {
//
//              SpellChecker spellChecker = new SpellChecker();
//              String spellingResult = spellChecker.checkString("hais");
//              System.out.println(spellingResult);
//              ognl.Token token
//              SpellCheckCorrection spellCheckCorrection = new SpellCheckCorrection();
////              spellCheckCorrection.setOriginal(tokenizer);
//
//
////              SpellChecker spellChecker = new SpellChecker();
////              WordTokenizer wordTokenizer = s;
////              System.out.println(spellChecker.checkString("helllo"));
////              System.out.println( spellChecker.checkString("happppy"));
//
//
//
//
////              spellChecker.checkSpelling()
////              System.out.println("Buffered "  + s + "Count : " + count);
//              count = count + 1;
//              String data = count + s + "\n";
//              fileWriter.write(data);
//          }
//
//
//      }
//
//
//
//      catch (Exception e)
//      {
//          System.out.println("error " +e.getMessage());
//      }
//    }
//}
