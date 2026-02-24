package it.faustobe.jigger.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import it.faustobe.jigger.data.local.entities.CocktailIngredient;
import it.faustobe.jigger.data.local.entities.Ingredient;
import java.lang.Class;
import java.lang.Float;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CocktailIngredientDao_Impl implements CocktailIngredientDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CocktailIngredient> __insertionAdapterOfCocktailIngredient;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByCocktailId;

  public CocktailIngredientDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCocktailIngredient = new EntityInsertionAdapter<CocktailIngredient>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cocktail_ingredients` (`cocktailId`,`ingredientId`,`quantity`,`unit`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CocktailIngredient entity) {
        if (entity.getCocktailId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getCocktailId());
        }
        if (entity.getIngredientId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getIngredientId());
        }
        statement.bindDouble(3, entity.getQuantity());
        if (entity.getUnit() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUnit());
        }
      }
    };
    this.__preparedStmtOfDeleteByCocktailId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cocktail_ingredients WHERE cocktailId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final CocktailIngredient cocktailIngredient) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCocktailIngredient.insert(cocktailIngredient);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<CocktailIngredient> cocktailIngredients) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCocktailIngredient.insert(cocktailIngredients);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteByCocktailId(final String cocktailId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByCocktailId.acquire();
    int _argIndex = 1;
    if (cocktailId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, cocktailId);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteByCocktailId.release(_stmt);
    }
  }

  @Override
  public List<Ingredient> getIngredientsForCocktail(final String cocktailId) {
    final String _sql = "SELECT i.* FROM ingredients i INNER JOIN cocktail_ingredients ci ON i.id = ci.ingredientId WHERE ci.cocktailId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (cocktailId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, cocktailId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfCurrentStock = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStock");
      final int _cursorIndexOfMinThreshold = CursorUtil.getColumnIndexOrThrow(_cursor, "minThreshold");
      final int _cursorIndexOfCostPerUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "costPerUnit");
      final List<Ingredient> _result = new ArrayList<Ingredient>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Ingredient _item;
        _item = new Ingredient();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpType;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmpType = null;
        } else {
          _tmpType = _cursor.getString(_cursorIndexOfType);
        }
        _item.setType(_tmpType);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        _item.setUnit(_tmpUnit);
        final float _tmpCurrentStock;
        _tmpCurrentStock = _cursor.getFloat(_cursorIndexOfCurrentStock);
        _item.setCurrentStock(_tmpCurrentStock);
        final float _tmpMinThreshold;
        _tmpMinThreshold = _cursor.getFloat(_cursorIndexOfMinThreshold);
        _item.setMinThreshold(_tmpMinThreshold);
        final Float _tmpCostPerUnit;
        if (_cursor.isNull(_cursorIndexOfCostPerUnit)) {
          _tmpCostPerUnit = null;
        } else {
          _tmpCostPerUnit = _cursor.getFloat(_cursorIndexOfCostPerUnit);
        }
        _item.setCostPerUnit(_tmpCostPerUnit);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<CocktailIngredient> getCocktailIngredients(final String cocktailId) {
    final String _sql = "SELECT * FROM cocktail_ingredients WHERE cocktailId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (cocktailId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, cocktailId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCocktailId = CursorUtil.getColumnIndexOrThrow(_cursor, "cocktailId");
      final int _cursorIndexOfIngredientId = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredientId");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final List<CocktailIngredient> _result = new ArrayList<CocktailIngredient>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CocktailIngredient _item;
        _item = new CocktailIngredient();
        final String _tmpCocktailId;
        if (_cursor.isNull(_cursorIndexOfCocktailId)) {
          _tmpCocktailId = null;
        } else {
          _tmpCocktailId = _cursor.getString(_cursorIndexOfCocktailId);
        }
        _item.setCocktailId(_tmpCocktailId);
        final String _tmpIngredientId;
        if (_cursor.isNull(_cursorIndexOfIngredientId)) {
          _tmpIngredientId = null;
        } else {
          _tmpIngredientId = _cursor.getString(_cursorIndexOfIngredientId);
        }
        _item.setIngredientId(_tmpIngredientId);
        final float _tmpQuantity;
        _tmpQuantity = _cursor.getFloat(_cursorIndexOfQuantity);
        _item.setQuantity(_tmpQuantity);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        _item.setUnit(_tmpUnit);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
