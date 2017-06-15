var stompClient = null;

function connect() {

	var headers = {};
	// use the header for authentication
	headers[csrfToken.headerName] = csrfToken.token;
	var socket = new SockJS('/chat');
	stompClient = Stomp.over(socket);
	stompClient.connect(headers, function(frame) {
		console.log('Connected: ' + frame);
		// subscribe to the current room
		stompClient.subscribe('/topic/' + roomId, function(message) {
			// when a message is received, show it
			showMessage(JSON.parse(message.body));
		});
	});
	// the connection is not closed by hand, but is closed when user requests another page
}

function sendMessage() {
	var content = $("#chat-text").val();
	var preview = $('#img-preview')[0];
	var imageStr = preview.getAttribute('src');
	// enable send when there is some text
	if (content || imageStr !== "") {
		stompClient.send("/app/" + roomId, {}, JSON.stringify({
			'content' : content,
			'image': imageStr
		}));
		$("#chat-text").val('');
		preview.setAttribute('src', "");
	}
}

function showMessage(message) {
	var userImage = (message.userImageUrl == null) ? '<img src="http://placehold.it/50/55C1E7/fff&text=U" alt="User Avatar" class="img-circle" />' : '<img src="' + message.userImageUrl + '" style="max-height: 50px; max-width: 50px" class="img-circle" />';
	if (message.userId != userId) {
		$(".chat")
				.append(
						'<li class="left clearfix">'
								+ '	<span class="chat-img pull-left">'
								+ userImage
								+ '	</span>'
								+ '	<div class="chat-body clearfix">'
								+ '		<div class="header">'
								+ '			<strong class="primary-font">' + message.userNickname + '</strong>'
								+ '			<small class="pull-right text-muted">'
								+ '				<span class="glyphicon glyphicon-time"></span>' + new Date(message.sendingTime)
								+ '			</small> '
								+ '		</div>'
								+ '		<p>' + message.text + '</p> '
								+ ' <img alt="" src="' + ((message.imageUrl === null) ? "" : message.imageUrl) + '" style="max-width: 300px; max-height: 300px;">'
								+ '	</div>' + '</li>');
	} else {
		$(".chat")
				.append(
						'<li class="right clearfix">'
								+ '	<span class="chat-img pull-right">'
								+ userImage
								+ '	</span>'
								+ '	<div class="chat-body clearfix">'
								+ '		<div class="header">'
								+ '			<small class="text-muted">'
								+ '				<span class="glyphicon glyphicon-time"></span>' + new Date(message.sendingTime)
								+ '			</small> '
								+ '			<strong class="pull-right primary-font">' + message.userNickname + '</strong>'
								+ '		</div>'
								+ '		<p class="align-right">' + message.text + '</p> '
								+ ' <img alt="" src="' + ((message.imageUrl === null) ? "" : message.imageUrl) + '" style="max-width: 300px; max-height: 300px;">'
								+ '	</div>' + '</li>');
	}
	$(".panel-body").animate({ scrollTop: $(document).height() }, 1000);
}

$(function() {
	$("#sendForm").on('submit', function(e) {
		e.preventDefault();
		sendMessage();
	});
});