package name.nxw.crawler.huanqiu;

import java.util.HashMap;
import java.util.Map;

/**
 * mil -> mil
 *
 */
public class Category {

    private static class CategoryInfo {
        private String id;
        private String node;
        private final String description;
        private final Boolean hasPortal;

        public CategoryInfo(String id, String node, String description) {
            this.id = id;
            this.node = node;
            this.description = description;
            hasPortal = true;
        }

        public CategoryInfo(String id, String node, String description, Boolean hasPortal) {
            this.id = id;
            this.node = node;
            this.description = description;
            this.hasPortal = hasPortal;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public String getDescription() {
            return description;
        }

        public Boolean getHasPortal() {
            return hasPortal;
        }
    }

    // key: prefix, value: category info
    private static Map<String, CategoryInfo> map;

    static {
        map = new HashMap<>();
        map.put("world", new CategoryInfo("world",
                "\"/e3pmh22ph/e3pmh2398\",\"/e3pmh22ph/e3pmh26vv\",\"/e3pmh22ph/e3pn6efsl\",\"/e3pmh22ph/efp8fqe21\"",
                "国际新闻"));
        map.put("china", new CategoryInfo("china",
                "\"/e3pmh1nnq/e3pmh1obd\",\"/e3pmh1nnq/e3pn61c2g\",\"/e3pmh1nnq/e3pn6eiep\",\"/e3pmh1nnq/e3pra70uk\",\"/e3pmh1nnq/e5anm31jb\",\"/e3pmh1nnq/e7tl4e309\"",
                "国内新闻"));
        map.put("mil", new CategoryInfo("mil",
                "\"/e3pmh1dm8/e3pmt7hva\",\"/e3pmh1dm8/e3pmtdr2r\",\"/e3pmh1dm8/e3pn62l96\",\"/e3pmh1dm8/e3pn6f3oh\"",
                "军事"));
        map.put("taiwan", new CategoryInfo("taiwan",
                "\"/e3pmh1fmg/e3pmh1g6o\",\"/e3pmh1fmg/e3pmt8gfv\",\"/e3pmh1fmg/e3pmt8i3n\",\"/e3pmh1fmg/e3pmt8lic\",\"/e3pmh1fmg/e3pmunk13\",\"/e3pmh1fmg/e3pn6elbj\"",
                "台海"));
        map.put("opinion", new CategoryInfo("opinion",
                "\"/e3pmub6h5/e3pmub75a\",\"/e3pmub6h5/e3pn00if8\",\"/e3pmub6h5/e3pn03vit\",\"/e3pmub6h5/e3pn4bi4t\",\"/e3pmub6h5/e3pr9baf6\",\"/e3pmub6h5/e3prafm0g\",\"/e3pmub6h5/e3prcgifj\",\"/e3pmub6h5/e81curi71\",\"/e3pmub6h5/e81cv14rf\",\"/e3pmub6h5/e81cv14rf/e81cv52ha\",\"/e3pmub6h5/e81cv14rf/e81cv7hp0\",\"/e3pmub6h5/e81cv14rf/e81cvaa3q\",\"/e3pmub6h5/e81cv14rf/e81cvcd7e\"",
                "评论"));
        map.put("finance", new CategoryInfo("finance",
                "\"/e3pmh1hmp/e3pmh1iab\",\"/e3pmh1hmp/e3pn46htn\",\"/e3pmh1hmp/e3pn60gdi\",\"/e3pmh1hmp/e3pn60gdi/e3pn60h31\",\"/e3pmh1hmp/e3pn60gdi/e3pru2fi2\",\"/e3pmh1hmp/e3pn60rs2\",\"/e3pmh1hmp/e3pn60rs2/e3pn60skq\",\"/e3pmh1hmp/e3pn60rs2/e3ptlr015\",\"/e3pmh1hmp/e3pn61831\",\"/e3pmh1hmp/e3pn61an9\",\"/e3pmh1hmp/e3pn61chp\",\"/e3pmh1hmp/e3pn62ihu\",\"/e3pmh1hmp/e3pn62uuq\",\"/e3pmh1hmp/e3pn6314j\",\"/e3pmh1hmp/e3pn6314j/e3pn6323a\",\"/e3pmh1hmp/e3pn6314j/e3ptma9ah\",\"/e3pmh1hmp/e3ptkencb\",\"/e3pmh1hmp/e3ptlrdc9\",\"/e3pmh1hmp/e3ptlrdc9/e3ptltkc2\",\"/e3pmh1hmp/e3ptlrdc9/e3ptm2ci2\",\"/e3pmh1hmp/e7i6qafud\",\"/e3pmh1hmp/e7i6t8c0j\",\"/e3pmh1hmp/e7lipkhq1\",\"/e3pmh1hmp/e7lipkhq1/e7lipkii0\",\"/e3pmh1hmp/e7lipkhq1/e7o08h1r8\"",
                "财经"));
        map.put("tech", new CategoryInfo("tech",
                "\"/e3pmh164r/e3pmh2hq8\",\"/e3pmh164r/e3pmh33i9\",\"/e3pmh164r/e3pmh356p\",\"/e3pmh164r/e3pmh3dh4\",\"/e3pmh164r/e3pmtlao3\",\"/e3pmh164r/e3pmtm015\",\"/e3pmh164r/e3pmtnh4j\",\"/e3pmh164r/e3pn1fd3s\",\"/e3pmh164r/e3pn46ri0\",\"/e3pmh164r/e3pn4bn46\",\"/e3pmh164r/e3pn4gh77\",\"/e3pmh164r/e3pn4qlss\",\"/e3pmh164r/e3pn6fo08\",\"/e3pmh164r/e3ptqlvrg\"",
                "科技"));
        map.put("auto", new CategoryInfo("auto",
                "\"/e3pmh24qk/e3pmh25cs\",\"/e3pmh24qk/e3pmh3bo0\",\"/e3pmh24qk/e3pmtj0sq\",\"/e3pmh24qk/e3pmtj57c\",\"/e3pmh24qk/e3pmtju0i\",\"/e3pmh24qk/e3pmtkgc2\",\"/e3pmh24qk/e3pn02kho\",\"/e3pmh24qk/e3pn02mp3\",\"/e3pmh24qk/e3pn0asn6\",\"/e3pmh24qk/e3pn4el6u\",\"/e3pmh24qk/e3pn4ens6\",\"/e3pmh24qk/e5hutg9ko\"",
                "汽车"));
        map.put("art", new CategoryInfo("art",
                "\"/e3pokdnam/e3pokdnrj\"",
                "艺术"));
        map.put("go", new CategoryInfo("go",
                "/e3pmh1tuv/e3pmh1ufv/e3pmh1v35",
                "文旅"));
        map.put("health", new CategoryInfo("health",
                "\"/e3pmt7dq2/e3pmt7edc\",\"/e3pmt7dq2/e3pmt904n\",\"/e3pmt7dq2/e3pmt9htm\",\"/e3pmt7dq2/e3pmtbedk\",\"/e3pmt7dq2/e3pmtvmsa\",\"/e3pmt7dq2/e3pn49kc7\",\"/e3pmt7dq2/e3pn4cagl\",\"/e3pmt7dq2/e3pn4f81k\",\"/e3pmt7dq2/e3pn50ich\",\"/e3pmt7dq2/e3pn61f01\",\"/e3pmt7dq2/e3pn6edle\",\"/e3pmt7dq2/e3pn6gvs7\",\"/e3pmt7dq2/e3prd5mi2\",\"/e3pmt7dq2/e3ptds3rp\",\"/e3pmt7dq2/e3ptds3rp/e3ptds4r8\",\"/e3pmt7dq2/e3ptt66fi\"",
                "健康"));
        map.put("energy", new CategoryInfo("energy",
                null,
                "能源",
                false));
        map.put("qinzi", new CategoryInfo("qinzi",
                "\"/ebggoaf45/ebgmgut3n\",\"/ebggoaf45/ebgmgut3n/ebgmhivbk\",\"/ebggoaf45/ebgmi0d9i\",\"/ebggoaf45/ebgmi0d9i/ebi36o0qb\",\"/ebggoaf45/ebi37sp8n\",\"/ebggoaf45/ebi37sp8n/ebi388sjn\",\"/ebggoaf45/ebi38m5d7\",\"/ebggoaf45/ebi38m5d7/ebi391j8t\",\"/ebggoaf45/ebi39m4up\",\"/ebggoaf45/ebi39m4up/ebi39uvki\"",
                "亲子"));
        map.put("city", new CategoryInfo("city",
                "\"/e3pmh1nv4/e3pn60pg4\",\"/e3pmh1nv4/e3pn637a1\",\"/e3pmh1nv4/e3pu0ucu5\",\"/e3pmh1nv4/e7td1g6u2\",\"/e3pmh1nv4/e7td1t7it\",\"/e3pmh1nv4/ecko2h4rc\",\"/e3pmh1nv4/ecko2on6j\",\"/e3pmh1nv4/ecko30dgg\"",
                "城市"));
        map.put("look", new CategoryInfo("look",
                "\"/e3pn4q8sp/e3pn4q9gc\",\"/e3pn4q8sp/e3pn7ibfm\",\"/e3pn4q8sp/e3pofukpl\",\"/e3pn4q8sp/e3pttoksk\"",
                "博览"));
        map.put("yrd", new CategoryInfo("yrd",
                "\"/e3pu6i31u/eecbiskt4\",\"/e3pu6i31u/eecc32cqf\",\"/e3pu6i31u/eecc35cit\",\"/e3pu6i31u/eecc38g1f\",\"/e3pu6i31u/eecc3c3fq\",\"/e3pu6i31u/eecc3f0eh\",\"/e3pu6i31u/eecc3i4cj\",\"/e3pu6i31u/eecc3lq5n\",\"/e3pu6i31u/eecc3om9o\",\"/e3pu6i31u/eecc3rbnu\"",
                "长三角"));
        map.put("uav", new CategoryInfo("uav",
                "\"/e3pn5tffs/e3pn5tg0p\",\"/e3pn5tffs/e3pn5tg0p/e3ptjsfnn\",\"/e3pn5tffs/e3pn6156o\",\"/e3pn5tffs/e3poit9nf\",\"/e3pn5tffs/e3poit9nf/e3ptjtt1d\",\"/e3pn5tffs/e3pshm06b\",\"/e3pn5tffs/e3pthq9rg\",\"/e3pn5tffs/e3ptkjts8\",\"/e3pn5tffs/e5ar1t85q\",\"/e3pn5tffs/e7rugtglo\",\"/e3pn5tffs/e7ruia4rh\",\"/e3pn5tffs/e7siujt5c\"",
                "无人机"));
        map.put("quality", new CategoryInfo("quality",
                "\"/e3pmh1m0l/e3pn61c7h\",\"/e3pmh1m0l/e3pn61hm0\",\"/e3pmh1m0l/e3pn61ks9\",\"/e3pmh1m0l/e5at2i193\",\"/e3pmh1m0l/e5hv56a1p\",\"/e3pmh1m0l/e7sj13p2a\",\"/e3pmh1m0l/e7sj17b0r\",\"/e3pmh1m0l/e7sj1gcbe\",\"/e3pmh1m0l/e7sj2rsu7\",\"/e3pmh1m0l/e7sj34723\",\"/e3pmh1m0l/e7sj3bpu2\"",
                "质量频道"));
        map.put("fashion", new CategoryInfo("fashion",
                "\"/e3pn4vu2g/e3pn4vuih\",\"/e3pn4vu2g/e3pn61569\",\"/e3pn4vu2g/e3pn7fph9\",\"/e3pn4vu2g/e3pn7v7f6\",\"/e3pn4vu2g/e3poftkqs\",\"/e3pn4vu2g/e3pokk591\",\"/e3pn4vu2g/e3pokk591/e3pokk5ti\",\"/e3pn4vu2g/e3pokk591/e3q91thfo\",\"/e3pn4vu2g/e3pright6\",\"/e3pn4vu2g/e3prijklc\",\"/e3pn4vu2g/e3ptq4t9b\"",
                "时尚"));
        map.put("", new CategoryInfo("",
                "",
                ""));
        map.put("", new CategoryInfo("",
                "",
                ""));
        map.put("", new CategoryInfo("",
                "",
                ""));
        map.put("", new CategoryInfo("",
                "",
                ""));
        map.put("", new CategoryInfo("",
                "",
                ""));
        map.put("", new CategoryInfo("",
                "",
                ""));
        map.put("", new CategoryInfo("",
                "",
                ""));
        map.put("", new CategoryInfo("",
                "",
                ""));
    }

    static String extractCategory(String url) {
        return "";
    }

    static String portal(String category) {
        return "";
    }

    static String categoryId(String category) {
        return "";
    }

    static String categoryNode(String category) {
        return "";
    }

    static String categoryDescription(String category) {
        return "";
    }

}
