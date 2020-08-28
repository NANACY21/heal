package com.personal.seckillService;

/**1.springboot+kafka+redis 秒杀功能示例
 * 2.定时任务
 * 描述
 * 招聘者、管理员均能创建秒杀活动
 * <p>
 * [秒杀活动]服务 以[秒杀活动]为最小单元
 * 不是说每个[秒杀活动]对应一种[商品] 是包含[商品]
 * https://mall.fkw.com/blog/21497
 * https://mall.fkw.com/blog/21497
 * https://blog.csdn.net/weixin_40990818/article/details/106765023
 * 商品表设计：https://www.cnblogs.com/nirao/p/9753099.html
 *
 * @author 李箎
 */
public class Description {
//限购5台苹果手机 肯定能选不同颜色的
    //库存 白的10个 黑的10个
    //全局秒杀库存 白的10个 黑的10个？？ 弄成一个整型变量即可
    //真实库存 黑的100个 白的100个 设置秒杀库存 黑10个白10个 第一个人买了10黑1白 第二个人还想买黑 应该能让买 所以全局秒杀库存整型吧
    //秒杀库存 继承该商品真实库存的

    //商品表 数据 应该是商家新增的 这里预置好了
    //商家创建[秒杀活动]
    // 商家设计秒杀界面样式
}
