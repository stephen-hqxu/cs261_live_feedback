package cs261_project.data_structure;

import cs261_project.DatabaseConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class utilityTest {



    @Test
    @DisplayName("Test should run")
    void shouldRunTest(){
        Assertions.assertEquals("1","1");
    }

    @ParameterizedTest
    @DisplayName("Sentiment analyzer should return a score of 5 for neutral statements")
    @ValueSource(strings = {
            "This is ok.",
            "It's alright.",
            "I like it.",
            "I would come again."
    })
    void shouldAnalyseNeutralStatements(String statement){
        int result = SentimentAnalyzer.getSentimentType(statement);
        Assertions.assertEquals(5,result);
    }


    @ParameterizedTest
    @DisplayName("Sentiment analyzer should return a score of 3-5 for negative statements")
    @ValueSource(strings = {
            "I hate it.",
            "This is a waste of time.",
            "I would not recommend it.",
            "This workshop is boring."
    })
    void shouldAnalyseNegativeStatements(String statement){
        int score = SentimentAnalyzer.getSentimentType(statement);
        boolean result = false;
        if (score <= 5 && score >= 3){
            result = true;
        }
        Assertions.assertTrue(result);
    }


    @ParameterizedTest
    @DisplayName("Sentiment analyzer should return a score of 5-7 for positive statements")
    @ValueSource(strings = {
            "I love it.",
            "This workshop was very useful.",
            "I would recommend it to others",
            "This event is interesting."
    })
    void shouldAnalysePositiveStatements(String statement){
        int score = SentimentAnalyzer.getSentimentType(statement);
        boolean result = false;
        if (score <= 7 && score >= 5){
            result = true;
        }
        Assertions.assertTrue(result);
    }


    @ParameterizedTest
    @DisplayName("Sentiment analyzer should return a score of 1-3 for very negative statements")
    @ValueSource(strings = {
            "This is the worst event I have ever attended, It was so boring!",
            "I almost died of boredom.",
    })
    void shouldAnalyseVeryNegativeStatements(String statement){
        int score = SentimentAnalyzer.getSentimentType(statement);
        boolean result = false;
        if (score <= 3 && score >= 1){
            result = true;
        }
        Assertions.assertTrue(result);
    }


    @Test
    void test(){
        int r = SentimentAnalyzer.getSentimentType("This is the worst event I have ever attended, It was so boring!");
        System.out.println(r);
        Assertions.assertTrue(true);
    }


}
