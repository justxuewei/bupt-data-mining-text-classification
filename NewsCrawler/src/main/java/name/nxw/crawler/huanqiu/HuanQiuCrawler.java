package name.nxw.crawler.huanqiu;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuanQiuCrawler extends BreadthCrawler {

    static int Limit = 20;
    static String MilitaryNode = parameterEncoder("\"/e3pmh1dm8/e3pmh1e5j\",\"/e3pmh1dm8/e3pmt7hva\",\"/e3pmh1dm8/e3pmt8erh\",\"/e3pmh1dm8/e3pmt8sc4\",\"/e3pmh1dm8/e3pmt91uh\",\"/e3pmh1dm8/e3pmt9fnl\",\"/e3pmh1dm8/e3pmtdr2r\",\"/e3pmh1dm8/e3pn4bp9c\",\"/e3pmh1dm8/e3pn4mifc\",\"/e3pmh1dm8/e3pn4ml1q\",\"/e3pmh1dm8/e3pn62l96\",\"/e3pmh1dm8/e3pn6enpt\",\"/e3pmh1dm8/e3pn6f3oh\",\"/e3pmh1dm8/e3pn6m2f2\",\"/e3pmh1dm8/e3pn7gjsn\",\"/e3pmh1dm8/e3pofv5vk\",\"/e3pmh1dm8/e3pog08cd\",\"/e3pmh1dm8/e3pruk1k4\",\"/e3pmh1dm8/e3prvbom4\",\"/e3pmh1dm8/e3ps1cp6o\",\"/e3pmh1dm8/e3ps5rbsc\",\"/e3pmh1dm8/e3ps6g627\",\"/e3pmh1dm8/e3ps923oq\",\"/e3pmh1dm8/e3ps9j98h\"");

    static String ApiType = "Api";
    static String ContentType = "Content";

//    static String ArticleURLPrefix = "https://mil.huanqiu.com/article/";

    public HuanQiuCrawler(String portal, String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        for (int index = 0; index < 100; index++) {
            int offset = index * Limit;
            String url = String.format("%1$s?node=%2$s&offset=%3$d&limit=%4$d",
                    portal,
                    MilitaryNode,
                    offset,
                    Limit);
            this.addSeed(url, ApiType);
        }
        this.setThreads(50);
        this.getConf().setTopN(100);
    }

    static String parameterEncoder(String p) {
        try {
            return URLEncoder.encode(p, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void visit(Page page, CrawlDatums crawlDatums) {
        String url = page.url();
        System.out.println("-->>>> " + url);

        if (page.matchType(ApiType)) {
            String content = new String(page.content(), StandardCharsets.UTF_8);
            String aidPattern = "\"aid\": \"(.{11})\"";
            Pattern pattern = Pattern.compile(aidPattern);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String aid = matcher.group(1);
                crawlDatums.add(ArticleURLPrefix + aid).type(ContentType);
            }
        } else if (page.matchType(ContentType)) {
            String content = new String(page.content(), StandardCharsets.UTF_8);
            System.out.println(content);
        }
    }

    public static void main(String[] args) throws Exception {
        HuanQiuCrawler mc = new HuanQiuCrawler("MC", true);
        mc.start(5);
    }
}
