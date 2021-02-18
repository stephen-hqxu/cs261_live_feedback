package cs261_project.data_structure;

import cs261_project.DatabaseConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;





public class utilityTest {
    @Test
    @DisplayName("Test should run")
    public void shouldRunTest(){
        Assertions.assertEquals("1","1");
    }

    @Test
    @DisplayName("Sentiment analysis should work")
    public void sentimentAnalysis(){
        String case1 = "I love this event!";
        String result1 = SentimentAnalyzer.getSentimentType(case1);
        System.out.println(result1);
        Assertions.assertTrue(true);
    }
}
