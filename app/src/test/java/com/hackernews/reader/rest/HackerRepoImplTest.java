package com.hackernews.reader.rest;

import com.hackernews.reader.rest.model.Comments;
import com.hackernews.reader.rest.model.Story;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sauyee on 29/12/17.
 */
public class HackerRepoImplTest {

    private static final String HACKER_NEWS_STORY_1 = "16000895";
    private static final String HACKER_NEWS_STORY_2 = "16008340";
    private static final String HACKER_NEWS_STORY_3 = "16007633";

    @Mock
    HackerNewsRestInterface hackerNewsRestInterface;
    private HackerRepository hackerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        hackerRepository = new HackerRepoImpl(hackerNewsRestInterface);
    }

    @Test
    public void getHackerTopStories_200OkResponse() {
        when(hackerNewsRestInterface.getHackerTopStories()).thenReturn(Observable.just(hackerTopStories()));

        TestSubscriber<List<String>> subscriber = new TestSubscriber<>();
        hackerRepository.getHackerTopStories().subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<String>> onNextEvents = subscriber.getOnNextEvents();
        List<String> stories = onNextEvents.get(0);

        Assert.assertEquals(HACKER_NEWS_STORY_1, stories.get(0));
        Assert.assertEquals(HACKER_NEWS_STORY_2, stories.get(1));
        Assert.assertEquals(HACKER_NEWS_STORY_3, stories.get(2));

        verify(hackerNewsRestInterface).getHackerTopStories();
    }

    @Test
    public void getHackerSingleStory_200OkResponse() {
        when(hackerNewsRestInterface.getHackerStory(anyString())).thenReturn(Observable.just(hackerStoryDetails()));

        TestSubscriber<Story> subscriber = new TestSubscriber<>();
        hackerRepository.getHackerStory(anyString()).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<Story> onNextEvents = subscriber.getOnNextEvents();
        Story story = onNextEvents.get(0);

        List<String> commentList = story.getKids();
        Assert.assertEquals("maury91", story.getBy());
        Assert.assertEquals("I made a talking emoji using regular emojis and JavaScript", story.getTitle());
        Assert.assertEquals("story", story.getType());
        Assert.assertEquals("16007744", commentList.get(0));
        Assert.assertEquals("16007987", commentList.get(1));

        verify(hackerNewsRestInterface).getHackerStory(anyString());
    }

    @Test
    public void getHackerComment_200OkResponse() {
        when(hackerNewsRestInterface.getHackerComment(anyString())).thenReturn(Observable.just(hackerCommentDetails()));

        TestSubscriber<Comments> subscriber = new TestSubscriber<>();
        hackerRepository.getHackerComment(anyString()).subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<Comments> onNextEvents = subscriber.getOnNextEvents();
        Comments comments = onNextEvents.get(0);

        List<String> commentList = comments.getKids();
        Assert.assertEquals("_nalply", comments.getBy());
        Assert.assertEquals("I&#x27;m Deaf and read lips.<p>Thanks for this extremely intriguing idea to use emoji to depict a talking person visually.<p>You know, some animated films get it right. Sometimes I clearly see utterances like «Thank you!», «Okay», but more often not. Especially older animations don&#x27;t care and just let the character move the mouth in very simplistic ways: «Babbabbabaa Baababba ba baaa.»<p>Similarly for that emoji. It is moving the mouth in quite arbitrary ways. It looks like: «Sobbabbabee be &lt;grin&gt; seebee &lt;grin&gt; &lt;frown&gt; babbaa».<p>To solve this problem we need about twelve emoji for utterances: «ah», «ay», «ee», «oh», «oo», «b», «d», «f», «l», «n», «r» and «s». The «r» emoji must be animated (so we see the trilling tongue). The other sounds are either invisible like «k» or cover several different sounds like «b» also covering «m» and «p».<p>«Okay» would be rendered by: «oh», «oo», «oo» (standing for «k» wich is invisible) «ay» and «ee».<p>Edit: Add «th». This is an extremely simple sound to lipread. I remember my delight when I learnt English in my youth and in an movie suddenly realised that an actor said «Thank you».", comments.getText());
        Assert.assertEquals("comment", comments.getType());
        Assert.assertEquals("16007791", commentList.get(0));
        Assert.assertEquals("16008139", commentList.get(1));

        verify(hackerNewsRestInterface).getHackerComment(anyString());
    }

    private List<String> hackerTopStories() {
        List<String> topStories = new ArrayList<>();
        topStories.add(HACKER_NEWS_STORY_1);
        topStories.add(HACKER_NEWS_STORY_2);
        topStories.add(HACKER_NEWS_STORY_3);
        return topStories;
    }

    private Story hackerStoryDetails() {
        List<String> commentList = new ArrayList<>(Arrays.asList("16007744", "16007987"));
        return new Story("maury91", 39, 16000389,
                commentList, 81, 1514137015, "I made a talking emoji using regular emojis and JavaScript",
                "story", "https://hackernoon.com/how-i-made-a-talking-emoji-using-regular-emojis-and-javascript-fe20e62ba10"
        );
    }

    private Comments hackerCommentDetails() {
        List<String> commentList = new ArrayList<>(Arrays.asList("16007791", "16008139"));
        return new Comments("_nalply", 16007744, commentList,
                16000389, "I&#x27;m Deaf and read lips.<p>Thanks for this extremely intriguing idea to use emoji to depict a talking person visually.<p>You know, some animated films get it right. Sometimes I clearly see utterances like «Thank you!», «Okay», but more often not. Especially older animations don&#x27;t care and just let the character move the mouth in very simplistic ways: «Babbabbabaa Baababba ba baaa.»<p>Similarly for that emoji. It is moving the mouth in quite arbitrary ways. It looks like: «Sobbabbabee be &lt;grin&gt; seebee &lt;grin&gt; &lt;frown&gt; babbaa».<p>To solve this problem we need about twelve emoji for utterances: «ah», «ay», «ee», «oh», «oo», «b», «d», «f», «l», «n», «r» and «s». The «r» emoji must be animated (so we see the trilling tongue). The other sounds are either invisible like «k» or cover several different sounds like «b» also covering «m» and «p».<p>«Okay» would be rendered by: «oh», «oo», «oo» (standing for «k» wich is invisible) «ay» and «ee».<p>Edit: Add «th». This is an extremely simple sound to lipread. I remember my delight when I learnt English in my youth and in an movie suddenly realised that an actor said «Thank you».",
                1514277142, "comment"
        );
    }
}