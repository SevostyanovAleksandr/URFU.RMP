package com.example.android.materialme;

/**
 * Data model for each row of the RecyclerView
 */
class Sport {

    // Переменные-члены, представляющие название и информацию о виде спорта.
    private String title;
    private String info;
    private final int imageResource;

    /**
     *Конструктор для модели спортивных данных.
     */
    public Sport(String title, String info, int imageResource) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
    }

    /**
     Получает звание чемпиона по этому виду спорта.
     */
    String getTitle() {
        return title;
    }

    /**
     Получает информацию о виде спорта.
     */
    String getInfo() {
        return info;
    }

    public int getImageResource() {
        return imageResource;
    }

}
