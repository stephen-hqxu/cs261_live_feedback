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
    @DisplayName("Sentiment analyzer should return a score less than 5 but at least 3 for negative statements")
    @ValueSource(strings = {
            "I hate it.",
            "This is a waste of time.",
            "I would not recommend it.",
            "This workshop is boring."
    })
    void shouldAnalyseNegativeStatements(String statement){
        int score = SentimentAnalyzer.getSentimentType(statement);
        boolean result = false;
        if (score < 5 && score >= 3){
            result = true;
        }
        Assertions.assertTrue(result);
    }


    @ParameterizedTest
    @DisplayName("Sentiment analyzer should return a score greater than 5 but at most 7 for positive statements")
    @ValueSource(strings = {
            "I love it.",
            "This workshop was very helpful.",
            "I would highly recommend it to others",
            "This event is interesting."
    })
    void shouldAnalysePositiveStatements(String statement){
        int score = SentimentAnalyzer.getSentimentType(statement);
        boolean result = false;
        if (score <= 7 && score > 5){
            result = true;
        }
        Assertions.assertTrue(result);
    }


    @ParameterizedTest
    @DisplayName("Sentiment analyzer should return a score less than 3 but at least 1 for very negative statements")
    @ValueSource(strings = {
            "This is the worst event I have ever attended, It was so boring!",
            "I absolutely hate this meeting, worst organisation ever, what a joke.",
            "Stupid.",
            "Terrible!"
    })
    void shouldAnalyseVeryNegativeStatements(String statement){
        int score = SentimentAnalyzer.getSentimentType(statement);
        boolean result = false;
        if (score < 3 && score >= 1){
            result = true;
        }
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @DisplayName("Sentiment analyzer should return a score greater than 7 but at most 10 for very positive statements")
    @ValueSource(strings = {
            "Wonderful, absolutely enjoyed it.",
            "The video was amazing, the presentation was exceptionally good!",
            "What an amazing event!",
            "I absolutely enjoyed this event, everything was perfect."

    })
    void shouldAnalyseVeryPositiveStatements(String statement){
        int score = SentimentAnalyzer.getSentimentType(statement);
        boolean result = false;
        if (score <= 10 && score > 7){
            result = true;
        }
        Assertions.assertTrue(result);
    }

}
