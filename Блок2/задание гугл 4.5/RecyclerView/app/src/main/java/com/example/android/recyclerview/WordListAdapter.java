package com.example.android.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Показывает, как реализовать простой адаптер для RecyclerView.
 * Демонстрирует, как добавить обработчик щелчков для каждого элемента в ViewHolder.
 */
public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LinkedList<String> mWordList;
    private final LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        /**
         * Создает новый пользовательский держатель представления для удержания представления для отображения в
         * RecyclerView.
         */
        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Получить положение элемента, на который был сделан щелчок.
            int mPosition = getLayoutPosition();

            // Используйте это для доступа к затронутому элементу в mWordList.
            String element = mWordList.get(mPosition);
            // Измените слово в списке mWordList.

            mWordList.set(mPosition, "Clicked! " + element);
// Уведомить адаптер о том, что данные изменились, чтобы он мог
// обновить RecyclerView для отображения данных.
            mAdapter.notifyDataSetChanged();
        }
    }

    public WordListAdapter(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // Раздуть представление элемента.
        View mItemView = mInflater.inflate(
                R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordListAdapter.WordViewHolder holder,
                                 int position) {
        // Извлеките данные для этой позиции.
        String mCurrent = mWordList.get(position);
        // Добавьте данные в держатель представления.
        holder.wordItemView.setText(mCurrent);
    }

    /**
     Возвращает общее количество элементов в наборе данных, хранящихся в адаптере.
     */
    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
