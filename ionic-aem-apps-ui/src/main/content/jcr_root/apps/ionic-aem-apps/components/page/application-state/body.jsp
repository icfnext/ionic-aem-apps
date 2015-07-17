<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>

<body>
    <cq:include script="header.jsp"/>

    <cq:include script="template.jsp"/>

    <cq:include script="footer.jsp"/>

    <c:if test="${isAppMode}">
        <cq:includeClientLib js="ionic-1.0.0"/>
    </c:if>
    <%-- TODO: Determine if this should go inside the is app mode check --%>
    <cq:include script="js_clientlibs.jsp"/>
</body>