package summer.app;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import summer.app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jivesoftware.smack.XMPPException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Chat extends ActionBarActivity {
    public static final String Login1 = "";
    private static final String TAG = Chat.class.getSimpleName();
    private EditText messageEditText;
    private Chat1 chat;
    private ChatAdapter adapter;
    private ListView messagesContainer;
    private TextView FriendId;
    public int Login2;
    public int companionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras = getIntent().getExtras();
        Login2 = extras.getInt(Login1);
        companionId = Login2;

        initViews();
    }

    private void initViews() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageEditText = (EditText) findViewById(R.id.messageEdit);
        Button sendButton = (Button) findViewById(R.id.chatSendButton);
       // TextView meLabel = (TextView) findViewById(R.id.meLabel);
        //TextView companionLabel = (TextView) findViewById(R.id.companionLabel);
       // RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        adapter = new ChatAdapter(this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);
        Intent intent = getIntent();
        chat = new SingleChat(this);
        int userId = intent.getIntExtra(SingleChat.EXTRA_USER_ID, 0);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastMsg = messageEditText.getText().toString();
                if (TextUtils.isEmpty(lastMsg)) {
                    return;
                }
                messageEditText.setText("");
                try {
                    chat.sendMessage(lastMsg);
                } catch (XMPPException e) {
                    Log.e(TAG, "failed to send a message", e);
                }
                showMessage(new ChatMessage(lastMsg, Calendar.getInstance().getTime(), false));
            }
        });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
       // scrollDown();
    }

    public void showMessage(List<ChatMessage> messages) {
        adapter.add(messages);
        adapter.notifyDataSetChanged();
      //  scrollDown();
    }
}
