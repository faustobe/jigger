package it.faustobe.jigger.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import it.faustobe.jigger.data.local.entities.Cocktail;
import it.faustobe.jigger.data.local.entities.ShiftCocktail;
import it.faustobe.jigger.data.models.CocktailWithCount;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ShiftCocktailDao_Impl implements ShiftCocktailDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ShiftCocktail> __insertionAdapterOfShiftCocktail;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLastCocktailEntry;

  public ShiftCocktailDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfShiftCocktail = new EntityInsertionAdapter<ShiftCocktail>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `shift_cocktails` (`id`,`shiftId`,`cocktailId`,`timestamp`,`quantity`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ShiftCocktail entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getShiftId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getShiftId());
        }
        if (entity.getCocktailId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCocktailId());
        }
        statement.bindLong(4, entity.getTimestamp());
        statement.bindLong(5, entity.getQuantity());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM shift_cocktails WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteLastCocktailEntry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM shift_cocktails WHERE shiftId = ? AND cocktailId = ? AND id = (SELECT id FROM shift_cocktails WHERE shiftId = ? AND cocktailId = ? ORDER BY timestamp DESC LIMIT 1)";
        return _query;
      }
    };
  }

  @Override
  public void insert(final ShiftCocktail shiftCocktail) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfShiftCocktail.insert(shiftCocktail);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final String shiftCocktailId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    if (shiftCocktailId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, shiftCocktailId);
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
  public void deleteLastCocktailEntry(final String shiftId, final String cocktailId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLastCocktailEntry.acquire();
    int _argIndex = 1;
    if (shiftId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, shiftId);
    }
    _argIndex = 2;
    if (cocktailId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, cocktailId);
    }
    _argIndex = 3;
    if (shiftId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, shiftId);
    }
    _argIndex = 4;
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
      __preparedStmtOfDeleteLastCocktailEntry.release(_stmt);
    }
  }

  @Override
  public List<ShiftCocktail> getCocktailsByShift(final String shiftId) {
    final String _sql = "SELECT * FROM shift_cocktails WHERE shiftId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (shiftId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, shiftId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfShiftId = CursorUtil.getColumnIndexOrThrow(_cursor, "shiftId");
      final int _cursorIndexOfCocktailId = CursorUtil.getColumnIndexOrThrow(_cursor, "cocktailId");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final List<ShiftCocktail> _result = new ArrayList<ShiftCocktail>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ShiftCocktail _item;
        _item = new ShiftCocktail();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpShiftId;
        if (_cursor.isNull(_cursorIndexOfShiftId)) {
          _tmpShiftId = null;
        } else {
          _tmpShiftId = _cursor.getString(_cursorIndexOfShiftId);
        }
        _item.setShiftId(_tmpShiftId);
        final String _tmpCocktailId;
        if (_cursor.isNull(_cursorIndexOfCocktailId)) {
          _tmpCocktailId = null;
        } else {
          _tmpCocktailId = _cursor.getString(_cursorIndexOfCocktailId);
        }
        _item.setCocktailId(_tmpCocktailId);
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _item.setTimestamp(_tmpTimestamp);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        _item.setQuantity(_tmpQuantity);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ShiftCocktail>> getCocktailsByShiftLive(final String shiftId) {
    final String _sql = "SELECT * FROM shift_cocktails WHERE shiftId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (shiftId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, shiftId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"shift_cocktails"}, false, new Callable<List<ShiftCocktail>>() {
      @Override
      @Nullable
      public List<ShiftCocktail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfShiftId = CursorUtil.getColumnIndexOrThrow(_cursor, "shiftId");
          final int _cursorIndexOfCocktailId = CursorUtil.getColumnIndexOrThrow(_cursor, "cocktailId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final List<ShiftCocktail> _result = new ArrayList<ShiftCocktail>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShiftCocktail _item;
            _item = new ShiftCocktail();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpShiftId;
            if (_cursor.isNull(_cursorIndexOfShiftId)) {
              _tmpShiftId = null;
            } else {
              _tmpShiftId = _cursor.getString(_cursorIndexOfShiftId);
            }
            _item.setShiftId(_tmpShiftId);
            final String _tmpCocktailId;
            if (_cursor.isNull(_cursorIndexOfCocktailId)) {
              _tmpCocktailId = null;
            } else {
              _tmpCocktailId = _cursor.getString(_cursorIndexOfCocktailId);
            }
            _item.setCocktailId(_tmpCocktailId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item.setTimestamp(_tmpTimestamp);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            _item.setQuantity(_tmpQuantity);
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
  public List<CocktailWithCount> getCocktailCountsForShift(final String shiftId) {
    final String _sql = "SELECT c.*, COUNT(sc.id) as count FROM cocktails c INNER JOIN shift_cocktails sc ON c.id = sc.cocktailId WHERE sc.shiftId = ? GROUP BY c.id ORDER BY count DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (shiftId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, shiftId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final int _cursorIndexOfCount = CursorUtil.getColumnIndexOrThrow(_cursor, "count");
      final List<CocktailWithCount> _result = new ArrayList<CocktailWithCount>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CocktailWithCount _item;
        final Cocktail _tmpCocktail;
        if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfCategory) && _cursor.isNull(_cursorIndexOfGlassType) && _cursor.isNull(_cursorIndexOfImageUrl) && _cursor.isNull(_cursorIndexOfIsCustom) && _cursor.isNull(_cursorIndexOfIsFavorite))) {
          _tmpCocktail = new Cocktail();
          final String _tmpId;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _cursor.getString(_cursorIndexOfId);
          }
          _tmpCocktail.setId(_tmpId);
          final String _tmpName;
          if (_cursor.isNull(_cursorIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _cursor.getString(_cursorIndexOfName);
          }
          _tmpCocktail.setName(_tmpName);
          final String _tmpCategory;
          if (_cursor.isNull(_cursorIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
          }
          _tmpCocktail.setCategory(_tmpCategory);
          final String _tmpGlassType;
          if (_cursor.isNull(_cursorIndexOfGlassType)) {
            _tmpGlassType = null;
          } else {
            _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
          }
          _tmpCocktail.setGlassType(_tmpGlassType);
          final String _tmpImageUrl;
          if (_cursor.isNull(_cursorIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
          }
          _tmpCocktail.setImageUrl(_tmpImageUrl);
          final boolean _tmpIsCustom;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
          _tmpIsCustom = _tmp != 0;
          _tmpCocktail.setCustom(_tmpIsCustom);
          final boolean _tmpIsFavorite;
          final int _tmp_1;
          _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
          _tmpIsFavorite = _tmp_1 != 0;
          _tmpCocktail.setFavorite(_tmpIsFavorite);
        } else {
          _tmpCocktail = null;
        }
        _item = new CocktailWithCount();
        final int _tmpCount;
        _tmpCount = _cursor.getInt(_cursorIndexOfCount);
        _item.setCount(_tmpCount);
        _item.setCocktail(_tmpCocktail);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<CocktailWithCount>> getCocktailCountsForShiftLive(final String shiftId) {
    final String _sql = "SELECT c.*, COUNT(sc.id) as count FROM cocktails c INNER JOIN shift_cocktails sc ON c.id = sc.cocktailId WHERE sc.shiftId = ? GROUP BY c.id ORDER BY count DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (shiftId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, shiftId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"cocktails",
        "shift_cocktails"}, false, new Callable<List<CocktailWithCount>>() {
      @Override
      @Nullable
      public List<CocktailWithCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfCount = CursorUtil.getColumnIndexOrThrow(_cursor, "count");
          final List<CocktailWithCount> _result = new ArrayList<CocktailWithCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CocktailWithCount _item;
            final Cocktail _tmpCocktail;
            if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfCategory) && _cursor.isNull(_cursorIndexOfGlassType) && _cursor.isNull(_cursorIndexOfImageUrl) && _cursor.isNull(_cursorIndexOfIsCustom) && _cursor.isNull(_cursorIndexOfIsFavorite))) {
              _tmpCocktail = new Cocktail();
              final String _tmpId;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpId = null;
              } else {
                _tmpId = _cursor.getString(_cursorIndexOfId);
              }
              _tmpCocktail.setId(_tmpId);
              final String _tmpName;
              if (_cursor.isNull(_cursorIndexOfName)) {
                _tmpName = null;
              } else {
                _tmpName = _cursor.getString(_cursorIndexOfName);
              }
              _tmpCocktail.setName(_tmpName);
              final String _tmpCategory;
              if (_cursor.isNull(_cursorIndexOfCategory)) {
                _tmpCategory = null;
              } else {
                _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
              }
              _tmpCocktail.setCategory(_tmpCategory);
              final String _tmpGlassType;
              if (_cursor.isNull(_cursorIndexOfGlassType)) {
                _tmpGlassType = null;
              } else {
                _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
              }
              _tmpCocktail.setGlassType(_tmpGlassType);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              _tmpCocktail.setImageUrl(_tmpImageUrl);
              final boolean _tmpIsCustom;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
              _tmpIsCustom = _tmp != 0;
              _tmpCocktail.setCustom(_tmpIsCustom);
              final boolean _tmpIsFavorite;
              final int _tmp_1;
              _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
              _tmpIsFavorite = _tmp_1 != 0;
              _tmpCocktail.setFavorite(_tmpIsFavorite);
            } else {
              _tmpCocktail = null;
            }
            _item = new CocktailWithCount();
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            _item.setCount(_tmpCount);
            _item.setCocktail(_tmpCocktail);
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
  public int getTotalCocktailsForShift(final String shiftId) {
    final String _sql = "SELECT COUNT(*) FROM shift_cocktails WHERE shiftId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (shiftId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, shiftId);
    }
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

  @Override
  public LiveData<Integer> getTotalCocktailsForShiftLive(final String shiftId) {
    final String _sql = "SELECT COUNT(*) FROM shift_cocktails WHERE shiftId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (shiftId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, shiftId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"shift_cocktails"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
