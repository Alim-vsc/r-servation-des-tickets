import java.util.List;

public interface Notifiable {
    public void notifier(String message);

    public List<String> getNotifications();
}
