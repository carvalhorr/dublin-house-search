package data;

public class MessageOnDaftAction extends Action {

    private String message;

    public MessageOnDaftAction() {
        super(ActionType.MESSAGE_ON_DAFT);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
