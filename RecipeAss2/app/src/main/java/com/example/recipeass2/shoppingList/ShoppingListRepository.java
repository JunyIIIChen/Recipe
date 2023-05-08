package com.example.recipeass2.shoppingList;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.recipeass2.database.AppDatabase;

import java.util.List;

public class ShoppingListRepository {
    private final ShoppingListItemDao shoppingListItemDao;

    public ShoppingListRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        shoppingListItemDao = db.shoppingListItemDao();
    }

    public LiveData<List<ShoppingListItem>> getAllShoppingListItems() {
        return shoppingListItemDao.getAllShoppingListItems();
    }

    public void insert(ShoppingListItem shoppingListItem) {
        new InsertAsyncTask(shoppingListItemDao).execute(shoppingListItem);
    }
    public void deleteAllShoppingListItems() {
        new DeleteAllAsyncTask(shoppingListItemDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<ShoppingListItem, Void, Void> {
        private ShoppingListItemDao asyncTaskDao;

        InsertAsyncTask(ShoppingListItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ShoppingListItem... shoppingListItems) {
            asyncTaskDao.insert(shoppingListItems[0]);
            return null;
        }
    }
    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ShoppingListItemDao asyncTaskDao;

        DeleteAllAsyncTask(ShoppingListItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }
}
