<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Meals</title>
    <link rel="stylesheet" href="css/myStyle.css" type="text/css">
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>

    <table>
        <tr>
            <th>DateTime</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="meal" items="${requestScope.mealsTo}">
            <c:set var="color" value="green"/>
            <c:if test="${meal.excess}">
                <c:set var="color" value="red"/>
            </c:if>

            <tr style="color:${color}">
                <td ><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd hh:mm"/></td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=edit&mealId=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>

        <p><a href="meals?action=insert">Add meal</a></p>
    </table>

</body>
</html>
