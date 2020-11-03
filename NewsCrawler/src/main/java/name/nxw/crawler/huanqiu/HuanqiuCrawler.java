package name.nxw.crawler.huanqiu;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import name.nxw.crawler.huanqiu.exception.CategoryNotFoundException;
import name.nxw.crawler.huanqiu.utility.MySQLConnector;
import name.nxw.crawler.huanqiu.utility.URLUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuanqiuCrawler extends BreadthCrawler {

    static final Logger logger = LoggerFactory.getLogger(HuanqiuCrawler.class);

    static String ApiType = "Api";
    static String ContentType = "Content";

    static String NewsSource = "Huanqiu";

    private final String portalCategory;
    private MySQLConnector mysql;

    public HuanqiuCrawler(String crawlPath, boolean autoParse, String portal,
                          int maxOffset, int limit,
                          int threadNum, int topN) {
        super(crawlPath, autoParse);

        this.portalCategory = portal;

        // mysql initialization
        try {
            mysql = new MySQLConnector("127.0.0.1", 10002, "qqnews", "root", "4d3CMl54p6K7O4zi");
        } catch (ClassNotFoundException e) {
            logger.error("The class of JDBC driver is not found.");
            System.exit(1);
        }

        int index = 0;
        int offset = 0;
        while (offset < maxOffset) {
            try {
                String apiUrl = URLUtility.buildNewsListUrl(portal, offset, limit);
                this.addSeed(apiUrl, ApiType);
            } catch (Exception e) {
                logger.error("Portal API URL can't be generated: {}", e.getMessage());
                System.exit(1);
            }
            offset = (++index) * limit;
        }
        this.setThreads(threadNum);
        this.getConf().setTopN(topN);
    }

    public void visit(Page page, CrawlDatums next) {
        if (page.matchType(ApiType)) {
            String content = new String(page.content(), StandardCharsets.UTF_8);
            String aidPattern = "\"aid\": \"(\\w{11})\"";
            Pattern pattern = Pattern.compile(aidPattern);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String aid = matcher.group(1);
                String newsUrl;
                try {
                    newsUrl = URLUtility.buildNewsUrl(portalCategory, aid);
                } catch (CategoryNotFoundException e) {
                    logger.error("AId \"{}\" is ignored, because category \"{}\" is unsupported.", aid, portalCategory);
                    continue;
                }
                next.add(newsUrl, ContentType);
            }
        } else if (page.matchType(ContentType)) {
            String html = new String(page.content(), StandardCharsets.UTF_8);
            String title = page.select("title").first().text();
            if (title == null) {
                logger.error("Title can't be selected from \"{}\".", page.url());
                return;
            }
            String content = page.selectText("body > div.all-con > div > div.b-container > div.l-container > div.l-con.clear > article");
            if (content == null || content.isEmpty()) {
                logger.error("Content can't be selected from \"{}\".", page.url());
                return;
            }
            String category = URLUtility.extractCategory(page.url());
            if (category == null) {
                logger.error("Category can't be extracted from \"{}\".", page.url());
                return;
            }
            String newsId = URLUtility.extractAId(page.url());
            if (newsId == null) {
                logger.error("News Id can't be extracted from \"{}\".", page.url());
                return;
            }
            // persist data
            try (Connection conn = mysql.getConn(); PreparedStatement pstmt = conn.prepareStatement("INSERT INTO news" +
                    "(news_id, content, title, url, news_source, category) " +
                    "VALUES (?, ?, ?, ?, ?, ?)")) {
                pstmt.setString(1, newsId);
                pstmt.setString(2, content);
                pstmt.setString(3, title);
                pstmt.setString(4, page.url());
                pstmt.setString(5, NewsSource);
                pstmt.setString(6, category);
                pstmt.executeUpdate();
            } catch (Exception e) {
                logger.error("SQL Error, error: {}", e.getMessage());
                return;
            }
            // search sub links in current page
            Pattern urlsPattern = Pattern.compile("//(\\w{1,20}\\.huanqiu\\.com/article/\\w{11})");
            Matcher urlsMatcher = urlsPattern.matcher(html);
            boolean flag = false;
            while (urlsMatcher.find()) {
                if (!flag) {
                    flag = true;
                }
                String subUrl = urlsMatcher.group(1);
                next.add("https://" + subUrl, ContentType);
            }
            if (!flag) {
                logger.error("The sub links are not found in \"{}\".", page.url());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (Entry<String, Category.CategoryInfo> entry: Category.map.entrySet()) {
            String category = entry.getKey();
            Category.CategoryInfo info = entry.getValue();
            if (!info.hasPortal()) continue;
            String crawlPath = String.format("%sCrawlerPath", category);
            HuanqiuCrawler crawler = new HuanqiuCrawler(crawlPath, true, category,
                    10000, 20, 50, 10000);
            crawler.getConf().setExecuteInterval(5000);
            crawler.start(10000);
        }
    }
}
