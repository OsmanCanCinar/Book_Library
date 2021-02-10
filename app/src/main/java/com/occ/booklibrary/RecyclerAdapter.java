package com.occ.booklibrary;
//17070006056 OSMAN CAN CINAR
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.RecordHolder> {

    ArrayList<String> author;
    ArrayList<String> book;
    ArrayList<String> pages;
    ArrayList<String> id;

    public RecyclerAdapter(ArrayList<String> author, ArrayList<String> book, ArrayList<String> pages, ArrayList<String> id) {
        this.author = author;
        this.book = book;
        this.pages = pages;
        this.id = id;
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyler_row,parent,false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder holder, final int position) {
        holder.authorDisplay.setText(author.get(position));
        holder.recordDisplay.setText(pages.get(position));
        holder.bookDisplay.setText(book.get(position));
        holder.idDisp.setText(id.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),MainActivity3.class);
                intent.putExtra("recordID",id.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return author.size();
    }

    class RecordHolder extends RecyclerView.ViewHolder {

        TextView recordDisplay, bookDisplay, authorDisplay, idDisp;

        public RecordHolder(@NonNull View itemView) {
            super(itemView);
            recordDisplay = (TextView) itemView.findViewById(R.id.pageCount);
            bookDisplay = (TextView) itemView.findViewById(R.id.bookName);
            authorDisplay = (TextView) itemView.findViewById(R.id.authorName);
            idDisp = (TextView) itemView.findViewById(R.id.recordNumber);
        }
    }
}
