package name.nxw.crawler.huanqiu.utility;

import name.nxw.crawler.huanqiu.Category;
import name.nxw.crawler.huanqiu.exception.CategoryNotFoundException;
import name.nxw.crawler.huanqiu.exception.PortalGenerationException;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtility {

    public static String extractCategory(String URL) {
        Pattern pattern = Pattern.compile("https://(\\w{1,20})\\.huanqiu\\.com/article/\\w{11}");
        Matcher matcher = pattern.matcher(URL);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String extractAId(String URL) {
        Pattern pattern = Pattern.compile("https://\\w{1,20}\\.huanqiu\\.com/article/(\\w{11})");
        Matcher matcher = pattern.matcher(URL);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String buildNewsListUrl(String category, int offset, int limit)
            throws CategoryNotFoundException, PortalGenerationException, URISyntaxException {
        Category.CategoryInfo info = Category.info(category);
        if (!info.hasPortal()) {
            throw new PortalGenerationException();
        }
        String apiURL = String.format("https://%s.huanqiu.com/api/list", category);
        String node = info.getNode();
        URIBuilder b = new URIBuilder(apiURL);
        b.addParameter("node", node);
        b.addParameter("offset", String.valueOf(offset));
        b.addParameter("limit", String.valueOf(limit));
        return b.build().toString();
    }

    public static String buildNewsUrl(String category, String id) throws CategoryNotFoundException {
        if (!Category.contains(category)) {
            throw new CategoryNotFoundException();
        }
        return String.format("https://%s.huanqiu.com/article/%s", category, id);
    }
}
