package it.polito.ai.project.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// the interceptor is used to bind HttpSession to the WebSocket session (copying attributes like sessionId)
		registry.addEndpoint("/chat").withSockJS().setSessionCookieNeeded(true);
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// which broker to use: in memory
		// all the messages with prefixes /topic and /queue will be handled
		// /topic is for publish-subscribe
		// /queue is for messages to a specific recipient
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages.simpDestMatchers("/**").hasRole("USER");
	}
	
	 public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		 // set the limit for message size (10 MB)
		 registration.setMessageSizeLimit(1024*1024*10);
	 }
}
