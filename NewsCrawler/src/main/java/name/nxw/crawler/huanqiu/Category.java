package name.nxw.crawler.huanqiu;

import name.nxw.crawler.huanqiu.exception.CategoryNotFoundException;
import name.nxw.crawler.huanqiu.exception.PortalGenerationException;

import java.util.HashMap;
import java.util.Map;

public class Category {

    public static class CategoryInfo {
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

        public Boolean hasPortal() {
            return hasPortal;
        }
    }

    // key: prefix, value: category info
    private static final Map<String, CategoryInfo> map;

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
        map.put("ent", new CategoryInfo("ent",
                "\"/e3pmh1jtb/e3pmtdgsc\",\"/e3pmh1jtb/e3pmtdgsc/e3pmtdhgk\",\"/e3pmh1jtb/e3pmtdgsc/e3pmtdnnp\",\"/e3pmh1jtb/e3pmtdgsc/e3pmtdt2b\",\"/e3pmh1jtb/e3pmtdgsc/e3pn7e7e0\",\"/e3pmh1jtb/e3pmtdl8t\",\"/e3pmh1jtb/e3pmtdl8t/e3pmtdlph\",\"/e3pmh1jtb/e3pmtdl8t/e3pmtgk5g\",\"/e3pmh1jtb/e3pmtdl8t/e3pmti0d9\",\"/e3pmh1jtb/e3pmth0vk\",\"/e3pmh1jtb/e3pmth0vk/e3pmth1kc\",\"/e3pmh1jtb/e3pmth0vk/e3pmth41r\",\"/e3pmh1jtb/e3pmth0vk/e3pn61i89\"",
                "娱乐"));
        map.put("v", new CategoryInfo("v",
                "\"/e3pmh2fmu/e3pmh2g69\",\"/e3pmh2fmu/e3pn61vrg\",\"/e3pmh2fmu/e3prkldqd\",\"/e3pmh2fmu/e3prvucof\",\"/e3pmh2fmu/e3ptgqp01\",\"/e3pmh2fmu/e3ptgqp01/e3ptrminr\"",
                "视频"));
        map.put("sports", new CategoryInfo("sports",
                "\"/e3pmh3jvm/e3pn4vk37\",\"/e3pmh3jvm/e3pn4vk37/e3pn61aah\",\"/e3pmh3jvm/e3pn4vk37/e3pn62b3q\",\"/e3pmh3jvm/e3pn4vk37/e3pn638jv\",\"/e3pmh3jvm/e3pn4vk37/e3pn669vr\",\"/e3pmh3jvm/e3pn4vk37/e82e6tcpo\",\"/e3pmh3jvm/e3pn61psg\",\"/e3pmh3jvm/e3pn61psg/e3pn61qfv\",\"/e3pmh3jvm/e3pn61psg/e7tn9k8oi\",\"/e3pmh3jvm/e3pn61psg/e7tn9o6uo\",\"/e3pmh3jvm/e3pn61psg/e7tn9rf8b\",\"/e3pmh3jvm/e3pn61psg/e7tna015g\",\"/e3pmh3jvm/e3pn62e6c\",\"/e3pmh3jvm/e3pn62e6c/e3pn62euk\",\"/e3pmh3jvm/e3pn62e6c/e3prbvcgu\",\"/e3pmh3jvm/e3pn62e6c/e82e138l9\",\"/e3pmh3jvm/e3pn7fhub\",\"/e3pmh3jvm/e3pn7fhub/e3pn7fif4\",\"/e3pmh3jvm/e80lb2feu\"",
                "体育"));
        map.put("lx", new CategoryInfo("lx",
                "\"/e3pmh20mi/e3pn60gah\",\"/e3pmh20mi/e3pt1kh3i\",\"/e3pmh20mi/e7ru9r4mj\",\"/e3pmh20mi/ectnin83p\",\"/e3pmh20mi/ectnissc9\",\"/e3pmh20mi/ectnj650m\",\"/e3pmh20mi/ectnjgbot\"",
                "教育"));
        map.put("house", new CategoryInfo("house",
                "\"/e8nf57tcn/e8nf6ch75\"",
                "房产"));
        map.put("hope", new CategoryInfo("hope",
                "\"/e3pmh4858/e3pn4u4ek\",\"/e3pmh4858/e3pttusns\",\"/e3pmh4858/e3ptucljl\",\"/e3pmh4858/e3ptucsjk\",\"/e3pmh4858/e3ptucsjk/e3ptuct5t\",\"/e3pmh4858/e3ptucsjk/e3ptud330\",\"/e3pmh4858/e3ptucsjk/e3ptv43ri\",\"/e3pmh4858/e3ptucsjk/e3ptv5fhm\",\"/e3pmh4858/e3ptucsjk/e3ptv6vdg\",\"/e3pmh4858/e3ptucsjk/e3ptva0gc\",\"/e3pmh4858/e3ptucsjk/e3ptvhqaa\",\"/e3pmh4858/e3ptucsjk/e3pu014qv\",\"/e3pmh4858/e3ptucsjk/e3pu0s7q3\",\"/e3pmh4858/e3ptucsjk/e3pu1b85j\",\"/e3pmh4858/e3ptucsjk/e3pu20n8h\",\"/e3pmh4858/e7ksn07g8\",\"/e3pmh4858/e7ksnbha2\",\"/e3pmh4858/e7lb8bavn\",\"/e3pmh4858/e7lb8pe57\",\"/e3pmh4858/e7lb91aqm\",\"/e3pmh4858/e7lb99q8k\",\"/e3pmh4858/e7lhc78q0\",\"/e3pmh4858/e7lhequu0\"",
                "公益"));
        map.put("society", new CategoryInfo("society",
                "\"/e3pmh19vt/e3pmh1ar3\",\"/e3pmh19vt/e3pn7fivc\",\"/e3pmh19vt/e3prv5gfn\",\"/e3pmh19vt/e3ps21dgq\",\"/e3pmh19vt/e3ps2ueac\",\"/e3pmh19vt/e3ps46nfp\"",
                "社会"));
        map.put("smart", new CategoryInfo("smart",
                "\"/e3pmh140m/e3pmh14i1\",\"/e3pmh140m/e3pmh2jgm\",\"/e3pmh140m/e3pmh31se\",\"/e3pmh140m/e3pmh4huv\",\"/e3pmh140m/e3pmh4huv/e3pmh4ign\",\"/e3pmh140m/e3pn62ehj\",\"/e3pmh140m/e3pn7fsu3\",\"/e3pmh140m/e5arbmudl\",\"/e3pmh140m/e7qeadr4p\",\"/e3pmh140m/e7qeal82i\",\"/e3pmh140m/e7qeaudr2\",\"/e3pmh140m/e7qeb3iev\",\"/e3pmh140m/e7qeb8nre\",\"/e3pmh140m/e7qebdhuc\"",
                "智能"));
        map.put("5gcenter", new CategoryInfo("5gcenter",
                "\"/efphbf5l6/efphbpf42\",\"/efphbf5l6/efphbun01\",\"/efphbf5l6/efphcdkof\",\"/efphbf5l6/efphcgica\",\"/efphbf5l6/efphck30i\",\"/efphbf5l6/efphcn5t2\",\"/efphbf5l6/efphcpss5\",\"/efphbf5l6/efphcsmrj\"",
                "5g生态"));
        map.put("bigdata", new CategoryInfo("bigdata",
                "\"/e3pn606hg/e3pn6072l\",\"/e3pn606hg/e3pn61287\",\"/e3pn606hg/e3ptussvj\",\"/e3pn606hg/e3ptv0m10\",\"/e3pn606hg/e81jhpqpq\",\"/e3pn606hg/e81ji3si2\",\"/e3pn606hg/e81jido1v\"",
                "大数据"));
        map.put("biz", new CategoryInfo("biz",
                "\"/e3pmh1rv3/e3pmh1sfd\",\"/e3pmh1rv3/e3pmh2e3h\",\"/e3pmh1rv3/e3pn60lt0\",\"/e3pmh1rv3/e3pn60rqk\",\"/e3pmh1rv3/e3pn60urv\",\"/e3pmh1rv3/e3pn614r6\",\"/e3pmh1rv3/e3pn61ned\",\"/e3pmh1rv3/e3pn62hv9\",\"/e3pmh1rv3/e3pn63btp\",\"/e3pmh1rv3/e3pn63m00\",\"/e3pmh1rv3/e3pok2bl1\",\"/e3pmh1rv3/e3pu1lrsc\",\"/e3pmh1rv3/e3pu1okro\",\"/e3pmh1rv3/e6a3d64vd\",\"/e3pmh1rv3/e6a3d6rr6\",\"/e3pmh1rv3/e7nopb37v\",\"/e3pmh1rv3/e7qcj6l0g\"",
                "商业"));
        map.put("oversea", new CategoryInfo("oversea",
                "\"/e3pmt7bdh/e3pn4bc7q\",\"/e3pmt7bdh/e3pn5250c\",\"/e3pmt7bdh/e3pn6g8t8\"",
                "海外看中国"));
        map.put("cul", new CategoryInfo("cul",
                "\"/e3pn677q4/e3ptvpbdi\",\"/e3pn677q4/e7n7nshou\",\"/e3pn677q4/e7n7nshou/e7n7o5sgv\",\"/e3pn677q4/e7n7nshou/e7n7oddi8\",\"/e3pn677q4/e7n7nshou/e7n7oj8js\",\"/e3pn677q4/e7n7nshou/e7n7opklk\",\"/e3pn677q4/e7n7nshou/e80schtc2\",\"/e3pn677q4/e7n7qip93\",\"/e3pn677q4/e7n7qip93/e7n7quqav\",\"/e3pn677q4/e7n7qip93/e7n7r604h\",\"/e3pn677q4/e7n7qip93/e7n7rlouf\",\"/e3pn677q4/e7n7s45fv\",\"/e3pn677q4/e7n7s45fv/e7n7safa1\",\"/e3pn677q4/e7n7s45fv/e7n7silkh\",\"/e3pn677q4/e7n7s45fv/e7n7sqsjs\",\"/e3pn677q4/e7n836lt3\",\"/e3pn677q4/e7n83ff31\",\"/e3pn677q4/e7n83ff31/e7n83ll7b\",\"/e3pn677q4/e7n83ff31/e7n83scbg\",\"/e3pn677q4/e7n83ff31/e7n8421o7\",\"/e3pn677q4/e7n84b0u1\",\"/e3pn677q4/e7n84le0h\",\"/e3pn677q4/e7n856422\"",
                "文化"));
        map.put("capital", new CategoryInfo("capital",
                "\"/e5d59phvs/e5d5m10mv\",\"/e5d59phvs/e5d5m10mv/e5d5o560g\",\"/e5d59phvs/e5d5m10mv/e5d5oecei\",\"/e5d59phvs/e5d5m10mv/e5d5pk5dq\",\"/e5d59phvs/e5d5m10mv/e5d5q73kg\",\"/e5d59phvs/e5d5qjh4s\",\"/e5d59phvs/e5d5qjh4s/e5d5qp63c\",\"/e5d59phvs/e5d5qjh4s/e5d5sanu9\",\"/e5d59phvs/e5d5qjh4s/e5d5sjom2\",\"/e5d59phvs/e5d5svess\",\"/e5d59phvs/e5d5svess/e5d5t6l0v\",\"/e5d59phvs/e5d5svess/e5d5tee08\",\"/e5d59phvs/e5d5svess/e5d5tp81t\",\"/e5d59phvs/e5d5svess/e5d5tvudv\",\"/e5d59phvs/e5d5u9tt0\",\"/e5d59phvs/e5d5u9tt0/e5d5uf626\",\"/e5d59phvs/e5d5u9tt0/e5d5uktk9\",\"/e5d59phvs/e5d5u9tt0/e5d5uqcrs\",\"/e5d59phvs/e5d5u9tt0/e5d5v01f0\",\"/e5d59phvs/e63f43ge6\",\"/e5d59phvs/e63f43ge6/e63f43h0u\",\"/e5d59phvs/e63f43ge6/e63fcq9bt\",\"/e5d59phvs/e63j24g2m\",\"/e5d59phvs/e63j24g2m/e63j24goo\"",
                "创投"));
        map.put("chamber", new CategoryInfo("chamber",
                "\"/e3pu1oj7h/e3pu1ok69\"",
                "商协"));
    }

    public static boolean contains(String category) {
        return map.containsKey(category);
    }

    public static CategoryInfo info(String category) throws CategoryNotFoundException {
        CategoryInfo info = map.get(category);
        if (info == null) {
            throw new CategoryNotFoundException();
        }
        return info;
    }

//    public static String id(String category) throws CategoryNotFoundException {
//        CategoryInfo info = map.get(category);
//        if (info == null) {
//            throw new CategoryNotFoundException();
//        }
//        return info.getId();
//    }
//
//    public static String node(String category) throws CategoryNotFoundException {
//        CategoryInfo info = map.get(category);
//        if (info == null) {
//            throw new CategoryNotFoundException();
//        }
//        return info.node;
//    }
//
//    static String description(String category) throws CategoryNotFoundException {
//        CategoryInfo info = map.get(category);
//        if (info == null) {
//            throw new CategoryNotFoundException();
//        }
//        return info.getDescription();
//    }

}
