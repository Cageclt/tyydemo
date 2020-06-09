import com.tyy.pojo.Article.Article;
import com.tyy.service.Article.ArticleServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ArticleTest {
    @Test
    // Service/Article/ArticleServiceImpl/queryArticleByNameLike
    public void queryArticleByNameLikeTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        for (Article article : articleServiceImpl.queryArticleByNameLike("说")) {
            System.out.println(article);
        }
    }

    @Test
    // Service/Article/ArticleServiceImpl/queryArticleById
    public void queryArticleByIdTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        Article article = articleServiceImpl.queryArticleById(1);
        System.out.println(article);
    }

    @Test
    // Service/Article/ArticleServiceImpl/addArticleView
    public void addArticleViewTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        System.out.println(articleServiceImpl.addArticleView(1));
    }


    @Test
    // Service/Article/ArticleServiceImpl/addReview
    public void addReviewTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId",1);
        map.put("ArticleId",1);
        map.put("Reviews","高三时午间休息读的一本书，" +
                "村上春树的小说我虽没有看过，但是他在这种杂文中的叙述感觉非常舒畅，让我在高三紧张的气氛中，" +
                "能有几个午后放松下来，听他关于对跑步的思考与见闻");
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(nowdate);
        map.put("Time",nowdate);
        map.put("Good",0);
        System.out.println(articleServiceImpl.addReview(map));
    }

    @Test
    // Service/Article/ArticleServiceImpl/addReviewNum
    public void addReviewNumTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        System.out.println(articleServiceImpl.addReviewNum(1));
    }

    @Test
    // Service/Article/ArticleServiceImpl/addR_Review
    public void addR_ReviewTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId",2);
        map.put("TargetId",1);
        map.put("Reviews","是真的，我是本人");
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(nowdate);
        map.put("Time",nowdate);
        map.put("Good",0);
        System.out.println(articleServiceImpl.addR_Review(map));
    }

    @Test
    // Service/Article/ArticleServiceImpl/addArticleGood
    public void addArticleGoodTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        System.out.println(articleServiceImpl.addArticleGood(1));
    }


    @Test
    // Service/Article/ArticleServiceImpl/addArticle
    public void addArticleTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId",2);
        map.put("Title","龙川半日游");
        map.put("Content","此处为文本路径");
        Date nowdate = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(nowdate);
        map.put("Time",nowdate);
        map.put("Location","龙川");
        System.out.println(articleServiceImpl.addArticle(map));
    }

    @Test
    //  service/userServiceImpl.addArticleCollect
    public void addArticleCollectTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl", ArticleServiceImpl.class);
        System.out.println(articleServiceImpl.addArticleCollect(1, 1));
    }

    @Test
    // Service/Article/ArticleServiceImpl/addArticleCollection
    public void addArticleCollectionTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        System.out.println(articleServiceImpl.addArticleCollection(1));
    }

    @Test
    // Service/Article/ArticleServiceImpl/addReviewGood
    public void addReviewGoodTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        System.out.println(articleServiceImpl.addReviewGood(1));
    }

    @Test
    // Service/Article/ArticleServiceImpl/addR_ReviewGood
    public void addR_ReviewGoodTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl articleServiceImpl = context.getBean("ArticleServiceImpl",ArticleServiceImpl.class);
        System.out.println(articleServiceImpl.addR_ReviewGood(1));
    }


}
