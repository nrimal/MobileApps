package edu.osu.myapplication;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SuggestionFragmentTest extends SuggestionFragment {
    @Test
    public void timeDifference1() {
        String time1 = "0000";
        String time2 = "2400";
        assertTrue(compareTime(time1, time2));
    }

    @Test
    public void timeDifference2() {
        String time1 = "0000";
        String time2 = "0000";
        assertTrue(compareTime(time1, time2));
    }

    @Test
    public void timeDifference3() {
        String time1 = "0410";
        String time2 = "0115";
        assertTrue(!compareTime(time1, time2));
    }

    @Test
    public void timeDifference4() {
        String time1 = "1210";
        String time2 = "0115";
        assertTrue(!compareTime(time1, time2));
    }

    @Test
    public void timeDifference5() {
        String time1 = "1745";
        String time2 = "0000";
        assertTrue(!compareTime(time1, time2));
    }
}