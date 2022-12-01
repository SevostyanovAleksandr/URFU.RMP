package com.example.android.materialme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/***
 * Класс адаптера для RecyclerView содержит спортивные данные.
 */
class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder>  {


    private ArrayList<Sport> mSportsData;
    private Context mContext;

    /**
     * Конструктор, который передает спортивные данные и контекст..
     */
    SportsAdapter(Context context, ArrayList<Sport> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;
    }


    /**
     * Требуемый метод для создания объектов viewholder.
     */
    @Override
    public SportsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false));
    }

    /**
     Требуемый метод, который привязывает данные к viewholder.
     */
    @Override
    public void onBindViewHolder(SportsAdapter.ViewHolder holder,
                                 int position) {
      // Получить текущий вид спорта.
        Sport currentSport = mSportsData.get(position);

        // Заполните текстовые представления данными.
        holder.bindTo(currentSport);
    }


    /**
     Необходимый метод для определения размера набора данных.
     */
    @Override
    public int getItemCount() {
        return mSportsData.size();
    }


    /**
     Класс ViewHolder, который представляет каждую строку данных в RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Переменные-члены для текстовых представлений
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mSportsImage;

        /**
         Конструктор для ViewHolder, используемый в onCreateViewHolder().
         */
        ViewHolder(View itemView) {
            super(itemView);

            //Инициализируйте представления.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mSportsImage = itemView.findViewById(R.id.sportsImage);

            // Установите OnClickListener на весь вид.
            itemView.setOnClickListener(this);
        }

        void bindTo(Sport currentSport){
            // Заполните текстовые представления данными.
            mTitleText.setText(currentSport.getTitle());
            mInfoText.setText(currentSport.getInfo());

            //Загрузите изображения в ImageView с помощью библиотеки Glide.
            Glide.with(mContext).load(
                    currentSport.getImageResource()).into(mSportsImage);

        }

        /**
         * Обработайте щелчок, чтобы показать подробную активность.
         */
        @Override
        public void onClick(View view) {
            Sport currentSport = mSportsData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentSport.getTitle());
            detailIntent.putExtra("image_resource",
                    currentSport.getImageResource());
            mContext.startActivity(detailIntent);
        }
    }
}
