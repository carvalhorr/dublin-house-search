package daft.handler;

import data.Action;
import data.MessageOnDaftAction;
import data.SearchMatchInfo;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class SendMessageToProperty implements daft.handler.ISearchMatchHandler {

    @Override
    public void handleNewMatch(SearchMatchInfo matchInfo, Action action) {

        try {

            URL url = new URL("https://www.daft.ie/ajax_endpoint.php");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            Map<String, String> arguments = new HashMap<>();
            arguments.put("action", "daft_contact_advertiser");
            arguments.put("from", matchInfo.getUser().getName());
            arguments.put("email", matchInfo.getUser().getEmail());
            arguments.put("message", ((MessageOnDaftAction)action).getMessage());
            arguments.put("contact_number", matchInfo.getUser().getPhone());
            arguments.put("type", matchInfo.getProperty().getFields().get("property_category"));
            arguments.put("id", matchInfo.getProperty().getId());
            arguments.put("self_copy", "");
            arguments.put("agent_id", "");

            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
                System.out.println(http.getResponseMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace());
        }
    }

}
