package com.citytechinc.aem.apps.ionic.core.tags;

import com.day.cq.wcm.api.components.IncludeOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

import javax.servlet.jsp.tagext.TagSupport;

import static com.day.cq.wcm.tags.DefineObjectsTag.DEFAULT_REQUEST_NAME;

//TODO: Some more testing needs to be done around how this behaves - 1) I don't know if we really need to explicitly set this back in the end tag, it's probably irrelevant at that point.  2) I'd like to see if we could get this to disable all the way down.  I think it will only disable the next include as it is written currently.
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
