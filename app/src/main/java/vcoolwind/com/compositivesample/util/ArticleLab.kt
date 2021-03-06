package vcoolwind.com.compositivesample.util

import vcoolwind.com.compositivesample.model.Article
import java.text.FieldPosition
import java.util.*

/**
 * Created by BlackStone on 2016/11/3.
 */
class ArticleLab {
    var articleList: List<Article> = listOf(
            Article(1,
                    "北京破冰！商网新浪、凤凰总编辑拿到了“正高”职称",
                    """
                北京市人力资源和社会保障局官网近日发布了《2016年北京市高级专业技术资格评审结果公示第30号（数字编辑）》的文件，魏岳江等26人通过了高级编辑专业技术资格评审，刘金星等39人通过了主任编辑专业技术资格评审。
                """),
            Article(2,
                    "东莞被砸运钞车 该不该开枪？",
                    """
                10月27日，广东东莞，一男子在马路上拿着疑似砖块追砸运钞车，随后遭押运员开枪射击致死。而有目击者称，事发前运钞车曾蹭到该男子，所以该男子捡砖头砸车。运钞车是否“碰蹭”到死者？死者是否有“抢劫”的意图？押运员开枪是否合法？
                """),
            Article(3,
                    "中国拿下马来西亚700亿铁路大单 预计5-6年完工",
                    """
                【环球时报报道 驻马来西亚特约记者 欧贤安 李晓骁】一列现代化的火车、一幅行驶路线图以及“550亿林吉特低息贷款”几个大字，这是马来西亚《星报》1日的封面头条。当日，在中国总理李克强和马来西亚总理纳吉布的见证下，中国交建与马来西亚铁路衔接公司在京签署了马来西亚东部沿海铁路项目合同，合同金额约745亿元人民币。
                """),
            Article(4,
                    "财阀、中产皆恐慌，朴槿惠惹怒的是整个韩国",
                    """
                        朴槿惠大妈这两天的日子不太好过，“闺蜜干政门”把这个韩国首位女总统推到了风口浪尖，鉴于韩国一直有清算总统的“优良传统”，朴槿惠在重压下辞职或被迫下台现在看来并非是危言耸听。不过在抛开“闺蜜门”背后，她早已为自己埋下了一颗雷，在亚太经济整体增长放缓的大环境下，朴槿惠种种一意孤行的政策，早已把韩国的大财阀与中产阶级都逼上了绝路。

                        我们先来回顾一下截至发稿时间为止整个“闺蜜干政门”事件的前因后果：

                        朴槿惠闺蜜崔顺实的女儿郑维娜9月以“马术特长生”的资格被韩国顶尖的梨花女子大学录取，但10月12日就有媒体曝出录取郑维娜涉嫌黑幕，最大的嫌疑是梨花女子大学在录取郑之前根本就没有所谓的“马术特长生”。随后更被曝光只要崔顺实一个电话，连导师都可以被更换，此事一经报道，立刻引起轩然大波，梨花女子大学校长崔庆熙也于19日辞职。此时暴露于公众之下的只是崔顺实母女，波及到朴槿惠的至多只有崔氏母女利用与朴槿惠的关系牟取私利。
                """),
            Article(5,
                    "王健林问海尔砸冰箱才几个钱？海尔回应霸气 网友点赞",
                    """
                    据悉，王健林在讲述万达大手笔回购沈阳太原街万达广场商铺时，称“比海尔砸冰箱那个事例，砸20几台冰箱才几个钱啊？我们砸了350几个商铺，砸了6亿多。”
                    海尔也不甘示弱，官方微博进行了回应：“我还真没有好好算过在车间工人三年工资还买不来一台冰箱的1985年，张瑞敏砸的76台冰箱对当初几乎发不出工资的海尔意味着什么。但我知道现在身为官博君的我为什么买不起房了。”
                """)
    )


    private constructor()

    companion object {
        val instance = ArticleLab()
    }

    fun get(position: Int): Article {

        return articleList[position]
    }

    fun getById(id: Int): Article? {
        for (a in articleList) {
            if (a.id == id) {
                return a
            }
        }
        return null
    }


    fun size(): Int {
        return articleList.size
    }

    fun swap(from: Int, to: Int) {
        Collections.swap(articleList, from, to)
    }

}