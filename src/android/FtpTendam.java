package cordova.plugin.ftptendam;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPFile;

/**
 * This class echoes a string called from JavaScript.
 */
public class FtpTendam extends CordovaPlugin {

    private FTPClient client = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.connect("connect")) {

            this.connect(args.getString(0), args.getString(1), args.getString(2), callbackContext);
            return true;

        } else if (action.equals("createinventorydir")) {

            this.createinventorydir(args.getString(0), args.getString(1), callbackContext);
            return true;

        } else if (action.equals("disconnect")) {

            this.disconnect(callbackContext);
            return true;

        }
        return false;
    }

    private void connect(String hostname, String username, String password, CallbackContext callbackContext) {

        if (hostname == null || hostname.length() <= 0) {
            callbackContext.error("Expected hostname.");
        } else {
            if (username == null && password == null) {
                username = "anonymous";
                password = "anonymous@";
            }

            try {
                this.client = new FTPClient();
                String[] address = hostname.split(":");
                if (address.length == 2) {
                    String host = address[0];
                    int port = Integer.parseInt(address[1]);
                    this.client.connect(host, port);
                } else {
                    this.client.connect(hostname);
                }
                this.client.login(username, password);
                callbackContext.success("Connect and login OK.");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }
        }

    }

    private void createinventorydir(String path, String directoryname, CallbackContext callbackContext) {
        String remoteDirPath = "/";

        try {
            if (path != null && path.length() > 0){
                remoteDirPath = path;
            }
            this.client.changeDirectory(remoteDirPath);
            this.client.createDirectory(directoryname);
            callbackContext.success("Create directory OK");
        } catch (Exception e) {
            callbackContext.error(e.toString());
        }

    }


    private void disconnect(CallbackContext callbackContext) {
        try {
            // `true` to perform a legal disconnect procedure (an QUIT command is sent to the server),
            // `false` to break the connection without advice.
            this.client.disconnect(true);
            callbackContext.success("Disconnect OK.");
        } catch (Exception e) {
            callbackContext.error(e.toString());
        }
    }

}
