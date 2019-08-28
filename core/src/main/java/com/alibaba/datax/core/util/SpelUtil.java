package com.alibaba.datax.core.util;

import com.alibaba.datax.common.log.EtlJobLogger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author
 */
public class SpelUtil {

    private static final Logger logger = LoggerFactory.getLogger(SpelUtil.class);

    /**
     * 替换变量
     *
     * @param param
     * @param content
     * @return
     */
    public static String replace(String param, String content) {

        if (!StringUtils.isEmpty(param)) {
            try {
                StandardEvaluationContext ctx = new StandardEvaluationContext();
                ExpressionParser ep = new SpelExpressionParser();
                JSONObject jsonObject = JSON.parseObject(param);
                for (String key : jsonObject.keySet()) {
                    ctx.setVariable(key, jsonObject.get(key));
                }
                content = ObjectUtils.toString(ep.parseExpression(content, new TemplateParserContext()).getValue(ctx));
            } catch (Exception e) {
                logger.error("解析参数异常 参数:{}" + param + "内容:{}" + content, e);
                EtlJobLogger.log("解析参数异常 参数:{}" + param + "内容:{}" + content, e.getMessage());
            }
        }
        EtlJobLogger.log("解析之后 内容:{}" + content);
        return content;
    }
}
