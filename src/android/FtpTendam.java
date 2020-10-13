package cordova.plugin.ftptendam;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        if (action.equals("connect")) {
            this.connect(args.getString(0), args.getString(1), args.getString(2), callbackContext);
            return true;

        } else if (action.equals("uploadinventorydir")) {
            this.uploadinventorydir(args.getString(0), args.getString(1), callbackContext);
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

    private void uploadinventorydir(String localFile, String remoteFile, CallbackContext callbackContext) {
        if (localFile == null || remoteFile == null)
        {
            System.out.println("Expected localFile and remoteFile.");
        }
        else
        {
            try {
                // Cambio sobre la versión original
                // String remoteFilePath = remoteFile.substring(0, remoteFile.lastIndexOf('/') + 1);
                String remoteFilePath = remoteFile.substring(0, remoteFile.lastIndexOf('/'));

                String remoteFileName = remoteFile.substring(remoteFile.lastIndexOf('/') + 1);                
                String localFilePath = localFile.substring(0, localFile.lastIndexOf('/') + 1);
                String localFileName = localFile.substring(localFile.lastIndexOf('/') + 1);
                System.out.println("Change directory to " + remoteFilePath);
                this.client.changeDirectory(remoteFilePath);
                File file = new File(localFile);
                InputStream in =  new FileInputStream(file);
                long size = file.length();
                System.out.println("Upload file " + remoteFileName);
                client.upload(remoteFileName, in, 0, 0, new CDVFtpTransferListener(size));
                // refer to CDVFtpTransferListener for transfer percent and completed
            } catch (Exception e) {
                System.out.println(e.toString());
            }
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

class CDVFtpTransferListener implements FTPDataTransferListener {
    public static final String TAG = CDVFtpTransferListener.class.getSimpleName();
    private long totalSize = 0;
    private long curSize = 0;
    private CallbackContext callbackContext = null;
    private PluginResult pluginResult = null;

    public CDVFtpTransferListener(long size, CallbackContext callbackContext) {
        this.totalSize = size;
        this.callbackContext = callbackContext;
    }

    public void started() {
        // Transfer started
        Log.i(TAG, "Transfer started");
        this.curSize = 0;
    }

    public void transferred(int length) {
        // Yet other length bytes has been transferred since the last time this
        // method was called
        this.curSize += length;
        float percent = (float)this.curSize / (float)this.totalSize;
        Log.d(TAG, "Transferred, totalSize=" + this.totalSize + ", curSize=" + this.curSize + ", percent=" + percent);
        // Tip: just return if percent < 1, to prevent js:successCallback() invoked twice, as completed() will also return 1.
        if (percent >= 0 && percent < 1) {
            this.pluginResult = new PluginResult(PluginResult.Status.OK, percent);
            this.pluginResult.setKeepCallback(true);
            this.callbackContext.sendPluginResult(this.pluginResult);
        }
    }

    public void completed() {
        // Transfer completed
        Log.i(TAG, "Transfer completed");
        this.pluginResult = new PluginResult(PluginResult.Status.OK, 1);
        this.pluginResult.setKeepCallback(false);
        this.callbackContext.sendPluginResult(this.pluginResult);
    }

    public void aborted() {
        // Transfer aborted
        Log.w(TAG, "Transfer aborted");
        this.pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        this.pluginResult.setKeepCallback(false);
        this.callbackContext.sendPluginResult(this.pluginResult);
    }

    public void failed() {
        // Transfer failed
        Log.e(TAG, "Transfer failed");
        this.pluginResult = new PluginResult(PluginResult.Status.ERROR);
        this.pluginResult.setKeepCallback(false);
        this.callbackContext.sendPluginResult(this.pluginResult);
    }
}