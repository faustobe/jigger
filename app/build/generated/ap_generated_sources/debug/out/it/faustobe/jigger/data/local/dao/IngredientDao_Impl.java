package it.faustobe.jigger.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import it.faustobe.jigger.data.local.entities.Ingredient;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class IngredientDao_Impl implements IngredientDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Ingredient> __insertionAdapterOfIngredient;

  private final EntityDeletionOrUpdateAdapter<Ingredient> __updateAdapterOfIngredient;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public IngredientDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfIngredient = new EntityInsertionAdapter<Ingredient>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `ingredients` (`id`,`name`,`type`,`unit`,`currentStock`,`minThreshold`,`costPerUnit`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Ingredient entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getType());
        }
        if (entity.getUnit() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUnit());
        }
        statement.bindDouble(5, entity.getCurrentStock());
        statement.bindDouble(6, entity.getMinThreshold());
        if (entity.getCostPerUnit() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getCostPerUnit());
        }
      }
    };
    this.__updateAdapterOfIngredient = new EntityDeletionOrUpdateAdapter<Ingredient>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `ingredients` SET `id` = ?,`name` = ?,`type` = ?,`unit` = ?,`currentStock` = ?,`minThreshold` = ?,`costPerUnit` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Ingredient entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getType());
        }
        if (entity.getUnit() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUnit());
        }
        statement.bindDouble(5, entity.getCurrentStock());
        statement.bindDouble(6, entity.getMinThreshold());
        if (entity.getCostPerUnit() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getCostPerUnit());
        }
        if (entity.getId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM ingredients WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Ingredient ingredient) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfIngredient.insert(ingredient);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Ingredient> ingredients) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfIngredient.insert(ingredients);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Ingredient ingredient) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfIngredient.handle(ingredient);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final String ingredientId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    if (ingredientId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, ingredientId);
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
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public List<Ingredient> getAllIngredients() {
    final String _sql = "SELECT * FROM ingredients ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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
  public LiveData<List<Ingredient>> getAllIngredientsLive() {
    final String _sql = "SELECT * FROM ingredients ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"ingredients"}, false, new Callable<List<Ingredient>>() {
      @Override
      @Nullable
      public List<Ingredient> call() throws Exception {
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Ingredient> getLowStockIngredients() {
    final String _sql = "SELECT * FROM ingredients WHERE currentStock <= minThreshold ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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
  public LiveData<List<Ingredient>> getLowStockIngredientsLive() {
    final String _sql = "SELECT * FROM ingredients WHERE currentStock <= minThreshold ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"ingredients"}, false, new Callable<List<Ingredient>>() {
      @Override
      @Nullable
      public List<Ingredient> call() throws Exception {
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Ingredient getIngredientById(final String ingredientId) {
    final String _sql = "SELECT * FROM ingredients WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ingredientId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ingredientId);
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
      final Ingredient _result;
      if (_cursor.moveToFirst()) {
        _result = new Ingredient();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpType;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmpType = null;
        } else {
          _tmpType = _cursor.getString(_cursorIndexOfType);
        }
        _result.setType(_tmpType);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        _result.setUnit(_tmpUnit);
        final float _tmpCurrentStock;
        _tmpCurrentStock = _cursor.getFloat(_cursorIndexOfCurrentStock);
        _result.setCurrentStock(_tmpCurrentStock);
        final float _tmpMinThreshold;
        _tmpMinThreshold = _cursor.getFloat(_cursorIndexOfMinThreshold);
        _result.setMinThreshold(_tmpMinThreshold);
        final Float _tmpCostPerUnit;
        if (_cursor.isNull(_cursorIndexOfCostPerUnit)) {
          _tmpCostPerUnit = null;
        } else {
          _tmpCostPerUnit = _cursor.getFloat(_cursorIndexOfCostPerUnit);
        }
        _result.setCostPerUnit(_tmpCostPerUnit);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Ingredient> getIngredientsByType(final String type) {
    final String _sql = "SELECT * FROM ingredients WHERE type = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
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
  public int getIngredientCount() {
    final String _sql = "SELECT COUNT(*) FROM ingredients";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
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
