package name.nxw.crawler.huanqiu.utility;

import static org.junit.jupiter.api.Assertions.*;

import name.nxw.crawler.huanqiu.exception.CategoryNotFoundException;
import name.nxw.crawler.huanqiu.exception.PortalGenerationException;
import org.junit.jupiter.api.Test;

class URLUtilityTests {

    @Test
    void buildHuanqiuApiUrlTest() {
        assertDoesNotThrow(() -> {
            String milPortal = URLUtility.buildNewsListUrl("mil", 0, 20);
            assertEquals(milPortal,
                    "https://mil.huanqiu.com/api/list?node=%22%2Fe3pmh1dm8%2Fe3pmt7hva%22%2C%22%2Fe3pmh1dm8%2Fe3pmtdr2r%22%2C%22%2Fe3pmh1dm8%2Fe3pn62l96%22%2C%22%2Fe3pmh1dm8%2Fe3pn6f3oh%22&offset=0&limit=20");
        });

        assertThrows(PortalGenerationException.class,
                () -> URLUtility.buildNewsListUrl("energy", 0, 20));

        assertThrows(CategoryNotFoundException.class,
                () -> URLUtility.buildNewsListUrl("1232", 0, 20));
    }

    @Test
    void extractCategoryTest() {
        String url = "https://mil.huanqiu.com/article/40VhveGHhIS";
        assertEquals(URLUtility.extractCategory(url), "mil");
    }

    @Test
    void extractAIdTest() {
        String url = "https://mil.huanqiu.com/article/40VhveGHhIS";
        assertEquals(URLUtility.extractAId(url), "40VhveGHhIS");
    }

}
