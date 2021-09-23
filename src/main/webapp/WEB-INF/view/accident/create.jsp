<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
          integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
          crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
          integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
          crossorigin="anonymous"></script>
  <title>Creation</title>
</head>
<body>
<div class="container">
  <form action="<c:url value='/save'/>" method='POST'>
    <table>
      <tr>
        <td>Тип:</td>
        <td>
          <select name="type.id" required>
            <c:forEach var="type" items="${types}">
              <option value="${type.id}">${type.name}</option>
            </c:forEach>
          </select>
        </td>
      </tr>
      <tr>
        <td>Название:</td>
        <td><input type='text' name='name' required></td>
      </tr>
      <tr>
        <td>Текст:</td>
        <td><input type='text' name='text' required></td>
      </tr>
      <tr>
        <td>Адресс:</td>
        <td><input type='text' name='address' required></td>
      </tr>
      <tr>
        <td>Статьи:</td>
        <td>
          <select name="rIds" multiple required>
            <c:forEach var="rule" items="${rules}">
              <option value="${rule.id}">${rule.name}</option>
            </c:forEach>
          </select>
      </tr>
      <tr>
        <td colspan='2'><input name="submit" type="submit" value="Сохранить"/>
        </td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>
