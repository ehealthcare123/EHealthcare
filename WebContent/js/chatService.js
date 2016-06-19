/**
 * This source handles the client communication with a Secure WebSocket
 */
var webSocket = null;
	
//Establish the WebSocket connection and set up event handlers
connect(connectionWay() + location.hostname + ":" + location.port + "/EHealthcare/WSSchat/" + sessionID.innerHTML);

//Send message if "Send" is clicked
id("send").addEventListener("click", function () {
    sendMessage(id("message").value);
});

//Send message if enter is pressed in the input field
id("message").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { sendMessage(e.target.value); }
});



//choose the right connection way (secured or not secured, this is here the question)
function connectionWay() {
    if (window.location.protocol == 'http:') {
        return 'ws://';
    } else {
    	return 'wss://';
    }
}

function hideElement (element) {
	element.style.display = 'none';           // Hide
}

function showElement (element) {
	element.style.display = 'block';          // Show
}

//connect to a given target
function connect(target) {
    if (target == '') {
        alert('Please select server side connection implementation. Parameter is empty!');
        return;
    }
    if ('WebSocket' in window) {
    	webSocket = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
    	webSocket = new MozWebSocket(target);
    } else {
        alert('WebSocket is not supported by this browser.');
        return;
    }
    
    //what happens if you get connected to WebSocket-Host
    webSocket.onopen = function () {
    };
    
    //what happens if you receive a message
    webSocket.onmessage = function (event) {
    	updateChat(event);
    };
    
    //what happens if you close your connection to WebSocket-Host
    webSocket.onclose = function (event) {
    	
    };
}

// close connection if open
function disconnect() {
    if (webSocket != null) {
    	webSocket.close();
    	webSocket = null;
    }
}

//Send a message if it's not empty, then clear the input field
function sendMessage(message) {
    if (message !== "") {
        webSocket.send(message);
        id("message").value = "";
    }
}

//Update the chat-panel, and the list of connected users
function updateChat(msg) {
    var data = JSON.parse(msg.data);
    insert("chat", data.userMessage);
    id("userlist").innerHTML = "";
    data.userlist.forEach(function (user) {
        insert("userlist", "<li>" + user + "</li>");
    });
}

//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}