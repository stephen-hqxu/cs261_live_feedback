package cs261_project.data_structure;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;


public class SentimentAnalyzer {

    // Calculates sentiment numerical value from given text
 private static int findSentiment(String text){
     Properties props;
     props = new Properties();
     props.setProperty("annotators","tokenize,ssplit, parse, sentiment");
     StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

     int mainSentiment = 0;

     //Check against null text
     if(text != null && text.length() > 0){

         int longest = 0;
         Annotation annotation = pipeline.process(text);
         for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)){
             Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
             int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
             String partText = sentence.toString();
             if(partText.length() > longest){
                 mainSentiment = sentiment;
                 longest = partText.length();
             }
         }
     }
     return mainSentiment;
 }

 //Returning the text value of the sentiment. This method should be called to work out sentiment
 public static String getSentimentType(String text){

     String sType;
     int sentiment = findSentiment(text);

     switch (sentiment){
         case 0:
             sType = "Very Negative";
             break;

         case 1:
             sType = "Negative";
             break;
         case 2:
             sType = "Neutral";
             break;
         case 3:
             sType="Positive";
             break;
         case 4:
             sType="Very Positive";
             break;
         default:
              sType = "";
     }

     return  sType;
 }

}
