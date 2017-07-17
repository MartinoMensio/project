package it.polito.ai.project.websocket;

public class WebSocketMessage {

    private String content;
    private String image;
    private Long alertId;

    public WebSocketMessage() {
    }

    public String getContent() {
        return content;
    }
    
    public String getImage() {
    	return image;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void setImage(String image) {
    	this.image = image;
    }

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}
}