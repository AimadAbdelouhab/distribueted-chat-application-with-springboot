'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var url = 'http://localhost:8090';

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/myChatAppApi');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    var MessageDto = {
        sender: username,
        text:'',
        type: 'JOIN'
    };

    // Tell your username to the server
    stompClient.send("/app/chat.register",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var MessageDto = {
            sender: username,
            text: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(MessageDto));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    console.log(message.JSON)
    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.text = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.text = message.sender + ' left!';
    } 
    else if (message.type === 'DELETED') {
        // Find the message to be deleted
        var messageElement = document.querySelector(`[data-message-id="${message.messageId}"]`);
        if (messageElement) {
            var textElement = messageElement.querySelector('p');
            textElement.innerHTML = '<em>Message deleted</em>';
            textElement.style.color = 'grey';
            var deleteButton = messageElement.querySelector(`[delete-button-id="${message.messageId}"]`);
            if (deleteButton) {
                // Remove the delete button from the message element
                deleteButton.remove();
            }
            return;
        }
    }
    else {
        messageElement.classList.add('chat-message');
        


        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);

        console.error('Message Sender:', message.sender);
        console.error('Username:', username);
        
          // Add delete button for messages sent by the current user
       if (message.sender === username) {

            var deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.setAttribute('delete-button-id', message.messageId);
            deleteButton.classList.add('delete-button');
            deleteButton.addEventListener('click', function (event) {
                deleteMessage(event, message.messageId);
            });
            messageElement.appendChild(deleteButton);
        }
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.text);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    // Set the data attribute for the message ID
    messageElement.setAttribute('data-message-id', message.messageId);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function deleteMessage(event, messageIdParam) {
    event.preventDefault();

    var messageDto = {
        sender: username,
        messageId: messageIdParam,
        type: 'TODELETE'
    };

    // Send the delete message to the server
    stompClient.send("/app/chat.delete", {}, JSON.stringify(messageDto));
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', send, true)
