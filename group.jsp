<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Group</title>
    <link rel="stylesheet" type="text/css" href="css\groupcss.css">
    <script>
        
        document.getElementById("searchbox").addEventListener("keyup", searchUsers);

        function addMember() {
    var username = document.getElementById("searchbox").value;
    var memberList = document.getElementById("member-list");
    var memberItems = memberList.getElementsByTagName("li");
    for (var i = 0; i < memberItems.length; i++) {
        if (memberItems[i].textContent === username) {
            alert("Member already added!");
            return;
        }
    }
    var listItem = document.createElement("li");
    listItem.textContent = username;
    memberList.appendChild(listItem);
    var input = document.createElement("input");
    input.type = "hidden";
    input.name = "members";
    input.value = username;
    document.querySelector("form").appendChild(input);
    document.getElementById("searchbox").value = "";

}

    </script>
</head>
<body>
    <h1>Create Group</h1>
    <form method="post" action="CreateGroupServlet">
        <div class="input-labels">
            Group Name: <input type="text" name="groupname"><br>
            Members: <br>
        </div>
        <input type="text" id="searchbox" name="searchbox" placeholder="Search usernames">
        <br>
        <button type="button" onclick="addMember()">Add</button>
        <br>
        Members List: <br>
        <ul id="member-list">
            <!-- Add a list item for the logged-in user -->
            <li><input type="hidden" name="members" value="<%= session.getAttribute("username") %>"><%= session.getAttribute("username") %></li>
        </ul>
        <br>
        <input type="submit" value="Create Group">
    </form>
    
      
</body>
</html>
