// Request-ის გაგზავნა
async function registerUser(){
    removeTextarea();
    var userName = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    var url = "http://localhost:8989/messenger/users?param1=" + userName + "&param2=" + password;
    var response = await fetch(url, { method: "POST" });
    var body = await response.text();

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    var div = document.getElementById("serverResponse");

    document.getElementById('username').value = '';
    document.getElementById('password').value = '';

    if (body.includes("User registered successfully!")) {
        alert("User registration was successful!");
    }else {
        alert(body);
    }
}
async function sendMessage(){
    removeTextarea();
    var receiverName = document.getElementById('username1').value;
    var message = document.getElementById('message').value;

    var url = "http://localhost:8989/messenger/messages?param1=" + receiverName + "&param2=" + message;
    var response = await fetch(url, { method: "POST" });
    var body = await response.text();

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    var div = document.getElementById("serverResponse");
    document.getElementById('username1').value = '';
    document.getElementById('message').value = '';

    if (body.includes("Message successfully sent to user")) {
        alert("Message successfully sent to user" + receiverName);
    }else{
        alert(body);
    }
}
async function checkMessages(){
    removeTextarea();
    var viewerName = document.getElementById('username2').value;
    var message = document.getElementById('password1').value;

    var url = "http://localhost:8989/messenger/messages?param1=" + viewerName + "&param2=" + message;
    var response = await fetch(url, { method: "GET" });
    var body = await response.text();

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    var div = document.getElementById("serverResponse");

    document.getElementById('username1').value = '';
    document.getElementById('message').value = '';

    if(body.includes("Error 404: Unknown error within server")){
        alert("Error 404: User not found");
    }else if(body.includes("Error 402: User not found")){
        alert("Error 402: user not found");
    }else{
        alert("Messages found!");
        var textarea = document.createElement("textarea");
        textarea.id = "inboxTextArea";
        textarea.value = body;
        textarea.spellcheck = false;
        textarea.style.height = "150px";
        textarea.style.marginTop = "35px";
        textarea.readOnly = true;
        textarea.className = "custom-input"; // Use the class name you've defined

        var container = document.querySelector(".container");
        container.appendChild(textarea);
    }
}
function removeTextarea() {
    var textarea = document.getElementById("inboxTextArea");
    if (textarea) {
        textarea.parentNode.removeChild(textarea);
    }
}









