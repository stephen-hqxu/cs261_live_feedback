package cs261_project.data_structure;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public final class SentimentAnalyzer {
    private static StanfordCoreNLP pipeline;

    //initialised right after the program is started
    static{
        Properties props;
        props = new Properties();
        props.setProperty("annotators","tokenize,ssplit, parse, sentiment");
        SentimentAnalyzer.pipeline = new StanfordCoreNLP(props);
    }

    private SentimentAnalyzer(){
        //prevent from being instantiated
    }

    // Calculates sentiment numerical value from given text
    private static int findSentiment(String text){
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

    /**
     * Returning the text value of the sentiment. This method should be called to work out sentiment
     * @param text The string for analysis
     * @return Return value from 1 to 10 according to the sentiment
     */
    public static byte getSentimentType(String text){
        int sentiment = findSentiment(text);

        //scale the sentiment from [0,4] to the range [1,10]
        return (byte)(sentiment * 2.25 + 1);
    }

}
