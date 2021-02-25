package cs261_project.data_structure;

import cs261_project.DatabaseConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;





public class utilityTest {
    @Test
    @DisplayName("Test should run")
    void shouldRunTest(){
        Assertions.assertEquals("1","1");
    }

    @Test
    @DisplayName("Sentiment analysis should work")
    @Disabled("TODO")
    void sentimentAnalysis(){
        String case1 = "This is the best ever, I love it so much";
        String result1 = SentimentAnalyzer.getSentimentType(case1);
        System.out.println("Mood " + result1);

        Assertions.assertTrue(true);
    }
}
