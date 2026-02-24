package it.faustobe.jigger.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import it.faustobe.jigger.data.local.dao.BartenderDao;
import it.faustobe.jigger.data.local.dao.BartenderDao_Impl;
import it.faustobe.jigger.data.local.dao.CocktailDao;
import it.faustobe.jigger.data.local.dao.CocktailDao_Impl;
import it.faustobe.jigger.data.local.dao.CocktailIngredientDao;
import it.faustobe.jigger.data.local.dao.CocktailIngredientDao_Impl;
import it.faustobe.jigger.data.local.dao.IngredientDao;
import it.faustobe.jigger.data.local.dao.IngredientDao_Impl;
import it.faustobe.jigger.data.local.dao.ShiftCocktailDao;
import it.faustobe.jigger.data.local.dao.ShiftCocktailDao_Impl;
import it.faustobe.jigger.data.local.dao.ShiftDao;
import it.faustobe.jigger.data.local.dao.ShiftDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ShiftDao _shiftDao;

  private volatile CocktailDao _cocktailDao;

  private volatile IngredientDao _ingredientDao;

  private volatile ShiftCocktailDao _shiftCocktailDao;

  private volatile CocktailIngredientDao _cocktailIngredientDao;

  private volatile BartenderDao _bartenderDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `shifts` (`id` TEXT NOT NULL, `bartenderId` TEXT, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `status` TEXT NOT NULL, `notes` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cocktails` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `glassType` TEXT NOT NULL, `imageUrl` TEXT, `isCustom` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `ingredients` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `unit` TEXT NOT NULL, `currentStock` REAL NOT NULL, `minThreshold` REAL NOT NULL, `costPerUnit` REAL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cocktail_ingredients` (`cocktailId` TEXT NOT NULL, `ingredientId` TEXT NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, PRIMARY KEY(`cocktailId`, `ingredientId`), FOREIGN KEY(`cocktailId`) REFERENCES `cocktails`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`ingredientId`) REFERENCES `ingredients`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cocktail_ingredients_cocktailId` ON `cocktail_ingredients` (`cocktailId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cocktail_ingredients_ingredientId` ON `cocktail_ingredients` (`ingredientId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `shift_cocktails` (`id` TEXT NOT NULL, `shiftId` TEXT NOT NULL, `cocktailId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `quantity` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`shiftId`) REFERENCES `shifts`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`cocktailId`) REFERENCES `cocktails`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_shift_cocktails_shiftId` ON `shift_cocktails` (`shiftId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_shift_cocktails_cocktailId` ON `shift_cocktails` (`cocktailId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `bartenders` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7d19b2091666f400f764765632ac6285')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `shifts`");
        db.execSQL("DROP TABLE IF EXISTS `cocktails`");
        db.execSQL("DROP TABLE IF EXISTS `ingredients`");
        db.execSQL("DROP TABLE IF EXISTS `cocktail_ingredients`");
        db.execSQL("DROP TABLE IF EXISTS `shift_cocktails`");
        db.execSQL("DROP TABLE IF EXISTS `bartenders`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsShifts = new HashMap<String, TableInfo.Column>(6);
        _columnsShifts.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("bartenderId", new TableInfo.Column("bartenderId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysShifts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesShifts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoShifts = new TableInfo("shifts", _columnsShifts, _foreignKeysShifts, _indicesShifts);
        final TableInfo _existingShifts = TableInfo.read(db, "shifts");
        if (!_infoShifts.equals(_existingShifts)) {
          return new RoomOpenHelper.ValidationResult(false, "shifts(it.faustobe.jigger.data.local.entities.Shift).\n"
                  + " Expected:\n" + _infoShifts + "\n"
                  + " Found:\n" + _existingShifts);
        }
        final HashMap<String, TableInfo.Column> _columnsCocktails = new HashMap<String, TableInfo.Column>(7);
        _columnsCocktails.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktails.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktails.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktails.put("glassType", new TableInfo.Column("glassType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktails.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktails.put("isCustom", new TableInfo.Column("isCustom", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktails.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCocktails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCocktails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCocktails = new TableInfo("cocktails", _columnsCocktails, _foreignKeysCocktails, _indicesCocktails);
        final TableInfo _existingCocktails = TableInfo.read(db, "cocktails");
        if (!_infoCocktails.equals(_existingCocktails)) {
          return new RoomOpenHelper.ValidationResult(false, "cocktails(it.faustobe.jigger.data.local.entities.Cocktail).\n"
                  + " Expected:\n" + _infoCocktails + "\n"
                  + " Found:\n" + _existingCocktails);
        }
        final HashMap<String, TableInfo.Column> _columnsIngredients = new HashMap<String, TableInfo.Column>(7);
        _columnsIngredients.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredients.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredients.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredients.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredients.put("currentStock", new TableInfo.Column("currentStock", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredients.put("minThreshold", new TableInfo.Column("minThreshold", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIngredients.put("costPerUnit", new TableInfo.Column("costPerUnit", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIngredients = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIngredients = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIngredients = new TableInfo("ingredients", _columnsIngredients, _foreignKeysIngredients, _indicesIngredients);
        final TableInfo _existingIngredients = TableInfo.read(db, "ingredients");
        if (!_infoIngredients.equals(_existingIngredients)) {
          return new RoomOpenHelper.ValidationResult(false, "ingredients(it.faustobe.jigger.data.local.entities.Ingredient).\n"
                  + " Expected:\n" + _infoIngredients + "\n"
                  + " Found:\n" + _existingIngredients);
        }
        final HashMap<String, TableInfo.Column> _columnsCocktailIngredients = new HashMap<String, TableInfo.Column>(4);
        _columnsCocktailIngredients.put("cocktailId", new TableInfo.Column("cocktailId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktailIngredients.put("ingredientId", new TableInfo.Column("ingredientId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktailIngredients.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCocktailIngredients.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCocktailIngredients = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysCocktailIngredients.add(new TableInfo.ForeignKey("cocktails", "CASCADE", "NO ACTION", Arrays.asList("cocktailId"), Arrays.asList("id")));
        _foreignKeysCocktailIngredients.add(new TableInfo.ForeignKey("ingredients", "CASCADE", "NO ACTION", Arrays.asList("ingredientId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCocktailIngredients = new HashSet<TableInfo.Index>(2);
        _indicesCocktailIngredients.add(new TableInfo.Index("index_cocktail_ingredients_cocktailId", false, Arrays.asList("cocktailId"), Arrays.asList("ASC")));
        _indicesCocktailIngredients.add(new TableInfo.Index("index_cocktail_ingredients_ingredientId", false, Arrays.asList("ingredientId"), Arrays.asList("ASC")));
        final TableInfo _infoCocktailIngredients = new TableInfo("cocktail_ingredients", _columnsCocktailIngredients, _foreignKeysCocktailIngredients, _indicesCocktailIngredients);
        final TableInfo _existingCocktailIngredients = TableInfo.read(db, "cocktail_ingredients");
        if (!_infoCocktailIngredients.equals(_existingCocktailIngredients)) {
          return new RoomOpenHelper.ValidationResult(false, "cocktail_ingredients(it.faustobe.jigger.data.local.entities.CocktailIngredient).\n"
                  + " Expected:\n" + _infoCocktailIngredients + "\n"
                  + " Found:\n" + _existingCocktailIngredients);
        }
        final HashMap<String, TableInfo.Column> _columnsShiftCocktails = new HashMap<String, TableInfo.Column>(5);
        _columnsShiftCocktails.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShiftCocktails.put("shiftId", new TableInfo.Column("shiftId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShiftCocktails.put("cocktailId", new TableInfo.Column("cocktailId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShiftCocktails.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShiftCocktails.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysShiftCocktails = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysShiftCocktails.add(new TableInfo.ForeignKey("shifts", "CASCADE", "NO ACTION", Arrays.asList("shiftId"), Arrays.asList("id")));
        _foreignKeysShiftCocktails.add(new TableInfo.ForeignKey("cocktails", "CASCADE", "NO ACTION", Arrays.asList("cocktailId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesShiftCocktails = new HashSet<TableInfo.Index>(2);
        _indicesShiftCocktails.add(new TableInfo.Index("index_shift_cocktails_shiftId", false, Arrays.asList("shiftId"), Arrays.asList("ASC")));
        _indicesShiftCocktails.add(new TableInfo.Index("index_shift_cocktails_cocktailId", false, Arrays.asList("cocktailId"), Arrays.asList("ASC")));
        final TableInfo _infoShiftCocktails = new TableInfo("shift_cocktails", _columnsShiftCocktails, _foreignKeysShiftCocktails, _indicesShiftCocktails);
        final TableInfo _existingShiftCocktails = TableInfo.read(db, "shift_cocktails");
        if (!_infoShiftCocktails.equals(_existingShiftCocktails)) {
          return new RoomOpenHelper.ValidationResult(false, "shift_cocktails(it.faustobe.jigger.data.local.entities.ShiftCocktail).\n"
                  + " Expected:\n" + _infoShiftCocktails + "\n"
                  + " Found:\n" + _existingShiftCocktails);
        }
        final HashMap<String, TableInfo.Column> _columnsBartenders = new HashMap<String, TableInfo.Column>(3);
        _columnsBartenders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBartenders.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBartenders.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBartenders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBartenders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBartenders = new TableInfo("bartenders", _columnsBartenders, _foreignKeysBartenders, _indicesBartenders);
        final TableInfo _existingBartenders = TableInfo.read(db, "bartenders");
        if (!_infoBartenders.equals(_existingBartenders)) {
          return new RoomOpenHelper.ValidationResult(false, "bartenders(it.faustobe.jigger.data.local.entities.Bartender).\n"
                  + " Expected:\n" + _infoBartenders + "\n"
                  + " Found:\n" + _existingBartenders);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7d19b2091666f400f764765632ac6285", "978494f21f1fca9b7052d68daae021c0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "shifts","cocktails","ingredients","cocktail_ingredients","shift_cocktails","bartenders");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `shifts`");
      _db.execSQL("DELETE FROM `cocktails`");
      _db.execSQL("DELETE FROM `ingredients`");
      _db.execSQL("DELETE FROM `cocktail_ingredients`");
      _db.execSQL("DELETE FROM `shift_cocktails`");
      _db.execSQL("DELETE FROM `bartenders`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ShiftDao.class, ShiftDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CocktailDao.class, CocktailDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(IngredientDao.class, IngredientDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ShiftCocktailDao.class, ShiftCocktailDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CocktailIngredientDao.class, CocktailIngredientDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BartenderDao.class, BartenderDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ShiftDao shiftDao() {
    if (_shiftDao != null) {
      return _shiftDao;
    } else {
      synchronized(this) {
        if(_shiftDao == null) {
          _shiftDao = new ShiftDao_Impl(this);
        }
        return _shiftDao;
      }
    }
  }

  @Override
  public CocktailDao cocktailDao() {
    if (_cocktailDao != null) {
      return _cocktailDao;
    } else {
      synchronized(this) {
        if(_cocktailDao == null) {
          _cocktailDao = new CocktailDao_Impl(this);
        }
        return _cocktailDao;
      }
    }
  }

  @Override
  public IngredientDao ingredientDao() {
    if (_ingredientDao != null) {
      return _ingredientDao;
    } else {
      synchronized(this) {
        if(_ingredientDao == null) {
          _ingredientDao = new IngredientDao_Impl(this);
        }
        return _ingredientDao;
      }
    }
  }

  @Override
  public ShiftCocktailDao shiftCocktailDao() {
    if (_shiftCocktailDao != null) {
      return _shiftCocktailDao;
    } else {
      synchronized(this) {
        if(_shiftCocktailDao == null) {
          _shiftCocktailDao = new ShiftCocktailDao_Impl(this);
        }
        return _shiftCocktailDao;
      }
    }
  }

  @Override
  public CocktailIngredientDao cocktailIngredientDao() {
    if (_cocktailIngredientDao != null) {
      return _cocktailIngredientDao;
    } else {
      synchronized(this) {
        if(_cocktailIngredientDao == null) {
          _cocktailIngredientDao = new CocktailIngredientDao_Impl(this);
        }
        return _cocktailIngredientDao;
      }
    }
  }

  @Override
  public BartenderDao bartenderDao() {
    if (_bartenderDao != null) {
      return _bartenderDao;
    } else {
      synchronized(this) {
        if(_bartenderDao == null) {
          _bartenderDao = new BartenderDao_Impl(this);
        }
        return _bartenderDao;
      }
    }
  }
}
