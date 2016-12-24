package com.example.vipul.mychatclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyChatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chats);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chats_view);
        Button sendButton = (Button)findViewById(R.id.send_button);
        final EditText sendText = (EditText)findViewById(R.id.send_message_text);
        recyclerView.setHasFixedSize(true);
        /*if(recyclerView!=null){
            recyclerView.setHasFixedSize(true);
        }*/
        final FirebaseRecyclerAdapter<Chat,ChatViewHolder> adapter = new FirebaseRecyclerAdapter<Chat, ChatViewHolder>(
                Chat.class,
                R.layout.user_chat_layout,
                ChatViewHolder.class,
                reference.getRoot().child("messages").getRef()
        ) {
            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, Chat model, int position) {
                viewHolder.text.setText(model.getText());
                viewHolder.user.setText(model.getName());
            }
        };
        final LinearLayoutManager manager = new LinearLayoutManager(this);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int messageCount = adapter.getItemCount();
                int lastVisiblePosition = manager.findLastCompletelyVisibleItemPosition();
                if(lastVisiblePosition==-1||(positionStart>=(messageCount-1)&&lastVisiblePosition==(positionStart-1))){
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat chat = new Chat("Myself",sendText.getText().toString());
                reference.getRoot().child("messages").push().setValue(chat);
                sendText.setText("");
            }
        });
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView user;
        TextView text;

        public ChatViewHolder(View itemView) {
            super(itemView);
            user = (TextView)itemView.findViewById(R.id.user_name);
            text = (TextView)itemView.findViewById(R.id.chat_text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chats_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_button:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(MyChatsActivity.this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
