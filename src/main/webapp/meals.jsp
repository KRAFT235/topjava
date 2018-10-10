<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        TABLE {
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
            width: 300px; /* Ширина таблицы */
        }
        TH, TD {
            border: 1px solid black; /* Параметры рамки */
            text-align: center; /* Выравнивание по центру */
            padding: 4px; /* Поля вокруг текста */
        }
        TH {
            height: 40px; /* Высота ячеек */
            vertical-align: bottom; /* Выравнивание по нижнему краю */
            padding: 0; /* Убираем поля вокруг текста */
        }
    </style>
</head>
<body>
<table>
    <tr>
        <td>Дата/Время</td>
        <td>Описание</td>
        <td>Калории</td>
    </tr>
<c:forEach var="meal" items="${listMeals}" >

    <%--<tr color = value>--%>
    <tr style="color:${meal.isExceed() ? 'red' : 'green'};" />
        <td>${meal.getDateTime().format(formater)}</td>
        <td>${meal.getDescription()}</td>
        <td>${meal.getCalories()}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>
