<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/projectStyle.css">
</head>
<body>
<header>
    <h1>Online-library</h1>
    <div id="authenticate">
        <table class="user_buttons">
            <tr>
                <sec:authorize access="hasAuthority('ADMINISTRATOR')">
                    <td><c:import url="templates/buttons/books_button.jsp"/></td>
                    <td><c:import url="templates/buttons/users_button.jsp"/></td>
                </sec:authorize>
                <td><c:import url="templates/buttons/basket_button.jsp"/></td>
                <td><c:import url="templates/buttons/profile_button.jsp"/></td>
                <td><c:import url="templates/buttons/logout_button.jsp"/></td>
            </tr>
        </table>
    </div>
</header>
<main>

    <p>Something went wrong...</p>
    <div id="error">
        <c:if test="${error != null}"><h3>${error}</h3></c:if>
    </div>
</main>

<footer>
    <a href="<c:url value="/index"/>">Home page</a>
</footer>
</body>
</html>