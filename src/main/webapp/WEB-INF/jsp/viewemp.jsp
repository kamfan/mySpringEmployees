<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>View Employees List</h1>
<table border="2" width="70%" cellpadding="2">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Salary</th>
        <th>Designation</th>
        <th>Buttons</th>
    </tr>

    <c:forEach var="emp" items="${list}">
        <tr>
            <td>${emp.id}</td>
            <td>${emp.name}</td>
            <td>${emp.salary}</td>
            <td>${emp.designation}</td>
            <td>${emp.email}</td>

            <td>
                <form:form method="post" action="email">
                <input type="hidden" name = "email" value="${emp.email}">
                <input type="submit" name="Wyslij maila" value="/email"/>
                </form:form>

                <form:form method="post" action="delete">
                    <input type="hidden" name = "id" value="${emp.id}"/>
                    <input type="submit" name="Delete" value="delete"/>
                </form:form>
                <form:form method="post" action="edit">
                    <input type="hidden" name = "id" value="${emp.id}"/>
                    <input type="submit" name="Edit" value="edit"/>
                </form:form>
        <%--    <form:form method="post" action="delete">
             <input type="submit" class="button" name="Delete" value="delete"/>
         </form:form>--%>
            </td>
        </tr>
    </c:forEach>

    <tr>
    <td colspan="6" align = "center">

        <form:form method="post" action="test">
            <input type="submit" name="test" value="Przycisk test"/>
        </form:form>
    </td>
    </tr>

</table>