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

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

    const auth = 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsImV4cCI6MTY4NDk4NjA0NywiZW1haWwiOiI5YmU2ODBiMS1iZTk5LTRmOGItOTQ0MC1mOTIxM2U3MzE1MTUifQ.h2-yW6ZEvd0QDXMaGJ-euUM66oB8b7p5BVIjDQTByZZQ1mukanFCUtw2UmjTvgwtoYTeqLhlgqbdpyJdGaeqHg';

    // 처음 로딩 시 사용자 정보 가져오기 (이름 및 폴더)
    if(auth !== '') {
        // 로그인한 유저 이름
        $.ajax({
            type: 'GET',
            url: `/users/user-info`,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", auth);
            },
            success: function (response) {
                if (response === 'fail') {
                    console.log(response);
                } else {
                    $('#name').val(response);
                }
            },
            error(error, status, request) {
                console.error(error);
            }
        });
    }

    function  getToken() {
    let cName = 'Authorization' + '=';
    let cookieData = document.cookie;
    let cookie = cookieData.indexOf('Authorization');
    let auth = '';
    if(cookie !== -1){
    cookie += cName.length;
    let end = cookieData.indexOf(';', cookie);
    if(end === -1)end = cookieData.length;
    auth = cookieData.substring(cookie, end);
}

    // kakao 로그인 사용한 경우 Bearer 추가
    if(auth.indexOf('Bearer') === -1 && auth !== ''){
    auth = 'Bearer ' + auth;
}

    return auth;
}

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/websocket');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.register",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Não foi possível se conectar ao WebSocket! Atualize a página e tente novamente ou entre em contato com o administrador.';
    connectingElement.style.color = 'red';
}


function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
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
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
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