package it.faustobe.jigger.data.local.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.faustobe.jigger.data.local.dao.BartenderDao;
import it.faustobe.jigger.data.local.dao.CocktailDao;
import it.faustobe.jigger.data.local.dao.CocktailIngredientDao;
import it.faustobe.jigger.data.local.dao.IngredientDao;
import it.faustobe.jigger.data.local.dao.ShiftCocktailDao;
import it.faustobe.jigger.data.local.dao.ShiftDao;
import it.faustobe.jigger.data.local.entities.Bartender;
import it.faustobe.jigger.data.local.entities.Cocktail;
import it.faustobe.jigger.data.local.entities.CocktailIngredient;
import it.faustobe.jigger.data.local.entities.Ingredient;
import it.faustobe.jigger.data.local.entities.Shift;
import it.faustobe.jigger.data.local.entities.ShiftCocktail;

@Database(
    entities = {
        Shift.class,
        Cocktail.class,
        Ingredient.class,
        CocktailIngredient.class,
        ShiftCocktail.class,
        Bartender.class
    },
    version = 1,
    exportSchema = true
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract ShiftDao shiftDao();
    public abstract CocktailDao cocktailDao();
    public abstract IngredientDao ingredientDao();
    public abstract ShiftCocktailDao shiftCocktailDao();
    public abstract CocktailIngredientDao cocktailIngredientDao();
    public abstract BartenderDao bartenderDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "jigger_database"
                    )
                    .addCallback(new DatabaseCallback(context))
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class DatabaseCallback extends RoomDatabase.Callback {
        private final Context context;

        DatabaseCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                AppDatabase database = getInstance(context);

                // Seed cocktails
                CocktailDao cocktailDao = database.cocktailDao();
                cocktailDao.insertAll(DatabaseSeeder.getInitialCocktails());

                // Seed ingredients
                IngredientDao ingredientDao = database.ingredientDao();
                ingredientDao.insertAll(DatabaseSeeder.getInitialIngredients());

                // Seed default bartender
                BartenderDao bartenderDao = database.bartenderDao();
                bartenderDao.insert(DatabaseSeeder.getDefaultBartender());
            });
        }
    }
}
