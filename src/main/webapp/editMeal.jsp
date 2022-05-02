<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="css/myStyle.css" type="text/css">
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<hr>
    <c:set var="action" value="Edit meal"/>
    <c:if test="${requestScope.meal.id == null}">
        <c:set var="action" value="Add meal"/>
    </c:if>
    <h2>${action}</h2>

    <form method="post" action="meals" name="addUser">
        <p><label>DateTime:</label> <input type="datetime-local" name="dateTime" value="${requestScope.meal.dateTime}"/></p>
        <p><label>Description:</label> <input style="width: 300px" type="text" name="description" value="${requestScope.meal.description}"/></p>
        <p><label>Calories:</label> <input type="number" name="calories" value="${requestScope.meal.calories}"/></p>
        <input type="hidden" name="mealId" value="${requestScope.meal.id}">
        <input style="margin-right: 5px" type="submit" value="Save"/>
        <input type="button" value="Cancel" onclick="history.back()">
    </form>
</body>
</html>
