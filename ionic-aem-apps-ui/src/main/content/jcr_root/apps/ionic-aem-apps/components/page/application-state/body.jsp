<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>

<body>
    <cq:include script="header.jsp"/>

    <cq:include script="template.jsp"/>

    <cq:include script="footer.jsp"/>

    <cq:includeClientLib js="ionic-1.0.0-beta.14"/>
    <cq:include script="js_clientlibs.jsp"/>
</body>