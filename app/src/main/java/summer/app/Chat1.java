package summer.app;

import org.jivesoftware.smack.XMPPException;

public interface Chat1 {
    void sendMessage(String message) throws XMPPException;

    void release() throws XMPPException;
}
