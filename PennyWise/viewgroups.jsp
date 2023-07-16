<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Groups</title>
    <link rel="stylesheet" href="css/viewgroupscss.css">
</head>
<body>
    <h2>Groups</h2>
    <form action="group.jsp">
        <button type="submit">Create New Group</button>
    </form>
    <ul>
        <% 
            List<String> groupidList = (List<String>) request.getAttribute("groupidList");
            List<String> groupnameList = (List<String>) request.getAttribute("groupnameList");

                for (int i = 0; i < groupidList.size() && i < groupnameList.size(); i++) { 
                    String groupId = groupidList.get(i);
                    String groupName = groupnameList.get(i);
                    String link = "groups/group" + groupId + ".jsp";
        %>
        <li>
            <a href="<%= link %>"><button><%= groupName %></button></a>
        </li>
        <% } %>
    </ul>
    
    
</body>
</html> 







