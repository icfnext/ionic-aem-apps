package com.icfi.aem.apps.ionic.core.tags;

import com.day.cq.wcm.api.components.IncludeOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

import javax.servlet.jsp.tagext.TagSupport;

import static com.day.cq.wcm.tags.DefineObjectsTag.DEFAULT_REQUEST_NAME;

public class SuppressDecorationTag extends TagSupport {

    private String originalTag;

    private String test;

    @Override
    public int doStartTag() {
        SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) pageContext.getAttribute(DEFAULT_REQUEST_NAME);

        IncludeOptions includeOptions = IncludeOptions.getOptions(slingRequest, true);

        originalTag = includeOptions.getDecorationTagName();
        includeOptions.setDecorationTagName("");

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() {
        SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) pageContext.getAttribute(DEFAULT_REQUEST_NAME);

        IncludeOptions.getOptions(slingRequest, true).setDecorationTagName(originalTag);

        return EVAL_PAGE;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    private boolean isSuppressDecoration() {
        return StringUtils.isBlank(getTest()) || Boolean.valueOf(getTest());
    }

}
